package com.enjoyf.webcache.quartz;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.util.HttpResult;
import com.enjoyf.util.MD5Util;
import com.enjoyf.webcache.bean.WebCacheClientType;
import com.enjoyf.webcache.bean.WebCacheSrcType;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.cache.UrlRuleCache;
import com.enjoyf.webcache.factory.PageStatFactory;
import com.enjoyf.webcache.service.WebCacheUrlRuleService;
import com.enjoyf.webcache.util.WebCacheUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.quartz.*;

import java.net.URL;
import java.text.ParseException;
import java.util.*;

/**
 * 定时将站点下当天的所有url的访问pv数添加到缓存中
 * Created by ericliu on 2015/3/17.
 */
public class PiwikCacheJob extends AbstractQuartzCronTrigger implements Job {
    private static WebCacheUrlRuleService webCacheUrlRuleService = new WebCacheUrlRuleService();

    private static String url = "http://stat.joyme.com/";
    private static String token = "698978c46986cbd2c66b4df017ef1308";

    private static UrlRuleCache urlRuleCache = new UrlRuleCache();

    public static String JOB_NAME = "pagestat_job";//任务名
    public static String JOB_GROUP_NAME = "pagestat_job_group";//任务组名

    public static String TRIGGER_NAME = "pagestat_trigger";//触发器名
    public static String TRIGGER_GROUP_NAME = "pagestat_trigger_group";//触发器组

    private static boolean block = false;

    public PiwikCacheJob() throws SchedulerException {
    }

