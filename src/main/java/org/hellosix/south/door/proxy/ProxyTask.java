package org.hellosix.south.door.proxy;

import javafx.util.Pair;
import org.hellosix.south.door.model.SiteInfo;
import org.hellosix.south.door.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @author lzz, Jay.H.Zou
 * @date 2019/6/25
 */
public class ProxyTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ProxyTask.class);

    private SiteInfo siteInfo;

    private boolean isStart;

    private ServerSocket serverSocket;

    private ExecutorService transDataThreadPool;

    // TODO: 可能有问题
    private TransDataTask clientToRemoteThread;

    private TransDataTask remoteToClientThread;

    public ProxyTask(ExecutorService transDataThreadPool, SiteInfo siteInfo, boolean isStart) {
        this.transDataThreadPool = transDataThreadPool;
        this.siteInfo = siteInfo;
        this.isStart = isStart;
    }

    @Override
    public void run() {
        if (siteInfo.getIsProxy()) {
            String address = siteInfo.getAddress();
            Integer proxyPort = siteInfo.getProxyPort();
            Pair<String, Integer> ipPort = UrlUtil.getIpPort(address);
            String originIp = ipPort.getKey();
            Integer originPort = ipPort.getValue();
            try {
                serverSocket = new ServerSocket(proxyPort);
                // logger.info("proxyPort=" + proxyPort + ";remoteIp=" + remoteIp + ";remotePort=" + remotePort);
                while (isStart) {
                    logger.info("start create proxy for " + address);
                    Socket clientSocket = null;
                    Socket remoteServerSocket = null;
                    try {
                        clientSocket = serverSocket.accept();
                        logger.info("accept one client");
                        remoteServerSocket = new Socket(originIp, originPort);
                        logger.info("create proxy for origin ip and port successfully, proxy port: " + proxyPort);
                        clientToRemote(clientSocket, remoteServerSocket);
                        remoteToClient(remoteServerSocket, clientSocket);
                    } catch (Exception e) {
                        logger.error("access origin address " + address + " failed", e);
                    }
                }
            } catch (IOException e2) {
                logger.error("create proxy port " + proxyPort + " for " + address + "failed", e2);
            }
        }
    }

    private void clientToRemote(Socket clientSocket, Socket remoteServerSocket) {
        clientToRemoteThread = new TransDataTask(clientSocket, remoteServerSocket, isStart);
        transDataThreadPool.submit(clientToRemoteThread);
        logger.info("client to remote is start......");
    }

    private void remoteToClient(Socket remoteServerSocket, Socket clientSocket) {
        remoteToClientThread = new TransDataTask(remoteServerSocket, clientSocket, isStart);
        transDataThreadPool.submit(remoteToClientThread);
        logger.info("remote to client is start......");
    }

    /**
     * stop thread before delete site
     *
     * @throws IOException
     */
    public void stop() throws IOException {
        serverSocket.close();
        clientToRemoteThread.stop();
        remoteToClientThread.stop();
        isStart = false;
    }
}
