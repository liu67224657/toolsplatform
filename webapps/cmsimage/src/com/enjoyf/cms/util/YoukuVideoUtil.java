package com.enjoyf.cms.util;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.URLUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * Created by zhimingli on 2015/6/24.
 */
public class YoukuVideoUtil {
    private URLUtil util = new URLUtil();

    public String getVideoLogo(String vid) throws JSONException {
        String logo = "";

        try {
            String s = null;
            String youkuURL = "http://v.youku.com/player/getPlayList/VideoIDS/" + vid;
            try {
                s = util.openURLWithTimeOut(youkuURL);
            } catch (Exception e) {
                LogService.errorSystemLog("===Error when visit youku which url is : " + youkuURL);
                return null;
            }

            JSONObject jsobj = JSONObject.fromObject(s);
            JSONArray jsonarr = jsobj.getJSONArray("data");
            if (jsonarr == null || jsonarr.size() == 0) {
                return null;
            }

            JSONObject obj1 = jsonarr.getJSONObject(0);
            logo = obj1.getString("logo");
        } catch (Exception e) {
            LogService.errorSystemLog("YoukuVideoUtil getVideoLog vid=" + vid);
            e.printStackTrace();
        }
        return logo;
    }


}
