package com.enjoyf.activity.weixin;

import com.enjoyf.activity.util.CookieUtil;
import com.enjoyf.activity.weixin.util.WeixinMenu;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.rmi.ConnectException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: pengxu Date: 14-5-13 Time: 上午11:52 To change
 * this template use File | Settings | File Templates.
 */
public class WeixinUtil {
    private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

    private static final String COOKIE_OPENID = "_cookie_openid_";
    private static final String COOKIE_ACCESSTOKE = "_cookie_accesstoken_";

    private static final int TIME_OUT_SEC = 7000;
    // 菜单创建URL （post）
    public static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /**
     * 发起HTTPS请求并获得结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（get/post）
     * @param outputStr     提交的数据
     * @return
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
                    .openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

            return jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("Weixin server connection timed out.");
        } catch (Exception e) {
            logger.error("https request error:{}", e);
        }
        return null;
    }


    //获取微信加密信息
    public static String getSignature(String jsapi_ticket, String timestamp,
                                      String nonce, String jsurl) throws IOException {
        /****
         * 对 jsapi_ticket、 timestamp 和 nonce 按字典排序 对所有待签名参数按照字段名的 ASCII
         * 码从小到大排序（字典序）后，使用 URL 键值对的格式（即key1=value1&key2=value2…）拼接成字符串
         * string1。这里需要注意的是所有参数名均为小写字符。 接下来对 string1 作 sha1 加密，字段名和字段值都采用原始值，不进行
         * URL 转义。即 signature=sha1(string1)。
         * **如果没有按照生成的key1=value&key2=value拼接的话会报错
         */
        String[] paramArr = new String[]{"jsapi_ticket=" + jsapi_ticket,
                "timestamp=" + timestamp, "noncestr=" + nonce, "url=" + jsurl};
        Arrays.sort(paramArr);
        // 将排序后的结果拼接成一个字符串
        String content = paramArr[0].concat("&" + paramArr[1]).concat("&" + paramArr[2]).concat("&" + paramArr[3]);
        String sha1 = SHA1Util.SHA1(content);
        return sha1;
    }

    // url（当前网页的URL，不包含#及其后面部分）
    public static String getFullUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        if (request.getQueryString() != null) {
            url.append("?");
            url.append(request.getQueryString());
        }
        return url.toString();
    }


    /**
     * 获取Ticket
     *
     * @return
     */
    public static String getTicketCache(String appid, String secret) {
        WeixinMemcache weixinMemcache = new WeixinMemcache();
        String weixinTicket = null;

        AccessToken accessToken = getAccessToken(appid, secret);

        if (accessToken != null && !StringUtil.isEmpty(accessToken.getToken())) {
            weixinTicket = weixinMemcache.getTicket(accessToken.getToken());

            if (StringUtil.isEmpty(weixinTicket)) {
                // 获取access_token的接口地址（GET） 限200（次/天）
                String JS_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken.getToken() + "&type=jsapi";
                JSONObject object = WeixinUtil.httpRequest(JS_TICKET_URL, "GET", null);
                try {
                    if (object != null) {
                        WeixinTicket ticket = new WeixinTicket();
                        ticket.setErrcode(object.getString("errcode"));
                        ticket.setErrmsg(object.getString("errmsg"));
                        ticket.setTicket(object.getString("ticket"));
                        ticket.setExpires_in(object.getInt("expires_in"));
                        weixinMemcache.putTicket(accessToken.getToken(), ticket.getTicket());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return weixinTicket;
    }


    /**
     * 获取access_token
     *
     * @param appId     凭证
     * @param appSecret 密匙
     * @return
     */
    public static AccessToken getAccessToken(String appId, String appSecret) {
        WeixinMemcache weixinMemcache = new WeixinMemcache();

        AccessToken accessToken = weixinMemcache.getAccessToken(appId);
        if (accessToken == null) {
            String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
            JSONObject jsonObject = WeixinUtil.httpRequest(access_token_url, "GET", null);
            try {
                if (jsonObject != null) {
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                }

                if (accessToken != null) {
                    weixinMemcache.putAccessToken(appId, accessToken);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return accessToken;
    }


    /**
     * 根据微信openid，获取用户信息
     *
     * @param appId
     * @param appSecret
     * @param openid
     * @return
     */
    public static WeixinUser getWeixinUser(String appId, String appSecret, String openid) {
        WeixinMemcache weixinMemcache = new WeixinMemcache();
        WeixinUser weixinUser = weixinMemcache.getUser(openid);

        if (weixinUser == null) {
            AccessToken accessToken = weixinMemcache.getAccessToken(appId);

            if (accessToken == null) {
                accessToken = getAccessToken(appId, appSecret);
            }
            if (accessToken != null) {
                String infourl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken.getToken() + "&openid=" + openid + "&lang=zh_CN";
                JSONObject jsonObject = WeixinUtil.httpRequest(infourl, "GET", null);
                try {
                    if (jsonObject != null) {
                        weixinUser = new WeixinUser();
                        weixinUser.setOpenid(jsonObject.getString("openid"));
                        weixinUser.setNickname(jsonObject.getString("nickname"));
                        weixinUser.setHeadimgurl(jsonObject.getString("headimgurl"));
                    }
                    if (weixinUser != null) {
                        weixinMemcache.putUser(openid, weixinUser);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return weixinUser;
    }


    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @return
     */
    public static WeixinUser getWeixinUserByAccesstoken(String accesstoken, String openid) {
        WeixinMemcache weixinMemcache = new WeixinMemcache();
        WeixinUser weixinUser = weixinMemcache.getUser(openid);
        if (weixinUser == null) {
            String infourl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accesstoken + "&openid=" + openid + "&lang=zh_CN";
            JSONObject jsonObject = WeixinUtil.httpRequest(infourl, "GET", null);
            try {
                if (jsonObject != null) {
                    weixinUser = new WeixinUser();
                    if (jsonObject.containsKey("openid")) {
                        weixinUser.setOpenid(jsonObject.getString("openid"));
                        weixinUser.setNickname(jsonObject.getString("nickname"));
                        weixinUser.setHeadimgurl(jsonObject.getString("headimgurl"));
                    }
                }

                if (weixinUser != null) {
                    weixinMemcache.putUser(openid, weixinUser);
                }
            } catch (JSONException e) {
                if (!StringUtil.isEmpty(openid)) {
                    weixinUser.setOpenid(openid);
                }
                e.printStackTrace();
                return weixinUser;
            }
        }
        return weixinUser;
    }

    /**
     * 根据微信openid，获取用户信息
     *
     * @param appId
     * @param appSecret
     * @param request
     * @return
     */
    public static Map<String, Object> getMapMessage(String appId, String appSecret, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> mapMessage = new HashMap<String, Object>();
        String code = request.getParameter("code");
        String openid = "";
        String oauthAccessToken = "";
        if (!StringUtil.isEmpty(code)) {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appSecret + "&grant_type=authorization_code&code=" + code;

            //  System.out.println("getMapMessage===url>" + url);
            JSONObject jsonObject = httpRequest(url, "GET", null);
            //   System.out.println("getMapMessage===jsonObject>" + jsonObject.toString());
            if (jsonObject != null) {
                if (jsonObject.containsKey("openid")) {
                    openid = jsonObject.getString("openid");
                    oauthAccessToken = jsonObject.getString("access_token");
                    CookieUtil.setCookie(request, response, COOKIE_OPENID, openid, TIME_OUT_SEC);
                    CookieUtil.setCookie(request, response, COOKIE_ACCESSTOKE, oauthAccessToken, TIME_OUT_SEC);
                } else {
                    openid = CookieUtil.getCookieValue(request, COOKIE_OPENID);
                    oauthAccessToken = CookieUtil.getCookieValue(request, COOKIE_ACCESSTOKE);
                }
            }
        }
        if (StringUtil.isEmpty(openid) || StringUtil.isEmpty(oauthAccessToken)) {
            openid = CookieUtil.getCookieValue(request, COOKIE_OPENID);
            oauthAccessToken = CookieUtil.getCookieValue(request, COOKIE_ACCESSTOKE);
        }


        String fullUrl = WeixinUtil.getFullUrl(request);
        // System.out.println("getMapMessage===fullUrl1>" + fullUrl);
        String wxhref = request.getParameter("wxhref");
        if (!StringUtil.isEmpty(wxhref)) {
            fullUrl = wxhref;
        }

        //System.out.println("getMapMessage===fullUrl2>" + fullUrl);

        //以下信息分享的时候需要 TODO
        String openid_timestamp = System.currentTimeMillis() / 1000 + ""; //时间以秒为单位
        String openid_nonce = "4b4cde46a659";//随机串
        String openid_signature = "";////加密结果
        try {
            //生成签名之前必须先了解一下jsapi_ticket，jsapi_ticket是公众号用于调用微信JS接口的临时票据。
            String ticket = WeixinUtil.getTicketCache(appId, appSecret);

            if (!StringUtil.isEmpty(ticket)) {
                openid_signature = getSignature(ticket, openid_timestamp, openid_nonce, fullUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapMessage.put("appId", appId);
        mapMessage.put("timestamp", openid_timestamp);
        mapMessage.put("nonceStr", openid_nonce);
        mapMessage.put("signature", openid_signature);
        mapMessage.put("openid", openid);
        WeixinUser weixinUser = null;

        // System.out.println("getMapMessage===openid>" + openid + ",oauthAccessToken-->" + oauthAccessToken);
        if (!StringUtil.isEmpty(openid) && !StringUtil.isEmpty(oauthAccessToken)) {
            weixinUser = getWeixinUserByAccesstoken(oauthAccessToken, openid);
            if (weixinUser == null) {
                System.out.println("weixinUser.is.null,oauthAccessToken=" + oauthAccessToken + ",openid=" + openid);
            }
        }
        mapMessage.put("weixinUser", weixinUser);
        return mapMessage;
    }

    /**
     * 创建自定义菜单
     *
     * @param menu        菜单列表
     * @param accessToken accessToken凭证 从getAccessToken()方法获取
     * @return
     */
    public static int createMenu(WeixinMenu menu, String accessToken) {
        int result = 0;

        // 拼装创建菜单的url
        String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.fromObject(menu).toString();
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

        if (null != jsonMenu) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                logger.error("创建菜单失败 errcode:{} errmsg:{}",
                        jsonObject.getInt("errcode"),
                        jsonObject.getString("errmsg"));
            }
        }
        return result;
    }
}
