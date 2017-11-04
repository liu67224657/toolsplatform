package com.enjoyf.mcms.bean.json;

import java.io.Serializable;

public class JoymePointArchiveJsonBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2549672018027253176L;
    private Long aid;
    private String title;
    private Long date;
    private String picurl;
    private String desc;
    private String url;

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
