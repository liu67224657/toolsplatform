package com.enjoyf.webcache.service;

import java.net.URL;
import java.util.List;

import com.enjoyf.util.MD5Util;
import com.enjoyf.util.collection.QueueList;
import com.enjoyf.util.collection.QueueListener;
import com.enjoyf.util.collection.QueueThreadN;
import com.enjoyf.webcache.bean.RefreshTimerUrl;
import com.enjoyf.webcache.util.WebCacheUtil;
import net.sf.json.JSONObject;

import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.framework.log.LogService;

public class SyncCacheService extends Thread {
    private static CacheService cacheService = new CacheService();
    private QueueThreadN queueThreadN = null;

    private static SyncCacheService instance = new SyncCacheService();

    public static SyncCacheService getInstance() {
        return instance;
    }

    private SyncCacheService() {
        queueThreadN = new QueueThreadN(16, new QueueListener() {

            @Override
            public void process(Object obj) {
                if (obj instanceof String) {
                    execute((String) obj);
                }
            }
        }, new QueueList(), "webcacheSyncCacheService");

        this.start();
    }

    @Override
    public void run() {
        runBlockCommand();
    }

    private void runBlockCommand() {
        //用redis的brpop锁机制，循环从缓存中读取需要刷新的页面的额url
        while (true) {
            try {
                List<String> refreshList = PropertiesContainer.getInstance().getNamingService().blockpopServQueue(PropertiesContainer.getInstance().getService());
                if (refreshList.size() == 2) {
                    LogService.infoFreshLog("queue add thread:" + refreshList.get(1), true);
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
        LogService.infoSystemLog("##########Begin to cleanPageCache object:+" + object + "##########", true);
        try {
            String clearPage = object.getString("clearpage");
            if (!clearPage.startsWith("http://") && !clearPage.startsWith("https://")) {
                clearPage = "http://" + clearPage;
            }

            int clearType = -1;
            if (object.containsKey("cleartype")) {
                try {
                    clearType = Integer.parseInt(object.getString("cleartype"));
                } catch (Exception e) {
                }
            }

            //如果为空取DB的设置
            if (clearType == -1) {
                LogService.warnSystemLog("##########cleanPageCache clearType not exists.get by db object is:+" + object + "##########");
                RefreshTimerUrl refreshTimerUrl = RefreshTimerService.getRefreshUrl(null, MD5Util.Md5(clearPage));
                if (refreshTimerUrl != null) {
                    clearType = refreshTimerUrl.getType();
                }
            }


            URL url = new URL(clearPage);
            String host = url.getHost();
            String path = url.getPath();
            //article域名源地址
            String cacheFilePath = WebCacheUtil.getCacheFile(path, host);
            System.out.println("---------------clean chahe:" + cacheFilePath + "---------------");
            cacheService.cleanCache(cacheFilePath, clearType);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when cleanPageCache", e);
        }
        LogService.infoSystemLog("##########End to clearCache##########", true);
    }

    private void doClearCache(JSONObject object) {
        LogService.infoSystemLog("##########Begin to clearCache##########", true);
        try {
            cacheService.cleanCache(null, 1);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when doClearCache", e);
        }
        LogService.infoSystemLog("##########End to clearCache##########", true);
    }

    private void doRefreshSEO(JSONObject object) {
        LogService.infoSystemLog("##########Begin to doRefreshSEO##########", true);
//        try {
//            seoConfigService.loadSeoConfig();
//        } catch (Exception e) {
//            LogService.errorSystemLog("Error when do loadSeoConfig", e);
//        }
        LogService.infoSystemLog("##########End to doRefreshSEO##########", true);
    }

}
