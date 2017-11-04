package com.enjoyf.mcms.filter;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.mcms.container.ConfigContainer;
import com.enjoyf.mcms.facade.ArchiveFacade;
import com.enjoyf.mcms.facade.ArchivePVFacade;
import com.enjoyf.mcms.facade.ArticleTypeFacade;
import com.enjoyf.mcms.factory.IArchiveListFactory;
import com.enjoyf.mcms.factory.ITagParseFactory;
import com.enjoyf.mcms.joymeapp.JoymeAppClientConstant;
import com.enjoyf.mcms.service.ChannelService;
import com.enjoyf.mcms.service.JoymeAppCommonParameterService;
import com.enjoyf.mcms.service.URLService;
import com.enjoyf.mcms.util.AppUtil;
import com.enjoyf.util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.util.Enumeration;

public class URLFilter implements Filter {


    private static ArchiveFacade facade = new ArchiveFacade();
    private static ArticleTypeFacade articleTypeFacade = new ArticleTypeFacade();
    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();


    private final static int DEFAULT_PATH = 1;
    private final static int GAME_PATH = 2;
    private final static int ARTICLE_PATH = 3;
    private final static int ARTICLE_LIST_PATH = 4;
    private final static int TAG_LIST_PATH = 5;
    // 根据一个类型的文章获得的列表
    private final static int ARTICLE_TYPE_LIST_PATH = 6;
    private final static int CATEGORY_TAG_PATH = 7;
    private static final int CATEGORY_ARCHIVES_PATH = 8;

    private final static String GAME_FLAG = "game";
    public final static String ARTICLE_LIST_FLAG = "archivelist";
    private final static String TAG_LIST_FLAG = "tags";
    private final static String CATEGORY_TAG_FLAG = "categorytags";
    private final static String CATEGORY_ARCHIVES_FLAG = "categoryarchivelist";
    private final static URLService urlService = new URLService();

    // private final static PVFacade pvFacade = new PVFacade();

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;


