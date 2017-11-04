package com.enjoyf.webcache.factory;

import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.util.WebCacheUtil;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/10/13.
 */
public class WikiCache extends AbstractWebCache {
    private final static String RESWIKI_FLAG = "reswiki.";

    private static Logger logger = LoggerFactory.getLogger(WikiCache.class);

    @Override
    public void processWebCache(String desRule, String srcRule, WebCacheUrlRule webCacheUrlRule, HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //用于5步跳转
        String host = request.getHeader("Host");
        if (host.contains(RESWIKI_FLAG)) {
            chain.doFilter(request, response);
        } else {
            String uri = request.getRequestURI();
            if(uri.indexOf(";jsessionid") > 0){
                uri = uri.substring(0, uri.indexOf(";jsessionid"));
            }
            if (uri.endsWith("/")) {
                uri += "index.shtml";
            }else {
                if(!uri.contains(".shtml")){
                    uri += "/index.shtml";
                }
            }
            uri = uri.replaceAll(request.getContextPath(), "");
            uri = "/" + uri.replaceAll("^/+", "");

            //用于得到url的string
            String desUrl = request.getScheme() + "://" + host + uri + (StringUtil.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
            URL url = new URL(desUrl);
            String path = url.getPath();
            logger.info("====WikiCache process full desUrl;"+desUrl+","+desRule+","+srcRule);

            String cacheFilePath = WebCacheUtil.getCacheFile(path, host);
            File cacheFile = new File(cacheFilePath);
            //判断是否需要重新抓取
            boolean needRefresh = WebCacheUtil.checkNeedRefreshCache(cacheFile);
            if (needRefresh) {
                String srcUrl = desUrl.replace(desRule, srcRule);
                if(StringUtil.isEmpty(srcUrl) || srcUrl.equals(desUrl)){
                    response.setStatus(404);
                    chain.doFilter(request, response);
                    return;
                }
                logger.info("====WikiCache process srcUrl;"+srcUrl);
                //抓取article
                Map<String, Object> result = URLUtil.openURLConnectionWithTimeOut(srcUrl, response);
                if (result != null) {
                    //抓取 成功 解析
                    processFile(result, host, path, desUrl, srcUrl, desRule, srcRule,  request, response, chain, webCacheUrlRule);
                } else {
                    //没抓到 404
                    logger.info("====WikiCache process 404 open url;"+srcUrl);
                    response.setStatus(404);
                    chain.doFilter(request, response);
                    return;
                }
            } else {
                logger.info("====WikiCache process cacheFile exits display:"+cacheFile);
                outPutFile(response, cacheFile);
                return;
            }
        }
    }

    @Override
    public String saveFile(String responseText, String host, String path, String desUrl, String srcUrl, String desRule, String srcRule, WebCacheUrlRule webCacheUrlRule) throws IOException {
        if (StringUtil.isEmpty(responseText)) {
            return null;
        }
        Document doc = Jsoup.parse(responseText);
        // check when network is error
        if (isErrorPage(doc)) {
            return null;
        }

        removeRobots(doc);

        addMetaDevice(doc, webCacheUrlRule);

        if(webCacheUrlRule.getGlobalSetting() != null){
            //add pv
            addPvScript(doc, webCacheUrlRule.getGlobalSetting().getPvSetting());
            //pc jump m
            String jumpRule = webCacheUrlRule.getGlobalSetting().getPcWapTabSetting();
            if(!StringUtil.isEmpty(jumpRule)){
                addPcJumpMJs(doc, desUrl.replace(desRule, jumpRule));
            }
        }

        // parse href
        parseHref(doc, host, desUrl, srcUrl, desRule, srcRule);

        //IWT流量跟踪系统
        addIWTJs(doc);

        if (!desUrl.endsWith(".xml")) {
            return makeNormalFileType(host, path, doc);
        } else {
            return makeXmlFileType(host, path, doc);
        }
    }

    protected void parseHref(Document doc, String host, String desUrl, String srcUrl, String desRule, String srcRule) {
        try {
            List elements = doc.getElementsByTag("a");
            for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                String href = element.attr("href");

                if (StringUtil.isEmpty(href) || href.startsWith(HREF_FLAG_JAVASCRIPT) || href.equals("#")) {
                    if (StringUtil.isEmpty(href)) {
                        element.attr("href", "javascript:void(0);");
                    }
                    continue;
                }

                if (!StringUtil.isEmpty(desRule) && !StringUtil.isEmpty(srcRule) && href.contains(srcRule)) {
                    href = href.replace(srcRule, desRule);
                } else {
                    //找出规则  替换
                    String temDesRule = null;
                    String tmpSrcRule = null;
                    Map<String, String> map = WebCacheUtil.genDesRule(href);
                    if (map != null) {
                        temDesRule = map.get("desRule");
                        tmpSrcRule = map.get("srcRule");
                        href = href.replace(tmpSrcRule, temDesRule);
                    }
                }
                element.attr("href", href);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            logger.info("WikiCache parseHref occur MalformedURLException:"+desUrl);
        }
    }
}
