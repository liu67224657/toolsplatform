package com.enjoyf.cms.factory;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.mcms.bean.GameBean;
import com.enjoyf.mcms.service.GameDownloadService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-6-11
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public class MHtmlPraser extends AbstractHtmlPraser {

    @Override
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
        System.out.println("mobile game module");
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


    //remove comment Js
    @Override
    protected void addCommentJs(Document doc, String reqUrl) {
        Element areaEle = doc.getElementById("comment_area");
        if(areaEle!=null){
            areaEle.remove();
        }
    }
}
