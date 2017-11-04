package com.enjoyf.webcache.service;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Map;

import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;
import com.enjoyf.webcache.bean.WebCacheSrcType;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.cache.UrlRuleCache;
import com.enjoyf.webcache.factory.WebCacheFactory;
import com.enjoyf.webcache.util.WebCacheUtil;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

import com.enjoyf.webcache.bean.JoymeRefresh;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.framework.log.LogService;

public class CacheService {
    private static JoymeRefreshService joymeRefreshService = new JoymeRefreshService();
    private static UrlRuleCache urlRuleCache = new UrlRuleCache();
    private static WebCacheUrlRuleService webCacheUrlRuleService = new WebCacheUrlRuleService();


    public static final int CLEAR_TYPE_FILE_DIR = 1;
    public static final int CLEAR_TYPE_FILE = 0;

    public void cleanCache(String file, int type) {
        LogService.infoFreshLog("clean cache file start:" + file, true);
        File cacheFile = null;
        try {
            if (file == null) {
                cacheFile = new File(PropertiesContainer.getInstance().getCacheFolder());
                LogService.infoSystemLog("##########End to cleanCache########## clear" + cacheFile.getAbsolutePath(), true);
                cacheFile.delete();
            } else {
                //如果最后一级目录不包含.,并且不是/结尾,自动补齐/
                // todo ugc wiki 如果接入他们的缓存页面是不包含后缀的这个要单独处理,1ugc的wiki页面缓存未见是xxx.html,在webUtil.getPath里处理
                String lastPath = file.contains("/")?file.substring(file.lastIndexOf("/"), file.length()):file;
                if (lastPath.indexOf(".") < 0 && !lastPath.endsWith("/")) {
                    file = file + "/";
                }
                cacheFile = new File(file);
            }

            if(!cacheFile.exists()){
                LogService.infoSystemLog("##########End to cleanCache##########"+cacheFile+" not exists.", true);
                return;
            }

            //如果是文件直接删除
            if (!cacheFile.isDirectory()) {
                FileUtils.forceDelete(cacheFile);
                LogService.infoSystemLog("##########End to cleanCache##########"+cacheFile, true);
                return;
            }

            //如果是文件夹
            File[] files = cacheFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                try {
                    if (files[i].isDirectory()) {
                        //如果刷新的类型是“子文件及子文件夹”（type = 1）就把文件夹也删掉
                        if (type == CLEAR_TYPE_FILE_DIR) {
                            FileUtils.deleteDirectory(files[i]);
                        }
                    } else {
                        FileUtils.forceDelete(files[i]);
                    }
                } catch (Exception e) {
                    LogService.warnSystemLog("Warning when delete the file :" + files[i].getName());
                }
            }
            LogService.infoFreshLog("clean cache file end:" + file, true);
        } catch (Exception e) {
            LogService.infoFreshLog("clean cache file error:" + file, true);
            e.printStackTrace();
        }
    }

    private void listFiles(File cacheFile) {
        File[] files = cacheFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                System.out.println(files[i].getPath());
                listFiles(files[i]);
            } else {
                System.out.println(files[i].getPath());
                makeCacheFile(files[i]);
            }
        }
    }

    private void makeCacheFile(File cacheFile) {
        LogService.infoFreshLog("fresh file:" + cacheFile, true);
        try {
            String cacheFolder = PropertiesContainer.getInstance().getCacheFolder();
            String desUrl = cacheFile.getPath().replaceAll("\\\\", "/").replace(cacheFolder + "/", "").replace("joyme.test", "joyme.test:8088");
            desUrl = "http://" + desUrl;
            URL url = new URL(desUrl);
            String host = url.getHost();
            String path = url.getPath();

            Map<String, String> map = WebCacheUtil.genSrcRule(desUrl);
            if (map != null) {
                String srcRule = map.get("srcRule");
                String desRule = map.get("desRule");
                // 匹配到 的 rule
                WebCacheUrlRule webCacheUrlRule = urlRuleCache.getUrlRuleMemCache(desRule);
                if (webCacheUrlRule == null) {
                    webCacheUrlRule = webCacheUrlRuleService.getWebCacheUrlRule(null, MD5Util.Md5(desRule));
                }
                if (webCacheUrlRule != null) {
                    String srcUrl = desUrl.replace(desRule, srcRule);
                    String html = URLUtil.openURLWithTimeOut(srcUrl);
                    if (!StringUtil.isEmpty(html)) {
                        WebCacheSrcType srcType = webCacheUrlRule.getSrcType();
                        WebCacheFactory.getFactory(srcType).saveFile(html, host, path, desUrl, srcUrl, desRule, srcRule, webCacheUrlRule);
                    }
                }
            }
        } catch (Exception e) {
            LogService.errorFreshLog("fresh file:" + cacheFile, e);
            e.printStackTrace();
        }
    }


    public boolean createCleanCache() {
        try {
            LogService.infoSystemLog("##########Begin create to clean cache##########", true);

            JSONObject object = new JSONObject();
            object.put("type", "clearCache");

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoSystemLog("##########End create to clean cache#############", true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在META-INFO的配置文件中，都有一个service_id,每个不同的机器id都不一样，在InitServlet启动时，会将service_id放入到redis集合中，那么redis中有多个service_id
     * 然后在将需要刷新的链接放入到redis的队列时，会循环service_id，每一个service_id放一个刷新的队列
     * 然后在InitServlet中启动时，调用redis的brpop锁机制，去循环从队列里取数据，然后每台机器刷自己的service_id对应的缓存。
     *
     * @param clearpage
     * @return
     */
    public boolean createCleanPageCache(String clearpage, int clearType) {
        try {
            LogService.infoFreshLog("====add event to fresh cache start:" + clearpage, true);

            JSONObject object = new JSONObject();
            object.put("type", "cleanPageCache");
            object.put("clearpage", clearpage);
            object.put("cleartype", clearType);

            JoymeRefresh refresh = new JoymeRefresh();
            refresh.setFreshContent(object.toString());
            refresh.setOperationTime(new Timestamp(System.currentTimeMillis()));
            refresh.setOperationUser("admin");

            joymeRefreshService.insertJoymeRefresh(null, refresh);

            LogService.infoFreshLog("====add event to fresh cache ended:" + clearpage, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
