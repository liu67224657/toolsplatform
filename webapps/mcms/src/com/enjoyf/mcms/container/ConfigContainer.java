package com.enjoyf.mcms.container;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.enjoyf.mcms.bean.JoymeChannel;
import com.enjoyf.mcms.factory.IHtmlParseFactory;
import com.enjoyf.mcms.service.JoymeChannelService;
import com.enjoyf.util.SystemUtil;

public class ConfigContainer {
    public static Properties prop = new Properties();
    private static JoymeChannelService joymeChannelService = new JoymeChannelService();
    // 用数据库配置
    public static Map channelMap = new HashMap();
    public final static String DEFAULT_CHANNEL_STRING = "default";
    public static Map channelHtmlFacotryMap = new HashMap();

    // public final static String GAME_TEMPLATE_KEY = "game_template";
    // public final static String ARCHIVE_TEMPLATE_KEY = "archive_template";
    // public final static String MORE_LINK_KEY = "more_link";
    // 数据库中的

    public static void init() throws Exception {
        SystemUtil su = new SystemUtil();
        String metaInfoFolder = su.getMetaInfFolderPath();
        // 读取整体配置文件
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

        // 读取渠道配置
        List channelList = joymeChannelService.queryJoymeChannel(null);
        for (Iterator iterator = channelList.iterator(); iterator.hasNext();) {
            JoymeChannel channel = (JoymeChannel) iterator.next();
            channelMap.put(channel.getChannelName(), channel);
            String clazz = channel.getHtmlFactory();
            IHtmlParseFactory factory = (IHtmlParseFactory) Class.forName(clazz).newInstance();
            channelHtmlFacotryMap.put(channel.getChannelName(), factory);
        }

    }

    public static String getConfigValue(String key) throws Exception {
        if (prop.isEmpty()) {
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

}
