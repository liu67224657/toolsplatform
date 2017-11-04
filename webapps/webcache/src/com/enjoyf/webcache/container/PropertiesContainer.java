package com.enjoyf.webcache.container;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.framework.memcached.MemCachedConfig;
import com.enjoyf.framework.memcached.MemCachedManager;
import com.enjoyf.framework.redis.RedisConfig;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.service.NamingService;
import com.enjoyf.service.Service;
import com.enjoyf.util.SystemUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;

public class PropertiesContainer {
    public final static long DISABLE_TIME = 24 * 60 * 60 * 1000;
    private static PropertiesContainer instance = new PropertiesContainer();

    public static final int CLEARPAGE_TYPE_FILE = 0;
    public static final int CLEARPAGE_TYPE_DIR = 1;


    private Properties prop = new Properties();
    public String[] replaceContext = null;
    private RedisManager redisManager;
    private Service service;
    private MemCachedManager memCachedManager;

    public static PropertiesContainer getInstance() {
        return instance;
    }

    private PropertiesContainer() {
        try {
            SystemUtil su = new SystemUtil();
            String metaInfoPath = su.getMetaInfFolderPath() + "/config.properties";
            prop.load(new FileInputStream(new File(metaInfoPath)));

            memCachedManager = new MemCachedManager(new MemCachedConfig(prop));
        } catch (IOException e) {
            LogService.errorSystemLog("e", e);
            System.exit(-1);
        }
    }

    public String getCacheFolder() {
        return prop.getProperty("cache_folder");
    }

//    public String getJoymeCommentListUrl() {
//        return prop.getProperty("joyme_comment_list_url");
//    }

//    public String getJoymeApiHotList() {
//        return prop.getProperty("joyme_comment_hotlist_api");
//    }

//    public String getJoymeCommentPostApi() {
//        return prop.getProperty("joyme_comment_post_api");
//    }

//    public String getJoymeCommentRemoveApi() {
//        return prop.getProperty("joyme_comment_remove_api");
//    }

//    public String getJoymeCommentApiList() {
//        return prop.getProperty("joyme_comment_commentlist_api");
//    }

    public String getJoymeCommentApiGetCId() {
        return prop.getProperty("joyme_comment_getcid_api");
    }

//    public String getJoymeCommentAgreeApi() {
//        return prop.getProperty("joyme_comment_agree_api");
//    }

    public String getJoymeCommentJs() {
        return prop.getProperty("joyme_comment_js");
    }

    public String getLoginCommonJs() {
        return prop.getProperty("joyme_login_js");
    }

    public String getJoymeOldCommentJs() {
        return prop.getProperty("joyme_old_comment_js");
    }

    public String getJqueryJs() {
        return prop.getProperty("joyme_jquery_js");
    }

    public String getJoymeDomain() {
        return prop.getProperty("joyme_domain");
    }

    public String[] getReplaceContext() {
        if (replaceContext == null) {
            replaceContext = prop.getProperty("replace_context").split(";");
        }
        return replaceContext;
    }


    public String getGameDownloadUrl() {
        return prop.getProperty("game_download_url");
    }

    public String getAppDownloadUrl() {
        return prop.getProperty("app_download_url");
    }


    public String getToolsLoginURL() {
        return prop.getProperty("tools_login_url") + "?reurl=" + prop.getProperty("cmsimage_main_jsp");
    }

    public String getToolsLoginOutURL() {
        return prop.getProperty("tools_loginout_url") + "?reurl=" + prop.getProperty("cmsimage_login_jsp");
    }

    public String getCmsimageLoginJsp() {
        return prop.getProperty("cmsimage_login_jsp");
    }

    public String sitemapStartRun() {
        return prop.getProperty("sitemap_start_run");
    }


    public RedisManager getRedisManager() {
        if (redisManager == null) {
            synchronized (PropertiesContainer.class) {
                if (redisManager == null) {
                    String host = prop.getProperty("redis_host");
                    int maxActivity = Integer.parseInt(prop.getProperty("redis_maxactivity"));
                    int maxWait = Integer.parseInt(prop.getProperty("redis_maxwait"));
                    int maxIdel = Integer.parseInt(prop.getProperty("redis_maxidel"));
                    String passowrd = prop.getProperty("redis_password");
                    RedisConfig redisConfig = new RedisConfig(host, maxActivity, maxWait, maxIdel,passowrd);
                    redisManager = new RedisManager(redisConfig);
                }
            }
        }
        return redisManager;
    }

    public MemCachedManager getMemCachedManager() {
        return memCachedManager;
    }

    public String getCmsimageMainJsp() {
        return prop.getProperty("cmsimage_main_jsp");
    }

    public Service getService() {
        String service_idString = prop.getProperty("service_id");
        if (service_idString != null) {
            service = Service.getServiceByString(service_idString);
        }
        return service;
    }

    public NamingService getNamingService() {
        return NamingService.getInstance(getRedisManager());
    }

    public String getphpCmsDomain() {
        return prop.getProperty("php_cms_domain");
    }

    public String getCdnName() {
        return prop.getProperty("cdn_name");
    }

    public String getJoymeAppDownloadJs() {
        return prop.getProperty("joyme_app_download_js");
    }

    public String getPcJumpMJs() {
        return prop.getProperty("joyme_pc_jump_m_js");
    }

    public String getJoymeGameDownloadJs() {
        return prop.getProperty("joyme_game_download_js");
    }

    public String getPiwikJobOpen() {
        return prop.getProperty("piwik_job_open");
    }

    public String getJoymeLiveJs() {
        return prop.getProperty("joyme_live_js");
    }

    public String getJoymeLiveNewJs() {
        return prop.getProperty("joyme_live_new_js");
    }

    public String getJoymeLiveCommentJs() {
        return prop.getProperty("joyme_live_comment_js");
    }

    public String getHtml5MediaJs() {
        return prop.getProperty("html5_media_min_js");
    }

    public String getBaiduSitemapPushApi() {
        return prop.getProperty("baidu_sitemap_push_api");
    }

    public String getIruiFile() {
        return prop.getProperty("irui_uri_file");
    }

    public String getHttpUrl() {
        return prop.getProperty("http_url");
    }

    public String sitemapPushOpen() {
        return prop.getProperty("sitemap_push_open");
    }


    public String getSitemapPath() {
        return prop.getProperty("sitemap_path");
    }

    public String getSitemapPathQuartz() {
        return prop.getProperty("sitemap_path_quartz");
    }


}
