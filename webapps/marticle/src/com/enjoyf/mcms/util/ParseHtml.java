package com.enjoyf.mcms.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.HtmlUtils;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.mcms.bean.temp.ArchiveBodyBean;
import com.enjoyf.mcms.container.ConfigContainer;

/**
 * @author liangtang
 * 
 */
public class ParseHtml {
    private static String domain = null;
    private final static String LINE_NODE = "p";
    private final static String IMAGE_NODE = "img";
    private final static String EMBED_NODE = "embed";

    public static String getNoStyleHtml(String html) {
        Document document = Jsoup.parse(html);

        // 去掉style
        removeStyle(document);

        // 修改img
        updateImg(document);

        // 处理视频
        updateEmbed(document);

        // 去掉底脚的查看更多信息
        removeJoymeInfoLink(document);

        return document.getElementsByTag("body").get(0).html();
    }

    /**
     * 得到手机访问的json串
     * 
     * @param body
     * @return
     */
    public static List getNoHtmlStyleList(String html) {
        // System.out.println(HtmlUtils.htmlUnescape(html));
        Document document = Jsoup.parse(html);
        Elements elements = document.getAllElements();
        List archiveBodyList = new ArrayList();
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            ArchiveBodyBean bean = new ArchiveBodyBean();
            // 处理p为文字类型
            if (element.nodeName().equals(LINE_NODE)) {
                bean.setContent(removeHtmlSymbol(element.text()));
                bean.setType(1);
                archiveBodyList.add(bean);
            }

            // 处理img为图片类型
            if (element.nodeName().equals(IMAGE_NODE)) {
                bean.setContent(getImgPath(element.attr("src")));
                bean.setType(2);
                bean.setHeight(getHeightOfImage(element));
                bean.setWidth(getWidthOfImage(element));
                archiveBodyList.add(bean);
            }

            // 处理视频
            if (element.nodeName().equals(EMBED_NODE)) {
                bean.setContent("请点击这里观看视频");
                bean.setLink(getMobile(element.attr("src")));
                bean.setType(3);
                archiveBodyList.add(bean);
            }
        }

        return archiveBodyList;
    }

    private static int getHeightOfImage(Element element) {
        int height = 0;
        if(element.hasAttr("height")){
            try{
                height = Integer.parseInt(element.attr("height"));
            }catch (Exception e) {
                LogService.errorSystemLog("Can't get the height of the image" , e);
            }
        }
        return height;
    }

    private static int getWidthOfImage(Element element) {
        int width = 0;
        if(element.hasAttr("width")){
            try{
                width = Integer.parseInt(element.attr("width"));
            }catch (Exception e) {
                LogService.errorSystemLog("Can't get the width of the image", e);
            }
        }
        return width;
    }

    private static String removeHtmlSymbol(String html) {
        html = HtmlUtils.htmlUnescape(html);
        // html = UnicodeUtil.UnicodeToString(html);
        return html;
    }

    private static void removeJoymeInfoLink(Document document) {
        Elements elements = document.getElementsByTag("a");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            if (element.attr("href").equalsIgnoreCase("http://www.joyme.com/article/yxgl/")) {
                element.remove();
            }
        }
    }

    private static void updateEmbed(Document document) {
        Elements elements = document.getElementsByTag("embed");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            // if (element.hasAttr("width"))
            element.attr("width", "100%");
            if (element.hasAttr("height"))
                element.removeAttr("height");

            if (element.hasAttr("src")) {
                String value = element.attr("src");

                element.appendElement("br");
                Element element2 = element.appendElement("a");
                element2.attr("href", getMobile(value));
                element2.attr("target", "_blank");
                element2.text("不能观看请点击这里");
            }
        }
    }

    private static void updateImg(Document document) {
        Elements elements = document.getElementsByTag(IMAGE_NODE);
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            if (element.hasAttr("width")) {
                try {
                    int width = Integer.parseInt(element.attr("width"));
                    if (width > 240)
                        element.attr("width", "100%");
                } catch (Exception e) {
                    element.attr("width", "100%");
                }
            } else {
                element.attr("width", "100%");
            }

            if (element.hasAttr("height"))
                element.removeAttr("height");

            // 如果是本站的图，加上域名
            element.attr("src", getImgPath(element.attr("src")));

            // 更改onclick
            if (element.hasAttr("onclick")) {
                element.removeAttr("onclick");
                // String value = element.attr("onclick");
                // if (value.startsWith("window.open(")) {
                // int startPosition = value.indexOf("'");
                // int endPosition = value.lastIndexOf("'");
                // if (startPosition > 0 && endPosition > 0) {
                // String path = value.substring(startPosition + 1,
                // endPosition);
                // element.attr("onclick", "window.open('" + getDomain() + path
                // + "')");
                // }
                // }
            }
        }
    }

    private static String getImgPath(String link) {
        return link.startsWith("/article") ? getDomain() + link : link;
    }

    private static void removeStyle(Document document) {
        Elements elements = document.getElementsByAttribute("style");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            element.removeAttr("style");
        }
    }

    /**
     * 获得域名
     * 
     * @return
     */
    private static String getDomain() {
        if (domain == null) {
            try {
                domain = ConfigContainer.getConfigValue("cms_domain");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return domain;
    }

    private static String getMobile(String value) {
        if (value.indexOf("youku") >= 0 && (value.endsWith(".swf") || value.endsWith(".html"))) {
            if (value.indexOf("v_show") >= 0) {
                return value;
            }

            int position = value.lastIndexOf("/");
            String id = "";
            if (position > 0) {
                id = value.substring(0, position);
                int position1 = id.lastIndexOf("/");
                if (position1 > 0) {
                    id = id.substring(position1 + 1, id.length());
                }

                String retString = "http://v.youku.com/v_show/id_" + id + ".html";
                return retString;
            }

            return value;
        } else {
            return value;
        }
    }
}
