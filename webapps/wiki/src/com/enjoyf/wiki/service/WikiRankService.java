package com.enjoyf.wiki.service;

import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.bean.temp.WikiRankBean;
import com.enjoyf.wiki.container.PropertiesContainer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/7/13
 * Description:
 */
public class WikiRankService {
    private WikiPageService wikiPageService = new WikiPageService();
    private static URLUtil urlUtil = new URLUtil();

    private static final String KEY_PREFIX_PHP_URL = "http://#key#." + PropertiesContainer.getInstance().getSubDomain() + "/wiki/";

    private static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日");


    private static final String REDIS_KEY_LASTMODIFYLIST = "wikipage_lmlist_";

    private static final String REDIS_KEY_HOTLIST = "wikipage_htlist_";

    private static final String REDIS_KEY_WIKI_IGNORE_PAGE_LIST = "wikipage_igplist_";


    /**
     * 对外访问接口，通过wikikey得到最新的列表数据
     *
     * @param wikiKey
     * @return
     */
    public List<WikiRankBean> getLastModifyList(String wikiKey) {
        List<String> list = PropertiesContainer.getInstance().getRedisManager().lrange(REDIS_KEY_LASTMODIFYLIST + wikiKey, 0, -1);

        List<WikiRankBean> returnList = new ArrayList<WikiRankBean>();
        for (String json : list) {
            WikiRankBean bean = WikiRankBean.getByJson(json);
            returnList.add(bean);
        }

        return returnList;
    }


