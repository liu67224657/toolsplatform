package com.enjoyf.webcache.quartz;

import com.enjoyf.util.CollectionUtil;
import com.enjoyf.webcache.bean.WebCacheUrl;
import com.enjoyf.webcache.cache.UrlRuleCache;
import com.enjoyf.webcache.service.SitemapFetcherService;
import com.enjoyf.webcache.service.WebCacheUrlService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 将所有方位过的url从缓存中取出，写入到db
 * Created by ericliu on 2015/3/17.
 */
public class WebCacheUrlJob extends AbstractQuartzCronTrigger implements Job {
    private static Logger logger = LoggerFactory.getLogger(WebCacheUrlJob.class);
    private static UrlRuleCache urlRuleCache = new UrlRuleCache();
    private static WebCacheUrlService webCacheUrlService = new WebCacheUrlService();

    public WebCacheUrlJob() throws SchedulerException {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long now = System.currentTimeMillis();
        System.out.println("====WebCacheUrlJob start " + System.currentTimeMillis());

        Set<String> urls = urlRuleCache.smembersUrlRules();
        if(!CollectionUtil.isEmpty(urls)){
            try {
                for(String urlJson:urls){
                    WebCacheUrl webCacheUrl = WebCacheUrl.parse(urlJson);
                    if(webCacheUrl != null){
                        WebCacheUrl exitsUrl = webCacheUrlService.queryWebcacheUrlbyId(null, webCacheUrl.getUrlId());
                        if(exitsUrl == null){
                            webCacheUrlService.insertWebcacheUrl(null, webCacheUrl);
                        }else {
                            if(!webCacheUrl.getRule_id().equals(exitsUrl.getRule_id())){
                                webCacheUrlService.updateWebcacheUrl(null, webCacheUrl);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("====WebCacheUrlJob end " + System.currentTimeMillis());
    }


    @Override
    public void init() throws SchedulerException {
        //每分钟执行一次
        //addTriggerJob("0 0/10 * * * ?", SiteMapJob.class);

        //每天5点执行一次
        addTriggerJob(" 0 30 12 ? * *", WebCacheUrlJob.class);
    }

}
