package com.enjoyf.wiki.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class WikiPage {
    private Long pageId;
    private String wikiKey;
    private String wikiUrl;
    private Timestamp createTime;
    private String httpUrl;
    private Integer pageStatus = 1; //0-- 失效 1-- 生效

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getWikiKey() {
        return wikiKey;
    }

    public void setWikiKey(String wikiKey) {
        this.wikiKey = wikiKey;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getPageStatus() {
        return pageStatus;
    }

    public void setPageStatus(Integer pageStatus) {
        this.pageStatus = pageStatus;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (wikiKey != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki_key");
            bean.setObj(wikiKey);
            columnList.add(bean);
        }
        if (wikiUrl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki_url");
            bean.setObj(wikiUrl);
            columnList.add(bean);
        }
        if (createTime != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("create_time");
            bean.setObj(createTime);
            columnList.add(bean);
        }
        if (pageStatus != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("page_status");
            bean.setObj(pageStatus);
            columnList.add(bean);
        }
        return columnList;
    }
}
