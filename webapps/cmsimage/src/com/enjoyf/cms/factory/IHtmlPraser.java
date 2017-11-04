package com.enjoyf.cms.factory;

import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-6-11
 * Time: 上午12:22
 * To change this template use File | Settings | File Templates.
 */
public interface IHtmlPraser {
    public String parseURL(String reqUrl, String srcUrl, String urlKey, String channel, String fileType, String referer, boolean isSeoOpen, String transferPath, String originalPath) throws IOException;

    public String saveURLFile(String responseText, String reqUrl, String srcUrl, String host, boolean isSeoOpen, String transferPath, String originalPath) throws IOException;

    public String getCacheFile(String realPath, Boolean isSeoOpen, String host);
}

