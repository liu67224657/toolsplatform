package com.enjoyf.mcms.bean;

import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class JoymeChannel {
    private Integer channelId;
    private String channelName;
    private String gameTemplate;
    private String archiveTemplate;
    private String moreLink;
    private String htmlFactory;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getGameTemplate() {
        return gameTemplate;
    }

    public void setGameTemplate(String gameTemplate) {
        this.gameTemplate = gameTemplate;
    }

    public String getArchiveTemplate() {
        return archiveTemplate;
    }

    public void setArchiveTemplate(String archiveTemplate) {
        this.archiveTemplate = archiveTemplate;
    }

    public String getMoreLink() {
        return moreLink;
    }

    public void setMoreLink(String moreLink) {
        this.moreLink = moreLink;
    }

    public void setHtmlFactory(String htmlFactory) {
        this.htmlFactory = htmlFactory;
    }

    public String getHtmlFactory() {
        return htmlFactory;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (channelName != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("channel_name");
            bean.setObj(channelName);
            columnList.add(bean);
        }
        if (gameTemplate != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("game_template");
            bean.setObj(gameTemplate);
            columnList.add(bean);
        }
        if (archiveTemplate != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("archive_template");
            bean.setObj(archiveTemplate);
            columnList.add(bean);
        }
        if (moreLink != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("more_link");
            bean.setObj(moreLink);
            columnList.add(bean);
        }
        return columnList;
    }
}
