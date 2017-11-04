package com.enjoyf.webcache.factory;

import com.enjoyf.webcache.bean.WebCacheUrlRule;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/9/30.
 */
public interface IWebCache {
    void processWebCache(String desRule, String srcRule, WebCacheUrlRule webCacheUrlRule, HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;

    String saveFile(String responseText, String host, String path, String desUrl, String srcUrl, String desRule, String srcRule, WebCacheUrlRule webCacheUrlRule) throws IOException;
}
