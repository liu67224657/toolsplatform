package com.enjoyf.webcache.servlet;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.service.NamingService;
import com.enjoyf.service.Service;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.facade.RuleFacade;
import com.enjoyf.webcache.quartz.*;
import com.enjoyf.webcache.service.SyncCacheService;
import com.enjoyf.webcache.service.TemplateService;
import com.enjoyf.webcache.util.IruiUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 2413378459563837687L;

    RuleFacade ruleFacade = new RuleFacade();

    @Override
    public void init() throws ServletException {
        System.out.println("===begin to init====");
        try {
            //启动时，将service_id注册到队列中
            Service service = PropertiesContainer.getInstance().getService();
            if (service != null && !service.getServiceId().contains("UNKNOWN")) {
                NamingService.getInstance(PropertiesContainer.getInstance().getRedisManager()).register(service);
                LogService.infoSystemLog("==========register success. service name is:" + service + "==========");
            } else {
                LogService.errorSystemLog("==========register failed. plz shutdown and check config==========");
                return;
            }

            //启动刷新的redis队列
            SyncCacheService.getInstance();
            //加载template模版文件
            TemplateService.reloadTemplate();

            IruiUtil.loadUrlByFile();
            //加载 url 的规则配置
            ruleFacade.loadRule();

            WebCacheUrlJob webCacheUrlJob = new WebCacheUrlJob();
            webCacheUrlJob.init();
            webCacheUrlJob.start();

            RefreshUrlJob refreshUrlJob = new RefreshUrlJob();
            refreshUrlJob.init();
            refreshUrlJob.start();
            

            if ("true".equals(PropertiesContainer.getInstance().getPiwikJobOpen())) {
                System.out.println("====piwik job init====");
                PiwikCacheJob piwikCacheJob = new PiwikCacheJob();
                piwikCacheJob.init();
                piwikCacheJob.start();

                PiwikDbJob piwikDbJob = new PiwikDbJob();
                piwikDbJob.init();
                piwikDbJob.start();
            }


            //从cmsimage转移过来，暂时负责cms下的sitemap
            SiteMapJob siteMapJob = new SiteMapJob();
            siteMapJob.init();
            siteMapJob.start();


            System.out.println("===init successful====");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("===Error when init====");
        }

        super.init();
    }

}
