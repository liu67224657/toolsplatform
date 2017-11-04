package com.enjoyf.webcache.controller;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.cdn.CdnRefreshFactory;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.service.CacheService;
import com.enjoyf.webcache.service.WebCacheUrlRuleService;
import com.enjoyf.webcache.util.WebCacheUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拱php获取url转换规则的接口
 * Created by zhimingli on 2015/8/3.
 */
@Controller
@RequestMapping(value = "/json/urlrule")
public class JsonUrlRuleController {

    @ResponseBody
    @RequestMapping("/desrule.do")
    public String desRule(HttpServletRequest request){
        String callback = request.getParameter("callback");
        try {
            List<Map<String, String>> ruleList = new ArrayList<Map<String, String>>();
            String urlStr = request.getParameter("urls");
            if(urlStr.indexOf(",") > 0){
                String[] urlArr = urlStr.split(",");
                for(String srcUrl:urlArr){
                    Map<String, String> map = WebCacheUtil.genDesRule(srcUrl);
                    if(map != null){
                        String desUrl = srcUrl.replace(map.get("srcRule"), map.get("desRule"));
                        Map<String, String> resultMap = new HashMap<String, String>();
                        resultMap.put(srcUrl, desUrl);
                        ruleList.add(resultMap);
                    }

                }
            }else {
                Map<String, String> map = WebCacheUtil.genDesRule(urlStr);
                if(map != null){
                    String desUrl = urlStr.replace(map.get("srcRule"), map.get("desRule"));
                    Map<String, String> resultMap = new HashMap<String, String>();
                    resultMap.put(urlStr, desUrl);
                    ruleList.add(resultMap);
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", ruleList);
            if(StringUtil.isEmpty(callback)){
                return jsonObject.toString();
            }else {
                return callback + "(["+jsonObject.toString()+"])";
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "system.error");
            if(StringUtil.isEmpty(callback)){
                return jsonObject.toString();
            }else {
                return callback + "(["+jsonObject.toString()+"])";
            }
        }
    }
}


