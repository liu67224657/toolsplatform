package com.enjoyf.wiki.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class JoymeWikiSitemap {
    private Long sitemapid;
    private String wikiKey;
    private String loc;
    private String priority;

    public Long getSitemapid() {
        return sitemapid;
    }

    public void setSitemapid(Long sitemapid) {
        this.sitemapid = sitemapid;
    }

    public String getWikiKey() {
        return wikiKey;
    }

    public void setWikiKey(String wikiKey) {
        this.wikiKey = wikiKey;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (wikiKey != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki_key");
            bean.setObj(wikiKey);
            columnList.add(bean);
        }
        if (loc != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("loc");
            bean.setObj(loc);
            columnList.add(bean);
        }
        if (priority != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("priority");
            bean.setObj(priority);
            columnList.add(bean);
        }
        return columnList;
    }
}


