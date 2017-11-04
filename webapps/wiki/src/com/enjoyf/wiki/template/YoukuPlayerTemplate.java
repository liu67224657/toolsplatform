package com.enjoyf.wiki.template;

/**
 * Created by zhimingli on 2015/7/24.
 */
public class YoukuPlayerTemplate {
    public static String YoukuPlayerTemplate_SCRIPT = "\n" +
            "<script type=\"text/javascript\" src=\"http://player.youku.com/jsapi\"></script> \n" +
            "<script type=\"text/javascript\" src=\"http://static.joyme.com/app/youku/js/youku_player.js\"></script>";

    public static String YoukuPlayerTemplate_STYLE = "\n" +
            "<style>\n" +
            "#youku_video{position: relative;display: block;overflow: hidden;}\n" +
            "#youku_video:after{content:\"\";width: 100%;height:100%;position: absolute;top:0;left:0;background: rgba(0,0,0,0.4);}\n" +
            "#youku_video:before{content:\"\";width:8rem;height:8rem;position: absolute;top:50%;left:50%;background:url(http://joymepic.joyme.com/article/youkugamecenter/ipad_video_begin.png);background-size: 100% 100%;margin-left: -4rem;margin-top: -4rem;z-index: 999;}\n" +
            "#youku_video img{display: block;width: 100%;height:auto;}\n" +
            "@media screen and (max-width: 414px){\n" +
            "#youku_video:before{width: 4rem;height: 4rem;margin-left: -2rem;margin-top: -2rem;}\n" +
            "}\n" +
            "</style>";

}
