package com.enjoyf.webcache.controller;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.*;
import com.enjoyf.webcache.bean.PageStat;
import com.enjoyf.webcache.bean.WebCacheClientType;
import com.enjoyf.webcache.bean.WebCacheSrcType;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.cache.PageStatCache;
import com.enjoyf.webcache.cache.PageStatCacheV2;
import com.enjoyf.webcache.cache.UrlRuleCache;
import com.enjoyf.webcache.service.PageStatService;
import com.enjoyf.webcache.service.WebCacheUrlRuleService;
import com.enjoyf.webcache.util.WebCacheUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * cms的运营后台接口，通过文章的id，返回文章的pv数、评论数等
 * Created by zhitaoshi on 2015/11/25.
 */
@Controller
@RequestMapping(value = "/json/pagestat")
public class JsonPageStatController {

    private static PageStatCacheV2 pageStatCache = new PageStatCacheV2();

    private static WebCacheUrlRuleService webCacheUrlRuleService = new WebCacheUrlRuleService();

    private static String url = "http://stat.joyme.com/";
    private static String token = "698978c46986cbd2c66b4df017ef1308";

    private static UrlRuleCache urlRuleCache = new UrlRuleCache();

    @ResponseBody
    @RequestMapping("/pvlist.do")
    public String desRule(HttpServletRequest request, HttpServletResponse response) {
        String callback = request.getParameter("callback");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            String pageIdStr = request.getParameter("pageids");
            LogService.infoSystemLog("====pageids:" + pageIdStr, true);
            String pageTypeStr = request.getParameter("pagetype");
            if (StringUtil.isEmpty(pageIdStr) || StringUtil.isEmpty(pageTypeStr)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "param.empty");
                if (StringUtil.isEmpty(callback)) {
                    return jsonObject.toString();
                } else {
                    return callback + "([" + jsonObject.toString() + "])";
                }
            }
            WebCacheSrcType pageType = WebCacheSrcType.getByCode(Integer.valueOf(pageTypeStr));
            if (pageType == null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "param.empty");
                if (StringUtil.isEmpty(callback)) {
                    return jsonObject.toString();
                } else {
                    return callback + "([" + jsonObject.toString() + "])";
                }
            }

            Map<String, PageStat> returnMap = new HashMap<String, PageStat>();

            Set<String> statIdSet = new HashSet<String>();
            if (pageIdStr.indexOf(",") > 0) {
                String[] pageIdArr = pageIdStr.split(",");
                for (String pageId : pageIdArr) {
                    try {
                        if (pageId.contains("%")) {
                            pageId = URLDecoder.decode(pageId, "UTF-8");
                        }
                    } catch (UnsupportedEncodingException e) {
                        LogService.errorSystemLog("====decode pageid error:" + pageId, e);
                    }
                    LogService.infoSystemLog("====statid:" + PageStat.buildStatId(pageId, pageType) + ",pageid:" + pageId, true);
                    statIdSet.add(PageStat.buildStatId(pageId, pageType));

                    PageStat pageStat = new PageStat();
                    pageStat.setStatId(PageStat.buildStatId(pageId, pageType));
                    pageStat.setPageId(pageId);
                    pageStat.setPageType(pageType);
                    pageStat.setPcPv(0);
                    pageStat.setmPv(0);
                    pageStat.setWanbaPv(0);
                    pageStat.setPvSum(0);
                    pageStat.setReplySum(0);
                    returnMap.put(pageStat.getPageId(), pageStat);
                }
            } else {
                String pageId = pageIdStr;
                try {
                    if (pageId.contains("%")) {
                        pageId = URLDecoder.decode(pageId, "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    LogService.errorSystemLog("====decode pageid error:" + pageId);
                }
                LogService.infoSystemLog("====statid:" + PageStat.buildStatId(pageId, pageType) + ",pageid:" + pageId, true);
                statIdSet.add(PageStat.buildStatId(pageId, pageType));

                PageStat pageStat = new PageStat();
                pageStat.setStatId(PageStat.buildStatId(pageId, pageType));
                pageStat.setPageId(pageId);
                pageStat.setPageType(pageType);
                pageStat.setPcPv(0);
                pageStat.setmPv(0);
                pageStat.setWanbaPv(0);
                pageStat.setPvSum(0);
                pageStat.setReplySum(0);
                returnMap.put(pageStat.getPageId(), pageStat);
            }

            List<String> idList = new ArrayList<String>();
            idList.addAll(statIdSet);
            if (CollectionUtil.isEmpty(idList)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "param.empty");
                if (StringUtil.isEmpty(callback)) {
                    return jsonObject.toString();
                } else {
                    return callback + "([" + jsonObject.toString() + "])";
                }
            }


            List<PageStat> list = PageStatService.queryPageStatByIdSet(null, idList);
            if (!CollectionUtil.isEmpty(list)) {
                for (PageStat pageStat : list) {
                    returnMap.put(pageStat.getPageId(), pageStat);
                }
            }

            //TODO 需求要求直播人数10倍显示,如果为live的话
            String pageshow = request.getParameter("pageshow");
            Date date = new Date();
            for (PageStat pageStat : returnMap.values()) {
                pageStat.setPcPv(pageStat.getPcPv() + pageStatCache.getInsertDbCache(pageStat.getStatId(), date, WebCacheClientType.PC));
                pageStat.setmPv(pageStat.getmPv() + pageStatCache.getInsertDbCache(pageStat.getStatId(), date, WebCacheClientType.M));
                pageStat.setWanbaPv(pageStat.getWanbaPv() + pageStatCache.getInsertDbCache(pageStat.getStatId(), date, WebCacheClientType.WANBA));

                pageStat.setPvSum(pageStat.getPcPv() + pageStat.getmPv() + pageStat.getWanbaPv());

                if ("live".equals(pageshow)) {
                    pageStat.setPvSum(pageStat.getPvSum() * 10);
                }
                
                returnMap.put(pageStat.getPageId(), pageStat);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", returnMap);
            if (StringUtil.isEmpty(callback)) {
                return jsonObject.toString();
            } else {
                return callback + "([" + jsonObject.toString() + "])";
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "system.error");
            if (StringUtil.isEmpty(callback)) {
                return jsonObject.toString();
            } else {
                return callback + "([" + jsonObject.toString() + "])";
            }
        }
    }

    @ResponseBody
    @RequestMapping("/contenttype.do")
    public String contentType(HttpServletRequest request, HttpServletResponse response) {
        String callback = request.getParameter("callback");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            String articleId = request.getParameter("articleid");
            String contentType = request.getParameter("contenttype");
            if (StringUtil.isEmpty(articleId) || StringUtil.isEmpty(contentType)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "param.empty");
                if (StringUtil.isEmpty(callback)) {
                    return jsonObject.toString();
                } else {
                    return callback + "([" + jsonObject.toString() + "])";
                }
            }
            Map<String, Integer> param = new HashMap<String, Integer>();
            param.put("contenttype", Integer.valueOf(contentType));

            PageStat pageStat = new PageStat();
            pageStat.setPageId(articleId);
            pageStat.setPageType(WebCacheSrcType.CMS);
            PageStatService.updatePageStatByColumn(null, param, pageStat);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            if (StringUtil.isEmpty(callback)) {
                return jsonObject.toString();
            } else {
                return callback + "([" + jsonObject.toString() + "])";
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "system.error");
            if (StringUtil.isEmpty(callback)) {
                return jsonObject.toString();
            } else {
                return callback + "([" + jsonObject.toString() + "])";
            }
        }
    }

    @ResponseBody
    @RequestMapping("/hotlist.do")
    public String hotList(HttpServletRequest request, HttpServletResponse response) {
        String callback = request.getParameter("callback");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            Map<String, Integer> param = new HashMap<String, Integer>();
            param.put("page_type", WebCacheSrcType.CMS.getCode());
            param.put("contenttype", 0);
            Pagination page = new Pagination(5, 1, 5);
            List<PageStat> newsList = PageStatService.queryPageStatByOrder(null, param, "pv_sum", "DESC", page);

            param = new HashMap<String, Integer>();
            param.put("page_type", WebCacheSrcType.CMS.getCode());
            param.put("contenttype", 4);
            page = new Pagination(5, 1, 5);
            List<PageStat> guideList = PageStatService.queryPageStatByOrder(null, param, "pv_sum", "DESC", page);

            param = new HashMap<String, Integer>();
            param.put("page_type", WebCacheSrcType.CMS.getCode());
            param.put("contenttype", 1);
            page = new Pagination(2, 1, 2);
            List<PageStat> videoList = PageStatService.queryPageStatByOrder(null, param, "pv_sum", "DESC", page);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");

            Map<String, List<String>> map = new HashMap<String, List<String>>();
            map.put("0", new ArrayList<String>());
            for (PageStat pageStat : newsList) {
                map.get("0").add(pageStat.getPageId());
            }
            map.put("1", new ArrayList<String>());
            for (PageStat pageStat : videoList) {
                map.get("1").add(pageStat.getPageId());
            }
            map.put("4", new ArrayList<String>());
            for (PageStat pageStat : guideList) {
                map.get("4").add(pageStat.getPageId());
            }
            jsonObject.put("result", map);

            if (StringUtil.isEmpty(callback)) {
                return jsonObject.toString();
            } else {
                return callback + "([" + jsonObject.toString() + "])";
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "system.error");
            if (StringUtil.isEmpty(callback)) {
                return jsonObject.toString();
            } else {
                return callback + "([" + jsonObject.toString() + "])";
            }
        }
    }

    @ResponseBody
    @RequestMapping("/importpv.do")
    public String importPv(HttpServletRequest request) {
        String siteId = request.getParameter("idsite");
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        try {
            String dateStr = startDate + "," + endDate;
            LogService.infoSystemLog("====import pv id site:" + siteId + "," + dateStr, true);
            HttpClientManager httpClientManager = new HttpClientManager();
            HttpResult pageUrlsResult = httpClientManager.get(url, new HttpParameter[]{
                    new HttpParameter("module", "API"),
                    new HttpParameter("method", "Actions.getPageUrls"),
                    new HttpParameter("idSite", siteId),
                    new HttpParameter("filter_limit", "-1"),
                    new HttpParameter("period", "range"),
                    new HttpParameter("date", dateStr),
                    new HttpParameter("format", "JSON"),
                    new HttpParameter("flat", "1"),
                    new HttpParameter("token_auth", token)
            }, "UTF-8");

            LogService.infoSystemLog("====import pv getPageUrls httpResult:" + pageUrlsResult.getReponseCode(), true);
            if (pageUrlsResult != null && pageUrlsResult.getReponseCode() == 200) {
                String pageUrls = pageUrlsResult.getResult();
                JSONArray pageUrlArray = null;
                try {
                    pageUrlArray = JSONArray.fromObject(pageUrls);
                } catch (Exception e) {
                    return null;
                }
                if (pageUrlArray != null && pageUrlArray.isArray() && !pageUrlArray.isEmpty()) {
                    //把url的pv数据写入db和cache
                    int i = 1;
                    for (Object piwikJsonObj : pageUrlArray.toArray()) {
                        try {
                            JSONObject piwikObj = JSONObject.fromObject(piwikJsonObj);
                            if (!piwikObj.containsKey("url")) {
                                continue;
                            }
                            String desUrl = piwikObj.getString("url");
                            if (desUrl.contains("/news/newpicture/") && desUrl.contains("#image")) {
                                System.out.println("====import pv desUrl:" + desUrl);

                                JSONObject jsonObject = JSONObject.fromObject(piwikJsonObj);
                                int pvNum = jsonObject.getInt("nb_hits");

                                //匹配每一个url的配置信息，匹配到说明是有效的url，无效的url不处理
                                Map<String, String> map = WebCacheUtil.genSrcRule(desUrl);
                                if (map != null) {
                                    String desRule = map.get("desRule");
                                    String srcRule = map.get("srcRule");
                                    if (desRule.equals("http://m.joyme.com/") || "http://www.joyme.com/".equals(desRule)) {
                                        LogService.infoSystemLog("====import pv get rule failed:" + i + "," + desUrl, true);
                                        continue;
                                    }
                                    LogService.infoSystemLog("====import pv get rule success:" + i + "," + desUrl, true);
                                    WebCacheUrlRule webCacheUrlRule = urlRuleCache.getUrlRuleMemCache(desRule);
                                    if (webCacheUrlRule == null) {
                                        try {
                                            webCacheUrlRule = webCacheUrlRuleService.getWebCacheUrlRule(null, MD5Util.Md5(desRule));
                                            if (webCacheUrlRule != null) {
                                                urlRuleCache.putUrlRuleMemCache(desRule, webCacheUrlRule);
                                            }
                                        } catch (JoymeDBException e) {
                                            LogService.errorSystemLog("====import pv getWebCacheUrlRule occur JoymeDBException", e);
                                        } catch (JoymeServiceException e) {
                                            LogService.errorSystemLog("====import pv getWebCacheUrlRule occur JoymeServiceException", e);
                                        }
                                    }
                                    if (webCacheUrlRule != null) {
                                        String pageId = "";
                                        if (webCacheUrlRule.getSrcType().equals(WebCacheSrcType.CMS)) {
                                            pageId = String.valueOf(WebCacheUtil.getArchiveId(desUrl));
                                        } else if (webCacheUrlRule.getSrcType().equals(WebCacheSrcType.WIKI)) {
                                            String wikiId = WebCacheUtil.getWikiId(desUrl);

                                            String wikiKey = webCacheUrlRule.getPageKey();
                                            if (StringUtil.isEmpty(wikiKey)) {
                                                wikiKey = WebCacheUtil.getWikiKey(desUrl);
                                            }
                                            pageId = wikiKey + "|" + wikiId;
                                        }
                                        System.out.println("====import pv pageId:" + pageId);
                                        String statId = PageStat.buildStatId(pageId, webCacheUrlRule.getSrcType());
                                        PageStat pageStatDb = PageStatService.getPageStatById(null, statId);
                                        if (pageStatDb == null) {
                                            pageStatDb = new PageStat();
                                            pageStatDb.setStatId(statId);
                                            pageStatDb.setPageId(pageId);
                                            pageStatDb.setPageType(webCacheUrlRule.getSrcType());
                                            if (webCacheUrlRule.getClientType().equals(WebCacheClientType.PC)) {
                                                pageStatDb.setPcPv(pvNum);
                                            } else if (webCacheUrlRule.getClientType().equals(WebCacheClientType.M)) {
                                                pageStatDb.setmPv(pvNum);
                                            } else if (webCacheUrlRule.getClientType().equals(WebCacheClientType.WANBA)) {
                                                pageStatDb.setWanbaPv(pvNum);
                                            }
                                            pageStatDb.setPvSum(pvNum);

                                            PageStatService.insertPageStat(null, pageStatDb);
                                        } else {
                                            pageStatDb = new PageStat();
                                            pageStatDb.setStatId(statId);
                                            pageStatDb.setPageId(pageId);
                                            pageStatDb.setPageType(webCacheUrlRule.getSrcType());
                                            if (webCacheUrlRule.getClientType().equals(WebCacheClientType.PC)) {
                                                pageStatDb.setPcPv(pvNum);
                                            } else if (webCacheUrlRule.getClientType().equals(WebCacheClientType.M)) {
                                                pageStatDb.setmPv(pvNum);
                                            } else if (webCacheUrlRule.getClientType().equals(WebCacheClientType.WANBA)) {
                                                pageStatDb.setWanbaPv(pvNum);
                                            }
                                            pageStatDb.setPvSum(pvNum);

                                            PageStatService.increasePageStat(null, pageStatDb);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            LogService.errorSystemLog("====import pv occur Exception", e);
        }
        LogService.infoSystemLog("====import pv ended:" + System.currentTimeMillis(), true);
        return null;
    }

//    @ResponseBody
//    @RequestMapping(value = "/importcomment.do")
//    public String importComment(HttpServletRequest request, HttpServletResponse response) {
//        LogService.infoSystemLog("=import comment start " + new Date(), true);
//        try {
//            int cp = 0;
//            Pagination page = null;
//            HttpClientManager httpClientManager = new HttpClientManager();
//            do {
//                cp += 1;
//                page = new Pagination(2000 * cp, cp, 2000);
//                LogService.infoSystemLog("=import comment cp:" + cp, true);
//                PageRows<PageStat> pageRows = PageStatService.queryPageStatByPage(null, null, page);
//                page = (pageRows == null ? page : pageRows.getPage());
//                if (pageRows != null && !CollectionUtil.isEmpty(pageRows.getRows())) {
//                    for (PageStat pageStat : pageRows.getRows()) {
//                        try {
//                            String uniKey = pageStat.getPageId();
//                            LogService.infoSystemLog("=import comment unikey:" + uniKey);
//                            if (pageStat.getPageType().equals(WebCacheSrcType.WIKI) && uniKey.indexOf("|") > 0 && !StringUtil.isEmpty(uniKey.substring(uniKey.indexOf("|") + 1, uniKey.length())) && StringUtil.isNumeric(uniKey.substring(uniKey.indexOf("|") + 1, uniKey.length()))) {
//                                uniKey += ".shtml";
//                            }
//                            String commentApi = "http://api.joyme.com/jsoncomment/bean/get";
//                            HttpResult httpResult = httpClientManager.get(commentApi, new HttpParameter[]{
//                                    new HttpParameter("unikey", uniKey)
//                            }, "UTF-8");
//                            int replySum = 0;
//                            Map<String, Integer> map = new HashMap<String, Integer>();
//                            if (httpResult != null && httpResult.getReponseCode() == 200) {
//                                JSONObject replyObj = JSONObject.fromObject(httpResult.getResult());
//                                LogService.infoSystemLog("=============================get comment result:" + uniKey + "," + replyObj.toString(), true);
//                                if (replyObj != null && replyObj.getString("rs").equals("1") && replyObj.getString("msg").equals("success") && replyObj.containsKey("result")) {
//                                    if (replyObj.getJSONObject("result") != null) {
//                                        replySum = replyObj.getJSONObject("result").containsKey("commentSum") ? replyObj.getJSONObject("result").getInt("commentSum") : 0;
//                                    }
//                                }
//                            }
//                            LogService.infoSystemLog("=import comment unikey:" + uniKey + ",replySum:" + replySum, true);
//                            map.put("reply_sum", replySum);
//                            PageStatService.updatePageStatByColumn(null, map, pageStat);
//                        } catch (Exception e) {
//                        }
//                    }
//                }
//                LogService.infoSystemLog("=import comment cp:" + cp + ",lastPage:" + page.isLastPage(), true);
//            } while (!page.isLastPage());
//        } catch (Exception e) {
//            LogService.errorSystemLog("=import comment error", e);
//        }
//        LogService.infoSystemLog("=import comment ended " + new Date(), true);
//        return null;
//    }

}