        doMobile(req, resp, chain, request, response);
        // pvFacade.insertPV(request);
    }

    //是否通过拦截器
    private boolean checkIsParse(String requestURL) throws MalformedURLException {
        return requestURL.endsWith("/") || requestURL.endsWith(".html") || requestURL.endsWith(".xml") || requestURL.endsWith(".shtml");
    }

    /**
     * 移动版的
     *
     * @param req
     * @param resp
     * @param chain
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void doMobile(ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String uri = request.getRequestURI();
        uri = getFullUri(uri);

        if (StringUtil.isEmpty(uri)) {
            uri = "";
        }

        if(uri.startsWith("/marticle/pc/")){
            uri.replace("/marticle/pc/", "/marticle/");
        }

        if (!checkIsParse(uri)) {
            chain.doFilter(req, resp);
            return;
        }

        int pathType = this.parsePath(request, uri);

        // 进行pv保存
        //todo
        if (ConfigContainer.isSavePv()) {
            ArchivePVFacade facade = new ArchivePVFacade(request);
            facade.start();
        }

        //判断来自什么机器
        String plat = AppUtil.getPlatForm(request);

        // 判断专题
        try {
            if (pathType == GAME_PATH) {
                doGameType(req, resp, chain, response, uri);
            } else if (pathType == ARTICLE_PATH) {
                doArticleType(req, resp, chain, request, response, uri, plat);
            } else if (pathType == ARTICLE_LIST_PATH) {
//                chain.doFilter(req, resp);
                doArchiveList(req, resp, chain, request, response, uri);
            } else if (pathType == TAG_LIST_PATH) {
                doTagType(req, resp, chain, request, response, uri, plat);
            } else if (pathType == ARTICLE_TYPE_LIST_PATH) {
                doArticleTypeList(req, resp, chain, response, uri);
            } else if (pathType == CATEGORY_TAG_PATH) {
                doCategoryTagList(req, resp, chain, response, uri);
            } else if (pathType == CATEGORY_ARCHIVES_PATH) {
                doCategoryArchivesList(req, resp, chain, response, uri);
            } else {
                chain.doFilter(req, resp);
            }
        } catch (Exception e) {
            chain.doFilter(req, resp);
        }
    }

    private String getFullUri(String path) {
        String[] paths = path.split("/");
        String tempPath = "";
        if (paths.length > 3) {
            String lastPath = paths[paths.length - 1];
            if (!lastPath.endsWith("/")) {
                if (lastPath.indexOf(".") < 0)
                    lastPath += "/index.html";

                for (int i = 0; i < paths.length; i++) {
                    if (i != paths.length - 1) {
                        tempPath += paths[i] + "/";
                    } else {
                        tempPath += lastPath;
                    }
                }
            } else {
                tempPath = path + "index.html";
            }
            return tempPath;
        } else if (paths.length == 3) { //只有域名的情况
            return path + "/index.html";
        } else {
            return null;
        }
    }

    private void doTagType(ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletRequest request, HttpServletResponse response,
                           String uri, String plat) throws Exception {
        int[] tagPageId = getTagPageId(uri);
        String channel = ChannelService.getChannel((HttpServletRequest) req);
        ITagParseFactory factory = ConfigContainer.getTagParseFactory(channel);
        String cachePath = factory.getTagCachePath(tagPageId[0], tagPageId[1], channel, plat);
        File file = new File(cachePath);

        // 如果是json
        if (channel.contains("json")) {
            response.setContentType("text/json; charset=utf-8");
        } else {
            response.setContentType("text/html; charset=utf-8");
        }

        if (file.exists()) {
            setUseCache(response, true);
            displayFile(response, file);
            return;
        } else {
            setUseCache(response, false);
            try {
                String html = factory.parseTagList(tagPageId[0], tagPageId[1], channel, plat);
                displayHtml(response, html);
                return;
            } catch (Exception e) {
                LogService.errorSystemLog("Error when parse tagId which name is " + tagPageId[0], e);
                chain.doFilter(req, resp);
            }
        }

    }

    private int[] getTagPageId(String uri) {
        int tagId = 0;
        int pageId = 1;
        int position = uri.lastIndexOf("/");
        if (position > 0) {
            String tmp = uri.substring(position + 1, uri.length());
            int position1 = tmp.indexOf(".");
            if (position1 > 0) {
                String tagPageStr = tmp.substring(0, position1);
                int position2 = tagPageStr.indexOf("_");
                if (position2 > 0) {
                    tagId = Integer.parseInt(tagPageStr.substring(0, position2));
                    pageId = Integer.parseInt(tagPageStr.substring(position2 + 1, tagPageStr.length()));
                } else {
                    tagId = Integer.parseInt(tagPageStr);
                }
            }
        }
        return new int[]{tagId, pageId};
    }

    private void doArchiveList(ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletRequest request, HttpServletResponse response,
                               String uri) throws Exception {
        int[] pageIdArray = getArchiveListPageId(uri);

        String channel = ChannelService.getChannel((HttpServletRequest) req);
        IArchiveListFactory factory = ConfigContainer.getArchiveListFactory(channel);
        String cachePath = factory.getPath(channel, pageIdArray[0], pageIdArray[1]);
        File file = new File(cachePath);

        // 如果是json
        if (channel.contains("json")) {
            response.setContentType("text/json; charset=utf-8");
        } else {
            response.setContentType("text/html; charset=utf-8");
        }

        if (file.exists()) {
            setUseCache(response, true);
            displayFile(response, file);
            return;
        } else {
            setUseCache(response, false);
            try {
                String html = facade.getArchiveList(request, pageIdArray[0], pageIdArray[1], cachePath);
                displayHtml(response, html);
                return;
            } catch (Exception e) {
                LogService.errorSystemLog("Error when parse tagId which name is " + pageIdArray[0], e);
                chain.doFilter(req, resp);
            }
        }

    }

    private int[] getArchiveListPageId(String uri) {
        int tagId = 0;
        int pageId = 1;
        int position = uri.lastIndexOf("/");
        if (position > 0) {
            String tmp = uri.substring(position + 1, uri.length());
            int position1 = tmp.indexOf(".");
            if (position1 > 0) {
                String tagPageStr = tmp.substring(0, position1);
                int position2 = tagPageStr.indexOf("_");
                if (position2 > 0) {
                    tagId = Integer.parseInt(tagPageStr.substring(0, position2));
                    pageId = Integer.parseInt(tagPageStr.substring(position2 + 1, tagPageStr.length()));
                } else {
                    tagId = Integer.parseInt(tagPageStr);
                }
            }
        }
        return new int[]{tagId, pageId};
    }

    private int[] getPictorialArchiveListPageId(String uri) {
        int category = 0; //类别：
        int tagId = 0;//标签
        int pageId = 1;//页码
        int position = uri.lastIndexOf("/");
        if (position > 0) {
            String tmp = uri.substring(position + 1, uri.length());
            int position1 = tmp.indexOf(".");
            if (position1 > 0) {
                String tagPageStr = tmp.substring(0, position1);
                String arr[] = tagPageStr.split("_");
                if (arr.length == 3) {
                    category = Integer.parseInt(arr[0]);
                    tagId = Integer.parseInt(arr[1]);
                    pageId = Integer.parseInt(arr[2]);
                } else if (arr.length == 2) {
                    category = Integer.parseInt(arr[0]);
                    tagId = Integer.parseInt(arr[1]);
                } else if (arr.length == 1) {
                    category = Integer.parseInt(arr[0]);
                }
            }
        }
        return new int[]{category, tagId, pageId};
    }

    private void doArticleType(ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletRequest request, HttpServletResponse response,
                               String uri, String plat) throws Exception {
        int position = uri.lastIndexOf("/");
        if (position > 0) {
            String fileName = urlService.getArchiveFileName(uri, position);
            int position1 = fileName.indexOf("_");
            if (position1 > 0) {
                fileName = fileName.substring(0, position1);
            }

            String filePath = getArchiveFilePath(uri, request);
            String channel = ChannelService.getChannel(request);

            String archiveFilePath = ConfigContainer.getHtmlParseFactory(channel).getArchiveCachePath(filePath, fileName, channel, plat);
            File file = new File(archiveFilePath);

            // 如果是json
            if (channel.contains("json")) {
                response.setContentType("text/json; charset=utf-8");
            } else {
                response.setContentType("text/html; charset=utf-8");
            }

            if (file.exists()) {
                setUseCache(response, true);
                displayFile(response, file);
                return;
            } else {
                setUseCache(response, false);
                int archiveId = facade.getArchiveId(uri);
                if (archiveId == 0) {
                    chain.doFilter(req, resp);
                    return;
                }

                try {
                    String html = facade.generateArticleHtml(request, response, archiveId, filePath, fileName, plat, uri);
                    displayHtml(response, html);
                    return;
                } catch (Exception e) {
                    LogService.errorSystemLog("Error when parse article which archiveId is :" + archiveId + " and filePath is :" + filePath
                            + " and fileName is : " + fileName, e);
                    chain.doFilter(req, resp);
                }
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    /**
     * 获得文章的path
     *
     * @param uri
     * @param request
     * @return
     */
    private String getArchiveFilePath(String uri, HttpServletRequest request) {
        String content = uri.split("/")[1];
        //String content = request.getContextPath();
        String temp = uri.replaceAll(content, "");
        temp = StringUtil.removeStartCharacter(temp, "/");
        int position = temp.lastIndexOf("/");
        temp = temp.substring(0, position);
        return temp;
    }

    /**
     * 处理专区页
     *
     * @param
     * @param resp
     * @param chain
     * @param response
     * @param url
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ServletException
     */
    private void doGameType(ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, String url) throws Exception,
            FileNotFoundException, ServletException {
        int position = url.lastIndexOf("/");
        String htmlFile = url.substring(position + 1, url.length()).replaceAll(".html", "");
        String channel = ChannelService.getChannel((HttpServletRequest) req);
        String path = ConfigContainer.getHtmlParseFactory(channel).getGameCachePath(htmlFile, channel);
        File file = new File(path);

        // 如果是json
        if (channel.contains("json")) {
            response.setContentType("text/json; charset=utf-8");
        } else {
            response.setContentType("text/html; charset=utf-8");
        }

        if (file.exists()) {
            setUseCache(response, true);
            displayFile(response, file);
            return;
        } else {
            setUseCache(response, false);
            Connection conn = null;
            try {
                conn = dao.getConnection();
                HttpServletRequest request = (HttpServletRequest) req;
                String[] channels = new String[]{ChannelService.getChannel(request)};

                String localPath = ConfigContainer.getLocalPath(request);
                String html = facade.generateSpecHtml(conn, htmlFile, channels, localPath);
                if (html != null) {
                    displayHtml(response, html);
                    return;
                } else
                    chain.doFilter(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                chain.doFilter(req, resp);
                return;
            } finally {
                dao.closeConnection(conn);
            }
        }
    }

    private void setUseCache(HttpServletResponse response, boolean isUseCache) {
        response.setHeader("use-cache", isUseCache + "");
    }


    private void doArticleTypeList(ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, String url) throws Exception,
            FileNotFoundException, ServletException {
        int position = url.lastIndexOf("/");
        String htmlFile = url.substring(position + 1, url.length()).replaceAll(".html", "");
        String channel = ChannelService.getChannel((HttpServletRequest) req);
        String path = ConfigContainer.getArticleTypeParseFactory(channel).getArticleTypePath(htmlFile, channel);

        // 如果是json
        if (channel.contains("json")) {
            response.setContentType("text/json; charset=utf-8");
        } else {
            response.setContentType("text/html; charset=utf-8");
        }

        File file = new File(path);
        if (file.exists()) {
            setUseCache(response, true);
            displayFile(response, file);
            return;
        } else {
            setUseCache(response, false);
            Connection conn = null;
            try {
                conn = dao.getConnection();
                HttpServletRequest request = (HttpServletRequest) req;

                String localPath = ConfigContainer.getLocalPath(request);
                String html = articleTypeFacade.generateArticleTypeHtml(htmlFile, channel, url, localPath);
                if (html != null) {
                    displayHtml(response, html);
                    return;
                } else
                    chain.doFilter(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                chain.doFilter(req, resp);
                return;
            } finally {
                dao.closeConnection(conn);
            }
        }
    }


    private void doCategoryTagList(ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, String url) throws Exception {
        String channel = ChannelService.getChannel((HttpServletRequest) req);
        String path = ConfigContainer.getCategoryParseFactory(channel).getCategoryTagsPath(channel);


        ////test  start
        HttpServletRequest request2 = (HttpServletRequest) req;
        StringBuffer param = new StringBuffer();
        param.append("requestUrl=" + request2.getRequestURI() + ",");
        param.append("method=" + request2.getMethod() + ",");
        Enumeration pNames = request2.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            String value = request2.getParameter(name);
            param.append(name + "=" + value + ",");
        }
        LogService.infoSystemLog("param=======:" + param.toString());
        System.out.println("param=======:" + param.toString());
        ///test end

        // 如果是json
        if (channel.contains("json")) {
            response.setContentType("text/json; charset=utf-8");
        } else {
            response.setContentType("text/html; charset=utf-8");
        }

        File file = new File(path);
        if (file.exists()) {
            setUseCache(response, true);
            displayFile(response, file);
            return;
        } else {
            setUseCache(response, false);
            Connection conn = null;
            try {
                conn = dao.getConnection();
                HttpServletRequest request = (HttpServletRequest) req;
                String html = facade.generateDedeCategoryTagsHtml(request, channel, path);
                if (html != null) {
                    displayHtml(response, html);
                    return;
                } else
                    chain.doFilter(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                chain.doFilter(req, resp);
                return;
            } finally {
                dao.closeConnection(conn);
            }
        }
    }

    private void doCategoryArchivesList(ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, String url) throws Exception {
        String channel = ChannelService.getChannel((HttpServletRequest) req);

        int[] categoryIdArray = getPictorialArchiveListPageId(url);

        String path = ConfigContainer.getCategoryParseFactory(channel).getArticleListPath(channel, categoryIdArray[0], categoryIdArray[1], categoryIdArray[2]);

        // 如果是json
        if (channel.contains("json")) {
            response.setContentType("text/json; charset=utf-8");
        } else {
            response.setContentType("text/html; charset=utf-8");
        }

        File file = new File(path);
        if (file.exists()) {
            setUseCache(response, true);
            displayFile(response, file);
            return;
        } else {
            setUseCache(response, false);
            Connection conn = null;
            try {
                conn = dao.getConnection();
                HttpServletRequest request = (HttpServletRequest) req;
                String html = facade.generateDedeCategoryArticleHtml(request, categoryIdArray[0], categoryIdArray[1], categoryIdArray[2], channel, path);
                if (html != null) {
                    displayHtml(response, html);
                    return;
                } else
                    chain.doFilter(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                chain.doFilter(req, resp);
                return;
            } finally {
                dao.closeConnection(conn);
            }
        }
    }


    /**
     * 分析path
     *
     * @param request
     * @param url
     * @return 属于哪种path
     */
    private int parsePath(HttpServletRequest request, String url) {
        String content = request.getContextPath();
        url = url.replaceAll(content, "");
        url = StringUtil.removeStartCharacter(url, "/");
        String[] strs = url.split("/");

        if (strs.length < 2)
            return DEFAULT_PATH;

        if (strs[1].equals(GAME_FLAG))
            return GAME_PATH;
        else if (strs[1].equals(ARTICLE_LIST_FLAG))
            return ARTICLE_LIST_PATH;
        else if (strs[1].equals(TAG_LIST_FLAG))
            return TAG_LIST_PATH;
        else if (strs[1].equals(CATEGORY_TAG_FLAG))
            return CATEGORY_TAG_PATH;
        else if (strs[1].equals(CATEGORY_ARCHIVES_FLAG))
            return CATEGORY_ARCHIVES_PATH;
        else if (url.endsWith(".html")) {
            String str = strs[strs.length - 1];
            if (str.contains("list_") || str.equals("index.html")) {
                return ARTICLE_TYPE_LIST_PATH;
            }
            return ARTICLE_PATH;
        } else
            return DEFAULT_PATH;

//        if (strs[0].equals(GAME_FLAG))
//            return GAME_PATH;
//        else if (strs[0].equals(ARTICLE_LIST_FLAG))
//            return ARTICLE_LIST_PATH;
//        else if (strs[0].equals(TAG_LIST_FLAG))
//            return TAG_LIST_PATH;
//        else if (strs[0].equals(CATEGORY_TAG_FLAG))
//            return CATEGORY_TAG_PATH;
//        else if(strs[0].equals(CATEGORY_ARCHIVES_FLAG))
//            return CATEGORY_ARCHIVES_PATH;
//        else if (url.endsWith(".html")) {
//            String str = strs[strs.length - 1];
//            if (str.contains("list_") || str.equals("index.html")) {
//                return ARTICLE_TYPE_LIST_PATH;
//            }
//            return ARTICLE_PATH;
//        } else
//            return DEFAULT_PATH;

    }

    /**
     * 显示
     *
     * @param response
     * @param file
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void displayFile(HttpServletResponse response, File file) throws IOException, FileNotFoundException {
        response.setCharacterEncoding("utf-8");
        FileInputStream is = new FileInputStream(file);

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

    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

}
