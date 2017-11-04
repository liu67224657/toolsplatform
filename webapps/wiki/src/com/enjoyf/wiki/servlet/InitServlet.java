package com.enjoyf.wiki.servlet;


import com.enjoyf.framework.log.LogService;
import com.enjoyf.service.NamingService;
import com.enjoyf.service.Service;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.bean.temp.WikiRankBean;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.facade.WikiLastModifiedFacade;
import com.enjoyf.wiki.facade.WikiSiteMapFacade;
import com.enjoyf.wiki.quartz.WikiSiteMapJob;
import com.enjoyf.wiki.service.*;
import com.enjoyf.wiki.template.TemplateUtil;
import com.enjoyf.wiki.tools.WikiUtil;
import com.enjoyf.wiki.util.DateUtil;
import org.jsoup.nodes.Document;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Date;
import java.util.List;

public class InitServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1451103869232974722L;

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        SystemService systemService = new SystemService();

        TemplateService templateService = new TemplateService();
        try {
            systemService.loadJoymeWIki();
            systemService.loadChannelConfigure();
            systemService.loadTemplate();
            try {
                templateService.reloadTemplate();
            } catch (Exception e) {
                e.printStackTrace();
            }

            new MongoDBService().initMongoDB();

            SyncCacheService.getInstance();

            if (PropertiesContainer.getInstance().isWikiRanking) {
                LogService.infoSystemLog("==================this machine isWikiRanking is true===============");
                SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
                Scheduler sched = schedFact.getScheduler();
                sched.start();
                JobDetail jobDetail = new JobDetail(" Income Report ", " Report Generation ", WikiLastModifiedFacade.class);
                jobDetail.getJobDataMap().put(" type ", " FULL ");
                CronTrigger trigger = new CronTrigger(" Income Report ", " Report Generation ");
                //<!--每天16点20分触发--> 0 20 16 ? * *

                trigger.setCronExpression("0 0 2 ? * *");
                sched.scheduleJob(jobDetail, trigger);
            }

            //生成sitemap定时任务
            WikiSiteMapJob wikiSiteMapJob = new WikiSiteMapJob();
            wikiSiteMapJob.init();
            wikiSiteMapJob.start();

            Service service = PropertiesContainer.getInstance().getService();
            if (service != null && !service.getServiceId().contains("UNKNOWN")) {
                NamingService.getInstance(PropertiesContainer.getInstance().getRedisManager()).register(service);
                LogService.infoSystemLog("==========register success. service name is:"+service+"==========");
            } else {
                LogService.errorSystemLog("==========register failed. plz shutdown and check config==========");
                return;
            }

            System.out.print("=========="+service+"Success when init==============");
        } catch (Exception e) {
            System.out.print("==========Exception when init==============");
            e.printStackTrace();
        } catch (Error e) {
            System.out.print("==========Error when init==============");
            e.printStackTrace();
        }


    }


}
