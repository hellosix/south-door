package com.lzz.model;

import net.sf.json.JSONObject;

/**
 * Created by lzz on 2018/2/4.
 */
public class ProxyModel {
    private int proxyPort;
    private int port;
    private String ip;
    private String group;
    private String describe;

    public ProxyModel(){

    }

    public ProxyModel(int proxyPort, int port, String ip) {
        this.proxyPort = proxyPort;
        this.port = port;
        this.ip = ip;
    }

    public ProxyModel(int proxyPort, int port, String ip, String group, String describe) {
        this.proxyPort = proxyPort;
        this.port = port;
        this.ip = ip;
        this.group = group;
        this.describe = describe;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String serializa(){
        return JSONObject.fromObject(this).toString();
    }

    public static ProxyModel unSerializa(String str){
        JSONObject jsonObject =  JSONObject.fromObject( str );
        ProxyModel proxyModel = (ProxyModel) JSONObject.toBean( jsonObject, ProxyModel.class);
        return  proxyModel;
    }
}
