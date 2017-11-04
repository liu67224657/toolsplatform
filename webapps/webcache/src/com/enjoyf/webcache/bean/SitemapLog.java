package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhitaoshi on 2015/8/25.
 */
public class SitemapLog implements Serializable{

    private String logId;
    private String pageUrl;
    private String domainKey;
    private String statusDesc;
    private ActStatus status;
    private Date pushDate;
    private String pushDateStr;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getDomainKey() {
        return domainKey;
    }

    public void setDomainKey(String domainKey) {
        this.domainKey = domainKey;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public String getPushDateStr() {
        return pushDateStr;
    }

    public void setPushDateStr(String pushDateStr) {
        this.pushDateStr = pushDateStr;
    }

    public ActStatus getStatus() {
        return status;
    }

    public void setStatus(ActStatus status) {
        this.status = status;
    }
}
