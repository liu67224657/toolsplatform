package com.enjoyf.crwalwiki.service;

import com.enjoyf.crwalwiki.bean.CrwalLog;

public class SyncCrwalService extends Thread {
    @Override
    public void run() {
        super.run();
        for (int i = 0;; i++) {
            try {
                this.execute();
                Thread.sleep(20 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }

    }

    public void execute() {
        CrwalLogService crwalLogService = new CrwalLogService();
        CrwalService crwalService = new CrwalService();
        Integer id = null;
        CrwalLog bean = null;
        boolean isSuccess = false;
        try {
            bean = crwalLogService.queryOldestNoCrwalLog(null);
            if (bean == null)
                return;

            String key = bean.getCrwalKey();
            id = bean.getId();
            crwalLogService.updateCrwalLogRunning(null, id);
            if(bean.getCrwalType() == 1){
                isSuccess = crwalService.doAllPageCrwal(key, id);
                System.out.println("======isSuccess:====" + true);
                if (isSuccess)
                    crwalLogService.updateCrwalLogFinish(null, 1, id, crwalService.zipFile);
            } else {
                String crwalPageUrls = bean.getPageUrls();
                
                isSuccess = crwalService.doPageCrwal(key , crwalPageUrls , id);
                System.out.println("======isSuccess:====" + true);
                if (isSuccess)
                    crwalLogService.updateCrwalLogFinish(null, 1, id, crwalService.zipFile);
            }
                 
                
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!isSuccess) {
                try {
                    if(id != null)
                        crwalLogService.updateCrwalLogFinish(null, 2, id, null);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
