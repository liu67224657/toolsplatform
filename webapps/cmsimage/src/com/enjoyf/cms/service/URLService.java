package com.enjoyf.cms.service;

import com.enjoyf.cms.bean.CommentContent;
import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.mcms.bean.GameBean;
import com.enjoyf.mcms.service.GameDownloadService;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

/**
 * @author liangtang
 */
public class URLService {
    private static URLUtil urlUtil = new URLUtil();
    private static GameDownloadService gameService = new GameDownloadService();
    private static ErrorLogService errorLogService = new ErrorLogService();
    private final static String ARTICLE_FLAG = "/";
    private final static String ARTICLE_PATH_FLAG = "/article/";
    private final static String VIP_FLAG = "/vip";
    private final static String TAG_FLAG = "/tags/";
    private final static String NEWS_FLAG = "/news/";
    private final static String NEXT_LINE = "\r\n";
    private final static String[] VIP_CHINESE_FLAG = new String[]{"重点游戏专区", "新闻中心"};
    private final static String WWW_JOYME_DOMAIN = "http://www.joyme.com/article";
    private final static String HREF_FLAG_JAVASCRIPT = "javascript";

    private CommentService commentService = new CommentService();


    public String parseURL(String url, String realPath, String secondDomain, String savePath, String type, String referer, boolean isSeoOpen) throws IOException {
        String html = urlUtil.openURLWithTimeOut(url);
        if (html == null) {
            recordErrorLog(url, referer);
            return null;
        }

        Document doc = Jsoup.parse(html);

        // check when network is error
        if (isErrorPage(doc))
            return null;

        // parse href
        parseHref(doc, realPath, secondDomain);

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

        // parse the image of style
        parseImageOfStyle(doc);

        //todo comment js
        addJquery(doc);
        addCommentJs(doc, url);


        if (!type.equals("xml")) {
            return makeNormalFileType(realPath, secondDomain, savePath, doc, isSeoOpen);
        } else {
            return makeXmlFileType(savePath, doc);
        }
    }

