package com.enjoyf.activity.weixin;

import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.facade.GoodsFacade;
import com.enjoyf.activity.weixin.util.BasicButton;
import com.enjoyf.activity.weixin.util.CommonButton;
import com.enjoyf.activity.weixin.util.ComplexButton;
import com.enjoyf.activity.weixin.util.WeixinMenu;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.util.StringUtil;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xupeng on 16/2/2.
 */
public class WeixinMenuMain {

    /**
     * @param args
     */
    private static String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private static final String APPID = "wx6adc249f511f2c93";   //订阅号 wx529ba0bd21499c1a
    private static final String SECRET = "fc467e22b19d21a5e045f598744e5841";   //订阅号 c54c523397a9a2330a8d6856dceadf2c

    /*private static final String APPID = "wx529ba0bd21499c1a";   //订阅号
    private static final String SECRET = "c54c523397a9a2330a8d6856dceadf2c";   //订阅号*/

    public static void main(String[] args) {
        try {
            //获取token
            String accessToken = getAccessToken(APPID,SECRET).getToken();
            if (!StringUtil.isEmpty(accessToken)) {
                //生成菜单  菜单生成规则参考微信文档 http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html
                int i = WeixinUtil.createMenu(getWeixinMenu(), accessToken);
                System.out.println(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static WeixinMenu getWeixinMenu() {
        // CommonButton为单个菜单类
        // ComplexButton为组合菜单 里面可放多个CommonButton 微信最多支持5个子菜单


        CommonButton peopleButton = new CommonButton();
        peopleButton.setName("名人堂");
        peopleButton.setKey("people");
        peopleButton.setType("click");

        CommonButton scoreMarketButton = new CommonButton();
        scoreMarketButton.setName("积分商城");
        scoreMarketButton.setKey("scoreMarket");
        scoreMarketButton.setType("click");

        CommonButton gameDownloadButton = new CommonButton();
        gameDownloadButton.setName("游戏下载");
        gameDownloadButton.setUrl("http://qyz.laohu.com/m/download1/index.html");
        gameDownloadButton.setType("view");

        CommonButton dailySignButton = new CommonButton();
        dailySignButton.setName("每日签到");
        dailySignButton.setType("click");
        dailySignButton.setKey("dailySign");

        CommonButton dailyQuestionButton = new CommonButton();
        dailyQuestionButton.setName("每日一题");
        dailyQuestionButton.setType("click");
        dailyQuestionButton.setKey("dailyQuestion");

        ComplexButton teaShopButton = new ComplexButton();
        teaShopButton
                .setSub_button(new BasicButton[]{peopleButton,scoreMarketButton,gameDownloadButton,
                        dailySignButton, dailyQuestionButton});
        teaShopButton.setName("青云福利");


        //关于青云
        CommonButton orgWebButton = new CommonButton();
        orgWebButton.setName("官方网站");
        orgWebButton.setUrl("http://qyz.laohu.com/");
        orgWebButton.setType("view");

        CommonButton handVideoButton = new CommonButton();
        handVideoButton.setName("手游视频");
        handVideoButton.setType("view");
        handVideoButton.setUrl("http://bbsqyz.hryouxi.com/forum.php?mod=viewthread&tid=220&extra=page%3D1");

        CommonButton battleButton = new CommonButton();
        battleButton.setName("攻略站");
        battleButton.setType("view");
        battleButton.setUrl("http://qyz.joyme.com/wxwiki/");

        CommonButton orgQQButton = new CommonButton();
        orgQQButton.setName("官方Q群");
        orgQQButton.setKey("orgQQ");
        orgQQButton.setType("click");

        CommonButton linkSupportButton = new CommonButton();
        linkSupportButton.setName("联系客服");
        linkSupportButton.setType("click");
        linkSupportButton.setKey("linkSupport");

        ComplexButton dailyReportButton = new ComplexButton();
        dailyReportButton
                .setSub_button(new BasicButton[]{orgWebButton,
                        handVideoButton, battleButton,orgQQButton,linkSupportButton});
        dailyReportButton.setName("关于青云");



        CommonButton seeAgainButton = new CommonButton();
        seeAgainButton.setName("「再」见碧瑶");
        seeAgainButton.setType("view");
        seeAgainButton.setUrl("http://qyz.laohu.com/zjby/");

        //生成的结果页 放入对应的类
        WeixinMenu weixinMenu = new WeixinMenu();
        
        weixinMenu.setButton(new BasicButton[]{dailyReportButton,seeAgainButton,
                teaShopButton });

        return weixinMenu;
    }
    /**
     * 获取access_token
     *
     * @param appId     凭证
     * @param appSecret 密匙
     * @return
     */
    private static AccessToken getAccessToken(String appId, String appSecret) {

        AccessToken accessToken = null;
        if (accessToken == null) {
            String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
            JSONObject jsonObject = httpRequest(access_token_url, "GET", null);
            try {
                if (jsonObject != null) {
                    accessToken = new AccessToken();
                    accessToken.setToken(jsonObject.getString("access_token"));
                    accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return accessToken;
    }
    /**
     * 发起HTTPS请求并获得结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（get/post）
     * @param outputStr     提交的数据
     * @return
     */
    private static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
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
        } catch (Exception e) {
        }
        return null;
    }
}
