package com.enjoyf.autobuilder.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class IosResource {
    private Integer resourceId;
    private String resourceName;
    private String resourceCode;
    private String resourceVersion;
    private String resourceIcons;
    private String resourceImages;
    private String resourceProfiles;
    private String resourceDir;

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

    public String getResourceIcons() {
        return resourceIcons;
    }

    public void setResourceIcons(String resourceIcons) {
        this.resourceIcons = resourceIcons;
    }

    public String getResourceImages() {
        return resourceImages;
    }

    public void setResourceImages(String resourceImages) {
        this.resourceImages = resourceImages;
    }

    public String getResourceProfiles() {
        return resourceProfiles;
    }

    public void setResourceProfiles(String resourceProfiles) {
        this.resourceProfiles = resourceProfiles;
    }

    public String getResourceDir() {
        return resourceDir;
    }

    public void setResourceDir(String resourceDir) {
        this.resourceDir = resourceDir;
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
        if (resourceIcons != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("resource_icons");
            bean.setObj(resourceIcons);
            columnList.add(bean);
        }
        if (resourceImages != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("resource_images");
            bean.setObj(resourceImages);
            columnList.add(bean);
        }
        if (resourceProfiles != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("resource_profiles");
            bean.setObj(resourceProfiles);
            columnList.add(bean);
        }
        if (resourceDir != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("resource_dir");
            bean.setObj(resourceDir);
            columnList.add(bean);
        }
        return columnList;
    }
}


