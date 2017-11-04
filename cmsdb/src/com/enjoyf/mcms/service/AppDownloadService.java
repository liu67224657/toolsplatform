package com.enjoyf.mcms.service;

import com.enjoyf.mcms.bean.AppDownloadBean;
import com.enjoyf.mcms.bean.GameBean;
import com.enjoyf.util.DateUtils;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AppDownloadService {
    private static URLUtil urlUtil = new URLUtil();

    public AppDownloadBean getApp(String url) throws IOException {
        String appJson = urlUtil.openURLWithTimeOut(url);
        if (appJson == null)
            return null;

        JSONObject object = JSONObject.fromObject(appJson);
        if (object.get("rs") != null & object.getInt("rs") == 0)
            return null;

        if (!object.has("result")) {
            return null;
        }

        // 获得game的详细内容
        JSONObject result = (JSONObject) object.get("result");
        AppDownloadBean bean = new AppDownloadBean();
        if (result.has("appid")) {
            bean.setAppId(result.getString("appid"));
        }
        if (result.has("url")) {
            bean.setUrl(result.getString("url"));
        }
        if (result.has("appname")) {
            bean.setAppName(result.getString("appname"));
        }
        return bean;
    }

}
