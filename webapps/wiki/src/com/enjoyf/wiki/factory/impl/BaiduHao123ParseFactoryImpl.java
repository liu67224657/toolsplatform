package com.enjoyf.wiki.factory.impl;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.factory.WikiPraseParam;
import com.enjoyf.wiki.service.WikiPageService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 13-12-27
 * Time: 上午10:32
 */
public class BaiduHao123ParseFactoryImpl extends AbstractParseFactoryImpl {
    private final static String ENCODING = "utf-8";
    private static WikiPageService pageService = new WikiPageService();

    protected void replaceURL(String domain, String path, String key, Document doc, JoymeWiki joymeWiki) throws UnsupportedEncodingException, JoymeDBException,
            JoymeServiceException {
        Elements elements = doc.getElementsByTag("a");
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            String href = element.attr("href");
            element.attr("target", "_blank");
            String temp = this.replaceURLtoWikePageId(domain, path, key, href, joymeWiki);
            element.attr("href", temp);
        }
    }

    private String replaceURLtoWikePageId(String domain, String path, String key, String url, JoymeWiki joymeWiki) throws UnsupportedEncodingException, JoymeDBException,
            JoymeServiceException {
        String domainPath = domain + path;
        url = getReplaceURL(key, url, domainPath);
        url = getReplaceURL(key, url, path);
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
                //前面加入path和key
                return flag + key + "/" + wikiPageId + ".shtml" + prefix;
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

    @Override
    protected Document genHtmlByDocument(Document doc, WikiPraseParam param) throws Exception {
        JoymeWiki joymeWiki = PropertiesContainer.getInstance().getJoymeWiki(param.getKey(), param.getWikiType());

        //删除模块
        super.removeHideMoudle(doc, param.getWikiType());
        LogService.infoSystemLog("====去掉" + param.getWikiType() + "隐藏模块成功=======");

        // 改href
        this.replaceURL(param.getDomain(), param.getPath(), param.getKey(), doc, joymeWiki);
        LogService.infoSystemLog("====改href成功=======");

        // 改图片
        super.replaceImage(param.getDomain(), doc);
        LogService.infoSystemLog("====改图片成功=======");

        // add jquery
        super.addJquery(doc);
        LogService.infoSystemLog("====add jquery 成功=======");

        super.removePhpFile(doc, joymeWiki);

        // 替换css
        super.replaceCss(param.getKey(), doc, param.getWikiType(), joymeWiki);

        //替换搜索框
        super.changeSearchInputBox(param.getKey(), doc.getElementsByTag("form"));
        LogService.infoSystemLog("====替换搜索框 成功=======");

        // 去掉no-follow ,特殊和模版不去
        String wikiKey = super.getWikiKey(param.getFullUrl());
        super.replaceNoFollow(doc, wikiKey);
        LogService.infoSystemLog("====去掉norobots 成功=======");

        if (param.getWikiType().equals("mwiki")) {
            super.addShareMetaKey(doc, param.getKey());
        }
        return doc;
    }
}
