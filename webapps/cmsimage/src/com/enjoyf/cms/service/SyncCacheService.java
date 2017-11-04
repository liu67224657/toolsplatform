package com.enjoyf.cms.service;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.enjoyf.cms.factory.HtmlFactory;
import com.enjoyf.cms.util.CmsimageFilePathUtil;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.collection.QueueList;
import com.enjoyf.util.collection.QueueListener;
import com.enjoyf.util.collection.QueueThreadN;
import net.sf.json.JSONObject;

import com.enjoyf.cms.bean.JoymeRefresh;
import com.enjoyf.cms.bean.JoymeRefreshLog;
import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.bean.ConnectionBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.framework.log.LogService;

public class SyncCacheService extends Thread {
    private static JoymeRefreshLogService joymeRefreshLogService = new JoymeRefreshLogService();
    private static SeoConfigService seoConfigService = new SeoConfigService();
    private static CacheService cacheService = new CacheService();
    private QueueThreadN queueThreadN = null;

    private static SyncCacheService instance = new SyncCacheService();

    public static SyncCacheService getInstance() {
        return instance;
    }

    private SyncCacheService() {
        queueThreadN = new QueueThreadN(8, new QueueListener() {

            @Override
            public void process(Object obj) {
                if (obj instanceof String) {
                    execute((String) obj);
                }
            }
        }, new QueueList(), "cmsimageSyncCacheService");

        this.start();
    }

    @Override
    public void run() {
        runBlockCommand();
    }

    private void runBlockCommand() {
        while (true) {
            try {
                List<String> refreshList = PropertiesContainer.getNamingService().blockpopServQueue(PropertiesContainer.getService());
                if (refreshList.size() == 2) {
                    queueThreadN.add(refreshList.get(1));
                }
            } catch (Exception e) {
                LogService.errorSystemLog("################ operate execute occured error.runBlockCommand ########################", e);
            }
        }
    }

    public void execute(String refreshContent) {
        try {
            JSONObject object = JSONObject.fromObject(refreshContent);

            String type = object.getString("type");

            if (type.equals("refreshSEO")) { // 刷首页
                doRefreshSEO(object);
            } else if (type.equals("clearCache")) { // 清缓存
                doClearCache(object);
            } else if (type.equals("cleanPageCache")) { // 清目录或者单个文件缓存
                cleanPageCache(object);
            }
        } catch (Exception e) {
            LogService.errorSystemLog("################ operate execute occured error.execute ########################", e);
        }
    }

    private void cleanPageCache(JSONObject object) {
        LogService.infoSystemLog("##########Begin to cleanPageCache##########", true);
        try {
            String clearPage = object.getString("clearpage");
            if (!clearPage.startsWith("http://") && !clearPage.startsWith("https://")) {
                clearPage = "http://" + clearPage;
            }
            if(clearPage.endsWith("/")){
                clearPage += "/";
            }
            URL url = new URL(clearPage);
            String host = url.getHost();
            String path = url.getPath();
            //article域名源地址
            String cacheFilePath = HtmlFactory.getFactory(host).getCacheFile(path, null, host);
            cacheService.cleanCache(cacheFilePath);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when cleanPageCache", e);
        }
        LogService.infoSystemLog("##########End to clearCache##########", true);
    }

    private void doClearCache(JSONObject object) {
        LogService.infoSystemLog("##########Begin to clearCache##########", true);
        try {
            cacheService.cleanCache(null);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when doClearCache", e);
        }
        LogService.infoSystemLog("##########End to clearCache##########", true);
    }

    private void doRefreshSEO(JSONObject object) {
        LogService.infoSystemLog("##########Begin to doRefreshSEO##########", true);
        try {
            seoConfigService.loadSeoConfig();
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do loadSeoConfig", e);
        }
        LogService.infoSystemLog("##########End to doRefreshSEO##########", true);
    }

}
