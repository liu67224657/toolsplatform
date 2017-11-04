package com.enjoyf.autobuilder.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class BuildLog {
    private long buildLogId;
    private String buildLogCode;
    private String jsonstring;
    private Integer buildPlatform;
    private String buildType;

    public long getBuildLogId() {
        return buildLogId;
    }

    public void setBuildLogId(long buildLogId) {
        this.buildLogId = buildLogId;
    }

    public String getBuildLogCode() {
        return buildLogCode;
    }

    public void setBuildLogCode(String buildLogCode) {
        this.buildLogCode = buildLogCode;
    }

    public String getJsonstring() {
        return jsonstring;
    }

    public void setJsonstring(String jsonstring) {
        this.jsonstring = jsonstring;
    }

    public Integer getBuildPlatform() {
        return buildPlatform;
    }

    public void setBuildPlatform(Integer buildPlatform) {
        this.buildPlatform = buildPlatform;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (buildLogCode != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("build_log_code");
            bean.setObj(buildLogCode);
            columnList.add(bean);
        }
        if (jsonstring != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("jsonstring");
            bean.setObj(jsonstring);
            columnList.add(bean);
        }
        if (buildPlatform != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("build_platform");
            bean.setObj(buildPlatform);
            columnList.add(bean);
        }
        if (buildType != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("build_type");
            bean.setObj(buildType);
            columnList.add(bean);
        }
        return columnList;
    }
}
