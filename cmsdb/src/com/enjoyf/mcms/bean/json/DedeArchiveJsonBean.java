package com.enjoyf.mcms.bean.json;

import java.util.List;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-11-15 下午4:47
 * Description:
 */
public class DedeArchiveJsonBean {
    private int aid;
    private String picurl;
    private String url;
    private long date;
    private String title;
    private String writer;
    private String desc;

     private List<JsonTagBean> tags;


    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<JsonTagBean> getTags() {
        return tags;
    }

    public void setTags(List<JsonTagBean> tags) {
        this.tags = tags;
    }
}
