package cn.hellosix.common.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by lzz on 2018/2/4.
 */
public class ProxyModel {
    private int proxyPort;
    private int port;
    private String ip;
    private String group;
    private String description;

    public ProxyModel() {}

    public ProxyModel(int proxyPort, int port, String ip) {
        this.proxyPort = proxyPort;
        this.port = port;
        this.ip = ip;
    }

    public ProxyModel(int proxyPort, int port, String ip, String group, String description) {
        this.proxyPort = proxyPort;
        this.port = port;
        this.ip = ip;
        this.group = group;
        this.description = description;
    }

    public static ProxyModel unSerialized(String str) {
        ProxyModel proxyModel = JSONObject.parseObject(str, ProxyModel.class);
        return proxyModel;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String serialized() {
        return JSONObject.toJSONString(this);
    }
}
