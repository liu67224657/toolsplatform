package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.SystemUtil;
import com.enjoyf.util.URLUtil;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.tools.UnicodeUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Iterator;

public class URLService {

    private final static String ENCODING = "utf-8";
    private static WikiPageService pageService = new WikiPageService();
    private static SystemUtil su = new SystemUtil();
    private static URLUtil urlUtil = new URLUtil();
//    private static final String BAIDU_FLAG = "<meta name=\"robots\" content=\"nofollow\" /> ";

    public Document getDocumentByUrl(String html) throws IOException {
        return Jsoup.parse(html);
    }

    public String getHTML(String pageURL) {
        StringBuilder pageHTML = new StringBuilder();
        try {
            URL url = new URL(pageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30000);
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
     * 通过unicode获得url
     * 
     * @param urlEncodeString
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getUnicodeByURLEncode(String urlEncodeString) throws UnsupportedEncodingException {
        String str = URLDecoder.decode(urlEncodeString, ENCODING);
        return UnicodeUtil.StringToUnicodeWithoutU(str);
    }

    /**
     * 得到普通的unicode
     * 
     * @param str
     * @return
     * @throws Exception
     */
    private String getNormalUnicode(String str) throws Exception {
        if (str.length() % 4 != 0) {
            throw new Exception("URL is invalid which str is : " + str);
        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0;; i++) {
            int begin = i * 4;
            int end = (i + 1) * 4;
            if (begin >= str.length())
                break;

            sb.append("\\u").append(str.substring(begin, end));
        }
        return sb.toString();
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

    public String getURLEncodingByUnicode(String unicodeString) throws Exception {
        unicodeString = this.getNormalUnicode(unicodeString);
        String temp = UnicodeUtil.UnicodeToString(unicodeString);
        return URLEncoder.encode(temp, ENCODING);
    }

    /**
     * 替换url到wikipageId 和首页
     * 
     * @param domain
     * @param path
     * @param key
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public String replaceURLtoWikePageId(String domain, String path, String key, String url) throws UnsupportedEncodingException, JoymeDBException,
            JoymeServiceException {
        String domainPath = domain + path;
        url = getReplaceURL(key, url, domainPath);
        url = getReplaceURL(key, url, path);
        // 替换首页
        url = this.getIndexPage(domain, key, url);
        return url;
    }

    // 这个是用unicode的方式，url很长
    // private String getReplaceURL(String url , String flag) throws
    // UnsupportedEncodingException {
    // if(url.startsWith(flag)){
    // String temp = url.substring(flag.length(), url.length());
    // String unicode = this.getUnicodeByURLEncode(temp);
    // return flag + unicode;
    // }
    // return url;
    // }

    private String getIndexPage(String domain, String key, String url) {
        String targetUrl = domain + "/";
        if (url.equals(targetUrl)) {
            return "./index.shtml";
        }
        return url;
    }

    private String getReplaceURL(String key, String url, String flag) throws UnsupportedEncodingException, JoymeDBException, JoymeServiceException {
        if (url.startsWith(flag)) {
            String urlkey = url.substring(flag.length(), url.length());
            String prefix = "";
            int position = urlkey.indexOf("#");
            // 除去锚点
            String temp = "";
            if (position > 0) {
                temp = urlkey.substring(0, position);
                prefix = urlkey.substring(position, urlkey.length());
            } else {
                temp = urlkey;
            }

            String chineseURLKey = URLDecoder.decode(temp, "utf-8");

            //去掉模版和特殊页面
            if (chineseURLKey.startsWith("模板:") || chineseURLKey.startsWith("特殊:") || chineseURLKey.startsWith("文件:")) {
                LogService.infoSystemLog("排除:" + chineseURLKey);
                return "#";
            } else if(chineseURLKey.equals("首页")){
                LogService.infoSystemLog("排除原始首页" + chineseURLKey);
                return "./index.shtml";
            }
            else {
                long wikiPageId = pageService.getWikiPageIdByChineseURL(null, key, chineseURLKey);
                // String unicode = this.getUnicodeByURLEncode(urlkey);
                return wikiPageId + ".shtml" + prefix;
            }
        }
        return url;
    }

    public String changeImageUrl(String domain, String url) {
        if (!url.startsWith("http://")) {
            // url = "http://mt.joyme.com" + url;
            url = domain + url;
        }
        return url;
    }

    /**
     * @param domain
     * @param path
     * @param key
     * @param url
     * @return
     * @throws Exception
     */
    public String parseHTML(String domain, String path, String key, String url, String saveFilePath,String wikiType) throws Exception {
//        String html = this.getHTML(url);
        String html = urlUtil.openURLWithTimeOut(url);
       
        Document doc = this.getDocumentByUrl(html);
       
        // 改href
        replaceURL(domain, path, key, doc);
        
        LogService.infoSystemLog("====改href成功=======");

        // 改图片
        replaceImage(domain, doc);
        
        LogService.infoSystemLog("====改图片成功=======");

        // add jquery
        addJquery(doc);
        
        LogService.infoSystemLog("====add jquery 成功=======");
        
        removePhpFile(doc);

        // 替换css
        replaceCss(key, doc,wikiType);
        
      //替换搜索框
        changeSearchInputBox(key, doc.getElementsByTag("form"));
        LogService.infoSystemLog("====替换搜索框 成功=======");

        // 去掉no-follow ,特殊和模版不去
        String wikiKey = getWikiKey(url);
        replaceNoFollow(doc, wikiKey);
        LogService.infoSystemLog("====去掉norobots 成功=======");
        
        String docHtml = doc.html();
        
        //去掉<img的\r\n
        StringBuffer retHtml = removeImgNextLine(docHtml, saveFilePath);
        return retHtml.toString();
    }

    private void replaceNoFollow(Document doc, String wikiKey) {
        Elements elements;
        if(!wikiKey.startsWith("模板:") && !wikiKey.startsWith("特殊:") && !wikiKey.startsWith("文件:")){
            elements = doc.getElementsByTag("meta"); 
            for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
                Element element = (Element) iterator.next();
                removeElement(element , "noindex");
                removeElement(element , "nofollow");
                removeElement(element , "noarchive");
            }
        }
    }

    private void replaceCss(String key, Document doc,String wikiType) throws IOException, Exception {
        Elements elements;
        elements = doc.getElementsByTag("link");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            if (element.attr("rel").equals("stylesheet")) {
                String href = element.attr("href");
                if (href.indexOf("load.php") >= 0) {
                    String cssPath = su.getWebRootPath() + "/css/" + key + "/style.css";
                    File file = new File(cssPath);
                    if (!file.exists()) {
                        makeCssFile(href, cssPath, file);
                    }
                    element.attr("href", "../css/" + key + "/style.css");
                }
                LogService.infoSystemLog("====替换 css 成功=======");
            }
            
            if (element.attr("rel").equals("shortcut icon")) {
            	String href = element.attr("href");
            	element.attr("href", PropertiesContainer.getInstance().getDomain(key,wikiType) + href);
            }
        }
    }

    private void addJquery(Document doc) {
        Elements elements;
        if (doc.html().indexOf("jquery-1.9.1.min.js") < 0) {
            elements = doc.getElementsByTag("head");
            if (elements.size() >= 1) {
                Element element = elements.get(0);
                Element jqueryElement = element.appendElement("script");
                jqueryElement.attr("src", "../js/jquery-1.9.1.min.js");
                jqueryElement.attr("language", "javascript");
            }
        }
    }

    private void replaceImage(String domain, Document doc) {
        Elements elements;
        elements = doc.getElementsByTag("img");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            String src = element.attr("src");
            element.attr("src", this.changeImageUrl(domain, src));
        }
    }

