package com.enjoyf.mcms.bean;

import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

public class JoymeChannelContent {
    private Integer channelContentId;
    private Integer channelId;
    private Integer specId;
    private String channelName;
    private String downloadUrl;
    private String advertiseContent;

    public Integer getChannelContentId() {
        return channelContentId;
    }

    public void setChannelContentId(Integer channelContentId) {
        this.channelContentId = channelContentId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getAdvertiseContent() {
        return advertiseContent;
    }

    public void setAdvertiseContent(String advertiseContent) {
        this.advertiseContent = advertiseContent;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (channelId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("channel_id");
            bean.setObj(channelId);
            columnList.add(bean);
        }
        if (specId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("spec_id");
            bean.setObj(specId);
            columnList.add(bean);
        }
        if (channelName != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("channel_name");
            bean.setObj(channelName);
            columnList.add(bean);
        }
        if (downloadUrl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("download_url");
            bean.setObj(downloadUrl);
            columnList.add(bean);
        }
        if (advertiseContent != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("advertise_content");
            bean.setObj(advertiseContent);
            columnList.add(bean);
        }
        return columnList;
    }
}
