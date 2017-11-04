package com.enjoyf.webcache.bean;

import com.enjoyf.util.MD5Util;
import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhitaoshi on 2016/5/12.
 */
public class RefreshTimerUrl implements Serializable{

    private String urlId;
    private String url;
    private int type;//0--子文件，1--子文件及子文件夹

    public String getUrlId() {
        return MD5Util.Md5(url);
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = JSONObject.fromObject(this);
        return jsonObject.toString();
    }
}
