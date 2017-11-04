package com.enjoyf.cms.filter;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.container.SEOContainer;
import com.enjoyf.cms.factory.HtmlFactory;
import com.enjoyf.cms.factory.IProcess;
import com.enjoyf.cms.factory.ProcessFactory;
import com.enjoyf.cms.service.PVService;
import com.enjoyf.cms.service.SitemapFetcherService;
import com.enjoyf.cms.util.CmsimageFilePathUtil;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;

/**
 * Servlet Filter implementation class URLFilter
 */
@WebFilter("/URLFilter")
public class URLFilter implements Filter {
    private final static String ARTICLE_FLAG = "/article";
    private final static int SITE_MAP = 1;
    private final static int STATIC = 2;
    private final static int ARTICLE = 3;


    public URLFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        doURLFilter(req, resp, chain, response, request);
    }

    @Deprecated
    private void doSavePV(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        new Thread(new PVService(request)).start();
    }

    private void doURLFilter(ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, HttpServletRequest request)
            throws IOException, ServletException {
        String requestURL = request.getRequestURL().toString();
        int processCode = ProcessFactory.checkProcess(requestURL);
        ProcessFactory.getFactory(processCode).process(requestURL, req, resp, chain, response, request);
    }

    /**
     * xml返回true,如果key在seoconfig存在，
     * 并且请求的值以transferPath为开头返回true支持SEO，
     * 默认为false
     *
     * @param urlString 请求的url 例如 http://lscs.joyme.com/xxxxx
     * @param key       CMS中的地址 例如：http://article.joyme.com/article/vip/lscs1/
     * @param fileType  XML或者...
     * @return
     */
    @Deprecated
    public boolean isSeoOpen(String urlString, String key, String fileType) {
        if (fileType.equals("xml"))
            return true;

        boolean isSeoOpen = false;
        if (SEOContainer.transferMap.get(key) != null) {
            String value = SEOContainer.transferMap.get(key).toString();
            if (urlString.startsWith(value)) {
                isSeoOpen = true;
            }
        }
        return isSeoOpen;
    }

    @Deprecated
    public String getOriganPathKey(String onlinePath, String channel) throws MalformedURLException {
        URL url = new URL(onlinePath);
        String path = url.getPath();
        int position = path.indexOf(ARTICLE_FLAG);
        String key = null;
        if (position >= 0) {
            key = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext();
            String temp = path.substring(position + ARTICLE_FLAG.length() + channel.length() + 1, path.length());
            boolean isSecondDomain = false;
            if (temp.startsWith("/vip/")) {
                temp = temp.replaceFirst("/vip/", "");
                isSecondDomain = true;
            } else {
                temp = StringUtil.removeStartCharacter(temp, "/");
            }
            int position1 = temp.indexOf("/");
            if (position1 > 0) {
                key += "/" + (isSecondDomain ? "vip/" : "") + temp.substring(0, position1);
            }

            key = StringUtil.removeLastCharacter(key, "/") + "/";
        }
        return key;
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
