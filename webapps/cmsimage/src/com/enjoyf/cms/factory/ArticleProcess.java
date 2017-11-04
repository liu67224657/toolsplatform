package com.enjoyf.cms.factory;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.service.ErrorLogService;
import com.enjoyf.cms.util.CmsimageFilePathUtil;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/9/18.
 */
public class ArticleProcess extends AbstractProcess {
    @Override
    public void process(String requestURL, ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        URL url = new URL(requestURL);
        String host = url.getHost();
        String path = url.getPath();
        if(path.endsWith("/")){
            requestURL += "index.html";
        }else {
            if(!path.contains(".html")){
                requestURL += "/index.html";
            }
        }
        url = new URL(requestURL);
        path = url.getPath();

        boolean isSeoOpen = false;
        String transferPath = null;
        String originalPath = null;
        //读取配置数据
        Map<String, String> map = CmsimageFilePathUtil.genOriginalPath(requestURL);
        //article域名源地址
        String srcUrl = "";
        if (map != null) {
            transferPath = map.get("transferPath");
            originalPath = map.get("originalPath");
            srcUrl = requestURL.replace(transferPath, originalPath);
            isSeoOpen = true;
        } else {
            //旧逻辑
            srcUrl = genOldRule(url);
            transferPath = requestURL;
            originalPath = srcUrl;
        }
        String cacheFilePath = HtmlFactory.getFactory(host).getCacheFile(path, isSeoOpen, host);
        File cacheFile = new File(cacheFilePath);
        //判断是否需要重新抓取
        boolean needRefresh = checkNeedRefreshCache(cacheFile);
        if (needRefresh) {
            String referer = request.getHeader("referer");
            //抓取article
            Map<String, Object> result = URLUtil.openURLConnectionWithTimeOut(srcUrl, response);
            if (result != null) {
                //保存
                saveFile(result, host, requestURL, srcUrl, isSeoOpen, transferPath, originalPath, req, resp, request, response, chain);
            } else {
                //没抓到 404
                recordErrorLog(srcUrl, referer);
                response.setStatus(404);
                chain.doFilter(req, resp);
                return;
            }
        } else {
            displayFile(response, cacheFile);
            return;
        }
    }

    private void saveFile(Map<String, Object> result, String host, String requestURL, String srcUrl, boolean isSeoOpen, String transferPath, String originalPath, ServletRequest req, ServletResponse resp, HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        int responseCode = 404;
        if (result.get("responseCode") != null) {
            responseCode = (Integer) result.get("responseCode");
        }
        if (responseCode == 200) {
            String responseText = (String) result.get("responseText");
            //解析 并 保存
            String html = HtmlFactory.getFactory(host).saveURLFile(responseText, requestURL, srcUrl, host, isSeoOpen, transferPath, originalPath);
            if (!StringUtil.isEmpty(html)) {
                //输出
                this.displayHtml(response, html);
                return;
            } else {
                //解析 html内容出错！ 500
                response.setStatus(500);
                chain.doFilter(req, resp);
                return;
            }
        } else {
            //状态码 不是200
            response.setStatus(500);
            chain.doFilter(req, resp);
            return;
        }
    }

    private boolean checkNeedRefreshCache(File cacheFile) {
        //没缓存  需要抓取
        if (!cacheFile.exists()) {
            return true;
        }
        //缓存超过 一天   需要更新缓存
        if (cacheFile.lastModified() != 0 && System.currentTimeMillis() - cacheFile.lastModified() > PropertiesContainer.DISABLE_TIME) {
            return true;
        }
        //如果缓存文件没有有效的内容  重新抓
        if (cacheFile.length() != 0 && cacheFile.length() < 100) {
            return true;
        }
        return false;
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

    /**
     * 显示
     *
     * @param response
     * @param file
     * @throws IOException
     * @throws java.io.FileNotFoundException
     */
    private void displayFile(HttpServletResponse response, File file) throws IOException, FileNotFoundException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
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

    private void recordErrorLog(String url, String referer) {
        new ErrorLogService().saveErrorLog(url, referer);
    }
}
