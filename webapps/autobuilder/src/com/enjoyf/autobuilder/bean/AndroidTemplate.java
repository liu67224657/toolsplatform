package com.enjoyf.autobuilder.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class AndroidTemplate {
    private Integer templateId;
    private String templateCode;
    private String templateVersion;
    private String templateUrl;
    private Integer platform;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion(String templateVersion) {
        this.templateVersion = templateVersion;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (templateCode != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("template_code");
            bean.setObj(templateCode);
            columnList.add(bean);
        }
        if (templateVersion != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("template_version");
            bean.setObj(templateVersion);
            columnList.add(bean);
        }
        if (templateUrl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("template_url");
            bean.setObj(templateUrl);
            columnList.add(bean);
        }
        if (platform != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("platform");
            bean.setObj(platform);
            columnList.add(bean);
        }
        return columnList;
    }
}


