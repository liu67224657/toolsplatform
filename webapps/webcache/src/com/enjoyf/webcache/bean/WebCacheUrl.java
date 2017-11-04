package com.enjoyf.webcache.bean;



import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;
import net.sf.json.JSONObject;


import java.sql.Timestamp;

import java.util.ArrayList;

import java.util.Date;

import java.util.List;



public class WebCacheUrl {

    private String urlId;

    private String url;

    private String urlKey;

    private String urlType;

    private String channel;

    private Integer httpCode;

    private String redirctUrl;

    private String tags;

    private Date lastModifyTime;

    private String rule_id;



    public String getUrlId() {

        return urlId;

    }



    public void setUrlId(String urlId) {

        this.urlId = urlId;

    }



    public String getUrl() {

        return url;

    }



    public void setUrl(String url) {

        this.url = url;

    }



    public String getUrlKey() {

        return urlKey;

    }



    public void setUrlKey(String urlKey) {

        this.urlKey = urlKey;

    }



    public String getUrlType() {

        return urlType;

    }



    public void setUrlType(String urlType) {

        this.urlType = urlType;

    }



    public String getChannel() {

        return channel;

    }



    public void setChannel(String channel) {

        this.channel = channel;

    }



    public Integer getHttpCode() {

        return httpCode;

    }



    public void setHttpCode(Integer httpCode) {

        this.httpCode = httpCode;

    }



    public String getTags() {

        return tags;

    }



    public void setTags(String tags) {

        this.tags = tags;

    }



    public Date getLastModifyTime() {

        return lastModifyTime;

    }



    public void setLastModifyTime(Date lastModifyTime) {

        this.lastModifyTime = lastModifyTime;

    }



    public String getRule_id() {

        return rule_id;

    }



    public void setRule_id(String rule_id) {

        this.rule_id = rule_id;

    }



    public String getRedirctUrl() {

        return redirctUrl;

    }



    public void setRedirctUrl(String redirctUrl) {

        this.redirctUrl = redirctUrl;

    }





    public List getNotNullColumnList() {

        List columnList = new ArrayList();

        if (urlId != null) {

            NotNullColumnBean bean = new NotNullColumnBean();

            bean.setColumnName("url_id");

            bean.setObj(urlId);

            columnList.add(bean);

        }

        if (url != null) {

            NotNullColumnBean bean = new NotNullColumnBean();

            bean.setColumnName("url");

            bean.setObj(url);

            columnList.add(bean);

        }

        if (urlKey != null) {

            NotNullColumnBean bean = new NotNullColumnBean();

            bean.setColumnName("url_key");

            bean.setObj(urlKey);

            columnList.add(bean);

        }

        if (urlType != null) {

            NotNullColumnBean bean = new NotNullColumnBean();

            bean.setColumnName("url_type");

            bean.setObj(urlType);

            columnList.add(bean);

        }

        if (channel != null) {

            NotNullColumnBean bean = new NotNullColumnBean();

            bean.setColumnName("channel");

            bean.setObj(channel);

            columnList.add(bean);

        }

        if (httpCode != null) {

            NotNullColumnBean bean = new NotNullColumnBean();

            bean.setColumnName("http_code");

            bean.setObj(httpCode);

            columnList.add(bean);

        }

        if (tags != null) {

            NotNullColumnBean bean = new NotNullColumnBean();

            bean.setColumnName("tags");

            bean.setObj(tags);

            columnList.add(bean);

        }

        if (rule_id != null) {

            NotNullColumnBean bean = new NotNullColumnBean();

            bean.setColumnName("rule_id");

            bean.setObj(rule_id);

            columnList.add(bean);

        }

        if (lastModifyTime != null) {

            NotNullColumnBean bean = new NotNullColumnBean();

            bean.setColumnName("last_modify_time");

            bean.setObj(new Timestamp(lastModifyTime.getTime()));

            columnList.add(bean);

        }

        return columnList;

    }

    public String toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("urlId", this.urlId);
        jsonObject.put("url", this.url);
        jsonObject.put("urlKey", this.urlKey);
        jsonObject.put("urlType", this.urlType);
        jsonObject.put("channel", this.channel);
        jsonObject.put("httpCode", this.httpCode);
        //jsonObject.put("tags", this.tags);
        jsonObject.put("rule_id", this.rule_id);
        //jsonObject.put("lastModifyTime", this.lastModifyTime);
        return jsonObject.toString();
    }

    public static WebCacheUrl parse(String jsonStr){
        WebCacheUrl webCacheUrl = null;
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        if(jsonObject != null){
            webCacheUrl = new WebCacheUrl();
            webCacheUrl.setUrlId(jsonObject.getString("urlId"));
            webCacheUrl.setUrl(jsonObject.getString("url"));
            webCacheUrl.setUrlKey(jsonObject.getString("urlKey"));
            webCacheUrl.setUrlType(jsonObject.getString("urlType"));
            webCacheUrl.setChannel(jsonObject.getString("channel"));
            webCacheUrl.setHttpCode(jsonObject.getInt("httpCode"));
            //webCacheUrl.setTags(jsonObject.getString("tags"));
            webCacheUrl.setRule_id(jsonObject.getString("rule_id"));
        }
        return webCacheUrl;
    }

}