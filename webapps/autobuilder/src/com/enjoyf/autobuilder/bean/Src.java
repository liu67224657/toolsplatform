package com.enjoyf.autobuilder.bean;


import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class Src {
    private Integer srcId;
    private String srcVersion;
    private String srcType;
    private String srcUrl;

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public String getSrcVersion() {
        return srcVersion;
    }

    public void setSrcVersion(String srcVersion) {
        this.srcVersion = srcVersion;
    }

    public String getSrcType() {
        return srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }


    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (srcVersion != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("src_version");
            bean.setObj(srcVersion);
            columnList.add(bean);
        }
        if (srcType != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("src_type");
            bean.setObj(srcType);
            columnList.add(bean);
        }
        if (srcUrl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("src_url");
            bean.setObj(srcUrl);
            columnList.add(bean);
        }
        return columnList;
    }
}


