package com.enjoyf.wiki.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JoymeTemplate {
    private Integer joymeTemplateId;
    private String templateName;
    private String channel;
    private String wiki;
    private Integer isIndex;
    private Object templateContext;
    private Timestamp createTime;
    private Integer isEnable;
    private String praseFactory;
    private String contextPath;

    public Integer getJoymeTemplateId() {
        return joymeTemplateId;
    }

    public void setJoymeTemplateId(Integer joymeTemplateId) {
        this.joymeTemplateId = joymeTemplateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public Integer getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Integer isIndex) {
        this.isIndex = isIndex;
    }

    public Object getTemplateContext() {
        return templateContext;
    }

    public void setTemplateContext(Object templateContext) {
        this.templateContext = templateContext;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getPraseFactory() {
        return praseFactory;
    }

    public void setPraseFactory(String praseFactory) {
        this.praseFactory = praseFactory;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (templateName != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("template_name");
            bean.setObj(templateName);
            columnList.add(bean);
        }
        if (channel != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("channel");
            bean.setObj(channel);
            columnList.add(bean);
        }
        if (wiki != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki");
            bean.setObj(wiki);
            columnList.add(bean);
        }
        if (isIndex != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("is_index");
            bean.setObj(isIndex);
            columnList.add(bean);
        }
        if (templateContext != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("template_context");
            bean.setObj(templateContext);
            columnList.add(bean);
        }
        if (createTime != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("create_time");
            bean.setObj(createTime);
            columnList.add(bean);
        }
        if (isEnable != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("is_enable");
            bean.setObj(isEnable);
            columnList.add(bean);
        }
        if (praseFactory != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("prase_factory");
            bean.setObj(praseFactory);
            columnList.add(bean);
        }
        if (contextPath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("context_path");
            bean.setObj(contextPath);
            columnList.add(bean);
        }
        return columnList;
    }
}


