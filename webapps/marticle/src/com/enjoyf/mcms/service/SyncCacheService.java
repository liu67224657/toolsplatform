package com.enjoyf.mcms.service;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.mcms.bean.JoymeRefresh;
import com.enjoyf.mcms.bean.JoymeRefreshLog;
import com.enjoyf.mcms.bean.temp.CreateItemBean;
import com.enjoyf.mcms.bean.temp.ReplaceChannelBean;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.facade.AdminConsoleFacade;
import com.enjoyf.util.collection.QueueList;
import com.enjoyf.util.collection.QueueListener;
import com.enjoyf.util.collection.QueueThreadN;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SyncCacheService extends Thread {
    private static JoymeRefreshLogService joymeRefreshLogService = new JoymeRefreshLogService();
    private static AdminConsoleFacade facade = new AdminConsoleFacade();

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
        }, new QueueList(), "marticleSyncCacheService");

        this.start();
    }

    @Override
    public void run() {
        runBlockCommand();
    }

    private void runBlockCommand() {
        while (true) {
            try {
                List<String> refreshList = ConfigContainer.getNamingService().blockpopServQueue(ConfigContainer.getService());
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

            if (type.equals("createItem")) { // 刷首页
                doCreateItem(object);
            } else if (type.equals("replaceChannelMap")) { // 广告语
                doReplaceChannelMap(object);
            } else if (type.equals("refresh")) { // 刷新缓存
                doRefresh(object);
            } else if (type.equals("cleanallcache")) {
                doCleanAllCache(object);
            } else if (type.equals("cleanalltag")) {
                doCleanAllTag(object);
            } else if (type.equals("cleanallarchivelist")) {
                doCleanAllArchiveList(object);
            } else if (type.equals("cleanallarchivetype")) {
                doCleanAllArchiveType(object);
            } else if (type.equals("cleanShare")) {
                doCleanShare(object);
            } else if (type.equals("cleansinglepage")) {
                doCleanSinglePage(object);
            } else if (type.equals("categorytags")) {
                doCleanAllCategorytags(object);
            } else if (type.equals("categoryarchivelist")) {
                doCleanAllCategoryarchivelist(object);
            }
        } catch (Exception e) {
            LogService.errorSystemLog("Error when clean all execute cache", e);
        }
    }

    private void doCleanAllCategoryarchivelist(JSONObject object) {
        try {
            LogService.infoSystemLog("====Begin to clean all Categoryarchivelist cache====");
            facade.cleanAllCategoryarchivelist();
            LogService.infoSystemLog("====End to clean all Categoryarchivelist cache ====");
        } catch (Exception e) {
            LogService.errorSystemLog("Error when clean all Categoryarchivelist cache");
        }
    }

    private void doCleanAllCategorytags(JSONObject object) {
        try {
            LogService.infoSystemLog("====Begin to clean all Categorytags cache====");
            facade.cleanAllCategorytags();
            LogService.infoSystemLog("====End to clean all Categorytags cache ====");
        } catch (Exception e) {
            LogService.errorSystemLog("Error when clean all Categorytags cache");
        }
    }

    private void doCleanSinglePage(JSONObject object) {
        try {
            LogService.infoSystemLog("====Begin to clean single page cache====");
            String path = object.getString("path");
            facade.cleanSinglePage(path);
            LogService.infoSystemLog("====End to clean single page cache ====");
        } catch (Exception e) {
            LogService.errorSystemLog("Error when clean single page cache");
        }
    }

    private void doCleanAllArchiveType(JSONObject object) {
        try {
            LogService.infoSystemLog("====Begin to clean all tag cache====");
            facade.cleanAllArchiveType();
            LogService.infoSystemLog("====End to clean all tag cache ====");
        } catch (Exception e) {
            LogService.errorSystemLog("Error when clean all tag cache");
        }
    }

    private void doCleanAllArchiveList(JSONObject object) {
        try {
            LogService.infoSystemLog("====Begin to clean all tag cache====");
            facade.cleanAllArchiveList();
            LogService.infoSystemLog("====End to clean all tag cache ====");
        } catch (Exception e) {
            LogService.errorSystemLog("Error when clean all tag cache");
        }
    }

    private void doCleanAllTag(JSONObject object) {
        try {
            LogService.infoSystemLog("====Begin to clean all tag cache====");
            facade.cleanAllTag();
            LogService.infoSystemLog("====End to clean all tag cache ====");
        } catch (Exception e) {
            LogService.errorSystemLog("Error when clean all tag cache");
        }
    }

    private void doCleanShare(JSONObject object) {
        try {
            LogService.infoSystemLog("====Begin to cleanShare cache====");
            facade.cleanShare();
            LogService.infoSystemLog("====End to cleanShare cache ====");
        } catch (Exception e) {
            LogService.errorSystemLog("Error when cleanSharecache");
        }
    }

    /**
     * 删除所有的缓存
     *
     * @param object
     */
    private void doCleanAllCache(JSONObject object) {
        try {
            LogService.infoSystemLog("====Begin to clean all cache====");
            facade.cleanAllCache();
            LogService.infoSystemLog("====End to clean all cache ====");
        } catch (Exception e) {
            LogService.errorSystemLog("Error when clean all cache");
        }
    }

    private void doRefresh(JSONObject object) {
        try {
            LogService.infoSystemLog("====Begin to refresh which path is:" + object.getString("filePath"), true);
            String specId = object.getString("specId");
            String filePath = object.getString("filePath");
            String localPath = object.getString("localPath");

            facade.refresh(specId, filePath, localPath);
            LogService.infoSystemLog("====End to refresh which path is:" + object.getString("filePath"), true);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do createItem which filePath is:" + object.get("filePath"), e);
        }
    }

    private void doCreateItem(JSONObject object) {
        try {
            CreateItemBean bean = new CreateItemBean();
            // bean.setChannelMap((Map) object.get("channelMap"));
            if (object.get("filePath") == null) {
                return;
            }

            bean.setFilePath(object.getString("filePath"));
            // bean.setLocalPath(object.getString("localPath"));
            bean.setSpecId(object.get("specId") == null ? null : object.getString("specId"));
            bean.setSpecLanguage(object.get("specLanguage") == null ? null : object.getString("specLanguage"));
            bean.setSpecName(object.get("specName") == null ? null : object.getString("specName"));
            bean.setSpecPicUrl(object.get("specPicUrl") == null ? null : object.getString("specPicUrl"));
            bean.setSpecSize(object.get("specSize") == null ? null : object.getString("specSize"));
            bean.setSpecType(object.get("specType") == null ? null : object.getString("specType"));
            bean.setSpecVersion(object.get("specVersion") == null ? null : object.getString("specVersion"));
            bean.setIsCompressImages((object.get("isCompressImages") == null ? "1" : object.getString("isCompressImages")));

            facade.insertJoymeSpec(bean);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do createItem which filePath is:" + object.get("filePath"), e);
        }
    }

    private void doReplaceChannelMap(JSONObject object) {
        try {
            ReplaceChannelBean bean = new ReplaceChannelBean();
            bean.setChannelMap((Map) object.get("channelMap"));
            if (object.get("filePath") == null) {
                return;
            }
            bean.setFilePath(object.getString("filePath"));
            bean.setLocalPath(object.getString("localPath"));
            bean.setSpecId(object.get("specId") == null ? null : object.getString("specId"));

            facade.replaceChannelMap(bean);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do createItem which filePath is:" + object.get("filePath"), e);
        }
    }

    private void insertRefreshLog(Connection conn, int refreshId) throws JoymeDBException, JoymeServiceException {
        try {
            JoymeRefreshLog bean = new JoymeRefreshLog();
            bean.setFreshId((int) refreshId);
            bean.setMachineId(ConfigContainer.getMachineId());
            bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
            joymeRefreshLogService.insertJoymeRefreshLog(conn, bean);
        } catch (Exception e) {
            LogService.errorSystemLog("Error when do insertRefreshLog", e);
        }
    }
}
