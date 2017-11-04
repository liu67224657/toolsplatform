package com.enjoyf.mcms.factory.impl;

import com.enjoyf.mcms.bean.GameBean;
import com.enjoyf.mcms.bean.JoymePointArchive;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.util.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 14/11/14
 * Description:
 */
public class WebViewParseSyhb4FactoryImpl extends AbstractParseFactoryImpl {

    protected void updateImg(int aid, Document document,String channel) throws Exception {
        Elements elements = document.getElementsByTag(IMAGE_NODE);
        int i = 0;
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            i++;

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

            // 修改src的路径
            // 如果是http://www.joyme.com/article/uploads开头的，需要先去找找upapp里面有没有相同的图片
            String imagePath = getImgPath(element.attr("src"));
            element.attr("src", imagePath);
            if (i == 1) { // 放入第一张图片的地址
                JoymePointArchive bean = new JoymePointArchive();
                bean.setArchiveId((long) aid);
                bean.setImageUrl(imagePath);
                joymePointArchiveService.updateJoymePointArchiveImageUrlByAId(null, bean);
            }

            // 修改onclick
            if (element.hasAttr("onclick")) {
                element.removeAttr("onclick");
            }
            element.parent().tagName().equalsIgnoreCase("a");
            element.parent().replaceWith(element);
        }
    }

    protected StringBuffer makeGameDownload(String channel, GameBean bean, String plat) {
        StringBuffer sb = new StringBuffer();
        String downloadURL = ConfigContainer.getGameCover() + "?gameid=" + bean.getGameDbId();

        sb.append("<div class=\"download-box\">").append(NEXT_LINE);
        if (StringUtil.isEmpty(downloadURL)) {
            sb.append("<a href=\"javascript:void(0);\">").append(NEXT_LINE);
        } else {
            sb.append("<a href=\"" + downloadURL + "\">").append(NEXT_LINE);
        }
        sb.append("<cite><img src=\"" + bean.getGameIcon() + "\" alt=\"\"></cite>").append(NEXT_LINE);
        sb.append("<h1>" + bean.getGameName() + "</h1>").append(NEXT_LINE);
        sb.append("<h2>" + bean.getDownloadRecommend() + "<span>免费下载</span></h2>").append(NEXT_LINE);
        sb.append("<b class=\"download-btn\"></b>").append(NEXT_LINE);
        sb.append("</a>").append(NEXT_LINE);
        sb.append("</div>");
        return sb;
    }

}
