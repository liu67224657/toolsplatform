package com.enjoyf.wiki.template;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.FileUtil;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.SystemUtil;
import com.enjoyf.wiki.bean.JoymeItem;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.bean.temp.WikiRankBean;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.service.JoymeItemService;
import com.enjoyf.wiki.service.TemplateService;
import com.enjoyf.wiki.service.WikiRankService;
import com.enjoyf.wiki.tools.ZipUtil;
import com.enjoyf.wiki.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-9-18 下午12:10
 * Description:
 */
public class TemplateUtil {
    private static SystemUtil su = new SystemUtil();

    private static final String JOYME_WIKI_BODY = "{joyme:wiki_body}";

    public static final String JOYME_WIKI_COMMENT = "{joyme:wiki_comment}";


    public static final String JOYME_WIKI_WAP_COMMENT = "{joyme:wiki_wap_comment}";

    public static final String JOYME_WX_WIKI_WAP_COMMENT = "{joyme:wx_wiki_wap_comment}";

    public static final String JOYME_WIKI_RECENT_UPDATE = "{joyme:wiki_recent_update}";

    public static final String JOYME_WIKI_VISITOR_TOP = "{joyme:wiki_visitor_top}";

    public static final String JOYME_WIKI_UPDATE_TIME = "{joyme:wiki_update_time}";

    private static final String KEY_JOYME_WIKI_BODY = "wiki_body";
    private static final Pattern PATTERN_JOYME_WIKI_TAG = Pattern.compile("\\{joyme:wiki_([a-zA-Z0-9\\-_]+)\\}");

    private static Pattern PATTERN_CSS_IMG = Pattern.compile("\\.{0,2}/?[/a-zA-Z0-9_\\-]+\\.jpg");


    private static WikiRankService wikiRankService = new WikiRankService();


    //rank
    public static final String JOYME_WIKI_RANK = "{joyme:wiki_rank}";
    public static final String JOYME_WIKI_RANK_NEWSLOOP_BEGIN = "{joyme:news-loop}";
    public static final String JOYME_WIKI_RANK_NEWSLOOP_END = "{/joyme:news-loop}";
    public static final String JOYME_WIKI_RANK_HOTLOOP_BEGIN = "{joyme:hot-loop}";
    public static final String JOYME_WIKI_RANK_HOTLOOP_END = "{/joyme:hot-loop}";
    public static final String JOYME_WIKI_RANK_NEWS_URL = "{joyme:news-url/}";
    public static final String JOYME_WIKI_RANK_NEWS_TEXT = "{joyme:news-text/}";
    public static final String JOYME_WIKI_RANK_NEWS_DATE = "{joyme:news-date/}";
    public static final String JOYME_WIKI_RANK_HOT_URL = "{joyme:hot-url/}";
    public static final String JOYME_WIKI_RANK_HOT_TEXT = "{joyme:hot-text/}";
    public static final String JOYME_WIKI_RANK_HOT_DATE = "{joyme:hot-date/}";
    public static final String JOYME_WIKI_RANK_IGNORE_ID = "rank-ignore";


    private static JoymeItemService joymeItemService = new JoymeItemService();

    private static final String DEFAULT_WIKI = "default";

    public static Document replaceWikiBody(String templateContext, String wikiBody) {
        templateContext = templateContext.replace(JOYME_WIKI_BODY, wikiBody);
        return Jsoup.parse(templateContext);
    }

    public static String replaceCommentReply(String templateContext, String wikiBody) {
        templateContext = templateContext.replace(JOYME_WIKI_COMMENT, wikiBody);
        return templateContext;
    }

    public static String replaceRank(String templateContext, String wikiBody) {
        templateContext = templateContext.replace(JOYME_WIKI_RANK, wikiBody);
        return templateContext;
    }

    public static boolean isIgnoreRank(Document doc) {
        return doc.getElementById("rank-ignore") != null;
    }