    private void parseImageOfStyle(Document doc) {
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

    private void parseGame(Document doc) throws IOException {
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

    private boolean isErrorPage(Document doc) {
        Element element = doc.getElementById("TITLE");
        if (element != null) {
            return element.text().equals("联通域名服务提示");
        } else {
            return false;
        }
    }

    private String makeXmlFileType(String savePath, Document doc) throws IOException {
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + getBody(doc);
        FileUtils.writeStringToFile(new File(savePath + "_seo"), str, "utf-8");
        return str;
    }

    private String makeNormalFileType(String realPath, String secondDomain, String savePath, Document doc, boolean isSeoOpen) throws IOException {
        String returnHtml = "";
        // save seo
        String seoFile = getCacheFile(savePath, true, secondDomain);
        saveDocToFile(doc, seoFile);

        returnHtml = doc.html();

        // add meta
        addmeta(doc);
        String noseoFile = getCacheFile(savePath, false, secondDomain);
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

    private void parseStyle(Document doc) {
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

    private void parseJs(Document doc) {
        List elements = doc.getElementsByTag("script");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            if (element.hasAttr("src")) {
                String src = element.attr("src");
                if (src.startsWith(ARTICLE_FLAG)) {
                    src = "http://" + PropertiesContainer.getphpCmsDomain() + src;
                }
                element.attr("src", src);
            }
        }
    }

    public String getCacheFile(String realPath, boolean isSeoOpen, String secondDomainName) {
        realPath = StringUtil.removeStartCharacter(realPath, "/");
        String file = PropertiesContainer.getCacheFolder() + "/" + (secondDomainName == null ? "default" : secondDomainName) + "/" + realPath;
        if (isSeoOpen)
            file += "_seo";
        else
            file += "_noseo";
        return file;
    }

    private void saveDocToFile(Document doc, String seoFile) throws IOException {
        FileUtils.writeStringToFile(new File(seoFile), doc.outerHtml(), "UTF-8");
    }

    /**
     * 增加不允许baidu搜索的meta
     *
     * @param doc
     */
    private void addmeta(Document doc) {
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

    private void parseCss(Document doc) {
        List elements = doc.getElementsByTag("link");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String href = element.attr("href");
            if (href != null && href.startsWith(ARTICLE_FLAG)) {
                String finalPath = "http://" + PropertiesContainer.getphpCmsDomain() + href;
                element.attr("href", finalPath);
            }
        }
    }

    private void parseImage(Document doc) {
        List elements = doc.getElementsByTag("img");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String src = element.attr("src");
            if (src != null && src.startsWith(ARTICLE_FLAG)) {
                String finalPath = "http://" + PropertiesContainer.getphpCmsDomain() + src;
                element.attr("src", finalPath);
            }

            if (src != null && src.startsWith(WWW_JOYME_DOMAIN)) {
                String replacePath = "http://" + PropertiesContainer.getphpCmsDomain() + "/article";
                String finalPath = src.replaceAll(WWW_JOYME_DOMAIN, replacePath);
                element.attr("src", finalPath);
            }

            String onclick = element.attr("onclick");
            if (onclick != null && onclick.contains("/article")) {
                onclick = onclick.replaceAll("/article", "http://" + PropertiesContainer.getphpCmsDomain() + src);
                element.attr("onclick", onclick);
            }
        }
    }

    /**
     * 处理连接为相对路径
     *
     * @param doc
     */
    private void parseHref(Document doc, String realPath, String secondDomain) {
        List elements = doc.getElementsByTag("a");

        // 处理已文件夹结尾的现象
        String tempRelaPath = realPath;
        if (!tempRelaPath.endsWith(".html")) {
            tempRelaPath = StringUtil.removeLastCharacter(tempRelaPath, "/");
            tempRelaPath += "/index.html";
        }


        //得到realPath的相对路径，即如果/xxx/xxx/xxx.html，那么该页面的相对路径是../../aaa.html
        String tempRealPathArray[] = StringUtil.removeStartCharacter(realPath, "/").split("/");
//        int length = StringUtil.removeStartCharacter(tempRelaPath, "/").split("/").length;
        String prefix = "";
        if (tempRealPathArray.length >= 2) {
            for (int i = 0; i < tempRealPathArray.length - 1; i++) {
                prefix += "../";
            }
        }

        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String href = element.attr("href");

            if (StringUtil.isEmpty(href) || href.startsWith(HREF_FLAG_JAVASCRIPT)|| href.equals("#")) {
                continue;
            }

            //href 不为空，以/article开头
            if (href != null && href.startsWith(ARTICLE_FLAG)) {

                if (secondDomain == null) { // 主站
                    String temp = checkIsArticle(tempRelaPath, prefix, href);

//                    String replaceFlag = href.startsWith(ARTICLE_FLAG + VIP_FLAG) ? ARTICLE_FLAG + VIP_FLAG : ARTICLE_FLAG;

                    href = href.replaceFirst(ARTICLE_FLAG, "");
                    href = StringUtil.removeStartCharacter(href, "/");

                    element.attr("href", temp + href);
                } else {//二级域名,将/vip/xxx 替换为空.头部的/去掉
                    String replaceFlag =  VIP_FLAG + "/" + secondDomain;
                    href = href.replaceFirst(replaceFlag, "");
                    href = StringUtil.removeStartCharacter(href, "/");
                    element.attr("href", prefix + href);
                }
            } else if (href != null && !href.startsWith(ARTICLE_FLAG)) {
                //href 不为空，不以/article开头，不把含:// 以相对路径开头  多用于每个页面的分页
                if (!href.contains("://") && !href.startsWith("/")) {

                    String temp = "";
                    for (int i = 0; i < tempRealPathArray.length - 1; i++) {
                        temp += tempRealPathArray[i] + "/";
                    }
                    element.attr("href", prefix + temp + href);
                }
            }
        }
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

    protected void addCommentJs(Document doc, String url) {
        String returnObj = null;

        Element areaEle = doc.getElementById("comment_area");
        if (areaEle == null) {
            return;
        }

        int fid = getArchiveId(url);
        if (fid == 0) {
            return;
        }

        String reurl = url.replace(PropertiesContainer.getphpCmsDomain()+"/" + PropertiesContainer.getphpProjectContext(), PropertiesContainer.getJoymeDomain());

//        reurl = reurl.replace(PropertiesContainer.getJoymeDomain() + "/" + PropertiesContainer.getphpProjectContext(), PropertiesContainer.getJoymeDomain());

//        DedeArchives archives = null;
//        try {
//            archives = archivesService.queryDedeArchivesbyId(null, fid);
//            if (archives == null) {
//                LogService.errorSystemLog("addCommentJs error.e: url:" + url);
//                return ;
//            }
//        } catch (Exception e) {
//            LogService.errorSystemLog("addCommentJs error.e: url:" + url, e);
//            return ;
//        }

        String title = "无题";
        Elements titleH2 = doc.getElementsByTag("h2");
        if (titleH2 != null && titleH2.size() > 0) {
            title = titleH2.get(0).text();
        }

        CommentContent commentContent = null;
        try {
          commentContent = commentService.getCid(String.valueOf(fid), reurl, title);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (doc.html().indexOf("comment.js") < 0 && commentContent != null && commentContent.getContentId() != null) {
            Elements elements = doc.getElementsByTag("head");
            if (elements.size() >= 1) {
                Element element = elements.get(0);
                Element commentElement = element.appendElement("script");
                commentElement.attr("src", PropertiesContainer.getJoymeCommentJs());
                commentElement.attr("language", "javascript");

                Element contentElement = element.appendElement("script");
                contentElement.html("var contentid = " + commentContent.getContentId() + ";");
                contentElement.append("var morelink = '" + PropertiesContainer.getJoymeCommentListUrl() + commentContent.getContentId() + "';");
                contentElement.attr("language", "javascript");

                String commentHtml = TemplateService.getCommentTemplate("default");

                returnObj = commentHtml.replaceAll("\\{joyme:comment-rurl/\\}", reurl);
                returnObj = returnObj.replaceAll("\\{joyme:comment-cid/\\}", String.valueOf(commentContent.getContentId()));
                returnObj = returnObj.replaceAll("\\{joyme:comment-postapi/\\}", PropertiesContainer.getJoymeCommentPostApi());
                returnObj = returnObj.replaceAll("\\{joyme:comment-deleteapi/\\}", PropertiesContainer.getJoymeCommentRemoveApi());
            }
        }

        if (returnObj != null) {
            areaEle.after(returnObj);
            areaEle.remove();
        }
    }

    private int getArchiveId(String url) {
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

}
