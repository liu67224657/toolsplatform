package com.enjoyf.webcache.factory;

import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.bean.WebCacheClientType;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.cache.UrlRuleCache;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.service.TemplateService;
import com.enjoyf.webcache.util.WebCacheUtil;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/9/30.
 */
public abstract class AbstractWebCache implements IWebCache {

    private static Logger logger = LoggerFactory.getLogger(AbstractWebCache.class);
    private static TemplateService templateService = new TemplateService();

    protected final static String HREF_FLAG_JAVASCRIPT = "javascript";

    @Override
    public void processWebCache(String desRule, String srcRule, WebCacheUrlRule webCacheUrlRule, HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        return;
    }

    protected void processFile(Map<String, Object> result, String host, String path, String desUrl, String srcUrl, String desRule, String srcRule, HttpServletRequest request, HttpServletResponse response, FilterChain chain, WebCacheUrlRule webCacheUrlRule) throws IOException, ServletException {
        int responseCode = 404;
        if (result.get("responseCode") != null) {
            responseCode = (Integer) result.get("responseCode");
        }
        if (responseCode == 200) {
            String responseText = (String) result.get("responseText");
            //解析 并 保存
            String html = saveFile(responseText, host, path, desUrl, srcUrl, desRule, srcRule, webCacheUrlRule);
            if (!StringUtil.isEmpty(html)) {
                //输出
                this.printWriterHtml(response, html);
                return;
            } else {
                //解析 html内容出错！ 500
                logger.info("====WebCache process save file error.desUrl" + desUrl + ",srcUrl:" + srcUrl);
                response.setStatus(500);
                chain.doFilter(request, response);
                return;
            }
        } else {
            //状态码 不是200
            logger.info("====WebCache process error:" + responseCode);
            response.setStatus(responseCode);
            chain.doFilter(request, response);
            return;
        }
    }

    private void printWriterHtml(HttpServletResponse response, String html) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(html);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    protected void outPutFile(HttpServletResponse response, File file) throws IOException, FileNotFoundException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        FileInputStream is = new FileInputStream(file);

        OutputStream os = null;
        try {
            os = response.getOutputStream();
            byte[] buff = new byte[8192];
            int len = -1;
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }

        } finally {
            if (is != null) {
                is.close();
                is = null;
            }

            if (os != null) {
                os.flush();
                os.close();
                os = null;
            }
        }
    }

    @Override
    public String saveFile(String responseText, String host, String path, String desUrl, String srcUrl, String desRule, String srcRule, WebCacheUrlRule webCacheUrlRule) throws IOException {
        return responseText;
    }

    protected void addPvScript(Document doc, String pvHtml) {
        if (!StringUtil.isEmpty(pvHtml)) {
            List elements = doc.getElementsByTag("body");
            if (elements.size() >= 1) {
                Element body = (Element) elements.get(0);
                body.append(pvHtml);
            }
        }
    }

    protected void addPcJumpMJs(Document doc, String jumpUrl) {
        if (!StringUtil.isEmpty(jumpUrl)) {
            List elements = doc.getElementsByTag("head");
            if (elements.size() >= 1) {
                Element head = (Element) elements.get(0);
                String jumpHTML = templateService.getJumpTemplate();
                jumpHTML = jumpHTML.replace("{jumpurl}", jumpUrl);
                head.append(jumpHTML);
            }
        }
    }

    protected boolean isErrorPage(Document doc) {
        Element element = doc.getElementById("TITLE");
        if (element != null) {
            return element.text().equals("联通域名服务提示");
        } else {
            return false;
        }
    }

    protected void addJquery(Document doc) {
        if (doc.html().indexOf("jquery-1.9.1.min.js") < 0) {
            Elements elements = doc.getElementsByTag("head");
            if (elements.size() >= 1) {
                Element element = elements.get(0);
                Element jqueryElement = element.appendElement("script");
                jqueryElement.attr("src", PropertiesContainer.getInstance().getJqueryJs());
                jqueryElement.attr("language", "javascript");
            }
        }
    }

    protected String getBody(Document doc) {
        return doc.getElementsByTag("body").get(0).html();
    }

    protected void removeRobots(Document doc) {
        List elements = doc.getElementsByTag("meta");
        if (elements.size() > 0) {
            for (int i = 0; i < elements.size(); i++) {
                Element element = (Element) elements.get(i);
                if (element.attr("name").contains("robots")) {
                    element.remove();
                }
            }
        }
    }

    protected void addMetaDevice(Document doc, WebCacheUrlRule webCacheUrlRule) {
        String html = "";
        if (webCacheUrlRule.getClientType().equals(WebCacheClientType.PC)) {
            html = "<meta name=\"applicable-device\"content=\"pc\">" +
                    "<meta name=\"mobile-agent\"content=\"format=xhtml;url=http://m.joyme.com/\">" +
                    "<meta name=\"mobile-agent\" content=\"format=html5;url=http://m.joyme.com\">";
        } else {
            html = "<meta name=\"applicable-device\"content=\"mobile\">";
        }
        Elements elements = doc.getElementsByTag("head");
        if (elements.size() > 0) {
            Element element = elements.get(0);
            Elements metaElements = element.getAllElements();
            if (metaElements.size() > 0) {
                Element meta = metaElements.get(1);
                meta.before(html);
            }
        }
    }

    protected String makeXmlFileType(String host, String path, Document doc) throws IOException {
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + getBody(doc);
        String seoFile = WebCacheUtil.getCacheFile(path, host);
        FileUtils.writeStringToFile(new File(seoFile), str, "utf-8");
        return str;
    }

    protected String makeNormalFileType(String host, String path, Document doc) throws IOException {
        String returnHtml = "";
        // save seo
        String seoFile = WebCacheUtil.getCacheFile(path, host);
        FileUtils.writeStringToFile(new File(seoFile), doc.outerHtml(), "UTF-8");
        returnHtml = doc.html();
        return returnHtml;
    }

    protected void addIWTJs(Document doc) {
        List elements = doc.getElementsByTag("body");
        if (elements.size() >= 1) {
            Element body = (Element) elements.get(0);
            String iwtHtml = templateService.getIWTTemplate();
            body.append(iwtHtml);
        }
    }
}
