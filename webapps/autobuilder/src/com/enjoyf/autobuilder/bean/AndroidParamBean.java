package com.enjoyf.autobuilder.bean;

public class AndroidParamBean {
    // app名称
    private String appName = null;
    // top tile
    private String topTitle = null;
    // 关于信息
    private String aboutTitleTxt = null;
    // about_c_text
    private String aboutCText = null;
    // appkey
    private String appKey = null;

    //umengkey
    private String umengKey = null;

    // 源文件地址
    private String sourceFileFolder = null;
    // 打包程序中源文件的地址
    private String targetFileFolder = null;
    // 要替换的库名
    private String packageReplaceStr = null;
    // 被替换的库名的标记
    private String packageReplaceFlag = "framework";
    // res文件地址
    private String sourceResFolder = null;
    // 被替换的文件地址
    private String targetResFolder = null;
    // version信息
    private String versionCode = null;
    private String versionName = null;
    // 设置asset
    private String sourceAssertFolder = null;
    // 在线的链接 比如 http://sgzj.m.joyme.com/
    private String onlineDomain = null;
    private String betaDomain = null;
    private String notifyUrl = null;

    private String wikiHost = null;

    private String appNameFlag = null;
    private String appShortName = null;

    // 设置颜色
    private boolean updateHomeMenuColorActive = false;
    private String homeMenuColorActive = null;
    private boolean updateHomeMenuColorN = false;
    private String homeMenuColorN = null;
    private String host = null;
    // 图片host
    private String image_url_host = null;

    public String getImage_url_host() {
        return image_url_host;
    }

    public void setImage_url_host(String image_url_host) {
        this.image_url_host = image_url_host;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAppShortName() {
        return appShortName;
    }

    public void setAppShortName(String appShortName) {
        this.appShortName = appShortName;
    }

    public String getAppNameFlag() {
        return appNameFlag;
    }

    public void setAppNameFlag(String appNameFlag) {
        this.appNameFlag = appNameFlag;
    }

    public String getPackageReplaceFlag() {
        return packageReplaceFlag;
    }

    public void setPackageReplaceFlag(String packageReplaceFlag) {
        this.packageReplaceFlag = packageReplaceFlag;
    }

    public String getSourceFileFolder() {
        return sourceFileFolder;
    }

    public void setSourceFileFolder(String sourceFileFolder) {
        this.sourceFileFolder = sourceFileFolder;
    }

    public String getTargetFileFolder() {
        return targetFileFolder;
    }

    public void setTargetFileFolder(String targetFileFolder) {
        this.targetFileFolder = targetFileFolder;
    }

    public String getPackageReplaceStr() {
        return packageReplaceStr;
    }

    public void setPackageReplaceStr(String packageReplaceStr) {
        this.packageReplaceStr = packageReplaceStr;
    }

    public String getSourceResFolder() {
        return sourceResFolder;
    }

    public void setSourceResFolder(String sourceResFolder) {
        this.sourceResFolder = sourceResFolder;
    }

    public String getTargetResFolder() {
        return targetResFolder;
    }

    public void setTargetResFolder(String targetResFolder) {
        this.targetResFolder = targetResFolder;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }

    public String getAboutTitleTxt() {
        return aboutTitleTxt;
    }

    public void setAboutTitleTxt(String aboutTitleTxt) {
        this.aboutTitleTxt = aboutTitleTxt;
    }

    public String getAboutCText() {
        return aboutCText;
    }

    public void setAboutCText(String aboutCText) {
        this.aboutCText = aboutCText;
    }

    public String getSourceAssertFolder() {
        return sourceAssertFolder;
    }

    public void setSourceAssertFolder(String sourceAssertFolder) {
        this.sourceAssertFolder = sourceAssertFolder;
    }

    public String getOnlineDomain() {
        return onlineDomain;
    }

    public void setOnlineDomain(String onlineDomain) {
        this.onlineDomain = onlineDomain;
    }

    public String getBetaDomain() {
        return betaDomain;
    }

    public void setBetaDomain(String betaDomain) {
        this.betaDomain = betaDomain;
    }

    public String getHomeMenuColorActive() {
        return homeMenuColorActive;
    }

    public void setHomeMenuColorActive(String homeMenuColorActive) {
        this.homeMenuColorActive = homeMenuColorActive;
    }

    public String getHomeMenuColorN() {
        return homeMenuColorN;
    }

    public void setHomeMenuColorN(String homeMenuColorN) {
        this.homeMenuColorN = homeMenuColorN;
    }

    public boolean isUpdateHomeMenuColorActive() {
        return updateHomeMenuColorActive;
    }

    public void setUpdateHomeMenuColorActive(boolean updateHomeMenuColorActive) {
        this.updateHomeMenuColorActive = updateHomeMenuColorActive;
    }

    public boolean isUpdateHomeMenuColorN() {
        return updateHomeMenuColorN;
    }

    public void setUpdateHomeMenuColorN(boolean updateHomeMenuColorN) {
        this.updateHomeMenuColorN = updateHomeMenuColorN;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUmengKey() {
        return umengKey;
    }

    public void setUmengKey(String umengKey) {
        this.umengKey = umengKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getWikiHost() {
        return wikiHost;
    }

    public void setWikiHost(String wikiHost) {
        this.wikiHost = wikiHost;
    }
}
