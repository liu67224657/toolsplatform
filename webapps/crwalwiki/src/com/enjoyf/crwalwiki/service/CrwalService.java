package com.enjoyf.crwalwiki.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.enjoyf.crwalwiki.container.ConfigContainer;
import com.enjoyf.crwalwiki.util.FileUtil;
import com.enjoyf.crwalwiki.util.ImageUtil;
import com.enjoyf.crwalwiki.util.URLUtil;
import com.enjoyf.crwalwiki.util.ZipUtil;

public class CrwalService {
    private ImageUtil util = new ImageUtil();
    private static URLUtil urlUtil = new URLUtil();
    private String saveFolderRoot = ConfigContainer.prop.getProperty("saveFolder");
    private String saveFolder = null;
    private String cacheFolder = ConfigContainer.prop.getProperty("cacheFolder");
    private String domain = ConfigContainer.prop.getProperty("domain");
    private String key = null;
    public String zipFile = null;

    private Map map = new HashMap();

    public boolean doAllPageCrwal(String key, int id) throws Exception {
        try {
            this.key = key;
            this.saveFolder = saveFolderRoot + "/" + id;
            String path = cacheFolder + "/" + key + "/index.shtml";
            File indexFile = new File(path);
            if (!indexFile.exists())
                throw new Exception("Can't find the index.shtml");

            // // save index.shtml
            // copyIndex(indexFile);

            String html = FileUtil.readFile(path, "utf-8");

            String savePath = saveFolder + "/index.shtml";

            parseHtml(savePath, html);

            System.out.println("=====parse html over=====");
            System.out.println("=====map size:" + map.size() + "=======");
            parseMap(map);
            System.out.println("=====parse map over=====");
            zipFile = saveFolderRoot + "/" + id + ".zip";
            ZipUtil.zip(zipFile, key+"_cache/", saveFolder);
            System.out.println("=====zip file success=====");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void parseHtml(String saveFilePath, String html) {
        System.out.println("begin to parse file:" + saveFilePath);
        try {
            Document doc = Jsoup.parse(html, "utf-8");
            // parse css
            // parseUrl(saveFilePath, doc);

            // parse url
            getUrlFromDoc(doc);

        } catch (Exception e) {
            System.out.println("error to parse file:" + saveFilePath);
            e.printStackTrace();
        }
    }

    private void parseMap(Map map) {
        String link = null;
        try {
            Document doc = null;
            String saveIndexFilePath = saveFolder + "/index.shtml";
            String sourceIndexFilePath = cacheFolder + "/" + key + "/index.shtml";
            doc = Jsoup.parse(new File(sourceIndexFilePath), "utf-8");
            this.parseUrl(saveIndexFilePath, doc);

            Set entrySet = map.entrySet();
            int i = 0;
            for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
                i++;
                try {
                    Entry entry = (Entry) iterator.next();
                    link = entry.getKey() + "";
                    String value = entry.getValue() + "";

                    String saveFilePath = saveFolder + "/" + link;
                    if (value.equals("copy")) {
                        try {
                            String sourcePath = cacheFolder + "/" + key + "/" + link;
                            doc = Jsoup.parse(new File(sourcePath), "utf-8");
                        } catch (Exception e) {
                            doc = getHtmlFromNet(link);
                        }
                    } else {
                        doc = getHtmlFromNet(link);
                    }
                    this.parseUrl(saveFilePath, doc);
                    System.out.println("========remain:" + (map.size() - i) + "======");
                } catch (Exception e) {
                    System.out.println("=====error when parse :" + link);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("=====error when parse :" + link);
            e.printStackTrace();
        }

    }

    private Document getHtmlFromNet(String link) throws IOException {
        Document doc;
        String url = domain + key + "/" + link+"?channel=app";
        String html = urlUtil.openURLWithTimeOut(url);
        doc = Jsoup.parse(html, "utf-8");
        return doc;
    }

    private void parseUrl(String saveFilePath, Document doc) throws IOException, Exception {
        System.out.println("====parse file:" + saveFilePath);
        // parse css
        parseCss(doc);
        // parse js
        parseJs(doc);
        // parse img
        parseImg(doc);
        // parse iframe
        parseIframe(doc);
        // write file
        FileUtil.writeFile(saveFilePath, doc.html());
    }

    private void parseIframe(Document doc) {
        Elements elements = doc.getElementsByTag("iframe");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            element.remove();
        }
    }

    private void parseJs(Document doc) throws IOException {
        // <script src="../js/jquery-1.9.1.min.js"
        // language="javascript"></script>
        String saveJsPath = saveFolder + "/js/jquery-1.9.1.min.js";
        File file = new File(saveJsPath);
        if (!file.exists()) {
            // String url = domain + "js/jquery-1.9.1.min.js";
            // String html = urlUtil.openURLWithTimeOut(url);
            // FileUtil.writeFile(saveJsPath, html);
            try {
                String jsFileLocation = ConfigContainer.prop.getProperty("tomcatHome") + "/webapps/ROOT/js/jquery-1.9.1.min.js";
                FileUtils.copyFile(new File(jsFileLocation), file);
            } catch (Exception e) {
                String url = domain + "js/jquery-1.9.1.min.js";
                String html = urlUtil.openURLWithTimeOut(url);
                FileUtil.writeFile(saveJsPath, html);
            }
        }

        Elements elements = doc.getElementsByTag("script");
        String href = "jquery-1.9.1.min.js";
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            if (element.attr("src").contains(href))
                element.attr("src", "./js/jquery-1.9.1.min.js");
        }
    }

    /**
     * parse css
     * 
     * @param doc
     * @throws IOException
     */
    private void parseCss(Document doc) throws IOException {
        // <link rel="stylesheet" href="../css/mt/style.css" />
        String saveCssPath = saveFolder + "/style/style.css";
        File file = new File(saveCssPath);
        if (!file.exists()) {

            try {
                String cssFileLocation = ConfigContainer.prop.getProperty("tomcatHome") + "/webapps/ROOT/css/mwiki/" + key + "/style.css";
                FileUtils.copyFile(new File(cssFileLocation), file);
            } catch (Exception e) {
                String url = domain + "css/" + key + "/style.css";
                String html = urlUtil.openURLWithTimeOut(url);
                FileUtil.writeFile(saveCssPath, html);
            }
        }

        Elements elements = doc.getElementsByTag("link");
        String href = "/css/mwiki/"+key+"/style.css";
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            if (element.attr("rel").equals("stylesheet") && element.attr("href").contains(href))
                element.attr("href", "./style/style.css");
        }
    }

