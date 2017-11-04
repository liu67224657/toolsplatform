package com.enjoyf.webcache.quartz;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.util.HttpResult;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.bean.PageStat;
import com.enjoyf.webcache.bean.WebCacheClientType;
import com.enjoyf.webcache.bean.WebCacheSrcType;
import com.enjoyf.webcache.cache.PageStatCache;
import com.enjoyf.webcache.cache.PageStatCacheV2;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.service.PageStatService;
import net.sf.json.JSONObject;
import org.quartz.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

/**
 * 定时将前一天的pv数累加到db
 * Created by ericliu on 2015/3/17.
 */
public class PiwikDbJob extends AbstractQuartzCronTrigger implements Job {
    private static PageStatCacheV2 pageStatCache = new PageStatCacheV2();
    private static PageStatService pageStatService = new PageStatService();

    public static String JOB_NAME = "pagestat_db_job";//任务名
    public static String JOB_GROUP_NAME = "pagestat_db_job_group";//任务组名

    public static String TRIGGER_NAME = "pagestat_db_trigger";//触发器名
    public static String TRIGGER_GROUP_NAME = "pagestat_db_trigger_group";//触发器组

    public PiwikDbJob() throws SchedulerException {
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
        LogService.infoSystemLog("====piwikDbJob start:" + System.currentTimeMillis(), true);
        try {
            java.util.Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, -1);

            Date beforeDate = calendar.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = df.format(beforeDate);

            do {
                String jsonVal = pageStatCache.getPageStatCachePool(dateStr);
                    if (!StringUtil.isEmpty(jsonVal)) {
                    JSONObject pageStat = JSONObject.fromObject(jsonVal);
                    if (!pageStat.containsKey("statId") || !pageStat.containsKey("pageId") || !pageStat.containsKey("pageType") || !pageStat.containsKey("clientType")) {
                        continue;
                    }
                    String statId = pageStat.getString("statId");
                    String pageId = pageStat.getString("pageId");
                    int pageType = pageStat.getInt("pageType");
                    int clientType = pageStat.getInt("clientType");
                    WebCacheClientType webCacheClientType = WebCacheClientType.getByCode(clientType);
                    if (webCacheClientType == null) {
                        continue;
                    }

                    int pvNum = pageStatCache.getInsertDbCache(statId,calendar.getTime(), webCacheClientType);

                    PageStat pageStatDb = pageStatService.getPageStatById(null, statId);
                    if (pageStatDb == null) {
                        pageStatDb = new PageStat();
                        pageStatDb.setStatId(statId);
                        pageStatDb.setPageId(pageId);
                        pageStatDb.setPageType(WebCacheSrcType.getByCode(pageType));
                        if (webCacheClientType.equals(WebCacheClientType.PC)) {
                            pageStatDb.setPcPv(pvNum);
                        } else if (webCacheClientType.equals(WebCacheClientType.M)) {
                            pageStatDb.setmPv(pvNum);
                        } else if (webCacheClientType.equals(WebCacheClientType.WANBA)) {
                            pageStatDb.setWanbaPv(pvNum);
                        }
                        pageStatDb.setPvSum(pvNum);

                        pageStatService.insertPageStat(null, pageStatDb);
                        LogService.infoSystemLog("====piwikDbJob insertPageStat success:" + statId + "," + pageId + "," + pageType + "," + clientType + "==pv:" + pvNum);
                    } else {
                        pageStatDb = new PageStat();
                        pageStatDb.setStatId(statId);
                        pageStatDb.setPageId(pageId);
                        pageStatDb.setPageType(WebCacheSrcType.getByCode(pageType));
                        if (webCacheClientType.equals(WebCacheClientType.PC)) {
                            pageStatDb.setPcPv(pvNum);
                        } else if (webCacheClientType.equals(WebCacheClientType.M)) {
                            pageStatDb.setmPv(pvNum);
                        } else if (webCacheClientType.equals(WebCacheClientType.WANBA)) {
                            pageStatDb.setWanbaPv(pvNum);
                        }
                        pageStatDb.setPvSum(pvNum);

                        boolean bool = (pageStatService.increasePageStat(null, pageStatDb) > 0);
                        if (bool) {
                            LogService.infoSystemLog("====piwikDbJob increasePageStat success:" + statId + "," + pageId + "," + pageType + "," + clientType + "==pv:" + pvNum);
                        }
                    }
                }
            } while (pageStatCache.lengthCachePool(dateStr) > 0l);
        } catch (Exception e) {
            LogService.errorSystemLog("====piwikDbJob occur Exception", e);
        }
        LogService.infoSystemLog("====piwikDbJob ended:" + System.currentTimeMillis(), true);
    }


    @Override
    public void init() throws SchedulerException {
        //每分钟执行一次
//        addTriggerJob("0 0/1 * * * ?", PiwikDbJob.class);

        //每天1点执行一次
        addTriggerJob("0 0 1 * * ?", PiwikDbJob.class);
    }

}
