package com.enjoyf.cms.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

import com.enjoyf.cms.bean.JoymeRefresh;
import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.framework.log.LogService;

public class CacheService {
    private static JoymeRefreshService joymeRefreshService = new JoymeRefreshService();

    public void cleanCache(String file) throws IOException {
        LogService.infoSystemLog("##########Begin to cleanCache##########", true);
        File cacheFile = null;
        //如果是null,就清理全部
        if (file == null) {
            cacheFile = new File(PropertiesContainer.getCacheFolder());
        } else {
            cacheFile = new File(file);
        }

        //单文件需要删2个文件
        if (!cacheFile.isDirectory()) {
            File seo = new File(file + "_seo");
            File no_seo = new File(file + "_noseo");
            seo.delete();
            no_seo.delete();
            LogService.infoSystemLog("##########End to cleanCache##########", true);
            return;
        }

        File[] files = cacheFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            try {
                if (files[i].isDirectory())
                    FileUtils.deleteDirectory(files[i]);
                else
                    files[i].delete();
            } catch (Exception e) {
                LogService.warnSystemLog("Warning when delete the file :" + files[i].getName());
            }
        }
        LogService.infoSystemLog("##########End to cleanCache##########", true);
    }


    public boolean createCleanCache() {
        try {
            LogService.infoSystemLog("##########Begin create to clean cache##########", true);

            JSONObject object = new JSONObject();
            object.put("type", "clearCache");

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(PropertiesContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########End create to clean cache#############", true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createCleanPageCache(String clearpage) {
        try {
            LogService.infoSystemLog("##########Begin create to clean cache##########", true);

            JSONObject object = new JSONObject();
            object.put("type", "cleanPageCache");
            object.put("clearpage", clearpage);

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setMachineId(PropertiesContainer.getMachineId());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########End create to clean cache#############", true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
