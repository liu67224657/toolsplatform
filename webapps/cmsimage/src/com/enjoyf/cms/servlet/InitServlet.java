package com.enjoyf.cms.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.quartz.SiteMapJob;
import com.enjoyf.cms.service.SeoConfigService;
import com.enjoyf.cms.service.SyncCacheService;
import com.enjoyf.cms.service.TemplateService;
import com.enjoyf.cms.service.UpdateSEOService;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.service.NamingService;
import com.enjoyf.service.Service;
import com.enjoyf.util.SystemUtil;

public class InitServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 2413378459563837687L;

    @Override
    public void init() throws ServletException {
        System.out.println("===begin to init====");
        try {
            //读配置文件
            loadProperties();
            //读SEO的数据库配置
            loadSeoConfig();
            //启动更新
            SyncCacheService.getInstance();

            //3小时更新一下SEO的数据库,有两次加载的问题在里面。
            new UpdateSEOService().start();

            TemplateService.reloadTemplate();

            //一个tomcat做这件事就行了
            if (PropertiesContainer.sitemapStartRun().equals("true")) {
                System.out.println("===sitemap job init====");
                SiteMapJob siteMapJob = new SiteMapJob();
                siteMapJob.init();
                siteMapJob.start();
            } else {
                System.out.println("===not use start run sitemap====");
            }

            //启动的时上报KEY
            Service service = PropertiesContainer.getService();
            if (service != null && !service.getServiceId().contains("UNKNOWN")) {
                NamingService.getInstance(PropertiesContainer.getRedisManager()).register(service);
                LogService.infoSystemLog("==========register success. service name is:" + service + "==========");
            } else {
                LogService.errorSystemLog("==========register failed. plz shutdown and check config==========");
                return;
            }
            System.out.println("===init successful====");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("===Error when init====");
        }

        super.init();
    }

    private void loadSeoConfig() throws JoymeDBException, JoymeServiceException {
        SeoConfigService service = new SeoConfigService();
        service.loadSeoConfig();
    }


    private void loadProperties() throws IOException, FileNotFoundException {
        SystemUtil su = new SystemUtil();
        String metaInfoPath = su.getMetaInfFolderPath() + "/config.properties";
        PropertiesContainer.prop.load(new FileInputStream(new File(metaInfoPath)));
    }
}
