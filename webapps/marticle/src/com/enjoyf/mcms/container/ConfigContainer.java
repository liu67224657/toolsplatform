package com.enjoyf.mcms.container;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.redis.RedisConfig;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.mcms.bean.JoymeChannel;
import com.enjoyf.mcms.bean.JoymeShare;
import com.enjoyf.mcms.factory.*;
import com.enjoyf.mcms.service.JoymeChannelService;
import com.enjoyf.service.NamingService;
import com.enjoyf.service.Service;
import com.enjoyf.util.SystemUtil;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.*;

public class ConfigContainer {
    public static Properties prop = new Properties();
    private static JoymeChannelService joymeChannelService = new JoymeChannelService();
    // 用数据库配置
    public static Map channelMap = new HashMap();
    public final static String DEFAULT_CHANNEL_STRING = "default";
    public static Map channelHtmlFacotryMap = new HashMap();
    public static Map channelTagFacotryMap = new HashMap();
    public static Map channelArticleTypeMap = new HashMap();
    public static Map channelArchiveListMap = new HashMap();
    public static Map channelCategoryMap = new HashMap();
    private static boolean isSavePv = true;

    private static JoymeShare joymeShare = null;


    private static RedisManager redisManager;

    private static Service service;
    // public final static String GAME_TEMPLATE_KEY = "game_template";
    // public final static String ARCHIVE_TEMPLATE_KEY = "archive_template";
    // public final static String MORE_LINK_KEY = "more_link";
    // 数据库中的

    public static void init() throws Exception {
        SystemUtil su = new SystemUtil();
        String metaInfoFolder = su.getMetaInfFolderPath();
        // 读取整体配置文件
        loadProperties(metaInfoFolder);

        loadChannelConfig(null);

        try {
            isSavePv = Boolean.parseBoolean(prop.getProperty("is_save_pv", "true"));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void loadProperties(String metaInfoFolder) throws IOException {
        String configFile = metaInfoFolder + "/config.properties";
        InputStream is = null;
        try {
            is = new FileInputStream(new File(configFile));
            prop.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
                is = null;
            }
        }
    }

    public static void loadChannelConfig(Connection conn) throws JoymeDBException, JoymeServiceException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        // 读取渠道配置
        List channelList = joymeChannelService.queryJoymeChannel(conn);
        for (Iterator iterator = channelList.iterator(); iterator.hasNext(); ) {
            JoymeChannel channel = (JoymeChannel) iterator.next();
            channelMap.put(channel.getChannelName(), channel);
            //生成HTML解析的工厂
            String clazz = channel.getHtmlFactory();
            IHtmlParseFactory factory = (IHtmlParseFactory) Class.forName(clazz).newInstance();
            channelHtmlFacotryMap.put(channel.getChannelName(), factory);
            //生成tag解析的工厂
            clazz = channel.getTagFactory();
            ITagParseFactory tagFactory = (ITagParseFactory) Class.forName(clazz).newInstance();
            channelTagFacotryMap.put(channel.getChannelName(), tagFactory);

            clazz = channel.getArticleTypeFactory();
            IArticleTypeParseFactory articleTypeFactory = (IArticleTypeParseFactory) Class.forName(clazz).newInstance();
            channelArticleTypeMap.put(channel.getChannelName(), articleTypeFactory);

            //todo
            clazz = channel.getArchiveListFactory();
            IArchiveListFactory moreFactory = (IArchiveListFactory) Class.forName(clazz).newInstance();
            channelArchiveListMap.put(channel.getChannelName(), moreFactory);

            clazz = channel.getCategoryFactory();
            ICategoryFactory categoryFactory = (ICategoryFactory) Class.forName(clazz).newInstance();
            channelCategoryMap.put(channel.getChannelName(), categoryFactory);
        }
    }

    public static String getConfigValue(String key) throws Exception {
        if (prop.isEmpty()) {
            SystemUtil su = new SystemUtil();
            String metaInfoPath = su.getMetaInfFolderPath();
            init();
        }

        return (String) prop.get(key);
    }

    public static String getCacheFolder() throws Exception {
        return getConfigValue("cache_file_folder");
    }

    public static JoymeChannel getChannelBean(String channel) {
        if (channel != null && channelMap.keySet().contains(channel))
            return (JoymeChannel) channelMap.get(channel);
        else
            return (JoymeChannel) channelMap.get(DEFAULT_CHANNEL_STRING);
    }

    public static String getGameTemplate(String channel) {
        return getChannelBean(channel).getGameTemplate();
    }

    public static String getArchiveTemplate(String channel) {
        return getChannelBean(channel).getArchiveTemplate();
    }

