package com.enjoyf.webcache.bean;

import com.enjoyf.util.MD5Util;

import java.io.Serializable;

/**
 * Created by zhitaoshi on 2015/11/20.
 */
public class PageStat implements Serializable {

    private String statId;
    private String pageId;
    private WebCacheSrcType pageType;
    private int pcPv = 0;
    private int mPv = 0;
    private int wanbaPv = 0;
    private int pvSum = 0;
    private int replySum = 0;
    private int contentType = 0;

    public String getStatId() {
        return buildStatId(this.pageId, this.pageType);
    }

    public void setStatId(String statId) {
        this.statId = statId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public WebCacheSrcType getPageType() {
        return pageType;
    }

    public void setPageType(WebCacheSrcType pageType) {
        this.pageType = pageType;
    }

    public int getPcPv() {
        return pcPv;
    }

    public void setPcPv(int pcPv) {
        this.pcPv = pcPv;
    }

    public int getmPv() {
        return mPv;
    }

    public void setmPv(int mPv) {
        this.mPv = mPv;
    }

    public int getWanbaPv() {
        return wanbaPv;
    }

    public void setWanbaPv(int wanbaPv) {
        this.wanbaPv = wanbaPv;
    }

    public int getPvSum() {
        return pvSum;
    }

    public void setPvSum(int pvSum) {
        this.pvSum = pvSum;
    }

    public int getReplySum() {
        return replySum;
    }

    public void setReplySum(int replySum) {
        this.replySum = replySum;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public static String buildStatId(String pageId, WebCacheSrcType pageType) {
        return MD5Util.Md5(pageId+"|"+pageType.getCode());
    }
}
