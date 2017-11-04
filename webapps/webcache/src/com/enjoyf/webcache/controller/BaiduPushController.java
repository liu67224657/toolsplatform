package com.enjoyf.webcache.controller;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.util.WebCacheUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 百度实时推送的接口（参考一下百度推送的文档，百度的推送有许多的条件限制，可能会导致推送的失败，如：单个站点一天推送上限额限制，以及每次推送接口，最多推送2000条url等）
 * 由于url的规则转换都在webcache维护，所以没有在源站那边直接推送，
 * 而是源站那边在添加、编辑文章时，将源站的url传给接口，接口通过规则转换之后，将对外输出的url，推送给百度
 * Created by zhitaoshi on 2016/4/20.
 */
@Controller
@RequestMapping(value = "/api/baidu")
public class BaiduPushController {

    @ResponseBody
    @RequestMapping(value = "/push.do")
    public String push(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value = "srcurl", required = false) String srcUrl) {
        String callback = request.getParameter("callback");
        JSONObject jsonObject = new JSONObject();
        if (StringUtil.isEmpty(srcUrl)) {
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "param.empty");
            return returnJsonObjectCallback(jsonObject, callback);
        }
        if(!srcUrl.contains(".joyme.com/")){
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "domain.nut.joyme.com");
            return returnJsonObjectCallback(jsonObject, callback);
        }
        String pushUrl = "";

        try {
            Map<String, String> map = WebCacheUtil.genDesRule(srcUrl);
            if (map != null) {
                String srcRule = map.get("srcRule");
                String desRule = map.get("desRule");
                pushUrl = srcUrl.replace(srcRule, desRule);
            }else {
                if(srcUrl.contains("article.joyme.com/")){
                    if(srcUrl.contains("/article/pc/")){
                        srcUrl = srcUrl.replace("/article/pc/", "/article/");
                        map = WebCacheUtil.genDesRule(srcUrl);
                        if (map != null) {
                            String srcRule = map.get("srcRule");
                            String desRule = map.get("desRule");
                            pushUrl = srcUrl.replace(srcRule, desRule);
                        }
                    }
                }
            }
            if(StringUtil.isEmpty(pushUrl)){
                pushUrl = srcUrl;
            }
            if(!pushUrl.contains(".joyme.com/") || pushUrl.contains("article.joyme.com")){
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "domain.nut.joyme.com");
                return returnJsonObjectCallback(jsonObject, callback);
            }
            String schema = pushUrl.startsWith("http://") ? "http://" : "https://";
            String key = pushUrl.substring(schema.length(), pushUrl.indexOf(".joyme."));

            String apiUrl = PropertiesContainer.getInstance().getBaiduSitemapPushApi().replace("{domainKey}", key);
            String result = post(apiUrl, pushUrl);
            JSONObject jsonObject2 = JSONObject.fromObject(result);

            //这里由于cmsimage的部分功能还没有接合到webcache，所以这块只能掉cmsimage的接口写入db，建议以后把业务接过来
            if(jsonObject2 != null && jsonObject2.containsKey("success")){
                HttpClientManager httpClientManager = new HttpClientManager();
                httpClientManager.get("http://cmsimage.joyme.com/sitemap/insertlog.do", new HttpParameter[]{
                        new HttpParameter("key", key),
                        new HttpParameter("pushurl", pushUrl),
                        new HttpParameter("result", result)
                }, "UTF-8");
            }

            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", result);
            return returnJsonObjectCallback(jsonObject, callback);
        } catch (Exception e) {
            LogService.errorSystemLog("BaiduPushController occur Exception.e", e);
        }
        jsonObject.put("rs", "1");
        jsonObject.put("msg", "success");
        return returnJsonObjectCallback(jsonObject, callback);
    }

    private static String post(String apiUrl, String pushUrl) {
        //测试慎重
        //reqBody = "http://www.joyme.com/collection/热血传奇手机版";
        String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            //建立URL之间的连接
            URLConnection conn = new URL(apiUrl).openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("Host", "data.zz.baidu.com");
            conn.setRequestProperty("User-Agent", "curl/7.12.1");
            conn.setRequestProperty("Content-Length", String.valueOf(pushUrl.getBytes("UTF-8").length));
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Charset", "UTF-8");

            //IO发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //获取conn对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            out.print(pushUrl);
            //进行输出流的缓冲
            out.flush();
            //通过BufferedReader输入流来读取Url的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            LogService.errorSystemLog("发送post请求出现异常！", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private String returnJsonObjectCallback(JSONObject jsonObject, String callback) {
        if (StringUtil.isEmpty(callback)) {
            return jsonObject.toString();
        } else {
            return callback + "([" + jsonObject.toString() + "])";
        }
    }

}