    public static String replaceWikiTag(String templateContext, String wiki, String channel, Integer isIndex, String wikiType) {

        Matcher matcher = PATTERN_JOYME_WIKI_TAG.matcher(templateContext);

        StringBuffer resutlStringBuffer = new StringBuffer();

        while (matcher.find()) {
            String itemKey = matcher.group(1);
            if (itemKey.equals(KEY_JOYME_WIKI_BODY)) {
                continue;
            }

            JoymeItem item = null;
            try {
                item = joymeItemService.queryJoymeItem(null, wiki, channel, isIndex, itemKey, wikiType);
                if (item == null) {
                    item = joymeItemService.queryJoymeItem(null, wiki, DEFAULT_WIKI, isIndex, itemKey, wikiType);

                    if (item == null) {
                        item = joymeItemService.queryJoymeItem(null, DEFAULT_WIKI, channel, isIndex, itemKey, wikiType);
                    }
                    if (item == null) {
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (item != null) {
                matcher.appendReplacement(resutlStringBuffer, item.getItemContext());
            } else {
                matcher.appendReplacement(resutlStringBuffer, "");
            }
        }
        matcher.appendTail(resutlStringBuffer);
        return resutlStringBuffer.toString();
    }

    public static String processCss(String wiki, String channel, Integer isIndex, String wikiType) throws JoymeServiceException, JoymeDBException, IOException {
//        //判断当前的工程目录下有没有CSS
        String cssPathSuffix = "/css/" + wiki + "/" + wikiType + "/" + channel + "/" + isIndex;
        String cssName = "wiki.css";
        String cssNamePath = cssPathSuffix + "/" + cssName;

        return PropertiesContainer.getInstance().getTemplateSourceUrl() + cssNamePath;
    }


    public static String genRankHtml(String html, String wikiKey, String wikiType,boolean isSupportDomain) {
        String result = null;
//        String html = doc.html();
        if (!html.contains(TemplateUtil.JOYME_WIKI_RANK)) {
            return "";
        }

        List<WikiRankBean> rankBeanList = wikiRankService.getLastModifyList(wikiKey);
        List<WikiRankBean> hostRankList = wikiRankService.getHotRank(wikiKey);

        String template = TemplateService.getRankTemplateMap("default");
        int newsStartPos = template.indexOf(TemplateUtil.JOYME_WIKI_RANK_NEWSLOOP_BEGIN);
        int newsEndPos = template.indexOf(TemplateUtil.JOYME_WIKI_RANK_NEWSLOOP_END);
        String newsItemTemaple = template.substring(newsStartPos + TemplateUtil.JOYME_WIKI_RANK_NEWSLOOP_BEGIN.length(), newsEndPos);
        StringBuffer sb = new StringBuffer();
        int newsIdx = 0;
        for (WikiRankBean bean : rankBeanList) {
            String url=isSupportDomain?"http://"+wikiKey+"."+PropertiesContainer.getInstance().getSubDomain()+"/"+bean.getId() + ".shtml":PropertiesContainer.getInstance().getWikiHost() + "/" + wikiKey + "/" + bean.getId() + ".shtml" ;

            String item = newsItemTemaple.replace(TemplateUtil.JOYME_WIKI_RANK_NEWS_URL,url )
                    .replace(TemplateUtil.JOYME_WIKI_RANK_NEWS_TEXT, bean.getText()).replace(TemplateUtil.JOYME_WIKI_RANK_NEWS_DATE, DateUtil.convert2String(bean.getTime(), "yyyy/MM/dd"));
            sb.append(item);
            if (newsIdx >= 7) {
                break;
            }
            newsIdx++;
        }
        result = template.substring(0, newsStartPos) + sb.toString() + template.substring(newsEndPos + TemplateUtil.JOYME_WIKI_RANK_NEWSLOOP_END.length());

        int hotStartPos = result.indexOf(TemplateUtil.JOYME_WIKI_RANK_HOTLOOP_BEGIN);
        int hotEndPos = result.indexOf(TemplateUtil.JOYME_WIKI_RANK_HOTLOOP_END);
        String hotItemTemaple = result.substring(hotStartPos + TemplateUtil.JOYME_WIKI_RANK_HOTLOOP_BEGIN.length(), hotEndPos);
        StringBuffer hotsb = new StringBuffer();
        long now = System.currentTimeMillis();
        int hotIdx = 0;
        for (WikiRankBean bean : hostRankList) {
            String url=isSupportDomain?"http://"+wikiKey+"."+PropertiesContainer.getInstance().getSubDomain()+"/"+bean.getId() + ".shtml":PropertiesContainer.getInstance().getWikiHost() + "/" + wikiKey + "/" + bean.getId() + ".shtml" ;


            String item = hotItemTemaple.replace(TemplateUtil.JOYME_WIKI_RANK_HOT_URL, url)
                    .replace(TemplateUtil.JOYME_WIKI_RANK_HOT_TEXT, bean.getText()).replace(TemplateUtil.JOYME_WIKI_RANK_HOT_DATE, DateUtil.convert2String(now, "yyyy/MM/dd"));
            hotsb.append(item);
            if (hotIdx >= 7) {
                break;
            }
            hotIdx++;
        }
        result = result.substring(0, hotStartPos) + hotsb.toString() + result.substring(hotEndPos + TemplateUtil.JOYME_WIKI_RANK_HOTLOOP_END.length());


        return result;
    }


    public static void main(String[] args) throws JoymeServiceException, JoymeDBException, IOException {
        int beginIndex = "xmjj_2000.zip".lastIndexOf("_") + 1;
        int endIndex = "xmjj_2000.zip".lastIndexOf(".");


        String numStr = "xmjj_2000.zip".substring(beginIndex, endIndex);
        System.out.println(numStr);

    }
}
