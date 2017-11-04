package com.enjoyf.mcms.util;

import com.enjoyf.util.StringUtil;
import com.mongodb.util.Hash;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-9-29
 * Time: 下午5:10
 * To change this template use File | Settings | File Templates.
 */
public class MarticleUtil {

    public static Integer NATIVE = 1;
    public static Integer WEB_VIEW = 4;

    public static String ID_JOYME_DAOYU = "joyme-daoyu";
    public static String ID_JOYME_JIESUYU = "joyme-jieshuyu";
    public static String ID_JOYME_DUANLUO = "joyme-duanluo";
    public static String ID_JOYME_HTML5 = "joyme-html5";

    public static String ID_JOYME_CENGJI = "joyme-cengji";


    public static String IMG_PICTYPE = "pictype";
    public static String IMG_ALT_TYPE_SIMPLE = "simple";
    public static String IMG_ALT_TYPE_DIFF = "diff";
    public static String IMG_ALT = "alt";

    static String before = "<span style=\"color:#ddd1100;size:20\">这种</span><span style=\"color:#000000;size:16\">“科学算法”实在是太棒了！小编当初为何就没这么机智</span>";
    String s = "<font size=20 color='#dd1100'>这种</font> <font size=16 color='#000000'>“科学算法”实在是太棒了！小编当初为何就没这么机智！\"</font> ";
    private static Pattern SPAN = Pattern.compile("<span style=\"([^/]+)\">([^<]+)</span>");
    private static Pattern SPAN2 = Pattern.compile("<span style=[\',\"]([^/]+)[\',\"]>([^<]+)</span>");

    public static String replaceStylecss(String str) {
        Matcher matcher = SPAN2.matcher(str);
        if (!StringUtil.isEmpty(str) && !SPAN2.matcher(str).find()) {
            str = "<font>" + str + "</font>";
        }
        while (matcher.find()) {
            String content = matcher.group(2);
            String style = matcher.group(1);

            String[] styles = style.split(";");
            String fontStyle = "";
            for (String s : styles) {

                String[] key2value = s.split(":");
                if (key2value[0].trim().equalsIgnoreCase("color")) {
                    fontStyle += " color='" + key2value[1].trim() + "'";
                } else if (key2value[0].trim().equalsIgnoreCase("size")) {
                    fontStyle += " size='" + key2value[1].trim() + "'";
                }
            }
            String result = "<font " + fontStyle + ">" + content + "</font>";

            str = str.replace(matcher.group(0), result);
        }

        str = str.replaceAll("\"", "'");

        return str;
    }

    public static Map<String, String> channelMap = new HashMap<String, String>();
    static {
        channelMap.put("wanba210","wanba210");
    }


    public static void main(String[] args) {
        Pattern S = Pattern.compile("<span style=\"([^/]+)\">([^<]+)</span>");
        String str = "<span style=\"color: red; size: 24;\">aaaaaaaaaaaaaaa啊啊啊啊</span>这是外面的了了了了了";
        Matcher matcher2 = S.matcher(str);
        if (!StringUtil.isEmpty(str) && !matcher2.find()) {
            str = "<font>" + str + "</font>";
        }
        Matcher matcher = S.matcher(str);
        while (matcher.find()) {
            String content = matcher.group(2);
            String style = matcher.group(1);

            String[] styles = style.split(";");
            String fontStyle = "";
            for (String s : styles) {

                String[] key2value = s.split(":");
                if (key2value[0].trim().equalsIgnoreCase("color")) {
                    fontStyle += " color='" + key2value[1].trim() + "'";
                } else if (key2value[0].trim().equalsIgnoreCase("size")) {
                    fontStyle += " size='" + key2value[1].trim() + "'";
                }
            }
            String result = "<font " + fontStyle + ">" + content + "</font>";

            str = str.replace(matcher.group(0), result);
        }
        System.out.println(str);
    }

    public static String addRobotsMetaHead(String htmlTemplate) {
        return htmlTemplate.replace("<head>", "<head><meta name=\"robots\" content=\"noindex.nofollow\">");
    }
}
