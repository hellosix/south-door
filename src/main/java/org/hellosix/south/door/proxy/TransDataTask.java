package org.hellosix.south.door.proxy;

/**
 * Created by lzz on 2017/9/11.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class TransDataTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(TransDataTask.class);

    private Socket getDataSocket;

    private Socket putDataSocket;

    private boolean isStart;

    public TransDataTask(Socket getDataSocket, Socket putDataSocket, boolean isStart) {
        this.getDataSocket = getDataSocket;
        this.putDataSocket = putDataSocket;
        this.isStart = isStart;
    }

    @Override
    public void run() {
        try {
            while (isStart) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = getDataSocket.getInputStream();
                    out = putDataSocket.getOutputStream();
                    byte[] data = new byte[1024];
                    int readLen = in.read(data);
                    // 没有数据就直接退出
                    if (readLen <= 0) {
                        logger.warn("stop trans data break.....");
                        break;
                    }
                    out.write(data, 0, readLen);
                    out.flush();
                } catch (Exception e) {
                    logger.error("", e);
                    try {
                        in.close();
                        out.close();
                    } catch (IOException ex) {
                        logger.error("close input or out stream error.", e);
                    }
                    break;
                }
            }
        }  finally {
            logger.info("trans data port data operation");
            try {
                if (!putDataSocket.isOutputShutdown()) {
                    putDataSocket.shutdownOutput();
                }
                if (!getDataSocket.isInputShutdown()) {
                    getDataSocket.shutdownInput();
                }
            } catch (Exception e) {
                logger.error("shutdown socket error", e);
            }
        }
    }

}