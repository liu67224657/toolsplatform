package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.collection.QueueList;
import com.enjoyf.util.collection.QueueListener;
import com.enjoyf.util.collection.QueueThreadN;
import com.enjoyf.wiki.bean.JoymeRefresh;
import com.enjoyf.wiki.bean.JoymeRefreshLog;
import com.enjoyf.wiki.bean.JoymeWiki;
import com.enjoyf.wiki.container.ChannelContainer;
import com.enjoyf.wiki.container.PropertiesContainer;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

public class SyncCacheService extends Thread {
    private static JoymeRefreshService joymeRefreshService = new JoymeRefreshService();
    private static CacheService cacheService = new CacheService();
    private static JoymeRefreshLogService joymeRefreshLogService = new JoymeRefreshLogService();
    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();
    private JoymeWikiService joymeWikiService = new JoymeWikiService();
    private SystemService systemService = new SystemService();

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
        }, new QueueList(), "syncCacheService");

        this.start();
    }

    @Override
    public void run() {
        runBlockCommand();
    }

    private void runBlockCommand() {
        while (true) {
            try {
                List<String> refreshList = PropertiesContainer.getInstance().getNamingService().blockpopServQueue(PropertiesContainer.getInstance().getService());

                /**
                 *holy crap!!!!
                 *list第一项放的是key，所以一定要get(1)。
                 *写api的人你写个注释能死么！
                 */
                if (refreshList.size() == 2) {
                    queueThreadN.add(refreshList.get(1));
                }
            } catch (Exception e) {
                LogService.errorSystemLog("################ operate execute occured error.runBlockCommand ########################", e);
            }
        }
    }

    private void execute(String refreshContent) {
        JSONObject object = JSONObject.fromObject(refreshContent);

        String type = object.getString("type");
        String wikiType = object.getString("wikitype");

        long now = System.currentTimeMillis();
        LogService.infoSystemLog("################start operate type:" + type + " refreshContent : " + refreshContent + " ########################", true);
        for (String channel : ChannelContainer.channelHtmlFacotryMap.keySet()) {
            try {
                if (type.equals(wikiType + "_initIndex")) { // 刷首页
                    doInitIndex(object, channel, wikiType);
                } else if (type.equals(wikiType + "_createIndex")) { // 刷首页
                    doCreateIndex(object, channel, wikiType);
                } else if (type.equals(wikiType + "_refreshPage")) { // 删一个
                    doRefreshPage(object, channel, wikiType);
                } else if (type.equals(wikiType + "_refreshwiki")) { // 删全部
                    doRefreshWiki(object, channel, wikiType);
                } else if (type.equals(wikiType + "_createAllIndex")) {
                    doCreateAllIndex(object, channel, wikiType);
                } else if (type.equals(wikiType + "_refreshSiteMap")) {//删除网站地图
                    doRefresSiteMap(null, wikiType);
                } else if (type.equals("reload_joymeTemplate")) {//重新读取
                    doRefresTemplate();
                }
            } catch (Exception e) {
                LogService.infoSystemLog("################ operate execute occured error. operateType: " + type + " ########################", true);
            }
        }
        LogService.infoSystemLog("################end operate type: " + type + " spend: " + (System.currentTimeMillis() - now) + " ########################", true);
    }

