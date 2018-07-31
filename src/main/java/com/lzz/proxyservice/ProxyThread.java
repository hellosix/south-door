package com.lzz.proxyservice;

import com.lzz.model.ProxyModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by lzz on 2018/2/2.
 */

public class ProxyThread implements Runnable{
    private transient static Log logger = LogFactory.getLog(ProxyThread.class);
    private ProxyModel proxyModel;
    private ServerSocket serverSocket;
    private ProxySwitch threadSwitch = new ProxySwitch();
    private boolean isTemp = false;

    public ProxyThread(ProxyModel proxyModel, boolean threadStart){
        this.proxyModel = proxyModel;
        this.threadSwitch.setStart( threadStart );
    }

    public ProxyThread(ProxyModel proxyModel, boolean threadStart, boolean isTemp){
        this.proxyModel = proxyModel;
        this.threadSwitch.setStart( threadStart );
        this.isTemp = isTemp;
    }

    @Override
    public void run() {
        try {

            int proxyPort = this.proxyModel.getProxyPort();
            String remoteIp = this.proxyModel.getIp();
            int remotePort = this.proxyModel.getPort();
            serverSocket = new ServerSocket(proxyPort);

            logger.info("proxyPort="+proxyPort + ";remoteIp=" + remoteIp + ";remotePort="+remotePort);
            int timeoutCount = 0;
            while( threadSwitch.isStart() == true ){
                logger.info("while true----------" + proxyPort);
                Socket clientSocket = null;
                Socket remoteServerSocket = null;
                try {
                    if( isTemp ){
                        serverSocket.setSoTimeout( 60000 );
                    }
                    clientSocket = serverSocket.accept();
                    timeoutCount = 0;
                    logger.info("accept one client");
                    remoteServerSocket = new Socket(remoteIp ,remotePort);
                    logger.info("create remoteip and port success proxyport " + proxyPort);
                    clientToRemote(clientSocket, remoteServerSocket);
                    remoteToClient(remoteServerSocket, clientSocket);
                } catch (SocketTimeoutException socketTimeoutException) {
                    if( isTemp ){
                        timeoutCount++;
                        System.out.println( timeoutCount + "----;;;");
                        if( timeoutCount > 5 ){
                            threadSwitch.setStart( false );
                            String host = remoteIp + ":" + remotePort;
                            ProxyManager.tmpProxyMap.remove( host );
                        }
                    }
                }catch (Exception e){
                    logger.error( e );
                }
            }
        } catch (Exception e) {
            logger.error( "ProxyThread", e );
        }finally {
            try {
                if( !serverSocket.isClosed() ){
                    serverSocket.close();
                }
            } catch (IOException e) {
                logger.error( e );
            }
            logger.info("stop-----------port: " + proxyModel.getProxyPort());
        }
    }

    private void clientToRemote(Socket clientSocket, Socket remoteServerSocket) {
        Runnable clientToRemoteThread = new TransDataTask(clientSocket, remoteServerSocket , this.threadSwitch);
        ProxyManager.threadPool.submit( clientToRemoteThread );
        logger.info( "client to remote is start......" );
    }

    private void remoteToClient(Socket remoteServerSocket, Socket clientSocket) {
        Runnable remoteToClientThread = new TransDataTask(remoteServerSocket ,clientSocket, this.threadSwitch);
        ProxyManager.threadPool.submit( remoteToClientThread );
        logger.info( "remote to client is start......" );
    }

    public void stop() throws IOException {
        this.serverSocket.close();
        this.threadSwitch.setStart(false);
    }

}
