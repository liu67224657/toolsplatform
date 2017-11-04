package com.enjoyf.util.video;

import com.enjoyf.util.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主要处理：优酷、腾讯
 * 其他：爱奇艺、搜狐、土豆
 * Created by zhimingli on 2016/10/11 0011.
 */
public class VideoReplaceUtil {
    private static Pattern pattern = Pattern.compile("vid(.*?),");


    public static void process(Document doc) {

        //处理iframe
        checkIframe(doc);


        checkScript(doc);

        checkEmbed(doc);
    }

    private static void checkEmbed(Document doc) {
        Elements elements = doc.getElementsByTag("embed");

        /**
         * v.joyme.beta/wmls/2016/1011/160026.html?1476166793
         * sohu的
         *   <p><object height="445" width="720">
         <param name="movie" value="http://share.vrs.sohu.com/my/v.swf&amp;topBar=1&amp;id=85380155&amp;autoplay=false&amp;xuid=0cd657ed5eda429x&amp;from=page"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><param name="wmode" value="Transparent"></param><embed wmode="Transparent" allowfullscreen="true" allowscriptaccess="always" quality="high" src="http://share.vrs.sohu.com/my/v.swf&amp;topBar=1&amp;id=85380155&amp;autoplay=false&amp;xuid=0cd657ed5eda429x&amp;from=page" type="application/x-shockwave-flash" height="445" width="720" /></object></p>
         */
        for (Element element : elements) {
            if (!StringUtil.isEmpty(element.attr("src"))) {
                element.parent().html("<a style='float: none;width: auto;position: relative;z-index: 1;font-size: 16px;text-align: center;display: inline-block;padding: 10px 10px 10px 25px;background: #333 url(http://static.joyme.com/mobile/cms/jmsy/images/video-icon.png) no-repeat 7px center;background-size: 15px 15px;color: #fff;border-radius: .1rem;' href=" + element.attr("src") + ">请点此链接观看视频</a>");

            }
        }
    }


    private static void checkIframe(Document doc) {
        Elements iframeEles = doc.getElementsByTag("iframe");
        for (Element element : iframeEles) {
            //<iframe width="100%" height="400px" frameborder="0" scrolling="auto" src="http://player.youku.com/embed/XMTMwOTY3ODYxMg==" style="background:transparent;"></iframe>

            //iframe width="100%" height="428" frameborder="0" scrolling="auto" src="http://v.qq.com/iframe/player.html?vid=z0177rge5pf&amp;tiny=0&amp;auto=0" style="background:transparent;"></iframe>

            //<iframe src="http://open.iqiyi.com/developer/player_js/coopPlayerIndex.html?vid=af9539fc7e49bf9b4df93452613ea2ff&tvId=547705700&accessToken=2.f22860a2479ad60d8da7697274de9346&appKey=3955c3425820435e86d0f4cdfe56f5e7&appId=1368&height=100%&width=100%" allowfullscreen="true" height="445" frameborder="0" width="720"></iframe>


            //<iframe src="http://www.tudou.com/programs/view/html5embed.action?type=0&code=2WBs3m8KrZA&lcode=&resourceId=424646195_06_05_99" allowtransparency="true" allowfullscreen="true" allowfullscreeninteractive="true" scrolling="no" border="0" style="width:720px;height:445px;" frameborder="0"></iframe>
            if (element.attr("src").contains("player.youku.com") || element.attr("src").contains("v.qq.com/iframe/player")
                    || element.attr("src").contains("open.iqiyi.com/developer/player_js/coopPlayerIndex.html")
                    || element.attr("src").contains("www.tudou.com/programs/view/html5embed.action")) {
                element.parent().html("<a style='float: none;width: auto;position: relative;z-index: 1;font-size: 16px;text-align: center;display: inline-block;padding: 10px 10px 10px 25px;background: #333 url(http://static.joyme.com/mobile/cms/jmsy/images/video-icon.png) no-repeat 7px center;background-size: 15px 15px;color: #fff;border-radius: .1rem;' href=" + element.attr("src") + ">请点此链接观看视频</a>");

            }
        }
    }


    private static void checkScript(Document doc) {

        String vid = "";
        Elements scriptEles = doc.getElementsByTag("script");

        for (Element element : scriptEles) {
            if (element.attr("src").contains("player.youku.com")) {
                Matcher matcher = pattern.matcher(element.html());

                while (matcher.find()) {
                    vid = matcher.group(1);
                    vid = vid.replaceAll("'", "").replaceAll(":", "").replaceAll("\\\"", "").trim();


                    element.parent().prepend("<a style='float: none;width: auto;position: relative;z-index: 1;font-size: 16px;text-align: center;display: inline-block;padding: 10px 10px 10px 25px;background: #333 url(http://static.joyme.com/mobile/cms/jmsy/images/video-icon.png) no-repeat 7px center;background-size: 15px 15px;color: #fff;border-radius: .1rem;' href='http://player.youku.com/embed/" + vid + "'>请点此链接观看视频</>");
                    element.remove();

                }
            }

        }


        Element youkuplayerElement = doc.getElementById("youkuplayer");
        if (youkuplayerElement != null) {
            youkuplayerElement.attr("style", "display:none");

            if (youkuplayerElement.nextElementSibling() != null) {
                youkuplayerElement.nextElementSibling().attr("style", "display:block");
            }
        }

        Elements articlevedioElement = doc.getElementsByClass("article-vedio");
        if (articlevedioElement != null) {
            for (Element el : articlevedioElement) {
                el.attr("style", "text-align: center;padding-top: 10px;");
            }
        }

    }


}