//    @Override
//    public void run() {
//        super.run();
//        for (int i = 0; ; i++) {
//            try {
//                this.execute();
//                // 每20秒查询一下
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                Thread.sleep(20 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void execute() throws Exception {
        int machineId = PropertiesContainer.getInstance().getMachineId();
        List refreshList = null;
//        int maxId = joymeRefreshLogService.queryMaxRefreshLogId(null, machineId);
//        refreshList = joymeRefreshService.queryJoymeRefreshItem(null, maxId);

        if (refreshList == null || refreshList.size() == 0)
            return;

        for (Iterator iterator = refreshList.iterator(); iterator.hasNext(); ) {
            int refreshId = 0;
            String type = null;
            long now = 0;
            try {
                JoymeRefresh refresh = (JoymeRefresh) iterator.next();
                refreshId = refresh.getFreshId();
                String refreshContent = refresh.getFreshContent();
                JSONObject object = JSONObject.fromObject(refreshContent);

                type = object.getString("type");
                String wikiType = object.getString("wikitype");

                now = System.currentTimeMillis();
                LogService.infoSystemLog("################start operate type:" + type + " refreshContent : " + refreshContent + " ########################", true);
                for (String channel : ChannelContainer.channelHtmlFacotryMap.keySet()) {
                    if (type.equals(wikiType + "_initIndex")) { // 刷首页
                        doInitIndex(object, channel, wikiType);
                    } else if (type.equals(wikiType + "_createIndex")) { // 刷首页
                        doCreateIndex(object, channel, wikiType);
                    } else if (type.equals(wikiType + "_refreshPage")) { // 删一个
                        doRefreshPage(object, channel, wikiType);
                    } else if (type.equals(wikiType + "_refreshwiki")) { // 删全部
                        doRefreshWiki(object, channel, wikiType);
                    } else if (type.equals(wikiType + "_createAllIndex")) {
                        doCreateAllIndex(object, channel, wikiType);
                    } else if (type.equals(wikiType + "_refreshSiteMap")) {//删除网站地图
                        doRefresSiteMap(null, wikiType);
                    } else if (type.equals("reload_joymeTemplate")) {//重新读取
                        doRefresTemplate();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogService.infoSystemLog("################ operate execute occured error. operateType: " + type + "id:" + refreshId + " ########################", true);
            }
            LogService.infoSystemLog("################end operate type: " + type + " spend: " + (System.currentTimeMillis() - now) + " ########################", true);
            insertRefreshLog(null, refreshId);
        }
    }

    private void doRefreshWiki(JSONObject object, String channel, String wikiType) throws IOException, JoymeDBException, JoymeServiceException {
        try {
            String key = object.getString("key");
            cacheService.refreshWiki(key, channel, wikiType);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do create index which key is : " + object.get("key"), e);
        }
    }

    private void doRefreshPage(JSONObject object, String channel, String wikiType) throws IOException, JoymeDBException, JoymeServiceException {
        try {
            String key = object.getString("key");
            String wikiPageId = object.getString("wikiPageId");
            cacheService.refreshPage(key, wikiPageId, channel, wikiType);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do create index which key is : " + object.get("key") + " and url wikiPageId " + object.get("wikiPageId"), e);
        }
    }

    private void doRefresSiteMap(Connection conn, String wikiType) throws IOException, JoymeDBException, JoymeServiceException {
        try {
            cacheService.refresSiteMap(wikiType);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do doRefresSiteMap which key is : " + wikiType + " and url wikiPageId " + wikiType, e);
        }
    }

    private void doRefresTemplate() throws IOException, JoymeDBException, JoymeServiceException {
        try {
            systemService.loadTemplate();
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do doRefresJoymeWiki", e);
        }
    }

    private void doCreateIndex(JSONObject object, String channel, String wikiType) throws JoymeDBException, JoymeServiceException {
        try {
            String key = object.getString("key");
            String domain = object.getString("domain");
            String url = object.getString("url");
            boolean refreshCss = object.getBoolean("refreshCss");
            String path = object.getString("path");
            cacheService.createIndex(key, domain, path, url, refreshCss, channel, wikiType);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do create index which key is : " + object.get("key") + " and url is " + object.get("url"), e);
        }
    }


    private void    doInitIndex(JSONObject object, String channel, String wikiType) throws JoymeDBException, JoymeServiceException {
        try {
            String key = object.getString("key");
            String domain = object.getString("domain");
            String url = object.getString("url");
            String androidPath = object.get("androidPath") == null ? null : object.getString("androidPath");
            String iosPath = object.get("iosPath") == null ? null : object.getString("iosPath");
            boolean refreshCss = object.getBoolean("refreshCss");
            String path = object.getString("path");
            if (object.get("wikiName") == null) {
                return;
            }
            String wikiName = object.getString("wikiName");
            boolean supportDomain = object.getBoolean("supportDomain");

            Integer pcKeepJscss = Integer.valueOf(object.getString("pcKeepJscss"));
            Integer mKeepJscss = Integer.valueOf(object.getString("mKeepJscss"));

            cacheService.initIndex(wikiName, key, domain, path, url, androidPath, iosPath, refreshCss, channel, wikiType, supportDomain, pcKeepJscss, mKeepJscss);
            doRefresTemplate();
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do create index which key is : " + object.get("key") + " and url is " + object.get("url"), e);
        }
    }


    private void doCreateAllIndex(JSONObject object, String channel, String wikiType) throws JoymeDBException, JoymeServiceException {

        LogService.infoSystemLog("################start create all index########################");
        long now = System.currentTimeMillis();
        boolean refreshCss = object.getBoolean("refreshCss");
        boolean refreshPage = object.getBoolean("refreshPage");

        if (refreshPage) {
            cacheService.refresAllPage();
        } else {
            List<JoymeWiki> joymeWikiList = joymeWikiService.queryJoymeWiki(null, wikiType);

            for (JoymeWiki joymeWiki : joymeWikiList) {
                try {
                    cacheService.createIndex(joymeWiki.getJoymeWikiKey(), joymeWiki.getJoymeWikiDomain()
                            , joymeWiki.getJoymeWikiPath(), joymeWiki.getJoymeWikiDomain() + "/wiki/%E9%A6%96%E9%A1%B5",
                            refreshCss, channel, wikiType);
                } catch (Exception e) {
                    LogService.errorSystemLog("Error when do create index which key is : " + object.get("key") + " and url is " + object.get("url"), e);
                }
            }
        }
        LogService.infoSystemLog("################start create all index speand: " + (System.currentTimeMillis() - now) + " ########################");
    }

    private void insertRefreshLog(Connection conn, long refreshId) throws JoymeDBException, JoymeServiceException {
        JoymeRefreshLog bean = new JoymeRefreshLog();
        bean.setFreshId((int) refreshId);
        bean.setMachineId(PropertiesContainer.getInstance().getMachineId());
        bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
        joymeRefreshLogService.insertJoymeRefreshLog(conn, bean);
    }
}
