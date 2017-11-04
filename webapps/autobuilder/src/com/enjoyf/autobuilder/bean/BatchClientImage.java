package com.enjoyf.autobuilder.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class BatchClientImage {
    public static final int IMAGE_TYPE_ICON = 0;
    public static final int IMAGE_TYPE_BACKGROUND = 1;
    public static final int IMAGE_TYPE_LOADINGPAGE = 2;

    private Integer id;
    private String pic;
    private String picXB;
    private String picB;
    private String picM;
    private String picS;
    private Integer picType;//icon background loadingpage
    private Integer platform;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getPicB() {
        return picB;
    }

    public void setPicB(String picB) {
        this.picB = picB;
    }

    public String getPicM() {
        return picM;
    }

    public void setPicM(String picM) {
        this.picM = picM;
    }

    public String getPicS() {
        return picS;
    }

    public void setPicS(String picS) {
        this.picS = picS;
    }

    public String getPicXB() {
        return picXB;
    }

    public void setPicXB(String picXB) {
        this.picXB = picXB;
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (pic != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("pic");
            bean.setObj(pic);
            columnList.add(bean);
        }
        if (picXB != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("pic_xb");
            bean.setObj(picB);
            columnList.add(bean);
        }
        if (picB != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("pic_b");
            bean.setObj(picB);
            columnList.add(bean);
        }
        if (picM != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("pic_m");
            bean.setObj(picM);
            columnList.add(bean);
        }
        if (picS != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("pic_s");
            bean.setObj(picS);
            columnList.add(bean);
        }
        if (platform != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("platform");
            bean.setObj(platform);
            columnList.add(bean);
        }
        if (picType != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("pic_type");
            bean.setObj(picB);
            columnList.add(bean);
        }
        return columnList;
    }
}


