package com.enjoyf.cms.quartz;

import com.enjoyf.cms.bean.IntRemoveStatus;
import com.enjoyf.cms.bean.Sitemap;
import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.service.SitemapFetcherService;
import com.enjoyf.cms.service.SitemapService;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.CollectionUtil;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by ericliu on 2015/3/17.
 */
public class SiteMapJob extends AbstractQuartzCronTrigger implements Job {
    private static final int PAGE_SIZE = 2000;

    private static SitemapFetcherService fetcherService = new SitemapFetcherService();
    private static SitemapService dbService = new SitemapService();

    public SiteMapJob() throws SchedulerException {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long now = System.currentTimeMillis();
        LogService.infoSystemLog(now + " SiteMapJob start", true);
        int cp = 0;
        Pagination page = null;

        try {
            Map<String, List<String>> xmlMap = new HashMap<String, List<String>>();
            do {
                cp += 1;
                page = new Pagination(PAGE_SIZE * cp, cp, PAGE_SIZE);
                //1 从数据库中读取sitamap的配置
                PageRows<Sitemap> pageRows = dbService.querySitemapByPage("", IntRemoveStatus.USED.getCode(), page);
                if (pageRows != null && !CollectionUtil.isEmpty(pageRows.getRows())) {
                    for (Sitemap sm : pageRows.getRows()) {
                        try {
                            LogService.infoSystemLog("SiteMapJob "+sm.getSitemapUrl(), true);
                            URL url = new URL(sm.getSitemapUrl());
                            String host = url.getHost();
                            if(xmlMap.containsKey(host)){
                                xmlMap.get(host).add(sm.getSitemapUrl());
                            }else {
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
            for(String host:xmlMap.keySet()){
                List<String> xmlList = xmlMap.get(host);
                if(!CollectionUtil.isEmpty(xmlList)){
                    fetcherService.generatorSitemapIndex(host, xmlList);
                }
            }
            LogService.infoSystemLog(System.currentTimeMillis() + " SiteMapJob end:" + (System.currentTimeMillis() - now) + " milliseconds", true);
            //百度推送
            if (PropertiesContainer.sitemapPushOpen().equals("true")) {
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
        //每30分钟执行一次
//        addTriggerJob("0 10 * * * ?", SiteMapJob.class);

        //每天1点执行一次
        addTriggerJob(" 0 30 12 ? * *", SiteMapJob.class);
    }

}
