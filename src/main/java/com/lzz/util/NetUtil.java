package com.lzz.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * Created by lzz on 2018/2/5.
 */
public class NetUtil {
    public static String getLocalIp() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        return ip;
    }

    public static boolean checkUrl(String address){
        boolean res = false;
        URL url;
        try {
            url = new URL( address.trim() );
            InputStream in = url.openStream();
            res = true;
        } catch (Exception ignore) {
        }
        return res;
    }

    public static boolean checkPort(Integer port){
        boolean res = false;
        try {
            String ip = getLocalIp();
            res = checkIpAndPort(ip, port);
        } catch (Exception ignore) {

        }
        return res;
    }

    public static boolean checkIp(String ip){
        boolean res = false;
        try {
            res = InetAddress.getByName(ip).isReachable(5000);
        } catch (Exception ignore) {
        }
        return res;
    }

    public static boolean checkIpAndPort(String ip, Integer port){
        boolean res = false;
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip, port));
            res = true;
        } catch (IOException ignore) {

        } finally {
            try {
                socket.close();
            } catch (IOException ignore) {}
        }
        return res;
    }
}
