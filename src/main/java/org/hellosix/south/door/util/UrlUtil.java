package org.hellosix.south.door.util;

import javafx.util.Pair;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
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
        }
        return new Pair<>(ip, port);
    }

    /**
     * eg:
     * http://127.0.0.1:8000/group/groupId -> http://127.0.0.1:8000/
     *
     * @param address
     * @return
     */
    public static String getBaseUrl(String address) {
        Pattern pattern = Pattern.compile("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+(:[0-9]+)?|(?:ww\u200C\u200Bw.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?\u200C\u200B(?:[\\w]*))?)");
        //pattern = Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
        Matcher matcher = pattern.matcher(address);
        String baseUrl = null;
        while (matcher.find()) {
            baseUrl = matcher.group(1);
        }
        return baseUrl;
    }

    public static String getProtocol(String address) {
        Pattern pattern = Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
        Matcher matcher = pattern.matcher(address);
        String protocol = null;
        while (matcher.find()) {
            protocol = matcher.group(1);
        }
        return protocol;
    }

    public static String getProxyAddress(String originAddress, int proxyPort) throws UnknownHostException {
        String originDomain = getBaseUrl(originAddress);
        String localIp = NetUtil.getLocalIp();
        String proxyDomain = getProtocol(originAddress) + "://" + localIp + ":" + proxyPort;
        return originAddress.replace(originDomain, proxyDomain);
    }


    public static void main(String[] args) throws MalformedURLException, UnknownHostException {
        String baseUrl = getBaseUrl("https://101.52.12.44:1234/getitadsfa/gwetgas/fsadf");
        System.out.println(baseUrl);

        String proxyAddress = getProxyAddress("https://101.52.12.44:1234/getitadsfa/gwetgas/fsadf", 5555);
        System.out.println(proxyAddress);
    }
}
