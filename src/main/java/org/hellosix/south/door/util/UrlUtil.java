package org.hellosix.south.door.util;

import javafx.util.Pair;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jay.H.Zou
 * @date 2019/6/25
 */
public class UrlUtil {

    private UrlUtil() {
    }

    public static final Pair<String, Integer> getIpPort(String address) {
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)");
        Matcher matcher = pattern.matcher(address);
        String ip = null;
        Integer port = null;
        //将符合规则的提取出来
        while (matcher.find()) {
            ip = matcher.group(1);
            port = Integer.valueOf(matcher.group(2));
            System.out.println("ip:" + ip);
            System.out.println("port:" + port);
        }
        return new Pair<>(ip, port);
    }

    public static void main(String[] args) {
        getIpPort("http://10.16.50.209:8888/getitadsfa/gwetgas/fsadf");
    }
}
