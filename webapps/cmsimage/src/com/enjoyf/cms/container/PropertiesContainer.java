package com.enjoyf.cms.container;

import com.enjoyf.framework.redis.RedisConfig;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.service.NamingService;
import com.enjoyf.service.Service;

import javax.sound.sampled.Control;
import java.util.Properties;

public class PropertiesContainer {
    public final static long DISABLE_TIME = 24 * 60 * 60 * 1000;
    public static Properties prop = new Properties();
    public static String[] replaceContext = null;

    private static RedisManager redisManager;

    private static Service service;

    public static String getphpCmsDomain() {
        return prop.getProperty("php_cms_domain");
    }

    public static String getphpProjectContext() {
        return prop.getProperty("php_project_context");
    }

    public static String getCacheFolder() {
        return prop.getProperty("cache_folder");
    }

    public static String getJoymeCommentListUrl() {
        return prop.getProperty("joyme_comment_list_url");
    }

    public static String getJoymeApiHotList() {
        return prop.getProperty("joyme_comment_hotlist_api");
    }

    public static String getJoymeCommentPostApi() {
        return prop.getProperty("joyme_comment_post_api");
    }

    public static String getJoymeCommentRemoveApi() {
        return prop.getProperty("joyme_comment_remove_api");
    }

    public static String getJoymeCommentApiList() {
        return prop.getProperty("joyme_comment_commentlist_api");
    }

    public static String getJoymeCommentApiGetCId() {
        return prop.getProperty("joyme_comment_getcid_api");
    }

    public static String getJoymeCommentAgreeApi() {
        return prop.getProperty("joyme_comment_agree_api");
    }

    public static String getJoymeCommentJs() {
        return prop.getProperty("joyme_comment_js");
    }

    public static String getJqueryJs() {
        return prop.getProperty("joyme_jquery_js");
    }

    public static String getJoymeDomain() {
        return prop.getProperty("joyme_domain");
    }

    public static int getMachineId() {
        return Integer.parseInt(prop.getProperty("machine_id"));
    }

    public static String[] getReplaceContext() {
        if (replaceContext == null) {
            replaceContext = prop.getProperty("replace_context").split(";");
        }
        return replaceContext;
    }

    public static String getMongoDBIP() {
        return prop.getProperty("mongo_db_ip");
    }

    public static String getMongoDBName() {
        return prop.getProperty("mongo_db_name");
    }

    public static String getMongoDBMaxConns() {
        return prop.getProperty("mongo_db_max_conns");
    }

    public static String getGameDownloadUrl() {
        return prop.getProperty("game_download_url");
    }

    public static String getAppDownloadUrl() {
        return prop.getProperty("app_download_url");
    }

    public static int getMongoDBPort() {
        try {
            return Integer.parseInt(prop.getProperty("mongo_db_port"));
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getToolsLoginURL() {
        return prop.getProperty("tools_login_url") + "?reurl=" + prop.getProperty("cmsimage_main_jsp");
    }

    public static String getToolsLoginOutURL() {
        return prop.getProperty("tools_loginout_url") + "?reurl=" + prop.getProperty("cmsimage_login_jsp");
    }

    public static String getCmsimageLoginJsp() {
        return prop.getProperty("cmsimage_login_jsp");
    }

    public static String sitemapStartRun() {
        return prop.getProperty("sitemap_start_run");
    }


    public static RedisManager getRedisManager() {
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

    public static String getCmsimageMainJsp() {
        return prop.getProperty("cmsimage_main_jsp");
    }

    public static Service getService() {
        String service_idString = prop.getProperty("service_id");
        if (service_idString != null) {
            service = Service.getServiceByString(service_idString);
        }
        return service;
    }

    public static NamingService getNamingService() {
        return NamingService.getInstance(getRedisManager());
    }

    public static String getEnvDomain(){
        return prop.getProperty("env_domain");
    }

    public static String getHttpUrl(){
        return prop.getProperty("http_url");
    }

    public static String getBaiduSitemapPushApi() {
        return prop.getProperty("baidu_sitemap_push_api");
    }

    public static String sitemapPushOpen() {
        return prop.getProperty("sitemap_push_open");
    }
}
