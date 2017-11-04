package com.enjoyf.util;

import com.enjoyf.framework.log.LogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * Created by zhimingli on 2015/7/21.
 */
public class PlayerUtil {

    //DOC http://open.youku.com/docs?id=46
    private static String YOUKU_API_GET_BASE_INFO = "https://openapi.youku.com/v2/videos/show.json?client_id=41d1c28bee0b6f7e&video_id=";

    public static String YOUKU_WIKI_ID = "_player_youku";
    public static String TX_WIKI_ID = "_player_tx";

    private static URLUtil util = new URLUtil();

    public static int addYoukuAttrLogo(Document doc) {
        Elements elements = doc.getElementsByAttributeValueStarting("id", "youkuplayer_");
        for (Element element : elements) {
            String vid = element.attr("data-vid");
            //判断不为空 且 iframe的src是优酷的地址
            if (!StringUtil.isEmpty(vid)) {
                if (vid.indexOf("youku.com") > 0) {
                    vid = vid.substring(vid.lastIndexOf("/") + 1);
                    vid = vid.replace("id_", "");
                    if (vid.indexOf(".") > 0) {
                        vid = vid.substring(0, vid.indexOf("."));
                    }
                    if (vid.indexOf("?") > 0) {
                        vid = vid.substring(0, vid.indexOf("?"));
                    }
                    if (vid.indexOf("#") > 0) {
                        vid = vid.substring(0, vid.lastIndexOf("#"));
                    }
                }
                String logo = getVideoLogo(vid);
                element.attr("logo", logo);
            }
        }
        return elements.size();
    }


    public static String getVideoLogo(String vid) throws JSONException {
        String logo = "";

        try {
            String s = null;
            String youkuURL = YOUKU_API_GET_BASE_INFO + vid;
            try {
                s = util.openURLWithTimeOut(youkuURL);
            } catch (Exception e) {
                LogService.errorSystemLog("===Error when visit youku which url is : " + youkuURL);
                return null;
            }
            JSONObject jsobj = JSONObject.fromObject(s);
            logo = jsobj.getString("bigThumbnail");
        } catch (Exception e) {
            LogService.errorSystemLog("YoukuVideoUtil getVideoLog vid=" + vid);
            e.printStackTrace();
        }
        return logo;
    }
}