    @Override
    protected void addTriggerJob(String cronExp, Class className) throws SchedulerException {
        //任务名称 任务组名称 任务执行类
        JobDetail jobDetail = new JobDetail(JOB_NAME, JOB_GROUP_NAME, className);

        CronTrigger trigger = new CronTrigger(TRIGGER_NAME, TRIGGER_GROUP_NAME);
        try {
            trigger.setCronExpression(cronExp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // schedule a job with JobDetail and Trigger
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (block) {
            LogService.infoSystemLog("====piwikJob block:" + System.currentTimeMillis(), true);
            return;
        }
        block = true;
//        PageStatCache.putPiwikJobTimeFlag();
        LogService.infoSystemLog("====piwikJob start:" + System.currentTimeMillis(), true);
        try {
            HttpClientManager httpClientManager = new HttpClientManager();
            //取到piwik的所有idsite
            HttpResult idSiteResult = httpClientManager.get(url, new HttpParameter[]{
                    new HttpParameter("module", "API"),
                    new HttpParameter("method", "SitesManager.getAllSites"),
                    new HttpParameter("format", "JSON"),
                    new HttpParameter("token_auth", token)
            }, "UTF-8");
            LogService.infoSystemLog("====piwikJob getAllSites httpResult:" + idSiteResult.toString(), true);
            if (idSiteResult != null && idSiteResult.getReponseCode() == 200) {
                String idSite = idSiteResult.getResult();
                JSONObject idSiteObject = JSONObject.fromObject(idSite);
                if (idSiteObject != null && !idSiteObject.isNullObject()) {
                    //抓取每个idsite下面，当日访问的所有的URL的pv数据
                    Iterator iterator = idSiteObject.keys();
                    while (iterator.hasNext()) {
                        String siteId = String.valueOf(iterator.next());

                        LogService.infoSystemLog("====piwikJob id site:" + siteId, true);
                        HttpResult pageUrlsResult = httpClientManager.get(url, new HttpParameter[]{
                                new HttpParameter("module", "API"),
                                new HttpParameter("method", "Actions.getPageUrls"),
                                new HttpParameter("idSite", siteId),//todo
                                new HttpParameter("filter_limit", "-1"),
                                new HttpParameter("period", "day"),
                                new HttpParameter("date", "today"),
                                new HttpParameter("format", "JSON"),
                                new HttpParameter("flat", "1"),
                                new HttpParameter("token_auth", token)
                        }, "UTF-8");
                        LogService.infoSystemLog("====piwikJob getPageUrls httpResult:" + pageUrlsResult.getReponseCode(), true);
                        if (pageUrlsResult != null && pageUrlsResult.getReponseCode() == 200) {
                            String pageUrls = pageUrlsResult.getResult();
                            JSONArray pageUrlArray = JSONArray.fromObject(pageUrls);
                            if (pageUrlArray != null && pageUrlArray.isArray() && !pageUrlArray.isEmpty()) {
                                //把url的pv数据写入db和cache

                                Map<String, List<JSONObject>> urlToObject = new HashMap<String, List<JSONObject>>();
                                for (Object piwikJsonObj : pageUrlArray.toArray()) {
                                    JSONObject piwikObj = JSONObject.fromObject(piwikJsonObj);
                                    if (!piwikObj.containsKey("url")) {
                                        continue;
                                    }
                                    String url = piwikObj.getString("url");
                                    if (url.contains("?")) {
                                        url = url.substring(0, url.indexOf("?"));
                                    }

                                    if (url.contains("#")) {
                                        url = url.substring(0, url.indexOf("#"));
                                    }

                                    if (!urlToObject.containsKey(url)) {
                                        urlToObject.put(url, new ArrayList<JSONObject>());
                                    }
                                    urlToObject.get(url).add(piwikObj);
                                }

                                int i = 1;
                                for (Map.Entry<String, List<JSONObject>> entry : urlToObject.entrySet()) {

                                    String desUrl = entry.getKey();
                                    //匹配每一个url的配置信息，匹配到说明是有效的url，无效的url不处理
                                    Map<String, String> map = WebCacheUtil.genSrcRule(desUrl);
                                    if (map != null) {
                                        String desRule = map.get("desRule");
                                        String srcRule = map.get("srcRule");
                                        if (desRule.equals("http://m.joyme.com/") || "http://www.joyme.com/".equals(desRule)) {
                                            LogService.infoSystemLog("====piwikJob get rule failed:" + i + "," + desUrl, true);
                                            continue;
                                        }
                                        LogService.infoSystemLog("====piwikJob get rule success:" + i + "," + desUrl, true);
                                        WebCacheUrlRule webCacheUrlRule = urlRuleCache.getUrlRuleMemCache(desRule);
                                        if (webCacheUrlRule == null) {
                                            try {
                                                webCacheUrlRule = webCacheUrlRuleService.getWebCacheUrlRule(null, MD5Util.Md5(desRule));
                                                if (webCacheUrlRule != null) {
                                                    urlRuleCache.putUrlRuleMemCache(desRule, webCacheUrlRule);
                                                }
                                            } catch (JoymeDBException e) {
                                                LogService.errorSystemLog("====piwikJob getWebCacheUrlRule occur JoymeDBException", e);
                                            } catch (JoymeServiceException e) {
                                                LogService.errorSystemLog("====piwikJob getWebCacheUrlRule occur JoymeServiceException", e);
                                            }
                                        }
                                        if (webCacheUrlRule != null) {
                                        int pv = 0;
                                        for (JSONObject piwikJsonObj : entry.getValue()) {
                                            pv += piwikJsonObj.getInt("nb_hits");
                                        }

                                            WebCacheSrcType srcType = webCacheUrlRule.getSrcType();
                                        PageStatFactory.factory(srcType).pageStat(desUrl, pv, webCacheUrlRule, new Date());
                                        }
                                    } else {
                                        if (desUrl.contains("joyme.com") && (desUrl.startsWith("http://") || desUrl.startsWith("https://"))) {
                                            URL url = new URL(desUrl);
                                            if (url.getHost().equals("wiki.joyme.com")) {
                                                LogService.infoSystemLog("====piwikJob sub domain success:" + i + "," + desUrl, true);
                                                WebCacheUrlRule webCacheUrlRule = new WebCacheUrlRule();
                                                webCacheUrlRule.setSrcType(WebCacheSrcType.WIKI);
                                                webCacheUrlRule.setClientType(WebCacheClientType.PC);

                                                int pv = 0;
                                                for (JSONObject piwikJsonObj : entry.getValue()) {
                                                    pv += piwikJsonObj.getInt("nb_hits");
                                                }
                                                PageStatFactory.factory(WebCacheSrcType.WIKI).pageStat(desUrl, pv, webCacheUrlRule, new Date());
                                            } else if (url.getHost().equals("m.wiki.joyme.com")) {
                                                LogService.infoSystemLog("====piwikJob sub domain success:" + i + "," + desUrl, true);
                                                WebCacheUrlRule webCacheUrlRule = new WebCacheUrlRule();
                                                webCacheUrlRule.setSrcType(WebCacheSrcType.WIKI);
                                                webCacheUrlRule.setClientType(WebCacheClientType.M);
                                                int pv = 0;
                                                for (JSONObject piwikJsonObj : entry.getValue()) {
                                                    pv += piwikJsonObj.getInt("nb_hits");
                                                }
                                                PageStatFactory.factory(WebCacheSrcType.WIKI).pageStat(desUrl, pv, webCacheUrlRule, new Date());
                                            } else {
                                                if (url.getPath().startsWith("/wiki")) {
                                                    LogService.infoSystemLog("====piwikJob sub domain success:" + i + "," + desUrl, true);
                                                    WebCacheUrlRule webCacheUrlRule = new WebCacheUrlRule();
                                                    webCacheUrlRule.setSrcType(WebCacheSrcType.WIKI);
                                                    webCacheUrlRule.setClientType(WebCacheClientType.PC);
                                                    int pv = 0;
                                                    for (JSONObject piwikJsonObj : entry.getValue()) {
                                                        pv += piwikJsonObj.getInt("nb_hits");
                                                    }
                                                    PageStatFactory.factory(WebCacheSrcType.WIKI).pageStat(desUrl, pv, webCacheUrlRule, new Date());
                                                } else if (url.getPath().startsWith("/mwiki")) {
                                                    LogService.infoSystemLog("====piwikJob sub domain success:" + i + "," + desUrl, true);
                                                    WebCacheUrlRule webCacheUrlRule = new WebCacheUrlRule();
                                                    webCacheUrlRule.setSrcType(WebCacheSrcType.WIKI);
                                                    webCacheUrlRule.setClientType(WebCacheClientType.M);
                                                    int pv = 0;
                                                    for (JSONObject piwikJsonObj : entry.getValue()) {
                                                        pv += piwikJsonObj.getInt("nb_hits");
                                                    }
                                                    PageStatFactory.factory(WebCacheSrcType.WIKI).pageStat(desUrl, pv, webCacheUrlRule, new Date());
                                                } else if (url.getPath().startsWith("/wxwiki")) {
                                                    LogService.infoSystemLog("====piwikJob sub domain success:" + i + "," + desUrl, true);
                                                    WebCacheUrlRule webCacheUrlRule = new WebCacheUrlRule();
                                                    webCacheUrlRule.setSrcType(WebCacheSrcType.WIKI);
                                                    webCacheUrlRule.setClientType(WebCacheClientType.M);
                                                    int pv = 0;
                                                    for (JSONObject piwikJsonObj : entry.getValue()) {
                                                        pv += piwikJsonObj.getInt("nb_hits");
                                                    }
                                                    PageStatFactory.factory(WebCacheSrcType.WIKI).pageStat(desUrl, pv, webCacheUrlRule, new Date());
                                                } else {
                                                    LogService.infoSystemLog("====piwikJob get rule failed:" + i + "," + desUrl, true);
                                                }
                                            }
                                        }
                                    }
                                    i++;

//                                int i = 1;
//                                for (Object piwikJsonObj : pageUrlArray.toArray()) {
//                                    JSONObject piwikObj = JSONObject.fromObject(piwikJsonObj);
//                                    if (!piwikObj.containsKey("url")) {
//                                        continue;
//                                    }
//                                    String desUrl = piwikObj.getString("url");
//                                    //匹配每一个url的配置信息，匹配到说明是有效的url，无效的url不处理
//                                    Map<String, String> map = WebCacheUtil.genSrcRule(desUrl);
//                                    if (map != null) {
//                                        String desRule = map.get("desRule");
//                                        String srcRule = map.get("srcRule");
//                                        if (desRule.equals("http://m.joyme.com/") || "http://www.joyme.com/".equals(desRule)) {
//                                            LogService.infoSystemLog("====piwikJob get rule failed:" + i + "," + desUrl, true);
//                                            continue;
//                                        }
//                                        LogService.infoSystemLog("====piwikJob get rule success:" + i + "," + desUrl, true);
//                                        WebCacheUrlRule webCacheUrlRule = urlRuleCache.getUrlRuleMemCache(desRule);
//                                        if (webCacheUrlRule == null) {
//                                            try {
//                                                webCacheUrlRule = webCacheUrlRuleService.getWebCacheUrlRule(null, MD5Util.Md5(desRule));
//                                                if (webCacheUrlRule != null) {
//                                                    urlRuleCache.putUrlRuleMemCache(desRule, webCacheUrlRule);
//                                                }
//                                            } catch (JoymeDBException e) {
//                                                LogService.errorSystemLog("====piwikJob getWebCacheUrlRule occur JoymeDBException", e);
//                                            } catch (JoymeServiceException e) {
//                                                LogService.errorSystemLog("====piwikJob getWebCacheUrlRule occur JoymeServiceException", e);
//                                            }
//                                        }
//                                        if (webCacheUrlRule != null) {
//                                            WebCacheSrcType srcType = webCacheUrlRule.getSrcType();
//                                            PageStatFactory.factory(srcType).pageStat(desUrl, piwikJsonObj, webCacheUrlRule, new Date());
//                                        }
//                                    } else {
//                                        if (desUrl.contains("joyme.com") && (desUrl.startsWith("http://") || desUrl.startsWith("https://"))) {
//                                            URL url = new URL(desUrl);
//                                            if (url.getHost().equals("wiki.joyme.com")) {
//                                                LogService.infoSystemLog("====piwikJob sub domain success:" + i + "," + desUrl, true);
//                                                WebCacheUrlRule webCacheUrlRule = new WebCacheUrlRule();
//                                                webCacheUrlRule.setSrcType(WebCacheSrcType.WIKI);
//                                                webCacheUrlRule.setClientType(WebCacheClientType.PC);
//                                                PageStatFactory.factory(WebCacheSrcType.WIKI).pageStat(desUrl, piwikJsonObj, webCacheUrlRule, new Date());
//                                            } else if (url.getHost().equals("m.wiki.joyme.com")) {
//                                                LogService.infoSystemLog("====piwikJob sub domain success:" + i + "," + desUrl, true);
//                                                WebCacheUrlRule webCacheUrlRule = new WebCacheUrlRule();
//                                                webCacheUrlRule.setSrcType(WebCacheSrcType.WIKI);
//                                                webCacheUrlRule.setClientType(WebCacheClientType.M);
//                                                PageStatFactory.factory(WebCacheSrcType.WIKI).pageStat(desUrl, piwikJsonObj, webCacheUrlRule, new Date());
//                                            } else {
//                                                if (url.getPath().startsWith("/wiki")) {
//                                                    LogService.infoSystemLog("====piwikJob sub domain success:" + i + "," + desUrl, true);
//                                                    WebCacheUrlRule webCacheUrlRule = new WebCacheUrlRule();
//                                                    webCacheUrlRule.setSrcType(WebCacheSrcType.WIKI);
//                                                    webCacheUrlRule.setClientType(WebCacheClientType.PC);
//                                                    PageStatFactory.factory(WebCacheSrcType.WIKI).pageStat(desUrl, piwikJsonObj, webCacheUrlRule, new Date());
//                                                } else if (url.getPath().startsWith("/mwiki")) {
//                                                    LogService.infoSystemLog("====piwikJob sub domain success:" + i + "," + desUrl, true);
//                                                    WebCacheUrlRule webCacheUrlRule = new WebCacheUrlRule();
//                                                    webCacheUrlRule.setSrcType(WebCacheSrcType.WIKI);
//                                                    webCacheUrlRule.setClientType(WebCacheClientType.M);
//                                                    PageStatFactory.factory(WebCacheSrcType.WIKI).pageStat(desUrl, piwikJsonObj, webCacheUrlRule, new Date());
//                                                } else if (url.getPath().startsWith("/wxwiki")) {
//                                                    LogService.infoSystemLog("====piwikJob sub domain success:" + i + "," + desUrl, true);
//                                                    WebCacheUrlRule webCacheUrlRule = new WebCacheUrlRule();
//                                                    webCacheUrlRule.setSrcType(WebCacheSrcType.WIKI);
//                                                    webCacheUrlRule.setClientType(WebCacheClientType.M);
//                                                    PageStatFactory.factory(WebCacheSrcType.WIKI).pageStat(desUrl, piwikJsonObj, webCacheUrlRule, new Date());
//                                                } else {
//                                                    LogService.infoSystemLog("====piwikJob get rule failed:" + i + "," + desUrl, true);
//                                                }
//                                            }
//                                        }
//                                    }
//                                    i++;
                                }
                            }
                        }
                    }
                }
            }
//            PageStatCache.putCacheTimeFlag();
        } catch (Exception e) {
            LogService.errorSystemLog("====piwikJob occur Exception", e);
        } finally {
            block = false;
        }
        LogService.infoSystemLog("====piwikJob ended:" + System.currentTimeMillis(), true);
    }


    public static void main(String[] args) {

    }


    @Override
    public void init() throws SchedulerException {
        //每2分钟执行一次
//        addTriggerJob(" 0 0/2 * * * ?", PiwikCacheJob.class);

        //每30分钟执行一次
        addTriggerJob(" 0 0/30 * * * ?", PiwikCacheJob.class);
    }

}
