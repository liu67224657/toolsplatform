package com.enjoyf.webcache.factory;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;
import com.enjoyf.webcache.bean.*;
import com.enjoyf.webcache.cache.UrlRuleCache;
import com.enjoyf.webcache.service.WebCacheUrlRuleService;
import com.enjoyf.webcache.util.WebCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/9/18.
 */
public class WebCacheProcess extends AbstractProcess {

    private static Logger logger = LoggerFactory.getLogger(WebCacheProcess.class);
    private static WebCacheUrlRuleService webCacheUrlRuleService = new WebCacheUrlRuleService();
    private static UrlRuleCache urlRuleCache = new UrlRuleCache();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, JoymeServiceException, JoymeDBException {
        //读取 配置表
        WebCacheUrlRule webCacheUrlRule = null;
        String requestURL = request.getRequestURL().toString();
        if(requestURL.indexOf(";jsessionid") > 0){
            requestURL = requestURL.substring(0, requestURL.indexOf(";jsessionid"));
        }
        URL url = new URL(requestURL);
        logger.info("====WebCacheProcess process url:"+requestURL);
        try {
            //通过request url 转换出 src url
            Map<String, String> map = WebCacheUtil.genSrcRule(requestURL);
            //如果 有 配置数据
            if (map != null) {
                String srcRule = map.get("srcRule");
                String desRule = map.get("desRule");

                // 匹配到 的 rule
                webCacheUrlRule = urlRuleCache.getUrlRuleMemCache(desRule);
                if(webCacheUrlRule == null){
                    webCacheUrlRule = webCacheUrlRuleService.getWebCacheUrlRule(null, MD5Util.Md5(desRule));
                    if(webCacheUrlRule != null){
                        urlRuleCache.putUrlRuleMemCache(desRule, webCacheUrlRule);
                    }
                }
                if(webCacheUrlRule != null){
                    WebCacheSrcType srcType = webCacheUrlRule.getSrcType();
                    //src type的工厂方法，
                    WebCacheFactory.getFactory(srcType).processWebCache(desRule, srcRule, webCacheUrlRule, request, response, chain);
                }else {
                    logger.info("====WebCacheProcess process 500 rule is null:"+requestURL);
                    response.setStatus(500);
                    chain.doFilter(request, response);
                }
            } else {
                //没有配置  直接 500
                logger.info("====WebCacheProcess process 500 rule is null:"+requestURL);
                response.setStatus(500);
                chain.doFilter(request, response);
            }
        } finally {
            //访问过的url， 放入缓存，在WebcacheUrlJob定时器中，将url记录到db的webcache_uel表中
            WebCacheUrl webCacheUrl = new WebCacheUrl();
            if(webCacheUrlRule != null){
                webCacheUrl.setRule_id(webCacheUrlRule.getRuleId());
            }
            webCacheUrl.setUrl(requestURL);
            if(url != null){
                webCacheUrl.setUrlKey(url.getHost());
            }
            webCacheUrl.setHttpCode(response.getStatus());
            webCacheUrl.setUrlId(MD5Util.Md5(requestURL));
            webCacheUrl.setChannel("");
            webCacheUrl.setUrlType("");
            webCacheUrl.setLastModifyTime(new Date());
            urlRuleCache.saddUrlRules(webCacheUrl);
        }
    }
}
