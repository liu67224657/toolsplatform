package com.enjoyf.wiki.filter;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.WikiPage;
import com.enjoyf.wiki.bean.temp.WikiParamBean;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.facade.WikiPVErrorFacade;
import com.enjoyf.wiki.facade.WikiPVFacade;
import com.enjoyf.wiki.facade.WikiSiteMapFacade;
import com.enjoyf.wiki.factory.IHtmlParseFactory;
import com.enjoyf.wiki.factory.WikiPraseParam;
import com.enjoyf.wiki.service.ChannelService;
import com.enjoyf.wiki.service.WikiPageService;
import com.enjoyf.wiki.service.WikiRankService;
import com.enjoyf.wiki.tools.AppUtil;
import com.enjoyf.wiki.tools.WikiUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class URLFilter implements Filter {
    private static WikiPageService pageService = new WikiPageService();
    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();
    private static AppUtil au = new AppUtil();
    private static ChannelService cs = new ChannelService();
    private ChannelService channelService = new ChannelService();
    private WikiSiteMapFacade wikiSiteMapFacade = new WikiSiteMapFacade();
    WikiRankService wrService = new WikiRankService();

    // private final static String flag = "";

    public void destroy() {
        // TODO Auto-generated method stub

    }

    //是否通过拦截器
    private boolean checkIsParse(String requestURL) throws MalformedURLException {
        return requestURL.endsWith("/") || requestURL.endsWith(".html") || requestURL.endsWith(".xml") || requestURL.endsWith(".shtml") || requestURL.endsWith(".txt");
    }


    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;


        //用于5步跳转
        String host = request.getHeader("Host");
        String uri = WikiUtil.appendIndex(request.getRequestURI());
        uri = uri.replaceAll(request.getContextPath(), "");
        uri = "/" + uri.replaceAll("^/+", "");

        //静态资源js css就直接过
        if (!checkIsParse(uri)) {
            chain.doFilter(request, response);
            return;
        }

        //用于得到url的string
        String urlStr = WikiUtil.appendIndex("http://" + host + uri + "?" + request.getQueryString());
        URL url = new URL(urlStr);
        String wikiType = WikiUtil.getWikiTypeByUrl(url, request);
        //网站地图
        if (uri.endsWith("sitemap.xml") || uri.endsWith("sitemap.txt")) {
            WikiParamBean bean = WikiUtil.getWikiParamBean(url, request);
            String key = bean.getKey();

            String path = null;
            if (wikiType.equals("wiki")) {
                path = wikiSiteMapFacade.getPath(key);
            }else{
                path = wikiSiteMapFacade.getMWikiSitemapPath(key);
            }
            File fileXml = new File(path + "/sitemap.xml");
            if (!fileXml.exists()) {
                if (key.equals("wiki")) {
//                    wikiSiteMapFacade.crateWikiXml();
                } else {
                    wikiSiteMapFacade.createXml(key);
                }

            }
            wikiSiteMapFacade.displayFileXML(response, fileXml, false);
            return;
        }


        //得到解析工厂
        String channel = channelService.getChannelByRequest(request);
        IHtmlParseFactory factory = channelService.getHtmlParseFactory(channel);

        //根据后缀判断
        if (uri.endsWith(".shtml") && !WikiUtil.isResWIki(url)) {
            WikiParamBean bean = WikiUtil.getWikiParamBean(url, request);


            if (bean == null) {
                chain.doFilter(arg0, arg1);
                return;
            }

            if (bean.getKey().contains(WikiUtil.WIKI_KEY_ILLEGEL_CODE)) {
                chain.doFilter(arg0, arg1);
                return;
            }

            //todo 可以去掉？
//            if (PropertiesContainer.getInstance().isSavePv) {
//                WikiPVFacade facade = new WikiPVFacade(request, channel, wikiType, bean.getKey());
//                facade.start();
//            }

            //todo 和产品讨论是否需要去掉这个逻辑
            String key = bean.getKey();
            try {
                String androidPath = PropertiesContainer.getInstance().getAndroidPath(key, wikiType);
                String iosPath = PropertiesContainer.getInstance().getIOSPath(key, wikiType);

                boolean isAndroid = au.checkIsAndroid(request);
                boolean isIOS = au.checkIsIOS(request);

                // 弹出提示框
                boolean isDownload = cs.checkDownloadByChannel(request, wikiType);
                if (isDownload && isAndroid && !StringUtil.isEmpty(androidPath)) {
                    doAndroidAlert(request, response, uri, key, androidPath);
                }

                if (isDownload && isIOS && !StringUtil.isEmpty(iosPath)) {
                    doIOSAlert(request, response, uri, key, iosPath);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            //支持二级域名同时来自于www域名
//            boolean noSEO = false;
//            try {
//                noSEO = PropertiesContainer.getInstance().supportSubDomain(key, wikiType) && WikiUtil.isWWWdomain(url);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            //支持二级域名来自于WWW站同时PC站  todo 是否还需要往2级域名上跳转不需要做这个判断，直接在nginx做跳转即可
//            if (noSEO && wikiType.equals(WikiUtil.PC_WIKI)) {
//                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
//
//                String redUrl = "http://" + key + "." + PropertiesContainer.getInstance().getSubDomain() + "/" + bean.getPageName();
//                if (bean.getPageName().equals("index.shtml")) {
//                    redUrl = "http://" + key + "." + PropertiesContainer.getInstance().getSubDomain() + "/";
//                }
//                if (request.getQueryString() != null && request.getQueryString().length() > 0) {
//                    redUrl += "?" + request.getQueryString();
//                }
//
//                response.setHeader("Location", redUrl);
//                return;
//            }

            String fileName = factory.getFullFileName(channel, key, true, bean.getPageName(), wikiType);
            File file = new File(fileName);
            if (file.exists()) {
                displayFile(response, file, true);

                int position = bean.getPageName().indexOf(".");
                long wikiPageId = 0;
                try {
                    wikiPageId = Long.parseLong(bean.getPageName().substring(0, position));
                    wrService.incrHotRank(key, wikiPageId);
                } catch (NumberFormatException e) {
                }
                return;
            }
            //todo 请测试
            else if (bean.getPageName().contains("index.shtml")) {
                String html = null;
                try {
                    String path = PropertiesContainer.getInstance().getPath(key, wikiType);
                    String domain = PropertiesContainer.getInstance().getDomain(key, wikiType);
                    if (domain == null || path == null) {
                        LogService.errorSystemLog("get wikidomain is error.:" + key);
                        chain.doFilter(arg0, arg1);
                        return;
                    }
                    String fullUrl = domain + path + URLEncoder.encode("首页", "utf-8");
                    String newFileName = bean.getPageName();
                    int isIndex = bean.getPageName().equals("index.shtml") ? 1 : 2;
                    WikiPraseParam param = new WikiPraseParam();
                    param.setFullUrl(fullUrl);
                    param.setSaveFileName("index.shtml");
                    param.setIndex(1);
                    param.setPath(path);
                    param.setWikiType(wikiType);
                    param.setKey(key);
                    param.setDomain(domain);
                    param.setPageInfo("首页");
                    param.setWikiPageId("index");
                    html = channelService.getHtmlParseFactory(channel).parseURLAndSave(channel, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (html == null) {
                    chain.doFilter(arg0, arg1);
                    return;
                }
                displayHtml(response, html);
            } else {
                // 处理
//                    String toUrl = urlpath[2];
                int position = bean.getPageName().indexOf(".");
                long wikiPageId = 0l;
                WikiPage wp = null;
                try {
                    try {
                        wikiPageId = Long.parseLong(bean.getPageName().substring(0, position));
                    } catch (NumberFormatException e) {
                    }
                    if (wikiPageId > 0l) {
                        wp = pageService.queryWikiPagebyId(null, wikiPageId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (wp == null) {
                    chain.doFilter(arg0, arg1);
                    return;
                }

                String html = null;
                try {
                    String urlEncodeUrl = wp.getWikiUrl();
                    String domain = PropertiesContainer.getInstance().getDomain(key, wikiType);
                    if (domain == null) {
                        LogService.errorSystemLog("get wikidomain is error.:" + key);
                        chain.doFilter(arg0, arg1);
                        return;
                    }

                    String path = PropertiesContainer.getInstance().getPath(key, wikiType);
                    String fullUrl = domain + path + URLEncoder.encode(urlEncodeUrl, "utf-8");
                    String newFileName = bean.getPageName();
                    int isIndex = bean.getPageName().equals("index.shtml") ? 1 : 2;

                    WikiPraseParam param = new WikiPraseParam();
                    param.setFullUrl(fullUrl);
                    param.setSaveFileName(newFileName);
                    param.setIndex(isIndex);
                    param.setPath(path);
                    param.setWikiType(wikiType);
                    param.setKey(key);
                    param.setDomain(domain);
                    param.setPageInfo(wp.getWikiUrl());
                    param.setSupportSubdomain(PropertiesContainer.getInstance().supportSubDomain(key, wikiType));
                    param.setWikiPageId(String.valueOf(wikiPageId));

                    html = factory.parseURLAndSave(channel, param);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (html == null) {
                    chain.doFilter(arg0, arg1);
                    return;
                }
                displayHtml(response, html);
                wrService.incrHotRank(key, wikiPageId);
            }
        } else {
            chain.doFilter(arg0, arg1);
        }
    }

    /**
     * IOS的五步
     *
     * @param request
     * @param response
     * @param url
     * @param key
     * @param
     * @param
     * @throws IOException
     */
    private void doIOSAlert(HttpServletRequest request, HttpServletResponse response, String url, String key, String iosPath) throws IOException {
        fiveStepAlert(request, response, url, key, iosPath);
    }

    /**
     * android的五部跳转
     *
     * @param request
     * @param response
     * @param url
     * @param key
     * @param androidPath
     * @param
     * @throws IOException
     */
    private void doAndroidAlert(HttpServletRequest request, HttpServletResponse response, String url, String key, String androidPath) throws IOException {
        fiveStepAlert(request, response, url, key, androidPath);
    }

    private void fiveStepAlert(HttpServletRequest request, HttpServletResponse response, String url, String key, String path)
            throws IOException {
        int visitTimes = 0;
        if (request.getSession().getAttribute("visitTimes") != null)
            visitTimes = (Integer) request.getSession().getAttribute("visitTimes");

        if (!StringUtil.isEmpty(path)) {
            // 访问次数加1
            visitTimes = visitTimes + 1;
            request.getSession().setAttribute("visitTimes", visitTimes);
            if (visitTimes == 5) {
                request.getSession().setAttribute("androidPath", path);
                String incomeUrl = request.getContextPath() + url;
                request.getSession().setAttribute("incomeUrl", incomeUrl);
                request.getSession().setAttribute("key", key);
                response.sendRedirect("/mwiki/download/android_download.jsp");
                return;
            }
        }
    }

    public void displayFile(HttpServletResponse response, File file, boolean checkFileLength) throws IOException, FileNotFoundException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        FileInputStream is = new FileInputStream(file);

        //如果小于300判断为404修改wiki_page
        if (checkFileLength && file.length() < 300) {
            String id = file.getName().substring(0, file.getName().lastIndexOf("."));
            WikiPage bean = new WikiPage();
            try {
                bean.setPageId(Long.valueOf(id));
                bean.setPageStatus(0);
                pageService.updateWikiPage(null, bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        OutputStream os = null;
        try {
            os = response.getOutputStream();
            byte[] buff = new byte[8192];
            int len = -1;
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }

        } finally {
            if (is != null) {
                is.close();
                is = null;
            }

            if (os != null) {
                os.flush();
                os.close();
                os = null;
            }
        }

    }

    private void displayHtml(HttpServletResponse response, String html) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(html);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
    }

}
