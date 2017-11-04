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
    private String tagFactory;
    private String tagTemplate;
    private String articleTypeFactory;
    private String articleTypeTemplate;
    private String archiveListFactory;
    private String archiveListTemplate;
    private String categoryFactory;

    public String getTagFactory() {
        return tagFactory;
    }

    public void setTagFactory(String tagFactory) {
        this.tagFactory = tagFactory;
    }

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

    public String getArticleTypeFactory() {
        return articleTypeFactory;
    }

    public void setArticleTypeFactory(String articleTypeFactory) {
        this.articleTypeFactory = articleTypeFactory;
    }

    public String getTagTemplate() {
        return tagTemplate;
    }

    public void setTagTemplate(String tagTemplate) {
        this.tagTemplate = tagTemplate;
    }

    public String getArticleTypeTemplate() {
        return articleTypeTemplate;
    }

    public void setArticleTypeTemplate(String articleTypeTemplate) {
        this.articleTypeTemplate = articleTypeTemplate;
    }

    public String getArchiveListFactory() {
        return archiveListFactory;
    }

    public void setArchiveListFactory(String archiveListFactory) {
        this.archiveListFactory = archiveListFactory;
    }

    public String getArchiveListTemplate() {
        return archiveListTemplate;
    }

    public void setArchiveListTemplate(String archiveListTemplate) {
        this.archiveListTemplate = archiveListTemplate;
    }

    public String getCategoryFactory() {
        return categoryFactory;
    }

    public void setCategoryFactory(String categoryFactory) {
        this.categoryFactory = categoryFactory;
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
        if (tagTemplate != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("tag_template");
            bean.setObj(tagTemplate);
            columnList.add(bean);
        }
        if (articleTypeTemplate != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("articletype_template");
            bean.setObj(articleTypeTemplate);
            columnList.add(bean);
        }
        if (archiveListFactory != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("archivelist_factory");
            bean.setObj(archiveListFactory);
            columnList.add(bean);
        }
        if (archiveListTemplate != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("archivelist_template");
            bean.setObj(archiveListTemplate);
            columnList.add(bean);
        }
        if (categoryFactory != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("category_factory");
            bean.setObj(categoryFactory);
            columnList.add(bean);
        }
        return columnList;
    }
}
