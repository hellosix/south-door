package org.hellosix.south.door.model;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2019/6/24
 */
public class SiteInfo extends SiteGroup{

    private String siteId;

    private String siteName;

    private String address;

    private Boolean isProxy;

    private Integer proxyPort;

    private String imagePath;

    private String proxyAddress;

    private String description;

    private Timestamp updateTime;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Boolean isProxy) {
        this.isProxy = isProxy;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProxyAddress() {
        return proxyAddress;
    }

    public void setProxyAddress(String proxyAddress) {
        this.proxyAddress = proxyAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Site{" +
                "siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", address='" + address + '\'' +
                ", isProxy=" + isProxy +
                ", proxyPort=" + proxyPort +
                ", imagePath='" + imagePath + '\'' +
                ", proxyAddress='" + proxyAddress + '\'' +
                ", description='" + description + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
