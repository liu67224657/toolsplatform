package com.enjoyf.mcms.bean;

import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class DedeAddonarticle {
    private Integer aid;
    private Integer typeid;
    private String body;
    private String redirecturl;
    private String templet;
    private String userip;
    private String htlistimg;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRedirecturl() {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    public String getTemplet() {
        return templet;
    }

    public void setTemplet(String templet) {
        this.templet = templet;
    }

    public String getUserip() {
        return userip;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getHtlistimg() {
        return htlistimg;
    }

    public void setHtlistimg(String htlistimg) {
        this.htlistimg = htlistimg;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (aid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("aid");
            bean.setObj(aid);
            columnList.add(bean);
        }
        if (typeid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("typeid");
            bean.setObj(typeid);
            columnList.add(bean);
        }
        if (body != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("body");
            bean.setObj(body);
            columnList.add(bean);
        }
        if (redirecturl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("redirecturl");
            bean.setObj(redirecturl);
            columnList.add(bean);
        }
        if (templet != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("templet");
            bean.setObj(templet);
            columnList.add(bean);
        }
        if (userip != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("userip");
            bean.setObj(userip);
            columnList.add(bean);
        }
        if (htlistimg != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("htlistimg");
            bean.setObj(htlistimg);
            columnList.add(bean);
        }
        return columnList;
    }
}
