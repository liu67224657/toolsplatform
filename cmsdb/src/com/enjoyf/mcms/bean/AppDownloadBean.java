package com.enjoyf.mcms.bean;

public class AppDownloadBean {
    private String appId;
    private String url;
    private String appName;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "AppDownloadBean{" +
                "appId='" + appId + '\'' +
                ", url='" + url + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }
}
