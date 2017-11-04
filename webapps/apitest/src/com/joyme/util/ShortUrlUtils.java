package com.joyme.util;


import com.joyme.util.http.HttpClientManager;
import com.joyme.util.http.HttpParameter;
import com.joyme.util.http.HttpResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhimingli
 * Date: 2015/02/02
 * Time: 11:35
 * api:http://open.weibo.com/wiki/Short_url/shorten
 */
public class ShortUrlUtils {
	private static String SINA_SHORT_URL_API = "http://api.t.sina.com.cn/short_url/shorten.json";

	public static String getSinaURL(String url_long) {
		if (StringUtils.isEmpty(url_long)) {
			return url_long;
		}
		HttpParameter[] params = new HttpParameter[]{
				new HttpParameter("source", "3904005873"),
				new HttpParameter("url_long", url_long),
		};
		HttpResult httpResult = new HttpClientManager().get(SINA_SHORT_URL_API, params);
		if (httpResult.getReponseCode() == 200) {
			JSONObject jsonObject = JSONObject.fromObject(JSONArray.fromObject(httpResult.getResult()).get(0));
			return jsonObject.get("url_short").toString();
		}
		return url_long;
	}

	public static void main(String[] args) {
		String s = getSinaURL("http://www.joyme.com/");
		System.out.println(s);
	}
}
