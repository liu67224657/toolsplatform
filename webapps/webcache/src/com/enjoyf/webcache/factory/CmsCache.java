package com.enjoyf.webcache.factory;

import com.enjoyf.util.PlayerUtil;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import com.enjoyf.util.video.VideoReplaceUtil;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.util.WebCacheUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/9/30.
 */
public class CmsCache extends AbstractWebCache {

    private static Logger logger = LoggerFactory.getLogger(CmsCache.class);

    private final static String ARTICLE_FLAG = "/article";
    private final static String ARTICLE_PATH_FLAG = "/article/";
    private final static String VIP_FLAG = "/vip";
    private final static String TAG_FLAG = "/tags/";
    private final static String NEWS_FLAG = "/news/";
    private final static String[] VIP_CHINESE_FLAG = new String[]{"重点游戏专区", "新闻中心"};
    private final static String WWW_JOYME_DOMAIN = "http://www.joyme.com/article";

    //todo error desRule srcRule
    @Override
    public void processWebCache(String desRule, String srcRule, WebCacheUrlRule webCacheUrlRule, HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //得到 完整的url
        String host = request.getHeader("Host");
        String requestURL = request.getRequestURL().toString();
        if (requestURL.indexOf(";jsessionid") > 0) {
            requestURL = requestURL.substring(0, requestURL.indexOf(";jsessionid"));
        }
        URL url = new URL(requestURL);
        String path = url.getPath();
        if (path.endsWith("/")) {
            path += "index.html";
        } else {
            if (!path.contains(".html")) {
                path += "/index.html";
            }
        }
        String desUrl = request.getScheme() + "://" + host + path + (StringUtil.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString());
        url = new URL(desUrl);
        path = url.getPath();
        logger.info("====CmsCache process full desUrl;" + desUrl);

        //获取缓存文件
        String cacheFilePath = WebCacheUtil.getCacheFile(path, host);
        File cacheFile = new File(cacheFilePath);
        //判断是否需要重新抓取
        boolean needRefresh = WebCacheUtil.checkNeedRefreshCache(cacheFile);
        if (needRefresh) {
            //替换出源站的srcurl
            String srcUrl = desUrl.replace(desRule, srcRule);
            if (StringUtil.isEmpty(srcUrl) || srcUrl.equals(desUrl)) {
                response.setStatus(404);
                chain.doFilter(request, response);
                return;
            }
            logger.info("====CmsCache process srcUrl;" + srcUrl);
            //抓取源站的页面内容
            Map<String, Object> result = URLUtil.openURLConnectionWithTimeOut(srcUrl, response);
            if (result != null) {
                //抓取成功  解析
                processFile(result, host, path, desUrl, srcUrl, desRule, srcRule, request, response, chain, webCacheUrlRule);
            } else {
                //没抓到 404
                logger.info("====CmsCache process 404 open url;" + srcUrl);
                response.setStatus(404);
                chain.doFilter(request, response);
                return;
            }
        } else {
            logger.info("====CmsCache process cacheFile exits display:" + cacheFile);
            outPutFile(response, cacheFile);
            return;
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
        //判断页面有没有jquery文件
        addJquery(doc);
        //seo需求 去掉源文件head中的robots meta
        removeRobots(doc);
        //seo需求 区分页面是m还是pc
        addMetaDevice(doc, webCacheUrlRule);

        if (webCacheUrlRule.getGlobalSetting() != null) {
            //判断后台配置有没有填写piwik代码，如果有，就添加到页面，（只有少数的几个，运营那边的习惯都写在了模版源文件的模版里，但是那样piwik里面有许多的article链接）
            addPvScript(doc, webCacheUrlRule.getGlobalSetting().getPvSetting());

            //手机访问www页面自动跳转m页面
            String jumpRule = webCacheUrlRule.getGlobalSetting().getPcWapTabSetting();
            if (!StringUtil.isEmpty(jumpRule)) {
                addPcJumpMJs(doc, desUrl.replace(desRule, jumpRule));
            }
        }

        //TODO 临时将视频显示修改
        // VideoReplaceUtil.process(doc);


        //将源页面的a标签中的href超链接统一转换成对外输出的url
        parseHref(doc, host, desUrl, srcUrl, desRule, srcRule);

        //以前图片的src相对路径的引用，转成源站的绝对路径，防止url被转换，后来的新业务已经都迁移到static了
        parseImage(doc);

        //图片的懒加载规则，前端提供的网站统一规则
        parseLazyImage(doc);

        //以前css的相对路径的引用，转成源站的绝对路径，防止url被转换，后来的新业务已经都迁移到static了
        parseCss(doc);

        //以前js的相对路径的引用，转成源站的绝对路径，防止url被转换，后来的新业务已经都迁移到static了
        parseJs(doc);

        //以前style的相对路径的引用，转成源站的绝对路径，防止url被转换，后来的新业务已经都迁移到static了
        parseStyle(doc);

        //以前的业务兼容，处理导航栏中重点游戏专区字样
        parseNavigate(doc);

        //游戏下载的引入
        addGameDownloadJs(doc);

        //着迷应用下载的引入
        addAppDownloadJs(doc);

        //以前image中style的相对路径的引用，转成源站的绝对路径，防止url被转换，后来的新业务已经都迁移到static了
        parseImageOfStyle(doc);

        //评论的js代码
        addCommentJs(doc, desUrl);

        //直播间的js代码，逻辑同评论
        addJoymeLiveJs(doc);
        //业务需求，替换优酷播放器的logo
        parseYoukuPlayer(doc, desUrl);

        //seo需求 添加IWT流量跟踪代码
        addIWTJs(doc);

        if (!desUrl.endsWith(".xml")) {
            return makeNormalFileType(host, path, doc);
        } else {
            return makeXmlFileType(host, path, doc);
        }
    }

    protected void parseHref(Document doc, String host, String desUrl, String srcUrl, String desRule, String srcRule) {
        System.out.println("descurl:" + desRule + ",srcurl:" + srcUrl + ",desrule:" + desRule + ",srcrule:" + srcRule);
        List elements = doc.getElementsByTag("a");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String href = element.attr("href");
            //三种情况不需要转换
            if (StringUtil.isEmpty(href) || href.startsWith(HREF_FLAG_JAVASCRIPT) || href.equals("#")) {
                if (StringUtil.isEmpty(href)) {
                    element.attr("href", "javascript:void(0);");
                }
                continue;
            }

            String srcScheme = srcUrl.contains("https://") ? "https://" : "http://";
            String srcHost = srcUrl.replace(srcScheme, "");
            srcHost = srcHost.substring(0, srcHost.indexOf("/"));
            //以 / 开头的绝对路径
            if (href.startsWith("/")) {
                if (href.startsWith("/article/")) {
                    //article/pc 和 article 都是PC的默认模板,
                    //所以将/article/pc 替换成  /article
                    //Notice.在后台规则配置中源站URL 应该配置成 http://article.joyme.alpha/article/zhibo/ 而不是 http://article.joyme.alpha/article/pc/zhibo/
                    if (href.startsWith("/article/pc/")) {
                        href = href.replace("/article/pc/", "/article/");
                    }
                } else {
                    href = "/article" + href;
                }
                String srcHref = srcScheme + srcHost + href;
                //转换出对外输出的url
                String desHref = WebCacheUtil.genUrlByRuleurl(srcHref, desRule, srcRule, host);
                element.attr("href", desHref);
            } else if (href.startsWith("../")) {
                //以 ../开头的相对路径
                String srcHref = srcScheme + srcHost;
                String srcPath = srcUrl.replace(srcHref, "");
                srcPath = srcPath.substring(0, srcPath.lastIndexOf("/")).replaceFirst("/", "");
                String[] temArray = null;
                if (srcPath.contains("/")) {
                    temArray = srcPath.split("/");
                } else {
                    temArray = new String[]{srcPath};
                }
                //用当前源站的url，从后向前，补足所有的 ../
                List<String> temList = genHref(href, new ArrayList<String>());
                int j = temArray.length - temList.size() - 1;
                if (j < 0) {
                    srcHref = srcHref + "/" + href.replaceAll("\\.\\.\\/", "");
                } else {
                    for (int i = 0; i <= j; i++) {
                        srcHref += "/" + temArray[i];
                    }
                    srcHref += "/" + href.replaceAll("\\.\\.\\/", "");
                }
                //替换成输出的url
                String desHref = WebCacheUtil.genUrlByRuleurl(srcHref, desRule, srcRule, host);
                element.attr("href", desHref);
            } else {
                //其他只要不是http开头的url
                if (!href.startsWith("http://") && !href.startsWith("https://")) {
                    //加上当前源站的 域名，再替换
                    String srcHref = srcUrl.substring(0, srcUrl.lastIndexOf("/")) + "/" + href;
                    String desHref = WebCacheUtil.genUrlByRuleurl(srcHref, desRule, srcRule, host);
                    element.attr("href", desHref);
                }
            }
        }
    }


