package com.lzz.util;

import org.junit.Test;

/**
 * Created by lzz on 2018/2/5.
 */
public class NetUtilTest {
    @Test
    public void testCheckUrl(){
        String url = "http://baidu.com";
        System.out.println( NetUtil.checkUrl( url ) );
    }

    @Test
    public void testCheckPort(){
        int port = 8082;
        System.out.println( NetUtil.checkPort(port));
    }

    @Test
    public void testCheckIp(){
        String ip = "127.1.0.1";
        System.out.println( NetUtil.checkIp(ip));
    }

    @Test
    public void testCheckIpAndPort(){
        String ip = "127.0.0.1";
        int port = 8082;
        System.out.println( NetUtil.checkIpAndPort(ip, port));
    }
}
