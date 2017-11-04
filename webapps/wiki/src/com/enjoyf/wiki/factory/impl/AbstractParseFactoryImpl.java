package com.enjoyf.wiki.factory.impl;


import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.PlayerUtil;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.SystemUtil;
import com.enjoyf.util.URLUtil;
import com.enjoyf.util.video.VideoReplaceUtil;
import com.enjoyf.wiki.bean.CommentContent;
import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.bean.JoymeWikiShare;
import com.enjoyf.wiki.bean.WikiRecommend;
import com.enjoyf.wiki.container.ChannelContainer;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.factory.IHtmlParseFactory;
import com.enjoyf.wiki.factory.WikiPraseParam;
import com.enjoyf.wiki.service.*;
import com.enjoyf.wiki.template.TemplateUtil;
import com.enjoyf.wiki.template.YoukuPlayerTemplate;
import com.enjoyf.wiki.tools.WikiUtil;
import com.enjoyf.wiki.util.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public abstract class AbstractParseFactoryImpl implements IHtmlParseFactory {

    private final static String ENCODING = "utf-8";
    private WikiPageService pageService = new WikiPageService();
    private JoymeWikiShareService joymeShareService = new JoymeWikiShareService();
    private CommentService commentService = new CommentService();

    private JoymeTemplateService templateService = new JoymeTemplateService();

    private WikiRecommendService wikiRecommendService = new WikiRecommendService();

    private static final String WIKI_NAME_FLAG = "_pc";
    private static final String M_WIKI_NAME_FLAG = "_m";

    private WikiRankService wikiRankService = new WikiRankService();

    private static SystemUtil su = new SystemUtil();
    private static URLUtil urlUtil = new URLUtil();

    //opt/html/wikihtml/wiki(mwiki)/360/mt(_seo)/
    public String getFileDirByChannel(String channel, String key, boolean isSEO, String wikiType) {
        return PropertiesContainer.getInstance().getCacheFolder() + "/" + wikiType + "/" + channel + "/" + key + (isSEO ? "" : "_noseo") + "/";
    }

    public String getFullFileName(String channel, String key, boolean isSEO, String fileName, String wikiType) {
        return getFileDirByChannel(channel, key, isSEO, wikiType) + fileName;
    }

    public String parseURLAndSave(String channel, WikiPraseParam param) throws Exception {
        LogService.infoSystemLog("crwal:" + param.getFullUrl(), false);
        param.setChannel(channel);

        String joymeTemplate = templateService.getTemplate(param.getKey(), channel, param.getIndex(), param.getWikiType());
        if (joymeTemplate == null) {
            LogService.infoSystemLog("template: " + param.getKey() + "_" + channel + "_" + param.getIndex() + "_" + param.getWikiType() + " is null.");
            return null;
        } else {
            //remove joymeTemplate jquery
            Document joymyeTemplateDoc = Jsoup.parse(joymeTemplate);
            removeJquery(joymyeTemplateDoc);
            joymeTemplate = joymyeTemplateDoc.html();

        }

        String fullName = getFileDirByChannel(channel, param.getKey(), true, param.getWikiType()) + param.getSaveFileName();

//        String html = this.makeWikibody(param.getDomain(), param.getPath(), param.getKey(), param.getFullUrl(), joymeTemplate, fullName, param.getWikiType());

        //如果key是wiki的话，直接写死
        if (param.getKey().equals("wiki")) {
            if (param.getSaveFileName().equals("index.shtml")) {
                param.setFullUrl("http://wikiadmin.joyme.com/wiki/%E9%A6%96%E9%A1%B5");
            } else {
                param.setFullUrl("http://wikiadmin.joyme.com/wiki/" + param.getPageInfo());
            }
        }
        String html = this.makeWikibody(joymeTemplate, fullName, param);


        if (html == null || StringUtil.isEmpty(html)) {
            return null;
        }

        if (joymeTemplate != null) {
            html = TemplateUtil.replaceWikiTag(html, param.getKey(), channel, param.getIndex(), param.getWikiType());

            //替换wap页评论
            if (html.contains(TemplateUtil.JOYME_WIKI_WAP_COMMENT)) {
                html = replaceWapComment(param, channel, html);
            }


            //替换微信wap页评论
            if (html.contains(TemplateUtil.JOYME_WX_WIKI_WAP_COMMENT)) {
                html = replaceWXWapComment(param, channel, html);
            }


            boolean isSupportDomain = PropertiesContainer.getInstance().supportSubDomain(param.getKey(), param.getWikiType());
            //访问量前10
            if (html.contains(TemplateUtil.JOYME_WIKI_VISITOR_TOP)) {
                html = replaceWikiVisitorTop(param, html, isSupportDomain);
            }

            //最近更新
            if (html.contains(TemplateUtil.JOYME_WIKI_RECENT_UPDATE)) {
                html = replaceWikiRecentUpdate(param, html, isSupportDomain);
            }

            //替换标签
            if (html.contains(TemplateUtil.JOYME_WIKI_UPDATE_TIME)) {
                String wxupdatetimehtml = "<div style=\"display:none\" id=\"wikiupdatetime\">" + new Date().getTime() + "</div>";
                html = html.replace(TemplateUtil.JOYME_WIKI_UPDATE_TIME, wxupdatetimehtml);
            }

        }

        this.writeFile(fullName, html);

        if (PropertiesContainer.getInstance().supportSubDomain(param.getKey(), param.getWikiType())) {
            Document doc = Jsoup.parse(html);
            Elements headElements = doc.getElementsByTag("head");
            if (headElements != null) {
                addmeta(doc);
            }
            fullName = getFileDirByChannel(channel, param.getKey(), false, param.getWikiType()) + param.getSaveFileName();
            this.writeFile(fullName, doc.html());
        }


        return html;
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

    protected void addShareMetaKey(Document doc, String wikiKey) {
        List elements = doc.getElementsByTag("head");
        if (elements.size() > 0) {
            Element element = (Element) elements.get(0);
            try {
                JoymeWikiShare joymeShare = joymeShareService.queryJoymeWikiShare(null, wikiKey);

                if (joymeShare != null) {
                    Element childElement = element.appendElement("meta");
                    childElement.attr("name", "share-content");
                    childElement.attr("content", joymeShare.getJoymeShareContent());

                    childElement = element.appendElement("meta");
                    childElement.attr("name", "share-pic");
                    childElement.attr("content", joymeShare.getJoymeSharePic());
                }
            } catch (JoymeDBException e) {
                e.printStackTrace();
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            }


        }
    }


    private String makeWikibody(String joymeTemplate, String saveFileName, WikiPraseParam param) throws Exception {
        String url = param.getFullUrl();
        if (param.getFullUrl().contains("?")) {
            url = param.getFullUrl() + "&wikitype=" + param.getWikiType();
        } else {
            url = param.getFullUrl() + "?wikitype=" + param.getWikiType();
        }

        String html = urlUtil.openURLWithTimeOut(url);
        if (html == null) {
            LogService.errorSystemLog("connect url get html is null.path:" + param.getPath() + " domain:" + param.getDomain());
            return null;
        }

        Document doc = this.getDocumentByHTML(html);

        if (ignorErrorTitle(doc)) {
            return null;
        }


//        doc = genHtmlByDocument(doc, param.getDomain(), param.getPath(), param.getKey(), param.getFullUrl(), param.getWikiType());
        doc = genHtmlByDocument(doc, param);
        replaceMetaElement(doc, param.getWikiType());

        String retHtml = "";
        if (joymeTemplate != null) {
            retHtml = merageWiki(joymeTemplate, doc, param);
        } else {
            retHtml = doc.html();
        }


        //去掉<img的\r\n
        StringBuffer sb = removeImgNextLine(retHtml);
        return sb.toString();
    }

    private boolean ignorErrorTitle(Document doc) {
        //<TITLE>联通域名服务提示</TITLE>
        Elements title = doc.getElementsByTag("title");
        if (title == null && title.size() > 0) {
            if (title.text().trim().equals("联通域名服务提示")) {
                return true;
            }
        }
        return false;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    protected Document genHtmlByDocument(Document doc, WikiPraseParam param) throws Exception {
        JoymeWiki joymeWiki = PropertiesContainer.getInstance().getJoymeWiki(param.getKey(), param.getWikiType());
        //删除模块
        removeHideMoudle(doc, param.getWikiType());
        LogService.infoSystemLog("====去掉" + param.getWikiType() + "隐藏模块成功=======");

        removeJsTag(doc, joymeWiki);


        //TODO 临时将视频显示修改,微信渠道不做处理
//        if (!param.getChannel().equals(ChannelContainer.WX_CHANNEL)) {
//            VideoReplaceUtil.process(doc);
//        }

        // 改href
        replaceURL(param.getDomain(), param.getPath(), param.getKey(), doc, joymeWiki);
        LogService.infoSystemLog("====改href成功=======");

        // 改图片
        replaceImage(param.getDomain(), doc);
        LogService.infoSystemLog("====改图片成功=======");


        //remove jquery
        removeJquery(doc);
        LogService.infoSystemLog("====remove jquery 成功=======");

        // add jquery
        addJquery(doc);
        LogService.infoSystemLog("====add jquery 成功=======");

        removePhpFile(doc, joymeWiki);

        // 替换css
        replaceCss(param.getKey(), doc, param.getWikiType(), joymeWiki);

        //替换搜索框
        changeSearchInputBox(param.getKey(), doc.getElementsByTag("form"));
        LogService.infoSystemLog("====替换搜索框 成功=======");

        // 去掉no-follow ,特殊和模版不去
        String wikiKey = getWikiKey(param.getFullUrl());
        replaceNoFollow(doc, wikiKey);
        LogService.infoSystemLog("====去掉norobots 成功=======");

        if (param.getWikiType().equals("mwiki")) {
            addShareMetaKey(doc, param.getKey());
            //替换youku 渠道 mwiki的title
            replaceMWikiTitle(doc, param);
        }

        //处理优酷或微信视频
        parsePlayer(doc, param);

        return doc;
    }

    protected Document parsePlayer(Document doc, WikiPraseParam param) {
        //如果不是优酷的域名
        int divSize = PlayerUtil.addYoukuAttrLogo(doc);
        Elements elements = doc.getElementsByTag("head");
        if (divSize > 0 && elements.size() >= 1) {
            Element element = elements.get(0);
            element.append(YoukuPlayerTemplate.YoukuPlayerTemplate_SCRIPT);
            element.append(YoukuPlayerTemplate.YoukuPlayerTemplate_STYLE);
        }

        //处理微信渠道视频，显示腾讯的div，删除优酷的div
        if (ChannelContainer.WX_CHANNEL.equals(param.getChannel())) {
            Elements txElements = doc.getElementsByAttributeValueStarting("id", PlayerUtil.TX_WIKI_ID);
            if (txElements != null) {
                for (Element element : txElements) {
                    element.attr("style", element.attr("style").replace("display:none", "display:block"));
                }
            }
            Elements youkuElements = doc.getElementsByAttributeValueStarting("id", PlayerUtil.YOUKU_WIKI_ID);
            if (youkuElements != null) {
                youkuElements.remove();
            }
        } else if (ChannelContainer.YOUKU_CHANNEL.equals(param.getChannel())) {
            Elements txElements = doc.getElementsByAttributeValueStarting("id", PlayerUtil.TX_WIKI_ID);
            if (txElements != null) {
                txElements.remove();
            }
        } else {
            //其他的渠道也显示优酷
            Elements txElements = doc.getElementsByAttributeValueStarting("id", PlayerUtil.TX_WIKI_ID);
            if (txElements != null) {
                txElements.remove();
            }
        }
        return doc;
    }


    //替换mwiki的title
    protected Document replaceMWikiTitle(Document doc, WikiPraseParam param) {
        //优酷渠道
        if (ChannelContainer.YOUKU_CHANNEL.equals(param.getChannel())) {
            Element element = doc.getElementsByTag("title").get(0);
            //非首页
            if (element != null && param.getIndex() == 2) {
                doc.getElementsByTag("title").get(0).text(param.getPageInfo());
            }

            //首页
            if (element != null && param.getIndex() == 1) {
                JoymeWiki joymeWiki = PropertiesContainer.getInstance().getJoymeWikiByKeyAndWikiType(param.getKey(), param.getWikiType());
                doc.getElementsByTag("title").get(0).text(joymeWiki.getJoymeWikiName() + " WIKI");
            }
        } else if (ChannelContainer.WX_CHANNEL.equals(param.getChannel())) {
            //微信渠道
            Element element = doc.getElementsByTag("title").get(0);
            //首页
            if (element != null && param.getIndex() == 1) {
                JoymeWiki joymeWiki = PropertiesContainer.getInstance().getJoymeWikiByKeyAndWikiType(param.getKey(), param.getWikiType());
                if (joymeWiki != null) {
                    String wikiName = joymeWiki.getJoymeWikiName() == null ? null : joymeWiki.getJoymeWikiName().toLowerCase();
                    if (wikiName != null && wikiName.contains(WIKI_NAME_FLAG) || wikiName.contains(M_WIKI_NAME_FLAG)) {
                        wikiName = wikiName.replace(WIKI_NAME_FLAG, "").replace(M_WIKI_NAME_FLAG, "");
                    }
                    doc.getElementsByTag("title").get(0).text(wikiName + "攻略站");
                }

            }
        }
        return doc;
    }

    protected Document removeHideMoudle(Document doc, String wikiType) {
        Elements elements = doc.getElementsByClass(wikiType + "_hide");
        elements.remove();

        return doc;
    }

    protected Document removeJsTag(Document doc, JoymeWiki joymeWiki) {
        if (JoymeWiki.getKeepJsCss(joymeWiki) == 0) {
            doc.select("script:not([src]):not([type])").remove();
        }
        Elements elements = doc.getElementsByTag("script");
        Element element = null;
        String href = "";
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            element = (Element) iterator.next();
            href = element.attr("src");
            if (href.startsWith("/")) {
                element.attr("src", joymeWiki.getJoymeWikiDomain() + href);
            }
        }
        return doc;
    }

    public static void main(String[] args) {
        String flag = "啊啊啊";
        System.out.println(flag.toLowerCase());
    }

    private String merageWiki(String joymeTempateContext, Document doc, WikiPraseParam param) {
        if (joymeTempateContext == null || joymeTempateContext.length() == 0) {
            return doc.html();
        }

        Document contextDoc = getDocumentByHTML(joymeTempateContext);

        doc = getSEOInfoByHiddenTag(doc);

        merageHeaderToContextDoc(contextDoc, doc);

//        String s = TemplateUtil.replaceWikiBody(contextDoc.html(), doc.getElementsByTag("body").get(0).html());

        contextDoc = TemplateUtil.replaceWikiBody(contextDoc.html(), doc.getElementsByTag("body").get(0).html());
        String s = contextDoc.html();
        //包括模板
        if (s.contains(TemplateUtil.JOYME_WIKI_COMMENT)) {
            String commentHtml = addCommentJs(contextDoc, param.getSaveFileName(), param.getPageInfo(), param.getKey(), param.getWikiType());
            if (!StringUtil.isEmpty(commentHtml)) {
                s = TemplateUtil.replaceCommentReply(contextDoc.html(), commentHtml);
            }
        }

        //没有忽略的标记，同时包括JOYME_WIKI_RANK
        if (s.contains(TemplateUtil.JOYME_WIKI_RANK)) {
            //删除忽略的标记同时替换为空
            Element ignoreEle = doc.getElementById(TemplateUtil.JOYME_WIKI_RANK_IGNORE_ID);
            if (ignoreEle != null) {
                s = TemplateUtil.replaceRank(s, "");
                //有忽略标签上报
                wikiRankService.putIgnoreWikipage(param.getKey(), param.getWikiPageId());
            } else {
                String rankHtml = null;
                try {
                    rankHtml = TemplateUtil.genRankHtml(s, param.getKey(), param.getWikiType(), param.isSupportSubdomain());
                } catch (Exception e) {
                    LogService.errorSystemLog("", e);
                }
                if (StringUtil.isEmpty(rankHtml)) {
                    LogService.infoSystemLog("get rank html is empty.");
                }
                //替换
                s = TemplateUtil.replaceRank(s, rankHtml);
            }
        }

        //流量统计代码
        Document iwtDoc = this.getDocumentByHTML(s);
        iwtDoc = addIWTJs(iwtDoc);
        s = iwtDoc.html();
        return s;
    }

    //merage wiki header
    private Document merageHeaderToContextDoc(Document templateDoc, Document wikiDoc) {
        Elements wikiElements = wikiDoc.getElementsByTag("head");
        Elements templateHead = templateDoc.getElementsByTag("head");

        if (templateHead != null && templateHead.size() > 0) {
            templateHead.get(0).prepend(wikiElements.html());

            Elements titleElements = templateHead.get(0).getElementsByTag("title");
            if (titleElements != null && titleElements.size() > 1) {
                titleElements.get(1).remove();
            }
            Elements keyWordsElements = templateHead.get(0).getElementsByAttributeValue("name", "keywords");
            if (keyWordsElements != null && keyWordsElements.size() > 1) {
                keyWordsElements.get(1).remove();
            }
            Elements descElements = templateHead.get(0).getElementsByAttributeValue("name", "description");
            if (descElements != null && descElements.size() > 1) {
                descElements.get(1).remove();
            }

        }

        return templateDoc;
    }

    private Document replaceMetaElement(Document documnet, String wikiType) {
        Elements templateHead = documnet.getElementsByTag("head");
        if (templateHead == null || templateHead.size() <= 0) {
            return documnet;
        }

        Elements charsetElements = templateHead.get(0).getElementsByAttribute("charset");
        if (charsetElements != null && charsetElements.size() > 0) {
            charsetElements.remove();
        }
        documnet.select("meta[name=MobileOptimized]").remove();
        documnet.select("meta[name=viewport]").remove();

        //在所有meta标签前面加上charset，mwiki后面加上320
        Elements metaElements = templateHead.get(0).getElementsByTag("meta");
        if (metaElements != null && metaElements.size() > 0) {
            if (wikiType.equals(WikiUtil.MOBILE_WIKI)) {
                metaElements.last().after("<meta name=\"MobileOptimized\" content=\"320\" />");
                metaElements.last().after("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" />");
            }
            metaElements.last().after("<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />");
        } else {
            templateHead.prepend("<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />");
        }

        //divice
        String html = "";
        if (wikiType.equals(WikiUtil.MOBILE_WIKI)) {
            html = "<meta name=\"applicable-device\"content=\"mobile\">";
        } else {
            html = "<meta name=\"applicable-device\"content=\"pc\">" +
                    "<meta name=\"mobile-agent\"content=\"format=xhtml;url=http://m.joyme.com/\">" +
                    "<meta name=\"mobile-agent\" content=\"format=html5;url=http://m.joyme.com\">";
        }
        documnet.select("meta[name=applicable-device]").remove();
        documnet.select("meta[name=mobile-agent]").remove();
        Elements headElements = templateHead.get(0).getAllElements();
        if (headElements.size() > 0) {
            Element meta = headElements.get(1);
            meta.before(html);
        }
        return documnet;
    }

    private Document addIWTJs(Document doc) {
        List elements = doc.getElementsByTag("body");
        if (elements.size() >= 1) {
            Element body = (Element) elements.get(0);
            String iwtHtml = TemplateService.getIWTTemplate();
            body.append(iwtHtml);
        }
        return doc;
    }

    private Document getSEOInfoByHiddenTag(Document doc) {
        Element hidden = doc.getElementById("seo_hidden");
        Elements headElements = doc.getElementsByTag("head");
        if (hidden == null || headElements == null) {
            return doc;
        }

        Element hiddenTitle = doc.getElementById("seo_hidden_title");
        if (hiddenTitle != null) {
            Elements elements = headElements.get(0).getElementsByTag("title");
            if (elements != null) {
                elements.get(0).text(hiddenTitle.text());
            }
        }

        Element hiddenKeyWords = doc.getElementById("seo_hidden_keywords");
        if (hiddenKeyWords != null) {
            Elements metaElements = headElements.get(0).getElementsByAttributeValue("name", "keywords");
            if (metaElements != null) {
                metaElements.get(0).attr("content", hiddenKeyWords.text());
            }
        }
        Element hiddenDesc = doc.getElementById("seo_hidden_description");
        if (hiddenDesc != null) {
            Elements descElements = headElements.get(0).getElementsByAttributeValue("name", "description");
            if (descElements != null) {
                descElements.get(0).attr("content", hiddenDesc.text());
            }
        }
        hidden.remove();
        return doc;
    }

    private Document getDocumentByHTML(String html) {
        return Jsoup.parse(html);
    }

    private void replaceURL(String domain, String path, String key, Document doc, JoymeWiki joymeWiki) throws UnsupportedEncodingException, JoymeDBException,
            JoymeServiceException {
        Elements elements = doc.getElementsByTag("a");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String href = element.attr("href");
            String temp = this.replaceURLtoWikePageId(domain, path, key, href, joymeWiki);
            element.attr("href", temp);
        }
    }


    private String replaceURLtoWikePageId(String domain, String path, String key, String url, JoymeWiki joymeWiki) throws UnsupportedEncodingException, JoymeDBException,
            JoymeServiceException {
        String domainPath = domain + path;
        url = getReplaceURL(key, url, domainPath);
        url = getReplaceURL(key, url, path);

        url = getReplaceURL(key, url, "/" + key);
        // 替换首页
        url = this.getIndexPage(domain, key, url, joymeWiki);

        return url;
    }

    private String getReplaceURL(String key, String url, String flag) throws UnsupportedEncodingException, JoymeDBException, JoymeServiceException {
        if (url.startsWith(flag)) {
            String urlkey = url.substring(flag.length(), url.length());
            String prefix = "";
            int position = urlkey.indexOf("#");
            // 除去锚点
            String temp = "";
            if (position > 0) {
                temp = urlkey.substring(0, position);
                prefix = urlkey.substring(position, urlkey.length());
            } else {
                temp = urlkey;
            }

            String chineseURLKey = URLDecoder.decode(temp, ENCODING);

            //去掉模版和特殊页面
            if (chineseURLKey.startsWith("模板:") || chineseURLKey.startsWith("特殊:") || chineseURLKey.startsWith("文件:")) {
                LogService.infoSystemLog("排除:" + chineseURLKey);
                return "#";
            } else if (chineseURLKey.equals("首页")) {
                LogService.infoSystemLog("排除原始首页" + chineseURLKey);
                return "./index.shtml";
            } else {
                long wikiPageId = pageService.getWikiPageIdByChineseURL(null, key, chineseURLKey);
                return wikiPageId + ".shtml" + prefix;
            }
        }
        return url;
    }

    protected String getIndexPage(String domain, String key, String url, JoymeWiki joymeWiki) {
        String targetUrl = domain + "/";
        if (joymeWiki.getSupportSubDomain() && (url.equals(targetUrl) || url.endsWith("index.shtml"))) {
            return domain;
        }
        if (url.equals(targetUrl)) {
            return "./index.shtml";
        }
        return url;
    }

    //////////////////
    protected void replaceImage(String domain, Document doc) {
        Elements elements;
        elements = doc.getElementsByTag("img");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String src = element.attr("src");
            element.attr("src", this.changeImageUrl(domain, src));

            String width = element.attr("width");
            String height = element.attr("height");

            String style = "";
            if (width != null) {
                style += "width: 100%;max-width: " + width.replace("px", "") + "px;height:auto";
                element.removeAttr("width");
                element.removeAttr("height");
            }
            element.attr("style", style);
        }
    }

    private String changeImageUrl(String domain, String url) {
        if (!url.startsWith("http://")) {
            url = domain + url;
        }
        return url;
    }


    private void removeJquery(Document doc) {
        Elements elementsScript = doc.getElementsByTag("script");
        for (Iterator iterator = elementsScript.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String src = element.attr("src");
            if (!StringUtil.isEmpty(src) && src.contains("jquery-1.9.1.min.js")) {
                element.remove();
            }
        }
    }


    protected void addJquery(Document doc) {
        if (doc.html().indexOf("jquery-1.9.1.min.js") < 0) {
            Elements elements = doc.getElementsByTag("head");
            if (elements.size() >= 1) {
                Element element = elements.get(0);
                Element jqueryElement = element.appendElement("script");
                jqueryElement.attr("src", PropertiesContainer.getInstance().getSourceUrl() + "/js/jquery-1.9.1.min.js");
                jqueryElement.attr("language", "javascript");
            }
        }
    }

    protected String addCommentJs(Document doc, String fid, String title, String wikiKey, String wikiType) {
        String returnObj = null;
        String html = doc.html();
        if (!html.contains(TemplateUtil.JOYME_WIKI_COMMENT) && !html.contains(TemplateUtil.JOYME_WIKI_WAP_COMMENT)) {
            return returnObj;
        }

        String url = PropertiesContainer.getInstance().getWikiHost() + "/" + wikiKey + "/" + fid;
        if (fid.indexOf("?") >= 0) {
            fid = fid.substring(0, fid.indexOf("?"));
        }
        String uniKey = wikiKey + "|" + fid;
        int domain = 1;

        if (html.indexOf("comment.js") < 0) {
            Elements elements = doc.getElementsByTag("body");
            if (elements.size() >= 1) {
                Element element = elements.get(0);
                Element commentElement = element.appendElement("script");
                if (wikiType.equals(WikiUtil.PC_WIKI)) {
                    commentElement.attr("src", PropertiesContainer.getInstance().getJoymePcCommentJsSrc());
                } else {
                    commentElement.attr("src", PropertiesContainer.getInstance().getJoymeMobileCommentJsSrc());
                }

                commentElement.attr("language", "javascript");

                Element contentElement = element.appendElement("script");
                contentElement.html("var unikey = '" + uniKey + "';");
                contentElement.append("var comment_domain = '" + domain + "';");
                contentElement.append("var title = '" + title + "';");
                contentElement.append("var uri = '" + url + "';");
                contentElement.attr("language", "javascript");

                String commentHtml = TemplateService.getCommentTemplate("default");

                returnObj = commentHtml.replaceAll("\\{joyme:comment-uri/\\}", url);
                returnObj = returnObj.replaceAll("\\{joyme:comment-unikey/\\}", uniKey);
                returnObj = returnObj.replaceAll("\\{joyme:comment-domain/\\}", domain + "");
                returnObj = returnObj.replaceAll("\\{joyme:comment-title/\\}", title);
            }
        }
        return returnObj;
    }


    /**
     * 去掉shtml中的php文件，减少调用时间
     *
     * @param doc
     */
    protected void removePhpFile(Document doc, JoymeWiki joymeWiki) {
        Elements elements = doc.getElementsByTag("link");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            if (element.attr("rel").equals("EditURI") && element.attr("href").indexOf(".php") > 0) {
                LogService.infoSystemLog("=====remove editurl ======");
                element.remove();
            }
            if (element.attr("rel").equals("search")) {
                LogService.infoSystemLog("=====remove search========");
                element.remove();
            }
            if (element.attr("rel").equals("alternate")) {
                LogService.infoSystemLog("=====remove alternate========");
                element.remove();
            }
        }


        elements = doc.getElementsByTag("script");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            if (element.attr("src").indexOf("load.php") > 0) {
                LogService.infoSystemLog("=====remove load.php script ======");
                if (JoymeWiki.getKeepJsCss(joymeWiki) == 0) {
                    element.remove();
                } else {
                    if (element.attr("src").startsWith("/")) {
                        element.attr("src", joymeWiki.getJoymeWikiDomain() + element.attr("src"));
                    }
                }
            }
        }
    }

    protected void replaceCss(String key, Document doc, String wikiType, JoymeWiki joymeWiki) throws IOException, Exception {
        Elements elements = doc.getElementsByTag("link");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();

            if (element.attr("rel").equals("stylesheet")) {
                String href = element.attr("href");
                if (JoymeWiki.getKeepJsCss(joymeWiki) == 0) {
                    if (href.indexOf("load.php") >= 0) {
                        String cssPath = su.getWebRootPath() + "css/" + wikiType + "/" + key + "/style.css";
                        File file = new File(cssPath);
                        if (!file.exists()) {
                            makeCssFile(href, cssPath, file);
                        }
                        element.attr("href", PropertiesContainer.getInstance().getSourceUrl() + "/css/" + wikiType + "/" + key + "/style.css");
                    }
                    LogService.infoSystemLog("====替换 css 成功=======");
                }

                if (href.startsWith("/")) {
                    element.attr("href", joymeWiki.getJoymeWikiDomain() + href);
                }
            }

            if (element.attr("rel").equals("shortcut icon")) {
                element.attr("href", "http://lib.joyme.com/static/img/favicon.ico");
                element.attr("type", "image/x-icon");
            }

        }

    }

    protected void changeSearchInputBox(String key, Elements elements) {
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            if (element.attr("action").equals("/index.php") && element.attr("id").equals("searchform")) {
                //改action
                element.attr("action", "../search/search.jsp");
                element.attr("method", "get");

                //增加一个key的hidden
                Element childElement = element.appendElement("input");
                childElement.attr("type", "hidden");
                childElement.attr("name", "key");
                childElement.attr("value", key);

                //增加一个pageNum的hidden
                childElement = element.appendElement("input");
                childElement.attr("type", "hidden");
                childElement.attr("name", "pageNum");
                childElement.attr("value", "1");

                //增加一个pageCount的hidden
                childElement = element.appendElement("input");
                childElement.attr("type", "hidden");
                childElement.attr("name", "pageCount");
                childElement.attr("value", "10");
            }
        }
    }

    protected String getWikiKey(String url) throws UnsupportedEncodingException {
        String wikiKey = "";
        int position = url.lastIndexOf("/");
        if (position >= 0) {
            wikiKey = url.substring(position + 1, url.length());
            wikiKey = URLDecoder.decode(wikiKey, ENCODING);
        }
        return wikiKey;
    }

    protected void replaceNoFollow(Document doc, String wikiKey) {
        Elements elements;
        if (!wikiKey.startsWith("模板:") && !wikiKey.startsWith("特殊:") && !wikiKey.startsWith("文件:")) {
            elements = doc.getElementsByTag("meta");
            for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                removeElement(element, "noindex");
                removeElement(element, "nofollow");
                removeElement(element, "noarchive");
            }
        }
    }

    private void removeElement(Element element, String value) {
        if (element.attr("name").equals("robots") && element.attr("content").equals(value)) {
            element.remove();
        }
    }

    private StringBuffer removeImgNextLine(String docHtml) throws IOException {

        InputStream is = null;
        StringBuffer retHtml = new StringBuffer();
        BufferedReader br = null;
        try {
            is = new ByteArrayInputStream(docHtml.getBytes("utf-8"));
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            //中文标点变成? ByteArrayInputStream的问题
            String line = null;
            while ((line = br.readLine()) != null) {
                String s = line;
                if (!line.trim().startsWith("<img")) {
                    s = line + "\r\n";
                } else {
                    s = s.trim();
                }
                retHtml.append(s);
            }

        } finally {
            if (br != null) {
                br.close();
                br = null;
            }
            if (is != null) {
                is.close();
                is = null;
            }
        }
        return retHtml;
    }

    private void writeFile(String path, String html) throws IOException {
        File file = new File(path);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        OutputStreamWriter write = new OutputStreamWriter(fileOutputStream, ENCODING);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new BufferedWriter(write));
            writer.write(html);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    private void makeCssFile(String href, String cssPath, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        String cssHtml = this.getHTML(href);
        this.writeFile(cssPath, cssHtml);
    }

    private String getHTML(String pageURL) {
        StringBuilder pageHTML = new StringBuilder();
        try {
            URL url = new URL(pageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), ENCODING));
            String line = null;
            while ((line = br.readLine()) != null) {
                pageHTML.append(line);
                pageHTML.append("\r\n");
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageHTML.toString();
    }


    private String replaceWapComment(WikiPraseParam param, String channel, String html) {
        CommentContent commentContent = null;
        String unikey = "";
        try {
            String uri = "";
            boolean isSupportDomain = PropertiesContainer.getInstance().supportSubDomain(param.getKey(), "wiki");
            if (isSupportDomain) {
                String lastURl = "/" + param.getSaveFileName();
                lastURl = lastURl.replaceAll("//", "/");
                uri = param.getDomain() + lastURl;
            } else {
                String lastURl = "/" + param.getKey() + "/" + param.getSaveFileName();
                lastURl = lastURl.replaceAll("//", "/");
                uri = "http://www.joyme.com/wiki" + lastURl;
            }

            String jsonparam = "{\"title\":\"" + param.getPageInfo() + "\",\"pic\":\"\",\"uri\":\"" + uri + "\",\"description\":\"\"}";

            unikey = param.getKey() + "|" + param.getSaveFileName();
            commentContent = commentService.getCid2(unikey, jsonparam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (commentContent == null) {
            html = html.replace(TemplateUtil.JOYME_WIKI_WAP_COMMENT, "");
            return html;
        }

        String contentUrl = PropertiesContainer.getInstance().getJoymeCommentListUrl2() + "?unikey=" + unikey + "&domain=1";

        StringBuffer str = new StringBuffer();
        str.append("");
        str.append("<div id = \"wap-comment-link-2\" >");
        str.append(" <a href = \"" + contentUrl + "\" > 我要评论 </a >");
        str.append("<span > 玩家评论：<i id='replynum'> " + commentContent.getReplyNum() + " </i ></span >");
        str.append(" </div >");
        str.append(" <a id = \"wap-comment-link\" href =\"" + contentUrl + "\" ></a >");

        html = html.replace(TemplateUtil.JOYME_WIKI_WAP_COMMENT, str.toString());
        Document doc = Jsoup.parse(html);

        Element head = doc.head();
        head.append("<link rel=\"stylesheet\" href=\"" + PropertiesContainer.getInstance().getTemplateSourceUrl() + "/css/mt/mwiki/default/1/wap-comment.css\" />");
        html = doc.html();
        return html;
    }


    private String replaceWXWapComment(WikiPraseParam param, String channel, String html) {
        CommentContent commentContent = null;
        String unikey = "";
        try {
            String uri = "";
            boolean isSupportDomain = PropertiesContainer.getInstance().supportSubDomain(param.getKey(), "wiki");
            if (isSupportDomain) {
                String lastURl = "/" + param.getSaveFileName();
                lastURl = lastURl.replaceAll("//", "/");
                uri = param.getDomain() + lastURl;
            } else {
                String lastURl = "/" + param.getKey() + "/" + param.getSaveFileName();
                lastURl = lastURl.replaceAll("//", "/");
                uri = "http://www.joyme.com/wiki" + lastURl;
            }

            String jsonparam = "{\"title\":\"" + param.getPageInfo() + "\",\"pic\":\"\",\"uri\":\"" + uri + "\",\"description\":\"\"}";

            unikey = param.getKey() + "|" + param.getSaveFileName();
            commentContent = commentService.getCid2(unikey, jsonparam);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (commentContent == null) {
            html = html.replace(TemplateUtil.JOYME_WX_WIKI_WAP_COMMENT, "");
            return html;
        }

        String contentUrl = PropertiesContainer.getInstance().getJoymeCommentListUrl2() + "?unikey=" + unikey + "&domain=1";

        StringBuffer str = new StringBuffer();
        str.append("<a id=\"wap-comment-link-v2\" href=\"" + contentUrl + "\">");
        str.append("<span>我要评论</span><b>评论</b>");
        str.append("</a>");

        html = html.replace(TemplateUtil.JOYME_WX_WIKI_WAP_COMMENT, str.toString());
        Document doc = Jsoup.parse(html);

        Element head = doc.head();
        head.append("<link rel=\"stylesheet\" href=\"" + PropertiesContainer.getInstance().getTemplateSourceUrl() + "/css/mt/mwiki/default/1/wap-comment.css\" />");
        head.append("<script> var unikey = '70334';var comment_domain = '2';var title = '经典再续!《梦幻西游》手游正式开启不删档测试';var uri = 'http://article.joyme.com/article/news/official/201502/1170334.html';</script>");

        html = doc.html();
        return html;
    }

    private String replaceWikiVisitorTop(WikiPraseParam param, String html, boolean isSupportDomain) throws ParseException, JoymeServiceException, JoymeDBException {
        long day = 24 * 60 * 60 * 1000L;
        String date = DateUtil.convert2String(System.currentTimeMillis() - day, DateUtil.DATE_FORMAT);
        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date + " 00:00:00");
        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date + " 23:59:59");
        List<WikiRecommend> list = wikiRecommendService.queryWikiRecommendList(null, param.getKey(), 1, start, end);
        StringBuffer str = new StringBuffer();
        str.append("<ul>");
        for (WikiRecommend wikiRecommend : list) {
            if (isSupportDomain) {
                str.append("<li><a href=\"http://" + param.getKey() + ".joyme.com" + wikiRecommend.getPhp_url() + "\">" + wikiRecommend.getTitle() + "</a></li>");
            } else {
                str.append("<li><a href=\"http://www.joyme.com" + wikiRecommend.getPhp_url() + "\">" + wikiRecommend.getTitle() + "</a></li>");
            }
        }
        str.append("</ul>");
        html = html.replace(TemplateUtil.JOYME_WIKI_VISITOR_TOP, str.toString());
        return html;
    }

    private String replaceWikiRecentUpdate(WikiPraseParam param, String html, boolean isSupportDomain) throws ParseException, JoymeServiceException, JoymeDBException {
        List<WikiRecommend> list = wikiRecommendService.queryWikiRecommendList(null, param.getKey(), 2);
        StringBuffer str = new StringBuffer();
        str.append("<ul>");
        for (WikiRecommend wikiRecommend : list) {
            if (isSupportDomain) {
                str.append("<li><a href=\"http://" + param.getKey() + ".joyme.com/" + wikiRecommend.getPageid() + ".shtml\">" + wikiRecommend.getTitle() + "</a></li>");
            } else {
                str.append("<li><a href=\"http://www.joyme.com/wiki/" + param.getKey() + "/" + wikiRecommend.getPageid() + ".shtml\">" + wikiRecommend.getTitle() + "</a></li>");
            }
        }
        str.append("</ul>");
        html = html.replace(TemplateUtil.JOYME_WIKI_RECENT_UPDATE, str.toString());
        return html;
    }


}