    protected List<String> genHref(String href, List<String> temList) {
        do {
            if (href.startsWith("../")) {
                temList.add("../");
                href = href.replaceFirst("../", "");
            }
        } while (href.startsWith("../"));
        return temList;
    }

    protected void parseImage(Document doc) {
        List elements = doc.getElementsByTag("img");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String src = element.attr("src");
            if (src != null && src.startsWith("/")) {
                String finalPath = "http://" + PropertiesContainer.getInstance().getphpCmsDomain() + src;
                element.attr("src", finalPath);
            }

            if (src != null && src.startsWith(WWW_JOYME_DOMAIN)) {
                String replacePath = "http://" + PropertiesContainer.getInstance().getphpCmsDomain() + "/article";
                String finalPath = src.replaceAll(WWW_JOYME_DOMAIN, replacePath);
                element.attr("src", finalPath);
            }

            String onclick = element.attr("onclick");
            if (onclick != null && onclick.contains(ARTICLE_FLAG)) {
                onclick = onclick.replaceAll(ARTICLE_FLAG, "http://" + PropertiesContainer.getInstance().getphpCmsDomain() + ARTICLE_FLAG);
                element.attr("onclick", onclick);
            }
        }
    }

    protected void parseLazyImage(Document doc) {
        List elements = doc.getElementsByTag("img");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String src = element.attr("data-url");
            if (src != null && src.startsWith("/")) {
                String finalPath = "http://" + PropertiesContainer.getInstance().getphpCmsDomain() + src;
                Map<String, String> map = WebCacheUtil.genDesRule(finalPath);
                if (map != null) {
                    String desUrl = map.get("desRule");
                    String srcUrl = map.get("srcRule");
                    finalPath = finalPath.replace(srcUrl, desUrl);
                }
                element.attr("data-url", finalPath);
            }

            if (src != null && src.startsWith(WWW_JOYME_DOMAIN)) {
                String replacePath = "http://" + PropertiesContainer.getInstance().getphpCmsDomain() + "/article";
                String finalPath = src.replaceAll(WWW_JOYME_DOMAIN, replacePath);
                element.attr("data-url", finalPath);
            }

            String onclick = element.attr("onclick");
            if (onclick != null && onclick.contains(ARTICLE_FLAG)) {
                onclick = onclick.replaceAll(ARTICLE_FLAG, "http://" + PropertiesContainer.getInstance().getphpCmsDomain() + ARTICLE_FLAG);
                element.attr("onclick", onclick);
            }
        }
    }

    protected void parseCss(Document doc) {
        List elements = doc.getElementsByTag("link");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String href = element.attr("href");
            if (href != null && href.startsWith("/")) {
                String finalPath = "http://" + PropertiesContainer.getInstance().getphpCmsDomain() + href;
                element.attr("href", finalPath);
            }
        }
    }

    protected void parseJs(Document doc) {
        List elements = doc.getElementsByTag("script");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            if (element.hasAttr("src")) {
                String src = element.attr("src");
                if (src.startsWith("/")) {
                    src = "http://" + PropertiesContainer.getInstance().getphpCmsDomain() + src;
                }
                element.attr("src", src);
            }
        }
    }

    protected void parseStyle(Document doc) {
        List elements = doc.getElementsByTag("style");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String text = element.html();
            if (text != null && text.contains(WWW_JOYME_DOMAIN)) {
                String replacePath = "http://" + PropertiesContainer.getInstance().getphpCmsDomain() + "/article";
                text = text.replaceAll(WWW_JOYME_DOMAIN, replacePath);
                element.html(text);
            }

        }
    }

    protected void parseNavigate(Document doc) {
        List elements = doc.getElementsByTag("div");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            if (element.attr("class").startsWith("location")) {
                String html = element.html();
                String[] strs = html.split("&gt;");
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < strs.length; i++) {
                    boolean isContainKeyWords = isContainKeyWord(strs[i]);
                    if (!isContainKeyWords)
                        sb.append(strs[i]).append("&gt");
                }

                String retHtml = StringUtil.removeLastCharacter(sb.toString(), "&gt");
                element.html(retHtml);
            }
        }
    }

    protected boolean isContainKeyWord(String str) {
        for (int j = 0; j < VIP_CHINESE_FLAG.length; j++) {
            if (str.contains(VIP_CHINESE_FLAG[j])) {
                return true;
            }
        }
        return false;
    }

    protected void addGameDownloadJs(Document doc) throws IOException {
        //http://webcache.joyme.com/js/game-download.js
        //只有页面包含此html才 加入js
        if (doc.html().indexOf("joyme-download") > 0 && doc.html().indexOf("game-download.js") < 0) {
            Elements elements = doc.getElementsByTag("body");
            if (elements.size() >= 1) {
                Element element = elements.get(0);
                Element commentElement = element.appendElement("script");
                commentElement.attr("src", PropertiesContainer.getInstance().getJoymeGameDownloadJs());
                commentElement.attr("language", "javascript");
            }
        }
    }


    protected void addAppDownloadJs(Document doc) throws IOException {
        //http://webcache.joyme.com/js/app-download.js
        //只有页面包含此html才 加入js
        if (doc.html().indexOf("joyme-appdownload") > 0 && doc.html().indexOf("app-download.js") < 0) {
            Elements elements = doc.getElementsByTag("body");
            if (elements.size() >= 1) {
                Element element = elements.get(0);
                Element commentElement = element.appendElement("script");
                commentElement.attr("src", PropertiesContainer.getInstance().getJoymeAppDownloadJs());
                commentElement.attr("language", "javascript");
            }
        }
    }

    //modify by ericliu 2016-10-08
    protected void parseImageOfStyle(Document doc) {
        //get a link
        Elements elements = doc.getElementsByTag("a");
        if (elements == null) {
            return;
        }

        //loop a link element's style and replace style ARTICLE_PATH_FLAG to http://article.joyme.com/article/
        for (Element element : elements) {
            if (!element.hasAttr("style")) {
                continue;
            }

            String value = element.attr("style");
            if (StringUtil.isEmpty(value)) {
                continue;
            }
            String[] styleArray = value.split(";");
            String tmpValue = "";
            for (String style : styleArray) {
                if (style.contains(ARTICLE_PATH_FLAG) && !style.contains("http://") && !style.contains("https://")) {
                    style = style.replaceAll(ARTICLE_PATH_FLAG, "http://article.joyme.com/article/");
                }
                tmpValue += style + ";";
            }
            element.attr("style", tmpValue);
        }
    }

    protected void addCommentJs(Document doc, String desUrl) {
        //这个id是跟运营那边约定好的，运营在页面上引入评论的地方埋一个隐藏的div，我们判断div是否存在，存在标识该页面有评论的引入，那么把评论的js代码添加到页面上
        //除了一些通用的js之外，一些个别页面的特定功能引入js代码，都是这个思路。
        Element areaEle = doc.getElementById("comment_area");
        Element areaLiveEle = doc.getElementById("comment_area_live");

        /**
         * 配置新版本js
         */
        if (areaEle != null) {
            if (doc.html().indexOf("comment.js") < 0) {
                Elements elements = doc.getElementsByTag("body");
                if (elements.size() >= 1) {
                    Element element = elements.get(0);
                    Element commentElement = element.appendElement("script");
                    commentElement.attr("src", PropertiesContainer.getInstance().getJoymeCommentJs());
                    commentElement.attr("language", "javascript");
                }
            }
        }

        //login
        if (areaEle != null || areaLiveEle != null) {
            //M端登录JS and CSS
            if (desUrl.contains("m.joyme") && doc.html().indexOf("login_common.js") < 0) {
                Elements elements = doc.getElementsByTag("body");
                if (elements.size() >= 1) {
                    Element element = elements.get(0);
                    Element commentElement = element.appendElement("script");
                    commentElement.attr("src", PropertiesContainer.getInstance().getLoginCommonJs());
                    commentElement.attr("language", "javascript");
                }
                Elements headElements = doc.getElementsByTag("head");
                if (headElements.size() >= 1) {
                    Element element = elements.get(0);
                    Element commentElement = element.appendElement("link");
                    commentElement.attr("href", "http://static." + PropertiesContainer.getInstance().getJoymeDomain() + "/mobile/cms/jmsy/logincont/login201611.css");
                    commentElement.attr("rel", "stylesheet");
                    commentElement.attr("type", "text/css");
                }
            }
        }

        /**
         * 配置老版本js
         */
        Element areaEle_old = doc.getElementById("comment_area_old");
        if (areaEle_old != null) {
            //防止js的重复添加（有时候运营经常copy模版，可能会误引入一些，在源页面不该印务的js）
            if (doc.html().indexOf("comment-old.js") < 0) {
                Elements elements = doc.getElementsByTag("body");
                if (elements.size() >= 1) {
                    Element element = elements.get(0);
                    Element commentElement = element.appendElement("script");
                    commentElement.attr("src", PropertiesContainer.getInstance().getJoymeOldCommentJs());
                    commentElement.attr("language", "javascript");
                }
            }
        }


    }

    protected void addJoymeLiveJs(Document doc) {
        Element areaEle = doc.getElementById("live_area");
        if (areaEle != null) {
            if (doc.html().indexOf("joymelive.js") < 0) {
                Elements elements = doc.getElementsByTag("body");
                if (elements.size() >= 1) {
                    Element element = elements.get(0);
                    Element commentElement = element.appendElement("script");
                    commentElement.attr("src", PropertiesContainer.getInstance().getJoymeLiveJs());
                    commentElement.attr("language", "javascript");
                }
            }
        }


        Element areaEleNew = doc.getElementById("live_area_new");
        if (areaEleNew != null) {
            if (doc.html().indexOf("joymelive-new.js") < 0) {
                Elements elements = doc.getElementsByTag("body");
                if (elements.size() >= 1) {
                    Element element = elements.get(0);
                    Element commentElement = element.appendElement("script");
                    commentElement.attr("src", PropertiesContainer.getInstance().getJoymeLiveNewJs());
                    commentElement.attr("language", "javascript");
                }
            }
        }

        /**
         * 配置版本js
         */
        Element areaEle_live = doc.getElementById("comment_area_live");
        if (areaEle_live != null) {
            //防止js的重复添加（有时候运营经常copy模版，可能会误引入一些，在源页面不该印务的js）
            if (doc.html().indexOf("comment_live.js") < 0) {
                Elements elements = doc.getElementsByTag("body");
                if (elements.size() >= 1) {
                    Element element = elements.get(0);
                    Element commentElement = element.appendElement("script");
                    commentElement.attr("src", PropertiesContainer.getInstance().getJoymeLiveCommentJs());
                    commentElement.attr("language", "javascript");
                }
            }
        }


        //addHtml5MediaJs(doc);
    }

    private void addHtml5MediaJs(Document doc) {
        Elements elements = doc.getElementsByTag("head");
        if (elements.size() >= 1) {
            Element element = elements.get(0);
            Element html5Element = element.appendElement("script");
            html5Element.attr("src", PropertiesContainer.getInstance().getHtml5MediaJs());
            html5Element.attr("language", "javascript");
        }
    }

    protected void parseYoukuPlayer(Document doc, String desUrl) {
        try {
            URL url = new URL(desUrl);
            //如果不是优酷的域名
            if (!url.getHost().contains("youku")) {
                return;
            }
            PlayerUtil.addYoukuAttrLogo(doc);
        } catch (MalformedURLException e) {
            System.out.println("===parse youku player error:" + desUrl);
        }
    }
}
