package com.enjoyf.mcms.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.enjoyf.mcms.container.TemplateContainer;
import com.enjoyf.util.SystemUtil;

public class TemplateService {
    public void reloadTemplate() throws Exception {
        SystemUtil su = new SystemUtil();
        String webRootPath = su.getWebRootPath();

        // 加载专区
        String gameFolder = webRootPath + "/template/game";
        Map map = getTemplateMap(gameFolder);
        TemplateContainer.gameTemplateMap = map;

        // 加载子页模板
        String archiveFolder = webRootPath + "/template/archive";
        map = getTemplateMap(archiveFolder);
        TemplateContainer.archiveTemplateMap = map;
    }

    private Map getTemplateMap(String folder) throws Exception{
        File file = new File(folder);
        File[] files = file.listFiles();

        Map map = new HashMap();
        
        
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                InputStreamReader isr = null;
                BufferedReader br = null;
                String fileName = files[i].getName();
                StringBuffer sb = new StringBuffer();
                try{
                    isr = new InputStreamReader(new FileInputStream(files[i]), "UTF-8");
                    br = new BufferedReader(isr);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\r\n");
                    }
                }finally{
                    if(br != null){
                        br.close();
                        br = null;
                    }
                    if(isr != null){
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
