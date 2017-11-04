package com.enjoyf.mcms.bean;

import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class DedeAddonspec {
    private Integer aid;
    private Integer typeid;
    private String note;
    private String templet;
    private String userip;
    private String redirecturl;
    private String bgpicture;
    private String bgpicture1;
    private Object zhuanti1;
    private Object zhuanti2;
    private Object zhuanti3;
    private Object zhuanti4;
    private Object zhuanti5;
    private Object zhuanti6;
    private Object zhuanti7;
    private Object zhuanti8;
    private Object zhuanti9;
    private Object zhuanti10;
    private Object zhuanti11;
    private Object zhuanti12;

    public Integer getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getRedirecturl() {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    public String getBgpicture() {
        return bgpicture;
    }

    public void setBgpicture(String bgpicture) {
        this.bgpicture = bgpicture;
    }

    public String getBgpicture1() {
        return bgpicture1;
    }

    public void setBgpicture1(String bgpicture1) {
        this.bgpicture1 = bgpicture1;
    }

    public Object getZhuanti1() {
        return zhuanti1;
    }

    public void setZhuanti1(Object zhuanti1) {
        this.zhuanti1 = zhuanti1;
    }

    public Object getZhuanti2() {
        return zhuanti2;
    }

    public void setZhuanti2(Object zhuanti2) {
        this.zhuanti2 = zhuanti2;
    }

    public Object getZhuanti3() {
        return zhuanti3;
    }

    public void setZhuanti3(Object zhuanti3) {
        this.zhuanti3 = zhuanti3;
    }

    public Object getZhuanti4() {
        return zhuanti4;
    }

    public void setZhuanti4(Object zhuanti4) {
        this.zhuanti4 = zhuanti4;
    }

    public Object getZhuanti5() {
        return zhuanti5;
    }

    public void setZhuanti5(Object zhuanti5) {
        this.zhuanti5 = zhuanti5;
    }

    public Object getZhuanti6() {
        return zhuanti6;
    }

    public void setZhuanti6(Object zhuanti6) {
        this.zhuanti6 = zhuanti6;
    }

    public Object getZhuanti7() {
        return zhuanti7;
    }

    public void setZhuanti7(Object zhuanti7) {
        this.zhuanti7 = zhuanti7;
    }

    public Object getZhuanti8() {
        return zhuanti8;
    }

    public void setZhuanti8(Object zhuanti8) {
        this.zhuanti8 = zhuanti8;
    }

    public Object getZhuanti9() {
        return zhuanti9;
    }

    public void setZhuanti9(Object zhuanti9) {
        this.zhuanti9 = zhuanti9;
    }

    public Object getZhuanti10() {
        return zhuanti10;
    }

    public void setZhuanti10(Object zhuanti10) {
        this.zhuanti10 = zhuanti10;
    }

    public Object getZhuanti11() {
        return zhuanti11;
    }

    public void setZhuanti11(Object zhuanti11) {
        this.zhuanti11 = zhuanti11;
    }

    public Object getZhuanti12() {
        return zhuanti12;
    }

    public void setZhuanti12(Object zhuanti12) {
        this.zhuanti12 = zhuanti12;
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
        if (note != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("note");
            bean.setObj(note);
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
        if (redirecturl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("redirecturl");
            bean.setObj(redirecturl);
            columnList.add(bean);
        }
        if (bgpicture != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("bgpicture");
            bean.setObj(bgpicture);
            columnList.add(bean);
        }
        if (bgpicture1 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("bgpicture1");
            bean.setObj(bgpicture1);
            columnList.add(bean);
        }
        if (zhuanti1 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti1");
            bean.setObj(zhuanti1);
            columnList.add(bean);
        }
        if (zhuanti2 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti2");
            bean.setObj(zhuanti2);
            columnList.add(bean);
        }
        if (zhuanti3 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti3");
            bean.setObj(zhuanti3);
            columnList.add(bean);
        }
        if (zhuanti4 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti4");
            bean.setObj(zhuanti4);
            columnList.add(bean);
        }
        if (zhuanti5 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti5");
            bean.setObj(zhuanti5);
            columnList.add(bean);
        }
        if (zhuanti6 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti6");
            bean.setObj(zhuanti6);
            columnList.add(bean);
        }
        if (zhuanti7 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti7");
            bean.setObj(zhuanti7);
            columnList.add(bean);
        }
        if (zhuanti8 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti8");
            bean.setObj(zhuanti8);
            columnList.add(bean);
        }
        if (zhuanti9 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti9");
            bean.setObj(zhuanti9);
            columnList.add(bean);
        }
        if (zhuanti10 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti10");
            bean.setObj(zhuanti10);
            columnList.add(bean);
        }
        if (zhuanti11 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti11");
            bean.setObj(zhuanti11);
            columnList.add(bean);
        }
        if (zhuanti12 != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("zhuanti12");
            bean.setObj(zhuanti12);
            columnList.add(bean);
        }
        return columnList;
    }
}
