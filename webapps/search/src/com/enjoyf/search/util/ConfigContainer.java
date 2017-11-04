package com.enjoyf.search.util;

import com.enjoyf.util.SystemUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ConfigContainer {
    public static Properties prop = new Properties();


    public static void init() throws Exception {
        SystemUtil su = new SystemUtil();
        String metaInfoFolder = su.getMetaInfFolderPath();
        // 读取整体配置文件
        loadProperties(metaInfoFolder);
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



    public static String getConfigValue(String key) throws Exception {
        if (prop.isEmpty()) {
            SystemUtil su = new SystemUtil();
            String metaInfoPath = su.getMetaInfFolderPath();
            init();
        }

        return (String) prop.get(key);
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

    public static String getSearchApiUrl() {
        return prop.getProperty("search.api.url");
    }


}
