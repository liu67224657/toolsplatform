package com.enjoyf.webcache.factory;


import java.net.MalformedURLException;
import java.net.URL;

public class ProcessFactory {
    private static final int NO_PROCESS = 0;
    private static final int SITEMAP = 1;
    private static final int WEBCACHE = 2;
    private static final int STATIC = 3;
    private static final int RESPONSE_301 = 301;
    private static final int RESPONSE_500 = 500;
    private static final int RESPONSE_404 = 404;

    public static IProcess getFactory(int processCode) {
        if (processCode == SITEMAP) {
            return new SiteMapProcess();
        } else if (processCode == WEBCACHE) {
            return new WebCacheProcess();
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
        if(path.indexOf(";jsessionid") > 0){
            path = path.substring(0, path.indexOf(";jsessionid"));
        }
        if(!path.endsWith("/") && !path.contains(".")){
            path += "/";
        }
        //1 sitemap.xml访问的是静态文件，不需要做处理，直接读取缓存文件，输出即可（sitemap.xml文件的生成在cmsimage的SitemapJob定时器中）
        if (path.endsWith("/sitemap.xml") || path.endsWith("/sitemap.txt")) {
            return SITEMAP;
        //2 链接的path中有连续两个//的，去掉//为/做301跳转
        } else if (url.getPath().contains("//")) {
            return RESPONSE_301;
        //3 目前我们网站需要处理的链接类型为cms和数字站wiki
        //cms的链接以 list_x_x.html 或 index.html 或 x.html(x为数字archiveid）结尾，
        // 其中list_x_x.html 和 index.html 为栏目的首页或列表聚合页，
        // 而一般访问的时候，index.html会被省略,比如：http://www.joyme.com  ,   http://www.joyme.com/news/
        // 所以cms的需要处理 .html 和 / 结尾的链接。
        // 数字站wiki均是以 index.shtml 和 x.shtml(x为数字wikiid）,而index.shtml也会省略不写，如：http://wiki.joyme.com/dtcq/
        // 所以wiki数字站需要处理  .shtml 和 / 结尾的链接。
        } else if (path.endsWith("/") || path.endsWith(".html") || path.endsWith(".xml") || path.endsWith(".shtml")) {
            return WEBCACHE;
        //4 一些静态js css 文件， 不需要处理
        } else if (path.endsWith(".js") || path.endsWith(".css") || path.endsWith(".ico")) {
            return STATIC;
        } else {
            return NO_PROCESS;
        }
    }
}
