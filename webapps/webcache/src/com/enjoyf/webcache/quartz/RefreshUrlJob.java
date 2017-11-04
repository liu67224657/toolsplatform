package com.enjoyf.webcache.quartz;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.CollectionUtil;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.cdn.CdnRefreshFactory;
import com.enjoyf.webcache.bean.RefreshTimerUrl;
import com.enjoyf.webcache.bean.WebCacheUrl;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.cache.UrlRuleCache;
import com.enjoyf.webcache.service.CacheService;
import com.enjoyf.webcache.service.RefreshTimerService;
import com.enjoyf.webcache.service.WebCacheUrlRuleService;
import com.enjoyf.webcache.service.WebCacheUrlService;
import com.enjoyf.webcache.util.WebCacheUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * webcache后台定时刷新的任务
 * Created by ericliu on 2015/3/17.
 */
public class RefreshUrlJob extends AbstractQuartzCronTrigger implements Job {

    private static RefreshTimerService refreshTimerService = new RefreshTimerService();
    private static CacheService cacheService = new CacheService();
    private static WebCacheUrlRuleService webCacheUrlRuleService = new WebCacheUrlRuleService();

    public static String JOB_NAME = "refreshurl_job";//任务名
    public static String JOB_GROUP_NAME = "refreshurl_job_group";//任务组名

    public static String TRIGGER_NAME = "refreshurl_trigger";//触发器名
    public static String TRIGGER_GROUP_NAME = "refreshurl_trigger_group";//触发器组

    public RefreshUrlJob() throws SchedulerException {
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
        LogService.infoFreshLog("====RefreshUrlJob start " + System.currentTimeMillis(), true);
        try {
            List<RefreshTimerUrl> list = refreshTimerService.queryRefreshUrl(null);
            if (!CollectionUtil.isEmpty(list)) {
                for (RefreshTimerUrl refreshTimerUrl : list) {
                    String clearpage = refreshTimerUrl.getUrl();
                    if (!StringUtil.isEmpty(clearpage)) {
                        LogService.infoFreshLog("====RefreshUrlJob refresh cache:" + clearpage, true);
                        cacheService.createCleanPageCache(clearpage,refreshTimerUrl.getType());
                        clearCDN(clearpage);
                    }
                }
            }
        } catch (Exception e) {
            LogService.errorFreshLog("====RefreshUrlJob error " + System.currentTimeMillis(), e);
        }
        LogService.infoFreshLog("====RefreshUrlJob end " + System.currentTimeMillis(), true);
    }

    private void clearCDN(String url) throws MalformedURLException, JoymeServiceException, JoymeDBException {
        Map<String, String> map = WebCacheUtil.genSrcRule(url);
        if (map != null) {
            String desRule = map.get("desRule");
            // 匹配到 的 rule
            WebCacheUrlRule webCacheUrlRule = webCacheUrlRuleService.getWebCacheUrlRule(null, MD5Util.Md5(desRule));
            String cdnName = webCacheUrlRule.getCdnType().getDesc();
            LogService.infoFreshLog("====RefreshUrlJob clean cdn:" + cdnName + "," + url, true);
            CdnRefreshFactory.getFactory(cdnName).clearCDN(url, null);
        }
    }

    @Override
    public void init() throws SchedulerException {
        //每15分钟执行一次
        addTriggerJob(" 0 0/15 * * * ?", RefreshUrlJob.class);
    }

}
