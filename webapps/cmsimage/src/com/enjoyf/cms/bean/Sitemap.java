package com.enjoyf.cms.bean;

import com.enjoyf.util.MD5Util;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhitaoshi on 2015/8/21.
 */
public class Sitemap implements Serializable{

    private String sitemapId;
    private String domainKey;
    private String contextPath;
    private SitemapOutRule outRule;
    private String sitemapUrl;
    private String mappingUrl;
    private String expDesc;
    private Date modifyDate;
    private IntRemoveStatus removeStatus;//IntRemoveStatus,0--删除，1--可用

    //no db
    private String modifyDateStr;

    public String getSitemapId() {
        return sitemapId;
    }

    public void setSitemapId(String sitemapId) {
        this.sitemapId = sitemapId;
    }

    public String getDomainKey() {
        return domainKey;
    }

    public void setDomainKey(String domainKey) {
        this.domainKey = domainKey;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public SitemapOutRule getOutRule() {
        return outRule;
    }

    public void setOutRule(SitemapOutRule outRule) {
        this.outRule = outRule;
    }

    public String getSitemapUrl() {
        return sitemapUrl;
    }

    public void setSitemapUrl(String sitemapUrl) {
        this.sitemapUrl = sitemapUrl;
    }

    public String getMappingUrl() {
        return mappingUrl;
    }

    public void setMappingUrl(String mappingUrl) {
        this.mappingUrl = mappingUrl;
    }

    public String getExpDesc() {
        return expDesc;
    }

    public void setExpDesc(String expDesc) {
        this.expDesc = expDesc;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public IntRemoveStatus getRemoveStatus() {
        return removeStatus;
    }

    public void setRemoveStatus(IntRemoveStatus removeStatus) {
        this.removeStatus = removeStatus;
    }

    public String getModifyDateStr() {
        return modifyDateStr;
    }

    public void setModifyDateStr(String modifyDateStr) {
        this.modifyDateStr = modifyDateStr;
    }

    public static String buildSitemapId(String domainKey, String contextPath){
        return MD5Util.Md5(domainKey +"_"+contextPath);
    }
}
