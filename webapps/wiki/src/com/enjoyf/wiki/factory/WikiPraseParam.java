package com.enjoyf.wiki.factory;

import com.enjoyf.wiki.container.PropertiesContainer;

import java.net.URLEncoder;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-2-6 下午4:57
 * Description:
 */
public class WikiPraseParam {
    private String key;
    private String domain;
    private String path;
    private String fullUrl;
    private String saveFileName;
    private int isIndex;
    private String wikiType;
    private String pageInfo;//数据库中的值或者seo的title;
    private String channel;
    private boolean supportSubdomain;
    private String wikiPageId;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public int getIndex() {
        return isIndex;
    }

    public void setIndex(int index) {
        isIndex = index;
    }

    public String getWikiType() {
        return wikiType;
    }

    public void setWikiType(String wikiType) {
        this.wikiType = wikiType;
    }

    public String getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(String pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(int isIndex) {
        this.isIndex = isIndex;
    }

    public boolean isSupportSubdomain() {
        return supportSubdomain;
    }

    public void setSupportSubdomain(boolean supportSubdomain) {
        this.supportSubdomain = supportSubdomain;
    }

    public String getWikiPageId() {
        return wikiPageId;
    }

    public void setWikiPageId(String wikiPageId) {
        this.wikiPageId = wikiPageId;
    }
}
