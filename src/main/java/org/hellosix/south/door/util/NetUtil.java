package org.hellosix.south.door.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * Access ip, port, url
 *
 * @author lzz
 * @date 2018/2/5.
 */
public class NetUtil {

    private NetUtil() {
    }

    /**
     * 获取本机 IP
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getLocalIp() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        return ip;
    }

    /**
     * 检查 URL 是否可以正常访问
     *
     * @param address
     * @return
     */
    public static boolean accessUrl(String address) {
        boolean result = false;
        URL url;
        try {
            url = new URL(address.trim());
            InputStream in = url.openStream();
            result = true;
        } catch (Exception ignore) {
        }
        return result;
    }

    /**
     * 检测端口是否可用（被占用）
     *
     * @param port
     * @return
     */
    public static boolean accessPort(int port) {
        boolean result = false;
        try {
            String ip = getLocalIp();
            result = accessHost(ip, port);
        } catch (Exception ignore) {
        }
        return result;
    }

    /**
     * 检查是否能访问远程机器
     *
     * @param ip
     * @return
     */
    public static boolean pingIp(String ip) {
        boolean result = false;
        try {
            result = InetAddress.getByName(ip).isReachable(5000);
        } catch (Exception ignore) {

        }
        return result;
    }

    /**
     * 检查 host 是否能访问
     *
     * @param ip
     * @param port
     * @return
     */
    public static boolean accessHost(String ip, int port) {
        boolean result = false;
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip, port));
            result = true;
        } catch (IOException ignore) {
        } finally {
            try {
                socket.close();
            } catch (IOException ignore) {
            }
        }
        return result;
    }
}
