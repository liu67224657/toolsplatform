package com.enjoyf.mcms.util;


import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.util.HttpResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by zhimingli
 * Date: 2015/02/02
 * Time: 11:35
 * api:http://open.weibo.com/wiki/Short_url/shorten
 */
public class ShortUrlUtils {
    private static String SINA_SHORT_URL_API = "http://api.t.sina.com.cn/short_url/shorten.json";
    private static HttpClientManager clientManager = new HttpClientManager();

    public static String getSinaURL(String url_long) {
        if (StringUtil.isEmpty(url_long)) {
            return url_long;
        }
        HttpResult result = null;
        try {
            result = clientManager.post(SINA_SHORT_URL_API, new HttpParameter[]{
                    new HttpParameter("source", "3904005873"),
                    new HttpParameter("url_long", url_long)
            }, null);
            if (result.getReponseCode() == 200) {
                JSONObject jsonObject = JSONObject.fromObject(JSONArray.fromObject(result.getResult()).get(0));
                return jsonObject.get("url_short").toString();
            }
        } catch (Exception e) {
            return url_long;
        }
        return url_long;
    }

    public static void main(String[] args) {
        String s = getSinaURL("http://www.joyme.com");
        System.out.println(s);
    }
}
