package com.enjoyf.autobuilder.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class Resource {
    private Integer resourceId;
    private String resourceName;
    private String resourceCode;
    private String resourceVersion;
    private String resourceAssets;
    private String resourceRes;

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public String getResourceAssets() {
        return resourceAssets;
    }

    public void setResourceAssets(String resourceAssets) {
        this.resourceAssets = resourceAssets;
    }

    public String getResourceRes() {
        return resourceRes;
    }

    public void setResourceRes(String resourceRes) {
        this.resourceRes = resourceRes;
    }


    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (resourceName != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("resource_name");
            bean.setObj(resourceName);
            columnList.add(bean);
        }
        if (resourceCode != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("resource_code");
            bean.setObj(resourceCode);
            columnList.add(bean);
        }
        if (resourceVersion != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("resource_version");
            bean.setObj(resourceVersion);
            columnList.add(bean);
        }
        if (resourceAssets != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("resource_assets");
            bean.setObj(resourceAssets);
            columnList.add(bean);
        }
        if (resourceRes != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("resource_res");
            bean.setObj(resourceRes);
            columnList.add(bean);
        }
        return columnList;
    }
}


