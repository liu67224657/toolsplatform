package com.enjoyf.mcms.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.facade.PVFacade;
import com.enjoyf.mcms.service.SyncCacheService;
import com.enjoyf.mcms.service.TemplateService;
import com.enjoyf.service.NamingService;
import com.enjoyf.service.Service;
import com.enjoyf.util.SystemUtil;

public class InitServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 5352229787670744390L;

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
        try {
            SystemUtil su = new SystemUtil();
            ConfigContainer.init();
            //初始化mongodb
//            new MongoDBService().initMongoDB();
            //初始化模板
            new TemplateService().reloadTemplate(su.getWebRootPath());
            //开始运行清楚缓存
            SyncCacheService.getInstance();

            //开始统计PV
            new PVFacade().start();

            //启动的时上报KEY
            Service service = ConfigContainer.getService();
            if (service != null && !service.getServiceId().contains("UNKNOWN")) {
                NamingService.getInstance(ConfigContainer.getRedisManager()).register(service);
                LogService.infoSystemLog("==========register success. service name is:" + service + "==========");
            } else {
                LogService.errorSystemLog("==========register failed. plz shutdown and check config==========");
                return;
            }
        } catch (Exception e) {
            System.out.println("==========Error when init=========");
            e.printStackTrace();
        }
        System.out.println("==========INIT SUCCESS=========");
    }

}
