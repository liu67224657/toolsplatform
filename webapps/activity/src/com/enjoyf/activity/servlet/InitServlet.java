package com.enjoyf.activity.servlet;

import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.framework.memcached.MemCachedConfig;
import com.enjoyf.framework.memcached.MemCachedManager;
import com.enjoyf.util.SystemUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by zhimingli on 2016/7/29 0029.
 */
public class InitServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("===begin to init====");
        try {
            //读配置文件
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.init();
        System.out.println("===end to init====");
    }

    private void load() {
        try {
            SystemUtil su = new SystemUtil();
            String metaInfoPath = su.getMetaInfFolderPath() + "/config.properties";
            PropertiesContainer.prop.load(new FileInputStream(new File(metaInfoPath)));

            // memCachedManager = new MemCachedManager(new MemCachedConfig(prop));
        } catch (IOException e) {
            LogService.errorSystemLog("e", e);
            System.exit(-1);
        }
    }

}
