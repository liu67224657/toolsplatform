package com.enjoyf.mcms.service;

import com.enjoyf.util.StringUtil;

public class URLService {
    public int getArchiveId(String uri, String fileName) {
        int archiveId = 0;
        String[] strs = uri.split("/");
        if (strs.length == 4) {
            if (fileName.length() > 8)
                archiveId = Integer.parseInt(fileName.substring(8, fileName.length()));
        } else if (strs.length == 5) {
            if (strs[3].length() == 8) {
                try {
                    archiveId = Integer.parseInt(fileName);
                } catch (Exception e) {
                    archiveId = 0;
                }
            }
        }
        return archiveId;
    }
    
    public String getArchiveFileName(String uri, int position) {
        String fileName = uri.substring(position + 1, uri.length());
        fileName = StringUtil.removeLastCharacter(fileName, ".html");
        return fileName;
    }
}
