package com.enjoyf.wiki.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;


public class PatternURLService {
    private static WikiPageService pageService = new WikiPageService();
//    private static final Pattern LINKE_PATTERN = Pattern.compile("<a[^>]+>[^<]*</a>", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
    
    /**
     * 入口
     * @param conn
     * @param domain
     * @param path
     * @param saveFilePath
     * @param key
     * @param url
     * @return
     * @throws Exception
     * @throws Exception
     */
    public String parseURLAndSave(Connection conn,String domain , String path ,  String saveFilePath, String key, String url) throws Exception, Exception {
        String html = this.parseHTML(domain , path , key, url);
        this.writeFile(saveFilePath, html);
        return html;
    }
    
    /**
     * 获取当前网页
     * @param pageURL
     * @return
     */
    
    
    public String getHTML(String pageURL) {
        StringBuilder pageHTML = new StringBuilder();
        try {
            URL url = new URL(pageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                pageHTML.append(line);
                pageHTML.append("\r\n");
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageHTML.toString();
    }
    
    /**
     * 分析网页
     * @param domain
     * @param path
     * @param key
     * @param url
     * @return
     * @throws Exception
     */
    public String parseHTML(String domain ,String path, String key, String url) throws Exception {
        URLService service = new URLService();
        String html = service.getHTML(url);
        
        // 改href
//        Matcher linkMatcher = LINKE_PATTERN.matcher(html);
//        while (linkMatcher.find()) {
//            String linkHtml = linkMatcher.group(0);
//            Document doc = Jsoup.parse(linkHtml);
//            Element linkElement = doc.getElementsByTag("a").get(0);
//            
//            String href = linkElement.attr("href");
////            String temp = this.replaceURLtoWikePageId(domain , path , key, href);
////            
////            linkElement.attr("href", temp);
////            html = html.replace(linkHtml, linkElement.outerHtml());
//        }
        
        return html;
    }
    
    /**
     * 替换url到wikipageId 和首页
     * @param domain
     * @param path
     * @param key
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public String replaceURLtoWikePageId(String domain, String path ,String key , String url) throws UnsupportedEncodingException, JoymeDBException,
            JoymeServiceException {
        String domainPath = domain + path;
        url = getReplaceURL(key, url, domainPath);
        url = getReplaceURL(key, url, path);
        //替换首页
        url = this.getIndexPage(domain ,key, url);
        return url;
    }
    
    private String getReplaceURL(String key, String url, String flag) throws UnsupportedEncodingException, JoymeDBException, JoymeServiceException {
        if (url.startsWith(flag)) {
            String urlkey = url.substring(flag.length(), url.length());
            String chineseURLKey = URLDecoder.decode(urlkey, "utf-8");

            long wikiPageId = pageService.getWikiPageIdByChineseURL(null, key, chineseURLKey);

            // String unicode = this.getUnicodeByURLEncode(urlkey);
            return wikiPageId + ".shtml";
        }
        return url;
    }
    
    public void writeFile(String path, String html) throws IOException {
        File file = new File(path);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        OutputStreamWriter write = new OutputStreamWriter(fileOutputStream, "UTF-8");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new BufferedWriter(write));
            writer.write(html);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
    
    
    /**
     * 改首页
     * @param domain
     * @param key
     * @param url
     * @return
     */
    private String getIndexPage(String domain , String key , String url ){
        String targetUrl = domain + "/";
        if(url.equals(targetUrl)){
            return "./index.shtml";
        }
        return url;
    }
    
    public static void main(String[] args) throws Exception {
        PatternURLService service = new PatternURLService();
        service.parseHTML("http://ma.joyme.com", "/wiki/", "ma", "http://ma.joyme.com");
    }
}