    // private void copyIndex(File indexFile) throws IOException {
    // File targetIndexFile = new File(saveFolder + "/index.shtml");
    // FileUtils.copyFile(indexFile, targetIndexFile);
    // }

    /**
     * parse url
     * 
     * @param doc
     */
    private void getUrlFromDoc(Document doc) {
        Elements elements = doc.getElementsByTag("a");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            String link = element.attr("href");
            if (link.endsWith(".shtml")) {
                int position = link.lastIndexOf(".");
                int startPosition= link.lastIndexOf("/")<=-1? 0:link.lastIndexOf("/")+1;
                if (position > 0) {
                    String part1 = link.substring(startPosition, position);
                    try {
                        Integer.parseInt(part1);
                        String sourceFilePath = cacheFolder + "/" + key + "/" + link;
                        File sourceFile = new File(sourceFilePath);
                        String targetFilepath = saveFolder + "/" + link;
                        File targetFile = new File(targetFilepath);

                        if (sourceFile.exists()) { // when source file is
                                                   // existed
                            if (!targetFile.exists() && !map.keySet().contains(link)) {
                                System.out.println("begin to copy :" + link);
                                // FileUtils.copyFile(sourceFile, targetFile);
                                String html = FileUtil.readFile(sourceFilePath, "utf-8");
                                map.put(link, "copy");
                                this.parseHtml(targetFilepath, html);
                            }
                        } else { // when source file is not existed, crwal it
                                 // from net.
                            String url = domain + key + "/" + part1+".shtml";
                            if (!targetFile.exists() && !map.keySet().contains(link)) {
                                System.out.println("begin to crwal :" + link);

                                String html = urlUtil.openURLWithTimeOut(url);
                                map.put(link, "crwal");
                                this.parseHtml(targetFilepath, html);
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
    }

    /**
     * parse Img
     * 
     * @param doc
     * @throws Exception
     */
    private void parseImg(Document doc) throws Exception {
        Elements elements = doc.getElementsByTag("img");
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();
            String imgUrl = element.attr("src");

            URL url = new URL(imgUrl);
            String path = url.getPath();
            String savePath = saveFolder + path;
            File file = new File(savePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists()) {
                util.saveImgFromUrl(imgUrl, savePath);
            } else {
                // System.out.println("pic crwal duplicated...ingore...");
            }
            element.attr("src", "." + path);
        }
    }

    public boolean doPageCrwal(String key , String pageString, int id) throws Exception {
        this.key = key;
        this.saveFolder = saveFolderRoot + "/" + id;
        //parse url
        parsePageMap(key, pageString);
        //parse map
        this.parseMap(map);
        
        zipFile = saveFolderRoot + "/" + id + ".zip";
        ZipUtil.zip(zipFile, key+"_cache/", saveFolder);
        return true;
    }

    public String parseKey(String pageString) {
        String[] str = pageString.split(";");
        if(str.length > 0){
            String temp = str[0].trim();

            if(temp.contains("/appwiki")){
                temp = temp.replaceAll("http://www.joyme.com/appwiki/", "");
            }else{
                temp = temp.replaceAll("http://www.joyme.com/mwiki/", "");
            }
            int position = temp.indexOf("/");
            if(position > 0){
                temp = temp.substring(0 , position);
            }
            return temp;
        }
        return null;
    }

    private void parsePageMap(String key, String pageString) {
        String[] pages = pageString.split(";");
        for (int i = 0; i < pages.length; i++) {
            String page = pages[i];
            int position = page.lastIndexOf("/");
            String htmlFile = page.substring(position + 1, page.length());
            String htmlCache = cacheFolder + "/" + key + "/index.shtml";
            File htmlCacheFile = new File(htmlCache);
            map.put(htmlFile, !htmlCacheFile.exists() ? "crwal" : "copy");
        }
    }

    public static void main(String[] args) throws Exception {
        String link="http:/wwwjoyme.com/123456.shtml";
        int position = link.lastIndexOf(".");
        int startPosition= link.lastIndexOf("/")<=-1? 0:link.lastIndexOf("/")+1;
        System.out.println(link.substring(startPosition,position));
    }
}
