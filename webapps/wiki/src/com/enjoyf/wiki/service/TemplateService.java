package com.enjoyf.wiki.service;

import com.enjoyf.util.SystemUtil;
import com.enjoyf.wiki.container.TemplateContainer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TemplateService {

    public static Map<String, String> commentTemplateMap = new HashMap<String, String>();
    public static Map<String, String> rankTemplateMap = new HashMap<String, String>();
    public static final String KEY_DEFAULT_CHANNEL = "default";

    public static void reloadTemplate() throws Exception {
        SystemUtil su = new SystemUtil();
        String webRootPath = su.getWebRootPath();

        // 加载专区
        String gameFolder = webRootPath + "/template/comment";
        Map map = getTemplateMap(gameFolder);
        commentTemplateMap = map;


        String rankFolder = webRootPath + "/template/rank";
        Map rankMap = getTemplateMap(rankFolder);
        rankTemplateMap = rankMap;
    }

    public static String getCommentTemplate(String channel) {
        return commentTemplateMap.get(channel + "_template.html");
    }

    public static String getIWTTemplate() {
        return commentTemplateMap.get("iwt.html");
    }

    public static String getRankTemplateMap(String channel) {
        return rankTemplateMap.get(channel + "_template.html");
    }

    private static Map getTemplateMap(String folder) throws Exception {
        File file = new File(folder);
        File[] files = file.listFiles();

        Map map = new HashMap();


        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                InputStreamReader isr = null;
                BufferedReader br = null;
                String fileName = files[i].getName();
                StringBuffer sb = new StringBuffer();
                try {
                    isr = new InputStreamReader(new FileInputStream(files[i]), "UTF-8");
                    br = new BufferedReader(isr);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\r\n");
                    }
                } finally {
                    if (br != null) {
                        br.close();
                        br = null;
                    }
                    if (isr != null) {
                        isr.close();
                        isr = null;
                    }
                }

                map.put(fileName, sb.toString());
            }
        }
        return map;
    }
}
