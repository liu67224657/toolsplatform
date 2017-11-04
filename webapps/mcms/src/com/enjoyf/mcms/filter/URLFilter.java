package com.enjoyf.mcms.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enjoyf.framework.jdbc.BaseJDBCDAO;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.mcms.facade.ArchiveFacade;
import com.enjoyf.mcms.factory.IHtmlParseFactory;
import com.enjoyf.mcms.factory.impl.DefaultParseFactoryImpl;
import com.enjoyf.mcms.service.ChannelService;
import com.enjoyf.mcms.service.URLService;
import com.enjoyf.util.StringUtil;

public class URLFilter implements Filter {

    private static ArchiveFacade facade = new ArchiveFacade();
    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();
//    private static HtmlService htmlService = new HtmlService();
    private static IHtmlParseFactory htmlFactory = new DefaultParseFactoryImpl();

    private final static int DEFAULT_PATH = 1;
    private final static int GAME_PATH = 2;
    private final static int ARTICLE_PATH = 3;
    private final static int ARTICLE_LIST_PATH = 4;

    private final static String GAME_FLAG = "game";
    public final static String ARTICLE_LIST_FLAG = "archivelist";
    private final static URLService urlService = new URLService();

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();
        int pathType = this.parsePath(request, uri);

        // 判断专题
        try {
            if (pathType == GAME_PATH) {
                doGameType(req, resp, chain, response, uri);
            } else if (pathType == ARTICLE_PATH) {
                doArticleType(req, resp, chain, request, response, uri);
            } else if (pathType == ARTICLE_LIST_PATH) {
                chain.doFilter(req, resp);
            } else {
                chain.doFilter(req, resp);
            }
        } catch (Exception e) {
            chain.doFilter(req, resp);
        }
        //        
    }

    private void doArticleType(ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletRequest request, HttpServletResponse response,
            String uri) throws Exception {
        int position = uri.lastIndexOf("/");
        if (position > 0) {
            String fileName = urlService.getArchiveFileName(uri, position);
            String filePath = getArchiveFilePath(uri, request);
            String channel = ChannelService.getChannel(request);
            String archiveFilePath = htmlFactory.getArchiveCachePath(filePath, fileName, channel);
            File file = new File(archiveFilePath);
            
            //如果是json
            if(channel.equals("json")){
                response.setContentType("text/json; charset=utf-8");
            }
            
            if (file.exists()) {
                displayFile(response, file);
                return;
            } else {
                int archiveId = urlService.getArchiveId(uri, fileName);
                if (archiveId == 0) {
                    chain.doFilter(req, resp);
                    return;
                }

                try {
                    String html = facade.generateArticleHtml(request, response, archiveId, filePath, fileName);
                    displayHtml(response, html);
                    return;
                } catch (Exception e) {
                    System.out.println("Error when parse article which archiveId is :" + archiveId + " and filePath is :" + filePath
                            + " and fileName is : " + fileName);
                    e.printStackTrace();
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
        String content = request.getContextPath();
        String temp = uri.replaceAll(content, "");
        temp = StringUtil.removeStartCharacter(temp, "/");
        int position = temp.lastIndexOf("/");
        temp = temp.substring(0, position);
        return temp;
    }

    

    /**
     * 处理专区页
     * 
     * @param arg0
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
        String path = htmlFactory.getGameCachePath(htmlFile, channel);

        File file = new File(path);
        if (file.exists()) {
            displayFile(response, file);
            return;
        } else {
            Connection conn = null;
            try {
                conn = dao.getConnection();
                HttpServletRequest request = (HttpServletRequest) req;
                String[] channels = new String[] { ChannelService.getChannel(request) };
                String html = facade.generateSpecHtml(conn, htmlFile, request, channels);
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

        if (strs[0].equals(GAME_FLAG))
            return GAME_PATH;
        else if (strs[0].equals(ARTICLE_LIST_FLAG))
            return ARTICLE_LIST_PATH;
        else if (url.endsWith(".html"))
            return ARTICLE_PATH;
        else
            return DEFAULT_PATH;

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