    public void fetchLastModifyList(String wikiKey) throws IOException {
        List<WikiRankBean> returnList = new ArrayList<WikiRankBean>();

        //get document by jsoup
        String url = getLastModifyUrl(wikiKey);
        String html = urlUtil.openURLWithTimeOut(url);
        Document document = Jsoup.parse(html);

        //得到最外div
        Element element = document.getElementById("mw-content-text");
        if (element == null) {
            return;
        }

        //得到ul 通过ul元素得到date元素
        Elements titleElements = element.getElementsByClass("wiki_title");
        Set<String> set = new HashSet<String>();
        long now = System.currentTimeMillis();
        for (Element titleElement : titleElements) {
            String title = titleElement.text();

            if (!isIgnoreLink(title) && !set.contains(title)) {
                try {
                    long id = getWikiIdByText(wikiKey, title);
                    if (id < 0l) {
                        continue;
                    }

                    if (isIgnoreWikiPage(wikiKey, String.valueOf(id))) {
                        continue;
                    }

                    WikiRankBean bean = new WikiRankBean();
                    bean.setId(id);
                    bean.setText(title);
                    bean.setWikikey(wikiKey);
                    bean.setTime(now);
                    set.add(title);
                    returnList.add(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }


        //不足8个，从历史记录里补足, 一个文章只能出现一次
        Set<Long> hasExistsIds = new HashSet<Long>();
        for (WikiRankBean bean : returnList) {
            hasExistsIds.add(bean.getId());
        }

        if (returnList.size() < 8) {
            //取历史记录，补充到8-returnlist.size()个元素，
            List<WikiRankBean> oldList = getLastModifyList(wikiKey);
            int oldIdx = 0;
            int oldAddSize = 8 - returnList.size();

            for (WikiRankBean oldBean : oldList) {
                //补充条件：第一次出现的ID，同时不是忽略过的页面
                if (!hasExistsIds.contains(oldBean.getId()) && !isIgnoreWikiPage(wikiKey, String.valueOf(oldBean.getId()))) {
                    returnList.add(oldBean);
                }

                hasExistsIds.add(oldBean.getId());
                if (oldIdx == oldAddSize - 1) {
                    break;
                }
                oldIdx++;
            }
        }

        //覆盖历史记录
        PropertiesContainer.getInstance().getRedisManager().remove(REDIS_KEY_LASTMODIFYLIST + wikiKey);
        int idx = 0;
        for (WikiRankBean bean : returnList) {
            hasExistsIds.add(bean.getId());
            PropertiesContainer.getInstance().getRedisManager().rpush(REDIS_KEY_LASTMODIFYLIST + wikiKey, bean.getJsonString());

            if (idx == 7) {
                break;
            }
            idx++;
        }

    }

    /**
     * 通过wikikey专区url，生成key，url，date，放在定时器或者后台
     *
     * @param wikiKey
     * @throws IOException
     */
    @Deprecated
    public void fetchLastedModifyList(String wikiKey) throws IOException {
        List<WikiRankBean> returnList = new ArrayList<WikiRankBean>();

        //get document by jsoup
        String url = getLastModifyUrl(wikiKey);
        String html = urlUtil.openURLWithTimeOut(url);
        Document document = Jsoup.parse(html);

        //得到最外div
        Elements elements = document.getElementsByClass("mw-content-text");
        if (elements.isEmpty()) {
            return;
        }
        Element changsDivEle = elements.first();

        //得到ul 通过ul元素得到date元素
        Elements ulEles = changsDivEle.getElementsByClass("special");
        Set<String> set = new HashSet<String>();
        for (Element ulEle : ulEles) {
            //处理date
            Element dateEle = ulEle.previousElementSibling();
            if (dateEle == null) {
                continue;
            }
            String date = dateEle.html();
            if (StringUtil.isEmpty(date)) {
                continue;
            }
            Date time = formatDate(date);
            if (time == null) {
                continue;
            }

            //处理url
            Elements linkEles = ulEle.getElementsByClass("mw-changeslist-title");
            for (Element linkEle : linkEles) {
                String text = linkEle.html();

                if (!isIgnoreLink(text) && !set.contains(text)) {
                    try {
                        long id = getWikiIdByText(wikiKey, text);
                        if (id < 0l) {
                            continue;
                        }

                        if (isIgnoreWikiPage(wikiKey, String.valueOf(id))) {
                            continue;
                        }

                        WikiRankBean bean = new WikiRankBean();
                        bean.setId(id);
                        bean.setText(text);
                        bean.setWikikey(wikiKey);
                        bean.setTime(time.getTime());
                        set.add(text);
                        returnList.add(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        }


        //不足8个，从历史记录里补足, 一个文章只能出现一次
        Set<Long> hasExistsIds = new HashSet<Long>();
        for (WikiRankBean bean : returnList) {
            hasExistsIds.add(bean.getId());
        }

        if (returnList.size() < 8) {
            //取历史记录，补充到8-returnlist.size()个元素，
            List<WikiRankBean> oldList = getLastModifyList(wikiKey);
            int oldIdx = 0;
            int oldAddSize = 8 - returnList.size();

            for (WikiRankBean oldBean : oldList) {
                //补充条件：第一次出现的ID，同时不是忽略过的页面
                if (!hasExistsIds.contains(oldBean.getId()) && !isIgnoreWikiPage(wikiKey, String.valueOf(oldBean.getId()))) {
                    returnList.add(oldBean);
                }

                hasExistsIds.add(oldBean.getId());
                if (oldIdx == oldAddSize - 1) {
                    break;
                }
                oldIdx++;
            }
        }

        //覆盖历史记录
        PropertiesContainer.getInstance().getRedisManager().remove(REDIS_KEY_LASTMODIFYLIST + wikiKey);
        int idx = 0;
        for (WikiRankBean bean : returnList) {
            hasExistsIds.add(bean.getId());
            PropertiesContainer.getInstance().getRedisManager().rpush(REDIS_KEY_LASTMODIFYLIST + wikiKey, bean.getJsonString());

            if (idx == 7) {
                break;
            }
            idx++;
        }

    }

    /**
     * 增加访问的值 rediskey wikipage_htlist_<wikikey>
     *
     * @param wikiKey
     * @param id
     */
    public void incrHotRank(String wikiKey, long id) {
        String idStr = String.valueOf(id);
        if (!isIgnoreWikiPage(wikiKey, idStr)) {
            PropertiesContainer.getInstance().getRedisManager().zincrby(REDIS_KEY_HOTLIST + wikiKey, 1.0, idStr);
        }
    }

    public void incrHotRank(String wikiKey, long id, double score) {
        String idStr = String.valueOf(id);
        if (!isIgnoreWikiPage(wikiKey, idStr)) {
            PropertiesContainer.getInstance().getRedisManager().zincrby(REDIS_KEY_HOTLIST + wikiKey, score, idStr);
        }
    }


    public void putIgnoreWikipage(String wikiKey, String pageId) {
        if (!StringUtil.isEmpty(pageId)) {
            PropertiesContainer.getInstance().getRedisManager().sadd(REDIS_KEY_WIKI_IGNORE_PAGE_LIST + wikiKey, pageId);
        }
    }

    private boolean isIgnoreWikiPage(String wikiKey, String pageId) {
        return PropertiesContainer.getInstance().getRedisManager().sismember(REDIS_KEY_WIKI_IGNORE_PAGE_LIST + wikiKey, pageId);
    }

    /**
     * 得到当前天的排，并且删除当前date前一天的key，注意：如果无以外date请传入昨天的时间
     *
     * @param wikiKey
     * @return
     */
    public List<WikiRankBean> getHotRank(String wikiKey) {
        Set<String> set = PropertiesContainer.getInstance().getRedisManager().zrange(REDIS_KEY_HOTLIST + wikiKey, 0, 7, RedisManager.RANGE_ORDERBY_DESC);

        List<WikiRankBean> returnList = new ArrayList<WikiRankBean>();
        long now = System.currentTimeMillis();
        for (String id : set) {
            if (isIgnoreWikiPage(wikiKey, id)) {
                PropertiesContainer.getInstance().getRedisManager().zrem(REDIS_KEY_HOTLIST + wikiKey, id);
            }

            try {
                WikiPage wikiPage = wikiPageService.queryWikiPagebyId(null, Long.valueOf(id));

                WikiRankBean bean = new WikiRankBean();
                bean.setId(wikiPage.getPageId());
                bean.setTime(now);
                bean.setText(wikiPage.getWikiUrl());

                returnList.add(bean);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        //删除前天的key
        return returnList;
    }


    /**
     * 获取最近修改的页面链接
     *
     * @param wikiKey
     * @return
     */
    private String getLastModifyUrl(String wikiKey) {
        String url = KEY_PREFIX_PHP_URL.replace("#key#", wikiKey);

        try {
            return url + URLEncoder.encode("更新日志列表", "utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private boolean isIgnoreLink(String text) {
        return text.contains(".")
                || text.contains(":")
                || text.contains("首页");
    }

    private Date formatDate(String date) {
        String dateStr = date.replaceAll("\\([^/]+\\)", "").replace("\\s+", "");
        try {
            return dateformat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private long getWikiIdByText(String wikiKey, String text) throws Exception {
        long id = wikiPageService.getWikiPageIdByChineseURL(null, wikiKey, text);

//        todo 防止出错不会生成wikipage
//        if (id <= 0l) {
//            WikiPage wikiPage = new WikiPage();
//            wikiPage = new WikiPage();
//            wikiPage.setWikiKey(wikiKey);
//            wikiPage.setWikiUrl(text);
//            wikiPage.setCreateTime(new Timestamp(System.currentTimeMillis()));
//            wikiPage.setPageStatus(1);
//            id = wikiPageService.insertWikiPage(null, wikiPage);
//        }

        return id;

    }
}
