package com.enjoyf.cms.factory;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.service.ErrorLogService;
import com.enjoyf.cms.service.TemplateService;
import com.enjoyf.cms.util.CmsimageFilePathUtil;
import com.enjoyf.mcms.bean.AppDownloadBean;
import com.enjoyf.mcms.bean.GameBean;
import com.enjoyf.mcms.service.AppDownloadService;
import com.enjoyf.mcms.service.GameDownloadService;
import com.enjoyf.util.PlayerUtil;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-6-11
 * Time: 上午10:10
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractHtmlPraser implements IHtmlPraser {
    private static URLUtil urlUtil = new URLUtil();
    protected GameDownloadService gameService = new GameDownloadService();
    protected AppDownloadService appDownloadService = new AppDownloadService();
    private static ErrorLogService errorLogService = new ErrorLogService();
    private final static String ARTICLE_FLAG = "/article";
    private final static String ARTICLE_PATH_FLAG = "/article/";
    private final static String VIP_FLAG = "/vip";
    private final static String TAG_FLAG = "/tags/";
    private final static String NEWS_FLAG = "/news/";
    protected final static String NEXT_LINE = "\r\n";
    private final static String[] VIP_CHINESE_FLAG = new String[]{"重点游戏专区", "新闻中心"};
    private final static String WWW_JOYME_DOMAIN = "http://www.joyme.com/article";
    private final static String HREF_FLAG_JAVASCRIPT = "javascript";


    public String parseURL(String reqUrl, String srcUrl, String urlKey, String channel, String fileType, String referer, boolean isSeoOpen, String transferPath, String originalPath) throws IOException {
        //url = "http://youku.joyme.beta/globalgame/xiaotu/2015/0611/81416.html";
        String html = urlUtil.openURLWithTimeOut(srcUrl);
        if (html == null) {
            if (html == null) {
                System.out.println("==========article src html null:" + srcUrl);
                recordErrorLog(srcUrl, referer);
                return null;
            }
        }

        Document doc = Jsoup.parse(html);

        // check when network is error
        if (isErrorPage(doc))
            return null;

        // parse href
        parseHref(doc, reqUrl, srcUrl, transferPath, originalPath);

        // parse image
        parseImage(doc);

        // parse css
        parseCss(doc);

        // parse js
        parseJs(doc);

        // parse style
        parseStyle(doc);

        // 处理导航栏中重点游戏专区字样
        parseNavigate(doc);

        // prase game
        parseGame(doc);

        //prase appdownloadlink
        parseAppDownLoadLink(doc);

        // parse the image of style
        parseImageOfStyle(doc);

        //todo comment js
        addJquery(doc);
        addCommentJs(doc, reqUrl);

        parseYoukuPlayer(doc, reqUrl);

        if (!fileType.equals("xml")) {
            return makeNormalFileType(reqUrl, urlKey, doc, isSeoOpen);
        } else {
            return makeXmlFileType(reqUrl, urlKey, doc);
        }
    }

    @Override
    public String saveURLFile(String responseText, String reqUrl, String srcUrl, String host, boolean isSeoOpen, String transferPath, String originalPath) throws IOException {
        if (StringUtil.isEmpty(responseText)) {
            return null;
        }
        Document doc = Jsoup.parse(responseText);
        // check when network is error
        if (isErrorPage(doc))
            return null;

        // parse href
        parseHref(doc, reqUrl, srcUrl, transferPath, originalPath);

        // parse image
        parseImage(doc);

        // parse css
        parseCss(doc);

        // parse js
        parseJs(doc);

        // parse style
        parseStyle(doc);

        // 处理导航栏中重点游戏专区字样
        parseNavigate(doc);

        // prase game
        parseGame(doc);

        //prase appdownloadlink
        parseAppDownLoadLink(doc);

        // parse the image of style
        parseImageOfStyle(doc);

        addJquery(doc);
        addCommentJs(doc, reqUrl);

        parseYoukuPlayer(doc, reqUrl);

        if (!reqUrl.endsWith(".xml")) {
            return makeNormalFileType(reqUrl, host, doc, isSeoOpen);
        } else {
            return makeXmlFileType(reqUrl, host, doc);
        }
    }


    protected void parseYoukuPlayer(Document doc, String reqUrl) {
        try {
            URL url = new URL(reqUrl);
            //如果不是优酷的域名
            if (!url.getHost().contains("youku")) {
                return;
            }
            PlayerUtil.addYoukuAttrLogo(doc);
        } catch (MalformedURLException e) {
            System.out.println("===parse youku player error:" + reqUrl);
        }
    }

    public String getCacheFile(String path, Boolean isSeoOpen, String host) {
        String file = PropertiesContainer.getCacheFolder() + "/" + host;
        if (path.startsWith("/")) {
            file += path;
        } else {
            file += "/" + path;
        }
        if (isSeoOpen != null) {
            if (isSeoOpen)
                file += "_seo";
            else
                file += "_noseo";
        }

        return file;
    }


    protected void parseImageOfStyle(Document doc) {
        List elements = doc.getElementsByTag("a");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            if (element.hasAttr("style")) {
                String value = element.attr("style");
                if (value.contains(ARTICLE_PATH_FLAG)) {
                    value = value.replaceAll(ARTICLE_PATH_FLAG, "http://article.joyme.com/article/");
                }
                element.attr("style", value);
            }
        }

    }

    protected void parseGame(Document doc) throws IOException {
        List elements = doc.getElementsByTag("p");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();

            if (element.attr("name").equals("joyme-download")) {
                String id = element.attr("id");
                if (id != null) {
                    StringBuffer sb = getGameDownload(id);
                    element.html(sb.toString());
                }
            }
        }
    }

    private StringBuffer getGameDownload(String id) throws IOException {
        StringBuffer sb = new StringBuffer();

        String url = PropertiesContainer.getGameDownloadUrl();
        url = MessageFormat.format(url, id);

        GameBean bean = gameService.getGame(url);
        if (bean != null) {
            sb.append("<div class=\"game-data\">").append(NEXT_LINE);
            sb.append(" <h2 class=\"fl\"><a><img title=\"\" alt=\"\" src=\"" + bean.getGameIcon() + "\" />" + bean.getGameName() + "</a></h2>")
                    .append(NEXT_LINE);

            sb.append("<ul class=\"fl\">").append(NEXT_LINE);
            sb.append("<li class=\"l-1\"><span>游戏平台：</span>" + bean.getDisplayPlatform() + "</li> ").append(NEXT_LINE);
            sb.append("<li class=\"l-2\"><span>游戏类型：</span>" + bean.getGameType(15) + "</li> ").append(NEXT_LINE);
            sb.append(" <li class=\"l-3\"><span>发行厂商：</span>" + bean.getFactoryName(14) + "</li> ").append(NEXT_LINE);
            sb.append("<li class=\"l-4\"><span>游戏大小：</span>" + bean.getDisplayGameSize() + "</li> ").append(NEXT_LINE);
            sb.append("<li class=\"l-5\"><span>更新时间：</span>" + bean.getGamePublishDate() + "</li> ").append(NEXT_LINE);
            sb.append(" <li class=\"l-6\"><span>游戏评分：</span>" + bean.getRate() + "</li> ").append(NEXT_LINE);
            sb.append("</ul> ").append(NEXT_LINE);
            sb.append(" <dl class=\"fl\"> ").append(NEXT_LINE);
            sb.append(" <dt>").append(NEXT_LINE);
            sb.append("游戏下载").append(NEXT_LINE);
            sb.append("</dt> ").append(NEXT_LINE);
            sb.append(" <dd> ").append(NEXT_LINE);
            String iphoneURL = gameService.getDownloadLink(bean, GameDownloadService.IPHONE, GameDownloadService.DEFAULT_CHANNEL);
            if (iphoneURL != null)
                sb.append(" <a class=\"a-dw\" href=\"" + iphoneURL + "\">iphone下载</a> ").append(NEXT_LINE);

            String ipadURL = gameService.getDownloadLink(bean, GameDownloadService.IPAD, GameDownloadService.DEFAULT_CHANNEL);
            if (ipadURL != null)
                sb.append(" <a class=\"a-dw\" href=\"" + ipadURL + "\">ipad下载</a> ").append(NEXT_LINE);

            String androidURL = gameService.getDownloadLink(bean, GameDownloadService.ANDROID, GameDownloadService.DEFAULT_CHANNEL);
            if (androidURL != null)
                sb.append(" <a class=\"i-dw\" href=\"" + androidURL + "\">安卓版下载</a> ").append(NEXT_LINE);

            sb.append("</dd> ").append(NEXT_LINE);
            sb.append(" </dl>").append(NEXT_LINE);
            sb.append(" </div> ").append(NEXT_LINE);
        }
        return sb;
    }


    protected void parseAppDownLoadLink(Document doc) throws IOException {
        Elements elements = doc.select("a[name=joyme-appdownload]");
        for (Element element : elements) {
            String aid = element.attr("aid");
            AppDownloadBean appBean = getAppBean(aid);
            if (appBean != null) {
                element.removeAttr("name").removeAttr("aid").attr("name", "idfa_downloadlink").attr("data-aid", aid).attr("href", appBean.getUrl());
            }
        }
    }

    private AppDownloadBean getAppBean(String aid) throws IOException {
        String url = PropertiesContainer.getAppDownloadUrl();
        url = MessageFormat.format(url, aid);
        return appDownloadService.getApp(url);
    }


    private boolean isErrorPage(Document doc) {
        Element element = doc.getElementById("TITLE");
        if (element != null) {
            return element.text().equals("联通域名服务提示");
        } else {
            return false;
        }
    }

    private String makeXmlFileType(String reqUrl, String host, Document doc) throws IOException {
        URL url = new URL(reqUrl);
        String path = url.getPath();
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + getBody(doc);
        String seoFile = getCacheFile(path, true, host);
        FileUtils.writeStringToFile(new File(seoFile), str, "utf-8");
        return str;
    }

    private String makeNormalFileType(String reqUrl, String host, Document doc, boolean isSeoOpen) throws IOException {
        URL url = new URL(reqUrl);
        String path = url.getPath();
        String returnHtml = "";
        // save seo
        String seoFile = getCacheFile(path, true, host);
        saveDocToFile(doc, seoFile);

        returnHtml = doc.html();

        // add meta
        addmeta(doc, isSeoOpen);
        String noseoFile = getCacheFile(path, false, host);
        saveDocToFile(doc, noseoFile);

        if (!isSeoOpen) {
            returnHtml = doc.html();
        }

        return returnHtml;
    }

    /**
     * 获得body的数据
     *
     * @param doc
     * @return
     */
    private String getBody(Document doc) {
        return doc.getElementsByTag("body").get(0).html();
    }

    protected void parseStyle(Document doc) {
        List elements = doc.getElementsByTag("style");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String text = element.html();
            if (text != null && text.contains(WWW_JOYME_DOMAIN)) {
                String replacePath = "http://" + PropertiesContainer.getphpCmsDomain() + "/article";
                text = text.replaceAll(WWW_JOYME_DOMAIN, replacePath);
                element.html(text);
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
                    src = "http://" + PropertiesContainer.getphpCmsDomain() + src;
                }
                element.attr("src", src);
            }
        }
    }

    private void saveDocToFile(Document doc, String seoFile) throws IOException {
        FileUtils.writeStringToFile(new File(seoFile), doc.outerHtml(), "UTF-8");
    }

    /**
     * 增加不允许baidu搜索的meta
     *
     * @param doc
     */
    private void addmeta(Document doc, boolean isSeoOpen) {
        List elements = doc.getElementsByTag("head");
        if (elements.size() > 0) {
            Element element = (Element) elements.get(0);
            Element childElement = element.appendElement("meta");
            childElement.attr("name", "robots");
            childElement.attr("content", "noindex");

            childElement = element.appendElement("meta");
            childElement.attr("name", "robots");
            childElement.attr("content", "noarchive");

            childElement = element.appendElement("meta");
            childElement.attr("name", "robots");
            childElement.attr("content", "nofollow");
        }
    }

    private void parseNavigate(Document doc) {
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

    private boolean isContainKeyWord(String str) {
        for (int j = 0; j < VIP_CHINESE_FLAG.length; j++) {
            if (str.contains(VIP_CHINESE_FLAG[j])) {
                return true;
            }
        }
        return false;
    }

    protected void parseCss(Document doc) {
        List elements = doc.getElementsByTag("link");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String href = element.attr("href");
            if (href != null && href.startsWith("/")) {
                String finalPath = "http://" + PropertiesContainer.getphpCmsDomain() + href;
                element.attr("href", finalPath);
            }
        }
    }

    protected void parseImage(Document doc) {
        List elements = doc.getElementsByTag("img");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String src = element.attr("src");
            if (src != null && src.startsWith("/")) {
                String finalPath = "http://" + PropertiesContainer.getphpCmsDomain() + src;
                element.attr("src", finalPath);
            }

            if (src != null && src.startsWith(WWW_JOYME_DOMAIN)) {
                String replacePath = "http://" + PropertiesContainer.getphpCmsDomain() + "/article";
                String finalPath = src.replaceAll(WWW_JOYME_DOMAIN, replacePath);
                element.attr("src", finalPath);
            }

            String onclick = element.attr("onclick");
            if (onclick != null && onclick.contains(ARTICLE_FLAG)) {
                onclick = onclick.replaceAll(ARTICLE_FLAG, "http://" + PropertiesContainer.getphpCmsDomain() + ARTICLE_FLAG);
                element.attr("onclick", onclick);
            }
        }
    }

    /**
     * 处理连接为相对路径
     *
     * @param doc
     * @param reqPath
     * @param srcPath
     */
    protected void parseHref(Document doc, String reqPath, String srcPath, String transferPath, String originalPath) {

        try {
            URL reqUrl = new URL(reqPath);
            URL srcUrl = new URL(srcPath);
            if (srcUrl == null || reqUrl == null) {
                return;
            }
            List elements = doc.getElementsByTag("a");
            for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                String href = element.attr("href");

                if (StringUtil.isEmpty(href) || href.startsWith(HREF_FLAG_JAVASCRIPT) || href.equals("#")) {
                    continue;
                }

                //以/开头的域名绝对路径
                if (href.startsWith("/")) {
                    if (href.startsWith("/article/")) {
                        if(href.startsWith("/article/pc/")){
                            href = href.replace("/article/pc/", "/article/");
                        }
                        String temHref = srcPath.replace(srcUrl.getPath(), "") + href;
                        temHref = genTmpHref(temHref, transferPath, originalPath, reqPath, reqUrl);
                        element.attr("href", temHref);
                    }else {
                        href = "/article"+href;
                        String temHref = srcPath.replace(srcUrl.getPath(), "") + href;
                        temHref = genTmpHref(temHref, transferPath, originalPath, reqPath, reqUrl);
                        element.attr("href", temHref);
                    }
                } else if (href.startsWith("../")) {
                    String temHref = srcPath.replace(srcUrl.getPath(), "");
                    String temPath = srcUrl.getPath().substring(0, srcUrl.getPath().lastIndexOf("/")).replaceFirst("/", "");
                    String[] temArray = null;
                    if (temPath.contains("/")) {
                        temArray = temPath.split("/");
                    } else {
                        temArray = new String[]{temPath};
                    }
                    List<String> temList = genHref(href, new ArrayList<String>());
                    int j = temArray.length - temList.size() - 1;
                    if (j < 0) {
                        temHref = temHref + "/" + href.replaceAll("\\.\\.\\/", "");
                    } else {
                        for (int i = 0; i <= j; i++) {
                            temHref += "/" + temArray[i];
                        }
                        temHref += "/" + href.replaceAll("\\.\\.\\/", "");
                    }
                    temHref = genTmpHref(temHref, transferPath, originalPath, reqPath, reqUrl);
                    element.attr("href", temHref);
                } else {
                    if (!href.startsWith("http://") && !href.startsWith("https://")) {
                        String temHref = srcPath.substring(0, srcPath.lastIndexOf("/")) + "/" + href;
                        temHref = genTmpHref(temHref, transferPath, originalPath, reqPath, reqUrl);
                        element.attr("href", temHref);
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("===parse href error:" + reqPath + "," + srcPath);
        }
    }

    private String genTmpHref(String temHref, String transferPath, String originalPath, String reqPath, URL reqUrl) throws MalformedURLException {
        //规则命中的 直接替换
        if (!StringUtil.isEmpty(transferPath) && !StringUtil.isEmpty(originalPath) && temHref.contains(originalPath)) {
            temHref = temHref.replace(originalPath, transferPath);
            URL temUrl = new URL(temHref);
            String temHost = reqPath.replace(reqUrl.getPath(), "");
            String returnHref = temHost + temUrl.getPath();
            if(temHref.indexOf("?") > 0){
                returnHref += temHref.substring(temHref.indexOf("?"), temHref.length());
            }
            temHref = returnHref;
        } else {
            //找出规则  替换
            String temTransfer = null;
            String tmpOriginal = null;
            //TODO 周会
//            Map<String, String> map = null;
//            try {
//                map = CmsimageFilePathUtil.genTransferPath(temHref);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
            Map<String, String> map = CmsimageFilePathUtil.genTransferPath(temHref);
            if (map != null) {
                temTransfer = map.get("transferPath");
                tmpOriginal = map.get("originalPath");
                temHref = temHref.replace(tmpOriginal, temTransfer);
            } else {
                if (temHref.contains("/vip/youku/")) {
                    if (temHref.contains("/article/android/vip/youku/")) {
                        temHref = temHref.substring(temHref.indexOf("/article/android/vip/youku/") + "/article/android/vip/youku/".length(), temHref.length());
                        String temHost = reqPath.replace(reqUrl.getPath(), "");
                        temHref = temHost + "/android" + temHref;
                    } else if (temHref.contains("/article/pc/vip/youku/")) {
                        temHref = temHref.substring(temHref.indexOf("/article/pc/vip/youku/") + "/article/pc/vip/youku/".length(), temHref.length());
                        String temHost = reqPath.replace(reqUrl.getPath(), "");
                        temHref = temHost + temHref;
                    }
                } else if (temHref.contains("/article/")) {
                    if (temHref.contains("/vip/")) {
                        temHref = temHref.substring(temHref.indexOf("/vip/") + "/vip/".length(), temHref.length());
                        if (temHref.indexOf("/") > 0) {
                            temHref = temHref.substring(temHref.indexOf("/"), temHref.length());
                        } else {
                            temHref = "/index.html";
                        }
                    } else {
                        temHref = temHref.substring(temHref.indexOf("/article/") + "/article/".length() - 1, temHref.length());
                    }
                    String temHost = reqPath.replace(reqUrl.getPath(), "");
                    temHref = temHost + temHref;
                }
            }
        }
        return temHref;
    }

    private List<String> genHref(String href, List<String> temList) {
        if (href.startsWith("../")) {
            temList.add("../");
            href = href.replaceFirst("../", "");
            return genHref(href, temList);
        }
        return temList;
    }

    private String checkIsArticle(String realPath, String prefix, String href) {
        String temp = "";
        if (realPath.contains(TAG_FLAG) && !href.contains(TAG_FLAG) && !href.contains(NEWS_FLAG))
            temp = prefix + "article/";
        else
            temp = prefix;
        return temp;
    }

    private void recordErrorLog(String url, String referer) {
        errorLogService.saveErrorLog(url, referer);
    }

    protected void addJquery(Document doc) {
        if (doc.html().indexOf("jquery-1.9.1.min.js") < 0) {
            Elements elements = doc.getElementsByTag("head");
            if (elements.size() >= 1) {
                Element element = elements.get(0);
                Element jqueryElement = element.appendElement("script");
                jqueryElement.attr("src", PropertiesContainer.getJqueryJs());
                jqueryElement.attr("language", "javascript");
            }
        }
    }

    protected void addCommentJs(Document doc, String reqUrl) {
        String returnObj = null;

        Element areaEle = doc.getElementById("comment_area");
        if (areaEle == null) {
            return;
        }

        int fid = getArchiveId(reqUrl);
        if (fid == 0) {
            return;
        }
        if (!reqUrl.startsWith("http://")) {
            reqUrl = "http://" + reqUrl;
        }

        String title = "无题";
        Elements titleH2 = doc.getElementsByTag("h2");
        if (titleH2 != null && titleH2.size() > 0) {
            title = titleH2.get(0).text();
        }

        int domain = 2;
        if (doc.html().indexOf("comment.js") < 0) {
            Elements elements = doc.getElementsByTag("head");
            if (elements.size() >= 1) {
                Element element = elements.get(0);
                Element commentElement = element.appendElement("script");
                commentElement.attr("src", PropertiesContainer.getJoymeCommentJs());
                commentElement.attr("language", "javascript");

                Element contentElement = element.appendElement("script");
                contentElement.html("var unikey = '" + fid + "';");
                contentElement.append("var comment_domain = '" + domain + "';");
                contentElement.append("var title = '" + title + "';");
                contentElement.append("var uri = '" + reqUrl + "';");
                contentElement.attr("language", "javascript");

                String commentHtml = TemplateService.getCommentTemplate("default");


                returnObj = commentHtml.replaceAll("\\{joyme:comment-uri/\\}", reqUrl);
                returnObj = returnObj.replaceAll("\\{joyme:comment-unikey/\\}", fid + "");
                returnObj = returnObj.replaceAll("\\{joyme:comment-domain/\\}", domain + "");
                returnObj = returnObj.replaceAll("\\{joyme:comment-title/\\}", title);

            }
        }

        if (returnObj != null) {
            areaEle.after(returnObj);
            areaEle.remove();
        }
    }

    protected int getArchiveId(String url) {
        String[] urls = url.split("/");
        int archiveId = 0;
        String number = "";
        for (int i = 0; i < urls.length; i++) {
            String item = urls[i];
            if (item.endsWith(".html")) {
                item = item.replaceAll(".html", "");
                int position = item.indexOf("_");
                if (position >= 0) {
                    item = item.substring(0, position);
                }
            }

            if (StringUtil.isNumeric(item)) {
                number += item;
            }
        }

        if (number.length() > 8 && number.startsWith("20")) {
            archiveId = Integer.parseInt(number.substring(8, number.length()));
        }

        return archiveId;
    }


    public static void main(String[] args) {
////        joyme-appdownload
//        DefaultHtmlPraser defaultHtmlPraser = new DefaultHtmlPraser();
//
//
//        Document document = Jsoup.parse("<a name=\"joyme-appdownload\" aid=\"35\">下载</a>");
//
//        try {
//            defaultHtmlPraser.parseAppDownLoadLink(document);
//
//            System.out.println(document.html());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String postUrl = "http://data.zz.baidu.com/urls?site=m.wiki.joyme.com&token=WzGLirMD1oFFXN4n";
        String reqBody = "http://m.wiki.joyme.com/ttkp/363854.shtml";
        String result = post(postUrl, reqBody);
        System.out.println(result);
//        postUrl = "http://data.zz.baidu.com/urls?site=v.joyme.com&token=WzGLirMD1oFFXN4n";
//        reqBody = "http://v.joyme.com/yxjbd/20150918/101531.html";
//        result = post(postUrl, reqBody);
//        System.out.println(result);
//        postUrl = "http://data.zz.baidu.com/urls?site=www.joyme.com&token=WzGLirMD1oFFXN4n";
//        reqBody = "http://www.joyme.com/news/blue/201509/17101095.html";
//        result = post(postUrl, reqBody);
//        System.out.println(result);
    }

    public static String post(String postUrl, String reqBody) {
        //测试慎重
        //reqBody = "http://www.joyme.com/collection/热血传奇手机版";
        String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            //建立URL之间的连接
            URLConnection conn = new URL(postUrl).openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("Host", "data.zz.baidu.com");
            conn.setRequestProperty("User-Agent", "curl/7.12.1");
            conn.setRequestProperty("Content-Length", String.valueOf(reqBody.getBytes("UTF-8").length));
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Charset", "UTF-8");

            //IO发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //获取conn对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            out.print(reqBody);
            //进行输出流的缓冲
            out.flush();
            //通过BufferedReader输入流来读取Url的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送post请求出现异常！" + e);
            e.printStackTrace();
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
}
