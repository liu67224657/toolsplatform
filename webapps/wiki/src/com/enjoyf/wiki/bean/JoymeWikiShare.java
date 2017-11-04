package com.enjoyf.wiki.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

public class JoymeWikiShare {
    private Integer joymeShareId;
    private String joymeWikiKey;
    private String joymeShareContent;
    private String joymeShareUrl;
    private String joymeSharePic;

    public Integer getJoymeShareId() {
        return joymeShareId;
    }

    public void setJoymeShareId(Integer joymeShareId) {
        this.joymeShareId = joymeShareId;
    }

    public String getJoymeWikiKey() {
        return joymeWikiKey;
    }

    public void setJoymeWikiKey(String joymeWikiKey) {
        this.joymeWikiKey = joymeWikiKey;
    }

    public String getJoymeShareContent() {
        return joymeShareContent;
    }

    public void setJoymeShareContent(String joymeShareContent) {
        this.joymeShareContent = joymeShareContent;
    }

    public String getJoymeShareUrl() {
        return joymeShareUrl;
    }

    public void setJoymeShareUrl(String joymeShareUrl) {
        this.joymeShareUrl = joymeShareUrl;
    }

    public String getJoymeSharePic() {
        return joymeSharePic;
    }

    public void setJoymeSharePic(String joymeSharePic) {
        this.joymeSharePic = joymeSharePic;
    }


    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (joymeWikiKey != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_wiki_key");
            bean.setObj(joymeWikiKey);
            columnList.add(bean);
        }
        if (joymeShareContent != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_share_content");
            bean.setObj(joymeShareContent);
            columnList.add(bean);
        }
        if (joymeShareUrl != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_share_url");
            bean.setObj(joymeShareUrl);
            columnList.add(bean);
        }
        if (joymeSharePic != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("joyme_share_pic");
            bean.setObj(joymeSharePic);
            columnList.add(bean);
        }
        return columnList;
    }
}


