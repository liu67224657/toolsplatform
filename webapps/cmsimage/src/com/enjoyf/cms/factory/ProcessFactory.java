package com.enjoyf.cms.factory;


import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;

public class ProcessFactory {
    private static final int NO_PROCESS = 0;
    private static final int SITEMAP = 1;
    private static final int ARTICLE = 2;
    private static final int STATIC = 3;
    private static final int RESPONSE_301 = 301;
    private static final int RESPONSE_500 = 500;
    private static final int RESPONSE_404 = 404;

    public static IProcess getFactory(int processCode) {
        if (processCode == SITEMAP) {
            return new SiteMapProcess();
        } else if (processCode == ARTICLE) {
            return new ArticleProcess();
        } else if (processCode == STATIC) {
            return new StaticProcess();
        } else if (processCode == RESPONSE_301) {
            return new Response301Process();
        }else {
            return new NoProcess();
        }
    }

    public static int checkProcess(String requestURL) throws MalformedURLException {
        URL url = new URL(requestURL);
        String path = url.getPath();
        if(!path.endsWith("/") && !path.contains(".")){
            path += "/";
        }
        if (path.endsWith("/sitemap.xml") || path.endsWith("/sitemap.txt")) {
            return SITEMAP;
        } else if (url.getPath().contains("//")) {
            return RESPONSE_301;
        } else if (path.endsWith("/") || path.endsWith(".html") || path.endsWith(".xml")) {
            return ARTICLE;
        } else if (path.endsWith(".js") || path.endsWith(".css")) {
            return STATIC;
        } else {
            return NO_PROCESS;
        }
    }
}
