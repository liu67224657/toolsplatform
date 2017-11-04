package com.enjoyf.wiki.facade;

import com.enjoyf.wiki.container.PropertiesContainer;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  2014/5/20 17:13
 * Description:
 */
public class ToolsTemplate {

    private static String templateSourceUrl = PropertiesContainer.getInstance().getTemplateSourceUrl();

    public static String CARD_COMPARE_WIKI_DEFAULT = "\n" +
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\t<head>\n" +
            "\t\t<meta charset=\"utf-8\" />\n" +
            "\t\t<title>卡牌对比页</title>\n" +
            "\t</head>\n" +
            "\t<link rel=\"stylesheet\" href=\"" + templateSourceUrl + "/css/wiki/util.css\" />\n" +
            "\t<body>\n" +
            "\t{joyme:wiki_body}\n" +
            "\t</body>\n" +
            "</html>\n";

    public static String CARD_COMPARE_MWIKI_DEFAULT = "\n" +
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\t<head>\n" +
            "\t\t<meta charset=\"utf-8\" />\n" +
            "\t\t<meta name=\"viewport\" content=\"width=device.width, initial-scale=1.0, user-scalable=no\" />\n" +
            "\t\t<title>卡牌对比页</title>\n" +
            "\t</head>\n" +
            "\t<link rel=\"stylesheet\" href=\"" + templateSourceUrl + "/css/wiki/util.css\" />\n" +
            "\t<body>\n" +
            "\t{joyme:wiki_body}\n" +
            "\t</body>\n" +
            "</html>\n";

    public static String CARD_OPINION_MWIKI_DEFAULT = "\n" +
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\t<head>\n" +
            "\t\t<meta charset=\"utf-8\" />\n" +
            "\t\t<meta name=\"viewport\" content=\"width=device.width, initial-scale=1.0, user-scalable=no\" />\n" +
            "\t\t<title>数据补充</title>\n" +
            "\t</head>\n" +
            "\t<script src=\""+templateSourceUrl+"/js/jquery-1.9.1.min.js\" language=\"javascript\"></script>\n" +
            "\t<link rel=\"stylesheet\" href=\"" + templateSourceUrl + "/css/dtcq/data/data.css\" />\n" +
            "\t<body>\n" +
            "\t{joyme:wiki_body}\n" +
            "\t</body>\n" +
            "</html>\n";
}
