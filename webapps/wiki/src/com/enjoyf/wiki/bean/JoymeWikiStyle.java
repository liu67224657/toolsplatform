package com.enjoyf.wiki.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JoymeWikiStyle {
    private Integer joymeWikiStyleId;
    private String joymeWikiKey;
    private InputStream joymeWikiFile;
    private Timestamp joymeModifyDate;
    private Integer joymeWikiIsindex;
    private String joymeWikiChannel;
    private String contextPath;
    private Long versionNum;

    public Integer getJoymeWikiStyleId() {
        return joymeWikiStyleId;
    }

    public void setJoymeWikiStyleId(Integer joymeWikiStyleId) {
        this.joymeWikiStyleId = joymeWikiStyleId;
    }

    public String getJoymeWikiKey() {
        return joymeWikiKey;
    }

    public void setJoymeWikiKey(String joymeWikiKey) {
        this.joymeWikiKey = joymeWikiKey;
    }

    public InputStream getJoymeWikiFile() {
        return joymeWikiFile;
    }

    public void setJoymeWikiFile(InputStream joymeWikiFile) {
        this.joymeWikiFile = joymeWikiFile;
    }

    public Timestamp getJoymeModifyDate() {
        return joymeModifyDate;
    }

    public void setJoymeModifyDate(Timestamp joymeModifyDate) {
        this.joymeModifyDate = joymeModifyDate;
    }

    public Integer getJoymeWikiIsindex() {
        return joymeWikiIsindex;
    }

    public void setJoymeWikiIsindex(Integer joymeWikiIsindex) {
        this.joymeWikiIsindex = joymeWikiIsindex;
    }

    public String getJoymeWikiChannel() {
        return joymeWikiChannel;
    }

    public void setJoymeWikiChannel(String joymeWikiChannel) {
        this.joymeWikiChannel = joymeWikiChannel;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public long getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(long versionNum) {
        this.versionNum = versionNum;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (joymeWikiKey != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_wiki_key");
            bean.setObj(joymeWikiKey);
            columnList.add(bean);
        }
        if (joymeWikiFile != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_wiki_file");
            bean.setObj(joymeWikiFile);
            columnList.add(bean);
        }
        if (joymeModifyDate != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_modify_date");
            bean.setObj(joymeModifyDate);
            columnList.add(bean);
        }
        if (joymeWikiIsindex != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_wiki_isindex");
            bean.setObj(joymeWikiIsindex);
            columnList.add(bean);
        }
        if (joymeWikiChannel != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_wiki_channel");
            bean.setObj(joymeWikiChannel);
            columnList.add(bean);
        }
        if (contextPath != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("context_path");
            bean.setObj(contextPath);
            columnList.add(bean);
        }
        if (versionNum != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("version_num");
            bean.setObj(versionNum);
            columnList.add(bean);
        }
        return columnList;
    }
}

