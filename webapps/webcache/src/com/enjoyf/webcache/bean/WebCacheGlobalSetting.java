package com.enjoyf.webcache.bean;

import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhitaoshi on 2015/10/22.
 */
public class WebCacheGlobalSetting implements Serializable{

    private String pvSetting;
    private String pcWapTabSetting;

    public String getPvSetting() {
        return pvSetting;
    }

    public void setPvSetting(String pvSetting) {
        this.pvSetting = pvSetting;
    }

    public String getPcWapTabSetting() {
        return pcWapTabSetting;
    }

    public void setPcWapTabSetting(String pcWapTabSetting) {
        this.pcWapTabSetting = pcWapTabSetting;
    }

    public String toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pvSetting", this.pvSetting);
        jsonObject.put("pcWapTabSetting", this.pcWapTabSetting);
        return jsonObject.toString();
    }

    public static WebCacheGlobalSetting parse(String jsonStr){
        WebCacheGlobalSetting globalSetting = null;
        if (!StringUtil.isEmpty(jsonStr)) {
            globalSetting = new WebCacheGlobalSetting();
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            globalSetting.setPvSetting(jsonObject.getString("pvSetting"));
            globalSetting.setPcWapTabSetting(jsonObject.getString("pcWapTabSetting"));
        }
        return globalSetting;
    }
}
