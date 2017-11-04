package com.enjoyf.mcms.bean;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-2-25
 * Time: 下午6:35
 * To change this template use File | Settings | File Templates.
 */
public class DedeAddonrelevance {
    private Integer aid;
    private String title;
    private String url;
    private Integer type;
    private Integer status;
    private String clientpic;
    private String description;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getClientpic() {
        return clientpic;
    }

    public void setClientpic(String clientpic) {
        this.clientpic = clientpic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (aid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("aid");
            bean.setObj(aid);
            columnList.add(bean);
        }
        if (title != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("title");
            bean.setObj(title);
            columnList.add(bean);
        }
        if (url != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("url");
            bean.setObj(url);
            columnList.add(bean);
        }
        if (type != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("type");
            bean.setObj(type);
            columnList.add(bean);
        }
        if (status != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("status");
            bean.setObj(status);
            columnList.add(bean);
        }
        if (clientpic != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("clientpic");
            bean.setObj(clientpic);
            columnList.add(bean);
        }
        if (description != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("description");
            bean.setObj(description);
            columnList.add(bean);
        }
        return columnList;
    }
}
