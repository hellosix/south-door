package cn.hellosix.common.model;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Created by lzz on 2018/2/4.
 */
@Component
public class UrlModel {
    private String showName;
    private String url;
    private Integer proxyPort;
    private String group;
    private String describe;


    public UrlModel() {

    }

    public UrlModel(String showName, String url, Integer proxyPort, String group, String describe) {
        this.showName = showName;
        this.url = url;
        this.proxyPort = proxyPort;
        this.group = group;
        this.describe = describe;
    }

    public static UrlModel unSerializa(String str) {
        JSONObject jsonObject = JSONObject.fromObject(str);
        UrlModel urlModel = (UrlModel) JSONObject.toBean(jsonObject, UrlModel.class);
        return urlModel;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
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

    public String serializa() {
        return JSONObject.fromObject(this).toString();
    }
}
