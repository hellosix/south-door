package com.lzz.model;

/**
 * Created by lzz on 2018/3/4.
 */
public class ShowUrlModel {
    private UrlModel urlModel;
    private String domain;
    private String showUrl;

    public ShowUrlModel(UrlModel urlModel, String domain, String showUrl) {
        this.urlModel = urlModel;
        this.domain = domain;
        this.showUrl = showUrl;
    }

    public UrlModel getUrlModel() {
        return urlModel;
    }

    public void setUrlModel(UrlModel urlModel) {
        this.urlModel = urlModel;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }
}
