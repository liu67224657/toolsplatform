package com.enjoyf.mcms.bean.json;

import java.util.List;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  2014/6/20 17:47
 * Description:
 */
public class DedeCategoryArchiveJsonBean {
    private int aid;
    private String pic;
    private String url;
    private long date;
    private String title;
    private String author;
    private String desc;

    private ParamTextJson param;

    private int menu_type=1;
    private int d_type=2;


    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(int menu_type) {
        this.menu_type = menu_type;
    }

    public int getD_type() {
        return d_type;
    }

    public void setD_type(int d_type) {
        this.d_type = d_type;
    }

    public ParamTextJson getParam() {
        return param;
    }

    public void setParam(ParamTextJson param) {
        this.param = param;
    }
}
