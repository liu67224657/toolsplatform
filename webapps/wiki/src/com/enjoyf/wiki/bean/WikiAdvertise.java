package com.enjoyf.wiki.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class WikiAdvertise {
    private Integer wikiAdvertiseId;
    private String wikiAdvertiseKey;
    private String wikiAdvertiseContextPath;
    private Integer wikiAdvertiseType;
    private String wikiAdvertiseUrl;
    private String wikiAdvertiseDesc;
    private String wikiAdvertiseHtml;
    private String wikiAdvertisePicUrl;

    public Integer getWikiAdvertiseId() {
        return wikiAdvertiseId;
    }

    public void setWikiAdvertiseId(Integer wikiAdvertiseId) {
        this.wikiAdvertiseId = wikiAdvertiseId;
    }

    public String getWikiAdvertiseKey() {
        return wikiAdvertiseKey;
    }

    public void setWikiAdvertiseKey(String wikiAdvertiseKey) {
        this.wikiAdvertiseKey = wikiAdvertiseKey;
    }

    public String getWikiAdvertiseContextPath() {
        return wikiAdvertiseContextPath;
    }

    public void setWikiAdvertiseContextPath(String wikiAdvertiseContextPath) {
        this.wikiAdvertiseContextPath = wikiAdvertiseContextPath;
    }

    public Integer getWikiAdvertiseType() {
        return wikiAdvertiseType;
    }

    public void setWikiAdvertiseType(Integer wikiAdvertiseType) {
        this.wikiAdvertiseType = wikiAdvertiseType;
    }

    public String getWikiAdvertiseUrl() {
        return wikiAdvertiseUrl;
    }

    public void setWikiAdvertiseUrl(String wikiAdvertiseUrl) {
        this.wikiAdvertiseUrl = wikiAdvertiseUrl;
    }

    public String getWikiAdvertiseDesc() {
        return wikiAdvertiseDesc;
    }

    public void setWikiAdvertiseDesc(String wikiAdvertiseDesc) {
        this.wikiAdvertiseDesc = wikiAdvertiseDesc;
    }

    public String getWikiAdvertiseHtml() {
        return wikiAdvertiseHtml;
    }

    public void setWikiAdvertiseHtml(String wikiAdvertiseHtml) {
        this.wikiAdvertiseHtml = wikiAdvertiseHtml;
    }

    public String getWikiAdvertisePicUrl() {
        return wikiAdvertisePicUrl;
    }

    public void setWikiAdvertisePicUrl(String wikiAdvertisePicUrl) {
        this.wikiAdvertisePicUrl = wikiAdvertisePicUrl;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (wikiAdvertiseKey != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki_advertise_key");
            bean.setObj(wikiAdvertiseKey);
            columnList.add(bean);
        }
        if (wikiAdvertiseContextPath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki_advertise_context_path");
            bean.setObj(wikiAdvertiseContextPath);
            columnList.add(bean);
        }
        if (wikiAdvertiseType != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki_advertise_type");
            bean.setObj(wikiAdvertiseType);
            columnList.add(bean);
        }
        if (wikiAdvertisePicUrl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki_advertise_picurl");
            bean.setObj(wikiAdvertisePicUrl);
            columnList.add(bean);
        }
        if (wikiAdvertiseUrl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki_advertise_url");
            bean.setObj(wikiAdvertiseUrl);
            columnList.add(bean);
        }
        if (wikiAdvertiseDesc != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki_advertise_desc");
            bean.setObj(wikiAdvertiseDesc);
            columnList.add(bean);
        }
        if (wikiAdvertiseHtml != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki_advertise_html");
            bean.setObj(wikiAdvertiseHtml);
            columnList.add(bean);
        }
        return columnList;
    }
}