    public static String getMoreLink(String channel) {
        return getChannelBean(channel).getMoreLink();
    }

    public static String getArticleTypeTemplate(String channel) {
        return getChannelBean(channel).getArticleTypeTemplate();
    }

    public static String getTagTemplate(String channel) {
        return getChannelBean(channel).getTagTemplate();
    }

    public static String getArchiveListTemplate(String channel) {
        return getChannelBean(channel).getArchiveListTemplate();
    }

    /**
     * 得到当前的路径
     *
     * @param request
     * @return
     */
    public static String getLocalPath(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String contentPath = request.getContextPath();
        int position = url.indexOf(contentPath);
        if (position > 0) {
            return url.substring(0, position + contentPath.length());
        }
        return contentPath;
    }

    public static IHtmlParseFactory getHtmlParseFactory(String key) {
        return (IHtmlParseFactory) channelHtmlFacotryMap.get(key);
    }

    public static ITagParseFactory getTagParseFactory(String key) {
        return (ITagParseFactory) channelTagFacotryMap.get(key);
    }

    public static IArticleTypeParseFactory getArticleTypeParseFactory(String key) {
        return (IArticleTypeParseFactory) channelArticleTypeMap.get(key);
    }

    public static IArchiveListFactory getArchiveListFactory(String key) {
        return (IArchiveListFactory) channelArchiveListMap.get(key);
    }

    public static int getMachineId() {
        return Integer.parseInt(prop.getProperty("machine_id"));
    }

    public static String getMarticleDomain() {
        return prop.getProperty("marticle_domain");
    }

    public static String getCMSDomain() {
        return prop.getProperty("cms_domain");
    }

    public static String getJoymeCommentQueryApi() {
        return prop.getProperty("joyme_comment_query_api");
    }

    public static String getChannelId() {
        if (!prop.containsKey("channel")) {
            return null;
        } else {
            return prop.getProperty("channel");
        }
    }

    public static String getMongoDBIp() {
        return prop.getProperty("mongo_db_ip");
    }

    public static int getMongoDBPort() {
        return Integer.parseInt(prop.getProperty("mongo_db_port"));
    }

    public static String getMongoDBName() {
        return prop.getProperty("mongo_db_name");
    }

    public static int getMongoDBMaxConns() {
        return Integer.parseInt(prop.getProperty("mongo_db_max_conns"));
    }

    public static String getGameDownloadUrl() {
        return prop.getProperty("game_download_url");
    }

    public static String getGameCover() {
        return prop.getProperty("game_cover");
    }

    public static boolean isSavePv() {
        return isSavePv;
    }

    public static JoymeShare getShare() {
        if (joymeShare == null) {
            joymeShare = new JoymeShare();
            joymeShare.setContent(prop.getProperty("share_content"));
            joymeShare.setPic(prop.getProperty("share_pic"));
            joymeShare.setLink(prop.getProperty("share_link"));
        }

        return joymeShare;
    }

    public static boolean cleanShare() throws Exception {
        joymeShare = null;
        SystemUtil su = new SystemUtil();
        String metaInfoFolder = su.getMetaInfFolderPath();
        init();
        return true;
    }

    public static ICategoryFactory getCategoryParseFactory(String channel) {
        return (ICategoryFactory) channelCategoryMap.get(channel);
    }

    public static String getToolsLoginURL() {
        return prop.getProperty("tools_login_url") + "?reurl=" + prop.getProperty("marticle_main_jsp");
    }

    public static String getToolsLoginOutURL() {
        return prop.getProperty("tools_loginout_url") + "?reurl=" + prop.getProperty("marticle_login_jsp");
    }

    public static String getMarticleLoginJsp() {
        return prop.getProperty("marticle_login_jsp");
    }

    public static String getMarticleMainJsp() {
        return prop.getProperty("marticle_main_jsp");
    }

    public static String getMarticleHost() {
        return prop.getProperty("marticle_host");
    }

    public static RedisManager getRedisManager() {
        if (redisManager == null) {
            synchronized (ConfigContainer.class) {
                if (redisManager == null) {
                    String host = prop.getProperty("redis_host");
                    int maxActivity = Integer.parseInt(prop.getProperty("redis_maxactivity"));
                    int maxWait = Integer.parseInt(prop.getProperty("redis_maxwait"));
                    int maxIdel = Integer.parseInt(prop.getProperty("redis_maxidel"));
                    String passowrd = prop.getProperty("redis_password");
                    RedisConfig redisConfig = new RedisConfig(host, maxActivity, maxWait, maxIdel, passowrd);
                    redisManager = new RedisManager(redisConfig);
                }
            }
        }
        return redisManager;
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
}
