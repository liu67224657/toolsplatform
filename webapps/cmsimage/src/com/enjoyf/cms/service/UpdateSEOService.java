package com.enjoyf.cms.service;

import com.enjoyf.framework.log.LogService;

public class UpdateSEOService extends Thread {
    private static SeoConfigService seoConfigService = new SeoConfigService();
    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                Thread.sleep(3 * 60 * 60 * 1000);
                seoConfigService.loadSeoConfig();
            } catch (Exception e) {
                LogService.errorSystemLog("Error when update seo by thread" , e);
            }
        }
    }
}
