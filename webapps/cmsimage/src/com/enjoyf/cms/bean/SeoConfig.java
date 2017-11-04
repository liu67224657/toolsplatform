package com.enjoyf.cms.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class SeoConfig {
    private String seoId;
    private String seoTransferPath;
    private String seoOriginalPath;
    private Timestamp createTime;

    public String getSeoId() {
        return seoId;
    }

    public void setSeoId(String seoId) {
        this.seoId = seoId;
    }

    public String getSeoTransferPath() {
        return seoTransferPath;
    }

    public void setSeoTransferPath(String seoTransferPath) {
        this.seoTransferPath = seoTransferPath;
    }

    public String getSeoOriginalPath() {
        return seoOriginalPath;
    }

    public void setSeoOriginalPath(String seoOriginalPath) {
        this.seoOriginalPath = seoOriginalPath;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if(seoId != null){
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("seo_id");
            bean.setObj(seoId);
            columnList.add(bean);
        }
        if (seoTransferPath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("seo_transfer_path");
            bean.setObj(seoTransferPath);
            columnList.add(bean);
        }
        if (seoOriginalPath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("seo_original_path");
            bean.setObj(seoOriginalPath);
            columnList.add(bean);
        }
        if (createTime != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("create_time");
            bean.setObj(createTime);
            columnList.add(bean);
        }
        return columnList;
    }
}
