package com.enjoyf.crwalwiki.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.enjoyf.crwalwiki.container.ConfigContainer;

public class CacheService {
    public void getKeyList(HttpServletRequest request) {
        String cacheFolder = ConfigContainer.prop.getProperty("cacheFolder");
        File file = new File(cacheFolder);
        File[] files = file.listFiles();
        List list = new ArrayList();
        for (int i = 0; i < files.length; i++) {
//            if(files[i].isFile()){
//                if(files[i].getName().startsWith("wiki_") && files[i].getName().endsWith(".properties")){
//                    String key = files[i].getName().replaceAll("wiki_", "").replaceAll(".properties", "");
//                    list.add(key);
//                }
//            }
            if(files[i].isDirectory()){
                 list.add(files[i].getName());
            }
        }
        request.setAttribute("keyList", list);
    }
}
