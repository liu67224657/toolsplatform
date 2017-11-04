package com.enjoyf.wiki.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class JoymeItem {
    private Integer joymeItemId;
    private String wiki;
    private String channel;
    private Integer isIndex;
    private String itemKey;
    private String itemType;
    private String itemDescription;
    private String itemProperties;
    private String itemContext;
    private Timestamp createDate;
    private String contextPath;

    public Integer getJoymeItemId() {
        return joymeItemId;
    }

    public void setJoymeItemId(Integer joymeItemId) {
        this.joymeItemId = joymeItemId;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Integer isIndex) {
        this.isIndex = isIndex;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemProperties() {
        return itemProperties;
    }

    public void setItemProperties(String itemProperties) {
        this.itemProperties = itemProperties;
    }

    public String getItemContext() {
        return itemContext;
    }

    public void setItemContext(String itemContext) {
        this.itemContext = itemContext;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getContextPath() {
        return contextPath;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (wiki != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("wiki");
            bean.setObj(wiki);
            columnList.add(bean);
        }
        if (channel != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("channel");
            bean.setObj(channel);
            columnList.add(bean);
        }
        if (isIndex != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("is_index");
            bean.setObj(isIndex);
            columnList.add(bean);
        }
        if (itemKey != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("item_key");
            bean.setObj(itemKey);
            columnList.add(bean);
        }
        if (itemType != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("item_type");
            bean.setObj(itemType);
            columnList.add(bean);
        }
        if (itemDescription != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("item_description");
            bean.setObj(itemDescription);
            columnList.add(bean);
        }
        if (itemProperties != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("item_properties");
            bean.setObj(itemProperties);
            columnList.add(bean);
        }
        if (itemContext != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("item_context");
            bean.setObj(itemContext);
            columnList.add(bean);
        }
        if (createDate != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("create_date");
            bean.setObj(createDate);
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


