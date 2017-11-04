package com.enjoyf.mcms.service;

import com.enjoyf.util.StringUtil;

public class URLService {
    public String getArchiveFileName(String uri, int position) {
        String fileName = uri.substring(position + 1, uri.length());
        fileName = StringUtil.removeLastCharacter(fileName, ".html");
        return fileName;
    }
}
