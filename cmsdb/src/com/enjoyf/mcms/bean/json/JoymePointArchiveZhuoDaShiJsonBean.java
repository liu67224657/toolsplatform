package com.enjoyf.mcms.bean.json;

import java.io.Serializable;

public class JoymePointArchiveZhuoDaShiJsonBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2549672018027253176L;
    private String title;
    private Long date;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
