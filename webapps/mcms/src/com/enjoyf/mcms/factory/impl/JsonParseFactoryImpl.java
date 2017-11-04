package com.enjoyf.mcms.factory.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.HtmlUtils;

import com.enjoyf.mcms.bean.DedeAddonarticle;
import com.enjoyf.mcms.bean.DedeArchives;
import com.enjoyf.mcms.bean.temp.ArchiveBodyBean;
import com.enjoyf.mcms.factory.IHtmlParseFactory;
import com.enjoyf.mcms.util.DateUtil;
import com.enjoyf.util.FileUtil;

public class JsonParseFactoryImpl extends AbstractParseFactoryImpl implements IHtmlParseFactory {
    public String makeArchiveHtml(DedeArchives dedeArchives, DedeAddonarticle dedeAddonarticle, String filePath, String fileName, String channel,
            String localPath) throws Exception {
        StringBuffer sb = new StringBuffer();
        // sb.append("<meta charset=\"utf-8\" />");

        JSONObject object = new JSONObject();

        object.put("rs", 1);
        // 标题
        object.put("title", dedeArchives == null ? "正文" : dedeArchives.getTitle());
        // 日期
        object.put("date", dedeArchives == null ? DEFAULT_TIME : DateUtil.convert2String(Long.parseLong(dedeArchives.getPubdate() + "000"),
                "yyyy年MM月dd日"));
        // 作者
        object.put("p_user", "着迷网");

        List noHtmlStyleBodyList = this.getNoHtmlStyleList(dedeAddonarticle.getBody());
        object.put("body", this.getBodyArray(noHtmlStyleBodyList));

        // 保存html
        sb.append(object.toString());
        String path = getArchiveCachePath(filePath, fileName, channel);
        FileUtil.writeFile(path, sb.toString());

        return sb.toString();
    }

    private JSONArray getBodyArray(List noHtmlStyleBodyList) throws JSONException {
        JSONArray array = new JSONArray();
        for (Iterator iterator = noHtmlStyleBodyList.iterator(); iterator.hasNext();) {
            ArchiveBodyBean bean = (ArchiveBodyBean) iterator.next();
            JSONObject object = new JSONObject();
            object.put("type", bean.getType());
            object.put("content", bean.getContent());
            if (bean.getLink() != null) {
                object.put("link", bean.getLink());
            }

            // 图片类型放入高和宽
            if (bean.getType() == 2) {
                object.put("width", bean.getWidth());
                object.put("height", bean.getHeight());
            }

            array.add(object.toString());
        }
        return array;
    }

    private List getNoHtmlStyleList(String html) {
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
                // bean.setLink(getMobileId(element.attr("src")));
                bean.setLink(this.getM3U8(getMobileId(element.attr("src"))));
                bean.setType(3);
                archiveBodyList.add(bean);
            }
        }

        return archiveBodyList;
    }

    /**
     * 得到m3u8的地址
     * 
     * @param mobileId
     * @return
     */
    private String getM3U8(String mobileId) {
        String link = "http://v.youku.com/player/getRealM3U8/vid/" + mobileId + "/type/mp4/v.m3u8";
        return link;
    }

    private static String removeHtmlSymbol(String html) {
        html = HtmlUtils.htmlUnescape(html);
        // html = UnicodeUtil.UnicodeToString(html);
        return html;
    }

    private static int getHeightOfImage(Element element) {
        int height = 0;
        if (element.hasAttr("height")) {
            try {
                height = Integer.parseInt(element.attr("height"));
            } catch (Exception e) {
                System.out.println("Can't get the height of the image");
            }
        }
        return height;
    }

    private static int getWidthOfImage(Element element) {
        int width = 0;
        if (element.hasAttr("width")) {
            try {
                width = Integer.parseInt(element.attr("width"));
            } catch (Exception e) {
                System.out.println("Can't get the width of the image");
            }
        }
        return width;
    }
}