    private void replaceURL(String domain, String path, String key, Document doc) throws UnsupportedEncodingException, JoymeDBException,
            JoymeServiceException {
        Elements elements = doc.getElementsByTag("a");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            String href = element.attr("href");
            // element.attr("href", "ass");
            String temp = this.replaceURLtoWikePageId(domain, path, key, href);
            element.attr("href", temp);
        }
    }

	/**
	 * 去掉shtml中的php文件，减少调用时间
	 * @param doc
	 */
	private void removePhpFile(Document doc) {
	    Elements elements = doc.getElementsByTag("link");
	    for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
	        Element element = (Element) iterator.next();
	        if(element.attr("rel").equals("EditURI") && element.attr("href").indexOf(".php") > 0){
	            LogService.infoSystemLog("=====remove editurl ======");
	            element.remove();
	        }
	        if(element.attr("rel").equals("search")){
	            LogService.infoSystemLog("=====remove search========");
	            element.remove();
	        }
	        if(element.attr("rel").equals("alternate")){
	            LogService.infoSystemLog("=====remove alternate========");
	            element.remove();
	        }
	    }
	    
	    elements = doc.getElementsByTag("script");
	    for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
	        Element element = (Element) iterator.next();
	        if(element.attr("src").indexOf("load.php") > 0){
	            LogService.infoSystemLog("=====remove load.php script ======");
                element.remove();
            }
	    }
    }

    private void removeElement(Element element , String value) {
		if(element.attr("name").equals("robots") && element.attr("content").equals(value)){
		    element.remove();
		}
	}

    private void changeSearchInputBox(String key, Elements elements) {
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            if(element.attr("action").equals("/index.php") && element.attr("id").equals("searchform")){
                //改action
//                element.attr("action" , "./search/"+key+"/search.jsp");
                element.attr("action" , "../search/search.jsp");
                element.attr("method" , "get");
            
                //增加一个key的hidden
                Element childElement = element.appendElement("input");
                childElement.attr("type" , "hidden");
                childElement.attr("name" , "key");
                childElement.attr("value" , key);
                
                //增加一个pageNum的hidden
                childElement = element.appendElement("input");
                childElement.attr("type" , "hidden");
                childElement.attr("name" , "pageNum");
                childElement.attr("value" , "1");
                
                //增加一个pageCount的hidden
                childElement = element.appendElement("input");
                childElement.attr("type" , "hidden");
                childElement.attr("name" , "pageCount");
                childElement.attr("value" , "10");
            }
        }
    }

    private String getWikiKey(String url) throws UnsupportedEncodingException {
        String wikiKey = "";
        int position = url.lastIndexOf("/");
        if(position >= 0 ){
            wikiKey = url.substring(position+1 , url.length());
            wikiKey = URLDecoder.decode(wikiKey , "utf-8");
        }
        return wikiKey;
    }

    /**
     * 去掉<img的\r\n
     * @param docHtml
     * @return
     * @throws IOException
     */
    private StringBuffer removeImgNextLine(String docHtml , String saveFilePath) throws IOException {
        String tempPath = saveFilePath + ".tmp";
        this.writeFile(tempPath, docHtml);
        
        InputStream is = null;
        StringBuffer retHtml = new StringBuffer();
        BufferedReader br = null;
        try {
            is = new ByteArrayInputStream(docHtml.getBytes());
            br = new BufferedReader(new InputStreamReader(is));
            //中文标点变成? ByteArrayInputStream的问题
//            is = new FileInputStream(tempPath);
//            br = new BufferedReader(new InputStreamReader(is , "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                String s = line;
                if (!line.trim().startsWith("<img")) {
                    s = line + "\r\n";
                } else {
                    s = s.trim();
                }
                retHtml.append(s);
            }

        } finally {
            if (br != null) {
                br.close();
                br = null;
            }
            if (is != null) {
                is.close();
                is = null;
            }
            if(new File(tempPath).exists()){
                new File(tempPath).delete(); 
            }
        }
        return retHtml;
    }

    /**
     * 创建css
     * 
     * @param href
     * @param cssPath
     * @param file
     * @throws IOException
     */
    private void makeCssFile(String href, String cssPath, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        String cssHtml = this.getHTML(href);
        this.writeFile(cssPath, cssHtml);
    }

    /**
     * @param conn
     * @param domain
     *            域名，没有最后的/
     * @param path
     *            /wiki/
     * @param saveFilePath
     *            保存的地址
     * @param key
     *            mt, mkhx...
     * @param url
     *            http://mt.joyme.com/wiki/****
     * @return
     * @throws Exception
     * @throws Exception
     */
    public String parseURLAndSave(Connection conn, String domain, String path, String saveFilePath, String key, String url,String wikiType) throws Exception,
            Exception {
        LogService.infoSystemLog("crwal:" + url);
        String html = this.parseHTML(domain, path, key, url, saveFilePath,wikiType);
        this.writeFile(saveFilePath, html);
        return html;
    }

//    public static void main(String[] args) throws Exception {
//        URLService service = new URLService();
//        String html = service.getHTML("http://j0yme.com/aRa1W");
//        System.out.println(html);
//
//        // Pattern
//        // pattern=Pattern.compile("<a((?!href).)href="\\[^"\\]"\\[^>\\]>");
//        // Pattern pattern2 =
//        // Pattern.compile("<a((?!href).)href="\\[^"\\]"\\[^>\\]>");
//    }
}
