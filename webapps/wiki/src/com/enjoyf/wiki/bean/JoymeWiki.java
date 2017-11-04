package com.enjoyf.wiki.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JoymeWiki implements Serializable{
    private Integer joymeWikiId;
    private String joymeWikiKey;
    private String joymeWikiDomain;
    private String joymeWikiPath;
    private String joymeWikiFactory;
    private String joymeWikiName;

    private String joymeAndroidPath;
    private String joymeIosPath;

    private String contextPath;
    private Boolean supportSubDomain;

    private Integer pcKeepJscss = 0; //是否保留wiki js css 0-不保留 1-保留
    private Integer mKeepJscss = 0;//是否保留wiki js css 0-不保留 1-保留

    public Integer getJoymeWikiId() {
        return joymeWikiId;
    }

    public void setJoymeWikiId(Integer joymeWikiId) {
        this.joymeWikiId = joymeWikiId;
    }

    public String getJoymeWikiKey() {
        return joymeWikiKey;
    }

    public void setJoymeWikiKey(String joymeWikiKey) {
        this.joymeWikiKey = joymeWikiKey;
    }

    public String getJoymeWikiDomain() {
        return joymeWikiDomain;
    }

    public void setJoymeWikiDomain(String joymeWikiDomain) {
        this.joymeWikiDomain = joymeWikiDomain;
    }

    public String getJoymeWikiPath() {
        return joymeWikiPath;
    }

    public void setJoymeWikiPath(String joymeWikiPath) {
        this.joymeWikiPath = joymeWikiPath;
    }

    public String getJoymeWikiFactory() {
        return joymeWikiFactory;
    }

    public void setJoymeWikiFactory(String joymeWikiFactory) {
        this.joymeWikiFactory = joymeWikiFactory;
    }

    public String getJoymeWikiName() {
        return joymeWikiName;
    }

    public void setJoymeWikiName(String joymeWikiName) {
        this.joymeWikiName = joymeWikiName;
    }

    public String getJoymeAndroidPath() {
        return joymeAndroidPath;
    }

    public void setJoymeAndroidPath(String joymeAndroidPath) {
        this.joymeAndroidPath = joymeAndroidPath;
    }

    public String getJoymeIosPath() {
        return joymeIosPath;
    }

    public void setJoymeIosPath(String joymeIosPath) {
        this.joymeIosPath = joymeIosPath;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public Boolean getSupportSubDomain() {
        return supportSubDomain;
    }

    public void setSupportSubDomain(Boolean supportSubDomain) {
        this.supportSubDomain = supportSubDomain;
    }

    public Integer getPcKeepJscss() {
        return pcKeepJscss;
    }

    public void setPcKeepJscss(Integer pcKeepJscss) {
        this.pcKeepJscss = pcKeepJscss;
    }

    public Integer getmKeepJscss() {
        return mKeepJscss;
    }

    public void setmKeepJscss(Integer mKeepJscss) {
        this.mKeepJscss = mKeepJscss;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (joymeWikiKey != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_wiki_key");
            bean.setObj(joymeWikiKey);
            columnList.add(bean);
        }
        if (joymeWikiDomain != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_wiki_domain");
            bean.setObj(joymeWikiDomain);
            columnList.add(bean);
        }
        if (joymeWikiPath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_wiki_path");
            bean.setObj(joymeWikiPath);
            columnList.add(bean);
        }
        if (joymeWikiFactory != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_wiki_factory");
            bean.setObj(joymeWikiFactory);
            columnList.add(bean);
        }
        if (joymeWikiName != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_wiki_name");
            bean.setObj(joymeWikiName);
            columnList.add(bean);
        }
        if (joymeAndroidPath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_android_path");
            bean.setObj(joymeAndroidPath);
            columnList.add(bean);
        }
        if (joymeIosPath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_ios_path");
            bean.setObj(joymeIosPath);
            columnList.add(bean);
        }
        if (contextPath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("context_path");
            bean.setObj(contextPath);
            columnList.add(bean);
        }
        if (supportSubDomain != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("support_subdomain");
            bean.setObj(supportSubDomain);
            columnList.add(bean);
        }
        if (pcKeepJscss != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("pc_keep_jscss");
            bean.setObj(pcKeepJscss);
            columnList.add(bean);
        }
        if (mKeepJscss != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("m_keep_jscss");
            bean.setObj(mKeepJscss);
            columnList.add(bean);
        }
        return columnList;
    }


    public static int getKeepJsCss(JoymeWiki joymeWiki) {
        int keepjscss = 0;
        if (joymeWiki.getContextPath().equals("wiki")) {
            keepjscss = joymeWiki.getPcKeepJscss();
        } else {
            keepjscss = joymeWiki.getmKeepJscss();
        }
        return keepjscss;
    }
}


