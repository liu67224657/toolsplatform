package com.enjoyf.mcms.bean;

import java.util.HashMap;
import java.util.Map;

public class GameBean {
    private String gameName = "";
    private String gameIcon = "";
    private boolean isAndroid = false;
    private boolean isIPhone = false;
    private boolean isIpad = false;
    private String displayPlatform = "";
    private String gameType = "";
    private String factoryName = "";
    private String androidGameSize = "";
    private String iphoneGameSize = "";
    private String ipadGameSize = "";
    private String displayGameSize = "";
    private String updateTime = "";
    private String rate = "";
    private Map ipadDownLink = new HashMap();
    private Map iphoneDownLink = new HashMap();
    private Map androidDownLink = new HashMap();
    private String gamePublishDate = "";
    private String wikiUrl = "";
    private String clientWikiUrl = "";
    private String cmsUrl = "";
    private String clientCmsUrl = "";
    private String displayWikiCmsUrl = "";
    private String downloadRecommend;//下载推荐
    private String gameDbId;

    public boolean isAndroid() {
        return isAndroid;
    }

    public void setAndroid(boolean isAndroid) {
        this.isAndroid = isAndroid;
    }

    public boolean isIPhone() {
        return isIPhone;
    }

    public void setIPhone(boolean isIPhone) {
        this.isIPhone = isIPhone;
    }

    public boolean isIpad() {
        return isIpad;
    }

    public void setIpad(boolean isIpad) {
        this.isIpad = isIpad;
    }

    public String getGameType() {
        return gameType == null ? "" : gameType;
    }

    public String getGameType(int maxLength) {
        String str = this.getGameType();
        return (str.length() > maxLength) ? str.substring(0, maxLength) + ".." : str;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getFactoryName() {
        return factoryName == null ? "" : factoryName;
    }

    public String getFactoryName(int maxLength) {
        String str = this.getFactoryName();
        return (str.length() > maxLength) ? str.substring(0, maxLength) + ".." : str;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRate() {
        return rate == null ? "" : rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Map getAndroidDownLink() {
        return androidDownLink;
    }

    public void setAndroidDownLink(Map androidDownLink) {
        this.androidDownLink = androidDownLink;
    }

    public String getGameName() {
        return gameName == null ? "" : gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    public String getAndroidGameSize() {
        return androidGameSize;
    }

    public String getIphoneGameSize() {
        return iphoneGameSize;
    }

    public void setIphoneGameSize(String iphoneGameSize) {
        this.iphoneGameSize = iphoneGameSize;
    }

    public String getIpadGameSize() {
        return ipadGameSize;
    }

    public void setIpadGameSize(String ipadGameSize) {
        this.ipadGameSize = ipadGameSize;
    }

    public void setAndroidGameSize(String androidGameSize) {
        this.androidGameSize = androidGameSize;
    }

    public Map getIpadDownLink() {
        return ipadDownLink;
    }

    public void setIpadDownLink(Map ipadDownLink) {
        this.ipadDownLink = ipadDownLink;
    }

    public Map getIphoneDownLink() {
        return iphoneDownLink;
    }

    public void setIphoneDownLink(Map iphoneDownLink) {
        this.iphoneDownLink = iphoneDownLink;
    }

    public String getGamePublishDate() {
        return gamePublishDate;
    }

    public void setGamePublishDate(String gamePublishDate) {
        this.gamePublishDate = gamePublishDate;
    }

    public String getDisplayPlatform() {
        return displayPlatform;
    }

    public String getDisplayGameSize() {
        return displayGameSize;
    }

    public void setDisplayGameSize(String displayGameSize) {
        this.displayGameSize = displayGameSize;
    }

    public void setDisplayPlatform(String displayPlatform) {
        this.displayPlatform = displayPlatform;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public String getCmsUrl() {
        return cmsUrl;
    }

    public void setCmsUrl(String cmsUrl) {
        this.cmsUrl = cmsUrl;
    }

    public String getDisplayWikiCmsUrl() {
        return displayWikiCmsUrl;
    }

    public void setDisplayWikiCmsUrl(String displayWikiCmsUrl) {
        this.displayWikiCmsUrl = displayWikiCmsUrl;
    }

    public String getClientWikiUrl() {
        return clientWikiUrl;
    }

    public void setClientWikiUrl(String clientWikiUrl) {
        this.clientWikiUrl = clientWikiUrl;
    }

    public String getClientCmsUrl() {
        return clientCmsUrl;
    }

    public void setClientCmsUrl(String clientCmsUrl) {
        this.clientCmsUrl = clientCmsUrl;
    }

    public String getDownloadRecommend() {
        return downloadRecommend;
    }

    public void setDownloadRecommend(String downloadRecommend) {
        this.downloadRecommend = downloadRecommend;
    }

    public String getGameDbId() {
        return gameDbId;
    }

    public void setGameDbId(String gameDbId) {
        this.gameDbId = gameDbId;
    }
}
