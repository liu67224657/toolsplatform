package com.enjoyf.webcache.quartz;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.CollectionUtil;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.webcache.bean.ActStatus;
import com.enjoyf.webcache.bean.Sitemap;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.service.SitemapFetcherService;
import com.enjoyf.webcache.service.SitemapService;
import org.quartz.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ericliu on 2015/3/17.
 */
public class SiteMapJob extends AbstractQuartzCronTrigger implements Job {
    private static final int PAGE_SIZE = 2000;

    private static SitemapFetcherService fetcherService = new SitemapFetcherService();
    private static SitemapService dbService = new SitemapService();

    public static String JOB_NAME = "sitemap_job";//任务名
    public static String JOB_GROUP_NAME = "sitemap_job_group";//任务组名

    public static String TRIGGER_NAME = "sitemap_trigger";//触发器名
    public static String TRIGGER_GROUP_NAME = "sitemap_trigger_group";//触发器组


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

    public SiteMapJob() throws SchedulerException {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long now = System.currentTimeMillis();
        int cp = 0;
        Pagination page = null;
        try {
            LogService.infoSystemLog(" SiteMapJob start: ============================", true);
            Map<String, List<String>> xmlMap = new HashMap<String, List<String>>();
            do {
                cp += 1;
                page = new Pagination(PAGE_SIZE * cp, cp, PAGE_SIZE);
                //1 从数据库中读取sitamap的配置
                PageRows<Sitemap> pageRows = dbService.querySitemapByPage("", ActStatus.USED.getCode(), page);
                if (pageRows != null && !CollectionUtil.isEmpty(pageRows.getRows())) {
                    for (Sitemap sm : pageRows.getRows()) {
                        try {
                            URL url = new URL(sm.getSitemapUrl());
                            //host=m.joyme.com www.joyme.com
                            String host = url.getHost();
                            if (xmlMap.containsKey(host)) {
                                xmlMap.get(host).add(sm.getSitemapUrl());
                            } else {
                                xmlMap.put(host, new ArrayList<String>());
                                xmlMap.get(host).add(sm.getSitemapUrl());
                            }
                            try {
                                fetcherService.genSitemap(sm, host);
                            } catch (Exception e) {
                                if (sm.getDomainKey().equals("www")) {
                                    xmlMap.get(host).remove(sm.getSitemapUrl());
                                }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } while (!page.isLastPage());

            //从缓存中取出url，生成sitemap文件
            for (String host : xmlMap.keySet()) {
                List<String> xmlList = xmlMap.get(host);
                if (!CollectionUtil.isEmpty(xmlList)) {
                    fetcherService.generatorSitemapIndex(host, xmlList);
                }
            }

            LogService.infoSystemLog(System.currentTimeMillis() + " SiteMapJob end:" + (System.currentTimeMillis() - now) + " milliseconds", true);
            //百度推送
            if (PropertiesContainer.getInstance().sitemapPushOpen().equals("true")) {
                fetcherService.pushBaiduUrls();
            } else {
                LogService.infoSystemLog("===not push open====", true);
            }
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init() throws SchedulerException {
        LogService.infoSystemLog("sitemap init start=============");
        //每天1点执行一次
        addTriggerJob(PropertiesContainer.getInstance().getSitemapPathQuartz(), SiteMapJob.class);
    }


}
