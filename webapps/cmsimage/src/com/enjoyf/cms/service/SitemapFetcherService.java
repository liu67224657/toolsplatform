package com.enjoyf.cms.service;

import com.enjoyf.cms.bean.IntRemoveStatus;
import com.enjoyf.cms.bean.Sitemap;
import com.enjoyf.cms.bean.SitemapLog;
import com.enjoyf.cms.bean.SitemapOutRule;
import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.util.CmsimageFilePathUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/7/6
 * Description:
 */
public class SitemapFetcherService {
    private static final String JOYME_SITEMAP_DOMAIN_KEYS = "joyme_sitemap_domain_keys";
    private static final String JOYME_SITEMAP_DOMAIN_URLS = "joyme_sitemap_domain_urls_";
    private static final String JOYME_SITEMAP_DOMAIN_PATH_URLS = "joyme_sitemap_path_urls_";

    private static final String JOYME_SITEMAP_HAS_PUSH_URLS = "joyme_sitemap_has_push_urls_";
    private static final String JOYME_SITEMAP_XMLS = "joyme_sitemap_xmls";

    private static URLUtil urlUtil = new URLUtil();

    private static RedisManager redisManager = PropertiesContainer.getRedisManager();
    private static SitemapService dbService = new SitemapService();

    public static String getPath(String host, String path) {
        String filePath = host;
        if (!StringUtil.isEmpty(path)) {
            if (path.startsWith("/")) {
                filePath += path;
            } else {
                filePath += "/" + path;
            }
        }
        //todo properties
        return "/opt/servicedata/sitemap/" + filePath;
    }

    private static void generatorSitemap(String host, String path) {
        org.dom4j.Document document = DocumentHelper.createDocument();
        org.dom4j.Element root = document.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
        try {
            //取出缓存中数据
            String setKey = getSitemapDomainPathUrlsKey(host, path);
            do {
                String url = redisManager.rpop(setKey);
                if (StringUtil.isEmpty(url)) {
                    continue;
                }
                org.dom4j.Element urlElement = root.addElement("url"); //添加root的子节点
                org.dom4j.Element locElement = urlElement.addElement("loc");
                locElement.addText(url);
                org.dom4j.Element priorityElement = urlElement.addElement("priority");
                priorityElement.addText(String.valueOf(genPriority(url)));
            } while (redisManager.length(setKey) > 0);
            redisManager.remove(setKey);

            //设置编码
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            //本地文件路径
            if(path.indexOf("?") > 0){
                path = path.substring(0, path.indexOf("?") + 1);
            }
            path = path.replace("/sitemap.xml", "").replace("/sitemap.txt", "");
            String filePath = getPath(host, path);
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            String xmlPath = filePath;
            // 输出全部原始数据，并用它生成新的我们需要的XML文件
            if (FileUtil.isFileOrDirExist(xmlPath)) {
                FileUtil.createDirectory(xmlPath);
            }

            File xmlFile = new File(xmlPath + "/sitemap.xml");
            //这里用FileOutputStream输出，用FileWriter输出是无法设置OutputFormat的编码的
            FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
            XMLWriter writer2 = new XMLWriter(fileOutputStream, format);
            writer2.write(document); //输出到文件
            writer2.close();

            File txtFile = new File(xmlPath + "/sitemap.txt");
            //这里用FileOutputStream输出，用FileWriter输出是无法设置OutputFormat的编码的
            FileOutputStream txtOutputStream = new FileOutputStream(txtFile);
            XMLWriter writer = new XMLWriter(txtOutputStream, format);
            writer.write(document); //输出到文件
            writer.close();
        } catch (Exception e) {
            System.out.println("host:" + host + ",path:" + path + " write xml file error");
        }
    }

    public static void displayFileXML(HttpServletResponse response, File file, boolean checkFileLength) throws IOException, FileNotFoundException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/xml");
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

    public void genSitemap(Sitemap sm, String host) throws Exception {
        if (sm == null) {
            return;
        }
        genXmlFile(sm.getSitemapUrl(), sm.getMappingUrl(), sm.getOutRule());
    }

    public static void genXmlFile(String sitemapUrl, String mappingUrl, SitemapOutRule outRule) throws Exception {
        URL siteMapUrl = new URL(sitemapUrl);
        //如果没有对应的规则，这块代码是为了兼容就逻辑
        if (outRule == null) {
            URL mapUrl = new URL(mappingUrl);
            String host = mapUrl.getHost();
            String path = mapUrl.getPath();
            if (host.startsWith("article")) {
                path = path.replaceAll("\\/article\\/", "/");   // /news/zxzt  /vip/hs/gl
                if (path.contains("/vip/")) {
                    path = path.substring(path.indexOf("/vip/") + "/vip/".length(), path.length());  // hs/gl
                    path = path.substring(path.indexOf("/"), path.length()); // /gl
                    String key = "";
                    if (path.indexOf("/") > 0) {
                        key = path.substring(0, path.indexOf("/")); //  hs
                    } else {
                        key = path;
                    }
                    sitemapUrl = "http://"+key+"."+PropertiesContainer.getEnvDomain() + (path.startsWith("/") ? "" : "/") + path;
                    outRule = SitemapOutRule.ARTICLE_VIP;
                }else if(path.contains("/wap/")){
                    sitemapUrl = "http://m."+PropertiesContainer.getEnvDomain() + (path.startsWith("/") ? "" : "/") + path;
                    outRule = SitemapOutRule.M_CMS;
                } else {
                    sitemapUrl = "http://www."+PropertiesContainer.getEnvDomain() + (path.startsWith("/") ? "" : "/") + path;
                    outRule = SitemapOutRule.ARTICLE;
                }
            }else if(host.startsWith("wiki.joyme")){
                sitemapUrl = mappingUrl;
                outRule = SitemapOutRule.WIKI;
            }else if(host.startsWith("m.wiki.joyme")){
                sitemapUrl = mappingUrl;
                outRule = SitemapOutRule.M_WIKI;
            }
        }else if(outRule.equals(SitemapOutRule.DEFAULT)){
            mappingUrl = sitemapUrl;
        }
        //抓取 源文件 xml
        String html = urlUtil.openURLWithTimeOut(mappingUrl);
        if (!StringUtil.isEmpty(html)) {
            LogService.infoSystemLog("===sitemap xml:" + sitemapUrl + "," + mappingUrl, true);
            //解析
            Document document = Jsoup.parse(html);
            genDocument(document, sitemapUrl, mappingUrl, outRule);
            //如果是article，转成www在cmsimage生成sitemap.xml
            if (outRule.equals(SitemapOutRule.ARTICLE) || outRule.equals(SitemapOutRule.ARTICLE_VIP) || outRule.equals(SitemapOutRule.M_CMS)) {
                generatorSitemap(siteMapUrl.getHost(), siteMapUrl.getPath());
            }
        }
    }

    private static void genDocument(Document document, String sitemapUrl, String mappingUrl, SitemapOutRule outRule) throws Exception {
        if (document == null) {
            return;
        }
        //如果是索引文件，递归抓取
        Elements sitemapEles = document.getElementsByTag("sitemap");
        if (!CollectionUtil.isEmpty(sitemapEles)) {
            for (Element ele : sitemapEles) {
                String xmlUrl = ele.getElementsByTag("loc").text();
                if (!StringUtil.isEmpty(xmlUrl) && xmlUrl.endsWith("/sitemap.xml")) {
                    genXmlFile(sitemapUrl, xmlUrl, null);
                }
            }
        }
        //取出url
        Elements urlEles = document.getElementsByTag("url");
        if (!CollectionUtil.isEmpty(urlEles)) {
            URL url = new URL(sitemapUrl);
            //将站点（域名）放入到缓存
            redisManager.sadd(JOYME_SITEMAP_DOMAIN_KEYS, url.getHost());
            for (Element ele : urlEles) {
                String loc = ele.getElementsByTag("loc").text();
                if (loc.indexOf("?") > 0) {
                    loc = loc.substring(0, loc.indexOf("?"));
                }
                if (loc.indexOf("#") > 0) {
                    loc = loc.substring(0, loc.indexOf("#"));
                }
                if (loc.endsWith("/")) {
                    loc = loc.substring(0, loc.length() - 1);
                }
                loc = loc.replaceAll(" ", "");
                if (StringUtil.isEmpty(loc)) {
                    continue;
                }
                //根据规则 转换 url
                loc = genUrl(loc, sitemapUrl, outRule);
                LogService.infoSystemLog("SiteMapJob xml loc:"+loc, true);
                //将url放入redis，用于百度推送
                putUrlPushRedis(url.getHost(), loc);

                //如果是article，放入缓存，用于生成cmsimage本地 sitemap.xml文件
                if (outRule.equals(SitemapOutRule.ARTICLE) || outRule.equals(SitemapOutRule.ARTICLE_VIP)|| outRule.equals(SitemapOutRule.M_CMS)) {
                    redisManager.lpush(getSitemapDomainPathUrlsKey(url.getHost(), url.getPath()), loc);
                }
            }
        }
    }

    private static double genPriority(String pageUrl) {
        double priority = 0.5;
        try {
            URL url = new URL(pageUrl);
            String path = url.getPath();
            if (path.startsWith("/")) {
                path.substring(1, path.length());
            }
            if (path.endsWith("/")) {
                path.substring(0, path.length() - 1);
            }
            if (path.contains("/")) {
                String[] paths = url.getPath().split("/");
                if (paths.length == 1) {
                    priority = 1.0;
                } else if (paths.length == 2) {
                    priority = 0.8;
                } else if (paths.length == 3) {
                    priority = 0.6;
                } else {
                    priority = 0.5;
                }
            } else {
                priority = 0.8;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return priority;
    }

    private static String getSitemapDomainPathUrlsKey(String host, String path) {
        return JOYME_SITEMAP_DOMAIN_PATH_URLS + host + path;
    }

    private static String getSitemapDomainUrlsKey(String domainKey) {
        return JOYME_SITEMAP_DOMAIN_URLS + domainKey;
    }

    private static void putUrlPushRedis(String host, String url) {
        //是否已经推送过
        //检查缓存
        if (!StringUtil.isEmpty(url)) {
            if (redisManager.sismember(JOYME_SITEMAP_HAS_PUSH_URLS + host, url)) {
                return;
            }
            //检查DB
            if (checkDbLog(url)) {
                return;
            }
            LogService.infoSystemLog("===sitemap baidu sadd url:" + url, true);
            redisManager.sadd(getSitemapDomainUrlsKey(host), url);
        }
    }

    private static String genUrl(String mappingUrl, String sitemapUrl, SitemapOutRule outRule) throws MalformedURLException {
        String transferUrl = "";
        URL url = new URL(sitemapUrl);
        String hostUrl = sitemapUrl.replace(url.getPath(), "");
        if (outRule.equals(SitemapOutRule.DEFAULT)) {
            transferUrl = mappingUrl;
        } else if (outRule.equals(SitemapOutRule.ARTICLE)) {
            String replaceUrl = "";
            if (mappingUrl.startsWith("http://")) {
                replaceUrl += "http://";
            } else if (mappingUrl.startsWith("https://")) {
                replaceUrl += "https://";
            }
            replaceUrl += outRule.getRule().replace("joyme.com", PropertiesContainer.getEnvDomain());
            transferUrl = mappingUrl.replace(replaceUrl, hostUrl);
        }else if (outRule.equals(SitemapOutRule.M_CMS)) {
            String replaceUrl = "";
            if (mappingUrl.startsWith("http://")) {
                replaceUrl += "http://";
            } else if (mappingUrl.startsWith("https://")) {
                replaceUrl += "https://";
            }
            replaceUrl += outRule.getRule().replace("joyme.com", PropertiesContainer.getEnvDomain());
            transferUrl = mappingUrl.replace(replaceUrl, hostUrl);
        }  else if (outRule.equals(SitemapOutRule.ARTICLE_VIP)) {
            String replaceUrl = "";
            if (mappingUrl.startsWith("http://")) {
                replaceUrl += "http://";
            } else if (mappingUrl.startsWith("https://")) {
                replaceUrl += "https://";
            }
            String key = CmsimageFilePathUtil.getSecondDomainName(url);
            replaceUrl += outRule.getRule().replace("joyme.com", PropertiesContainer.getEnvDomain()) + "/" + key;
            transferUrl = mappingUrl.replace(replaceUrl, hostUrl);
        } else if (outRule.equals(SitemapOutRule.WIKI)) {
            String replaceUrl = "";
            if (mappingUrl.startsWith("http://")) {
                replaceUrl += "http://";
            } else if (mappingUrl.startsWith("https://")) {
                replaceUrl += "https://";
            }
            replaceUrl += outRule.getRule().replace("joyme.com", PropertiesContainer.getEnvDomain());
            transferUrl = mappingUrl.replace(replaceUrl, hostUrl);
        } else if (outRule.equals(SitemapOutRule.M_WIKI)) {
            String replaceUrl = "";
            if (mappingUrl.startsWith("http://")) {
                replaceUrl += "http://";
            } else if (mappingUrl.startsWith("https://")) {
                replaceUrl += "https://";
            }
            replaceUrl += outRule.getRule().replace("joyme.com", PropertiesContainer.getEnvDomain());
            transferUrl = mappingUrl.replace(replaceUrl, hostUrl);
        }
        return transferUrl;
    }

    public static String buildSitemapUrl(String domainKey, String contextPath) {
        String sitemapUrl = "";
        if (!StringUtil.isEmpty(domainKey)) {
            sitemapUrl += PropertiesContainer.getHttpUrl() + domainKey + "." + PropertiesContainer.getEnvDomain();
        }
        if (!StringUtil.isEmpty(contextPath)) {
            if (contextPath.startsWith("/")) {
                sitemapUrl += contextPath;
            } else {
                sitemapUrl += "/" + contextPath;
            }
            if (contextPath.endsWith("/")) {
            } else {
                sitemapUrl += "/";
            }
        }
        if (!StringUtil.isEmpty(sitemapUrl)) {
            if (!sitemapUrl.endsWith("/")) {
                sitemapUrl += "/";
            }
            sitemapUrl += "sitemap.xml";
        }
        return sitemapUrl;
    }

    public static String buildMappingUrl(String domainKey, String contextPath, SitemapOutRule outRule) {
        String mappingUrl = "";
        if (outRule.equals(SitemapOutRule.DEFAULT)) {

        } else if (outRule.equals(SitemapOutRule.ARTICLE)) {
            mappingUrl += PropertiesContainer.getHttpUrl() + outRule.getRule().replace("joyme.com", PropertiesContainer.getEnvDomain());
            if (!StringUtil.isEmpty(contextPath)) {
                if (contextPath.startsWith("/")) {
                    mappingUrl += contextPath;
                } else {
                    mappingUrl += "/" + contextPath;
                }
                if (contextPath.endsWith("/")) {
                } else {
                    mappingUrl += "/";
                }
            }
            //抓取 sitemap 去掉 /article/
            mappingUrl = mappingUrl.replaceAll("\\/article\\/", "/");
        } else if (outRule.equals(SitemapOutRule.M_CMS)) {
            mappingUrl += PropertiesContainer.getHttpUrl() + outRule.getRule().replace("joyme.com", PropertiesContainer.getEnvDomain());
            if (!StringUtil.isEmpty(contextPath)) {
                if (contextPath.startsWith("/")) {
                    mappingUrl += contextPath;
                } else {
                    mappingUrl += "/" + contextPath;
                }
                if (contextPath.endsWith("/")) {
                } else {
                    mappingUrl += "/";
                }
            }
            //抓取 sitemap 去掉 /article/
            mappingUrl = mappingUrl.replaceAll("\\/article\\/", "/");
        }else if (outRule.equals(SitemapOutRule.ARTICLE_VIP)) {
            mappingUrl += PropertiesContainer.getHttpUrl() + outRule.getRule().replace("joyme.com", PropertiesContainer.getEnvDomain()) + "/" + domainKey;
            if (!StringUtil.isEmpty(contextPath)) {
                if (contextPath.startsWith("/")) {
                    mappingUrl += contextPath;
                } else {
                    mappingUrl += "/" + contextPath;
                }
                if (contextPath.endsWith("/")) {
                } else {
                    mappingUrl += "/";
                }
            }
            //抓取 sitemap 去掉 /article/
            mappingUrl = mappingUrl.replaceAll("\\/article\\/", "/");
        } else if (outRule.equals(SitemapOutRule.WIKI)) {
            mappingUrl += PropertiesContainer.getHttpUrl() + outRule.getRule().replace("joyme.com", PropertiesContainer.getEnvDomain());
            if (!StringUtil.isEmpty(contextPath)) {
                if (contextPath.startsWith("/")) {
                    mappingUrl += contextPath;
                } else {
                    mappingUrl += "/" + contextPath;
                }
                if (contextPath.endsWith("/")) {
                } else {
                    mappingUrl += "/";
                }
                mappingUrl = mappingUrl.replace("/wiki/", "/");
            }
        } else if (outRule.equals(SitemapOutRule.M_WIKI)) {
            mappingUrl += PropertiesContainer.getHttpUrl() + outRule.getRule().replace("joyme.com", PropertiesContainer.getEnvDomain());
            if (!StringUtil.isEmpty(contextPath)) {
                if (contextPath.startsWith("/")) {
                    mappingUrl += contextPath;
                } else {
                    mappingUrl += "/" + contextPath;
                }
                if (contextPath.endsWith("/")) {
                } else {
                    mappingUrl += "/";
                }
                mappingUrl = mappingUrl.replace("/mwiki/", "/");
            }
        }
        if (!StringUtil.isEmpty(mappingUrl)) {
            if (!mappingUrl.endsWith("/")) {
                mappingUrl += "/";
            }
            mappingUrl += "sitemap.xml";
        }
        return mappingUrl;
    }

    public void pushBaiduUrls() {
        LogService.infoSystemLog(System.currentTimeMillis() + " push baidu urls start", true);
        //取出所有的站点
        Set<String> keySet = redisManager.smembers(JOYME_SITEMAP_DOMAIN_KEYS);
        if (CollectionUtil.isEmpty(keySet)) {
            return;
        }

        for (String host : keySet) {
            String key = host.substring(0, host.indexOf(".joyme"));
            String postUrl = PropertiesContainer.getBaiduSitemapPushApi().replace("{domainKey}", key);
            //每天最多 remain 条 url，一次请求最多2000条
            int remain = 1;
            try {
                do {
                    //取 站点 下的缓存 url
                    Set<String> urls = getUrls(host, remain);
                    if(!CollectionUtil.isEmpty(urls)){
                        remain = genResult(host, postUrl, urls, remain);
                    }
                }while (redisManager.scard(getSitemapDomainUrlsKey(host)) > 0l && remain > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //推完 去缓存
            redisManager.remove(getSitemapDomainUrlsKey(host));
        }
        //推完 去缓存
        redisManager.remove(JOYME_SITEMAP_DOMAIN_KEYS);
        LogService.infoSystemLog(System.currentTimeMillis() + " push baidu urls end", true);
    }

    private int genResult(String key, String postUrl, Set<String> urls, int remain) {
        String reqBody = "";
        int i = 0;
        //百度限制，一行一条数据，所以\n分割
        for (String url : urls) {
            reqBody += url;
            i++;
            if (i < urls.size()) {
                reqBody += "\n";
            }
        }

        String result = post(postUrl, reqBody);
        LogService.infoSystemLog(reqBody + "\n" + result, true);
        //返回不为空
        if (!StringUtil.isEmpty(result)) {
            JSONObject jsonObject = JSONObject.fromObject(result);
            //成功
            if (jsonObject != null && jsonObject.containsKey("success")) {
                //达到今日限额，不再推送
                remain = jsonObject.getInt("remain");
                LogService.infoSystemLog(key + " remain " + remain, true);
                //写入 db
                insertLog(key, urls, jsonObject);
                if (remain <= 0) {
                    return 0;
                }
            } else if (jsonObject != null && jsonObject.containsKey("error")) {
                for (String url : urls) {
                    redisManager.srem(JOYME_SITEMAP_HAS_PUSH_URLS + key, url);
                }
                remain = 0;
            }
        }else {
            remain = 0;
        }
        return remain;
    }

    private Set<String> getUrls(String key, int remain) {
        Set<String> urls = new HashSet<String>();
        do {
            try {
                String url = redisManager.spop(getSitemapDomainUrlsKey(key));
                if (!StringUtil.isEmpty(url)) {
                    //阻塞，防止多个tomcat，重复提交
                    if (redisManager.sismember(JOYME_SITEMAP_HAS_PUSH_URLS + key, url)) {
                        continue;
                    }
                    if (checkDbLog(url)) {
                        continue;
                    }
                    LogService.infoSystemLog("===sitemap push spop url:" + url, true);
                    redisManager.sadd(JOYME_SITEMAP_HAS_PUSH_URLS + key, url);
                    urls.add(url);
                    //百度有两个限制，1、每次最多2000，2、每个站点有推送上限（这个上限不确定，百度会根据站点的情况调整），所以我们先推送1条，得到这个限制数，然后在进行最大量的推送。
                    if (urls.size() >= 2000 || urls.size() >= remain) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (redisManager.scard(getSitemapDomainUrlsKey(key)) > 0l);
        return urls;
    }

    public static String post(String postUrl, String reqBody) {
        //测试慎重
        //reqBody = "http://www.joyme.com/collection/热血传奇手机版";
        String result = "";
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            //建立URL之间的连接
            URLConnection conn = new URL(postUrl).openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("Host", "data.zz.baidu.com");
            conn.setRequestProperty("User-Agent", "curl/7.12.1");
            conn.setRequestProperty("Content-Length", String.valueOf(reqBody.getBytes("UTF-8").length));
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Charset", "UTF-8");

            //IO发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //获取conn对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            out.print(reqBody);
            //进行输出流的缓冲
            out.flush();
            //通过BufferedReader输入流来读取Url的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            LogService.errorSystemLog("发送post请求出现异常！", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    //todo
    public void generatorSitemapIndex(String host, List<String> xmlSet) {
        if (CollectionUtil.isEmpty(xmlSet)) {
            return;
        }
        org.dom4j.Document document = DocumentHelper.createDocument();
        org.dom4j.Element root = document.addElement("sitemapindex");
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            for (String xml : xmlSet) {
                org.dom4j.Element sitemapElement = root.addElement("sitemap"); //添加root的子节点
                org.dom4j.Element locElement = sitemapElement.addElement("loc");
                locElement.addText(xml);
                org.dom4j.Element lastmodElement = sitemapElement.addElement("lastmod");
                lastmodElement.addText(df.format(new Date()));
            }
            //输出全部原始数据，在编译器中显示
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");//根据需要设置编码

            String path = getPath(host, "");
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            String xmlPath = path;
            // 输出全部原始数据，并用它生成新的我们需要的XML文件
            if (FileUtil.isFileOrDirExist(xmlPath)) {
                FileUtil.createDirectory(xmlPath);
            }

            File xmlFile = new File(xmlPath + "/sitemap.xml");
            FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
            XMLWriter writer = new XMLWriter(fileOutputStream, format);
            writer.write(document); //输出到文件
            writer.close();

            File xmlFile2 = new File(xmlPath + "/sitemap.txt");
            FileOutputStream fileOutputStream2 = new FileOutputStream(xmlFile2);
            XMLWriter writer2 = new XMLWriter(fileOutputStream2, format);
            writer2.write(document); //输出到文件
            writer2.close();
        } catch (Exception e) {
            System.out.println("write www sitemap index xml file error");
        }
    }

    private void insertLog(String key, Set<String> urls, JSONObject jsonObject) {
        try {
            JSONArray notSameSite = null;
            JSONArray notValid = null;
            if (jsonObject.containsKey("not_same_site")) {
                notSameSite = jsonObject.getJSONArray("not_same_site");
            }
            if (jsonObject.containsKey("not_valid")) {
                notValid = jsonObject.getJSONArray("not_valid");
            }

            Date date = new Date();
            for (String url : urls) {
                if (notSameSite != null && notSameSite.contains(url)) {
                    SitemapLog sitemapLog = new SitemapLog();
                    sitemapLog.setPushDate(date);
                    sitemapLog.setPageUrl(url);
                    sitemapLog.setStatusDesc("不是" + key + "的url而未处理");
                    sitemapLog.setDomainKey(key);
                    sitemapLog.setLogId(MD5Util.Md5(url));
                    sitemapLog.setStatus(IntRemoveStatus.ERROR);
                    sitemapLog = dbService.insertJoymeSitemapLog(sitemapLog);
                    if (sitemapLog != null) {
                        //写入 db 就去掉 缓存
                        redisManager.srem(JOYME_SITEMAP_HAS_PUSH_URLS + key, url);
                    }
                } else if (notValid != null && notValid.contains(url)) {
                    SitemapLog sitemapLog = new SitemapLog();
                    sitemapLog.setPushDate(date);
                    sitemapLog.setPageUrl(url);
                    sitemapLog.setStatusDesc("不合法的url");
                    sitemapLog.setDomainKey(key);
                    sitemapLog.setLogId(MD5Util.Md5(url));
                    sitemapLog.setStatus(IntRemoveStatus.ERROR);
                    sitemapLog = dbService.insertJoymeSitemapLog(sitemapLog);
                    if (sitemapLog != null) {
                        //写入 db 就去掉 缓存
                        redisManager.srem(JOYME_SITEMAP_HAS_PUSH_URLS + key, url);
                    }
                } else {
                    SitemapLog sitemapLog = new SitemapLog();
                    sitemapLog.setPushDate(date);
                    sitemapLog.setPageUrl(url);
                    sitemapLog.setStatusDesc("推送成功");
                    sitemapLog.setDomainKey(key);
                    sitemapLog.setLogId(MD5Util.Md5(url));
                    sitemapLog.setStatus(IntRemoveStatus.USED);
                    sitemapLog = dbService.insertJoymeSitemapLog(sitemapLog);
                    if (sitemapLog != null) {
                        //写入 db 就去掉 缓存
                        redisManager.srem(JOYME_SITEMAP_HAS_PUSH_URLS + key, url);
                    }
                }
            }
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkDbLog(String url) {
        boolean bool = false;
        try {
            SitemapLog sitemapLog = dbService.getJoymeSitemapLog(MD5Util.Md5(url));
            if (sitemapLog != null) {
                bool = true;
            }
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return bool;
    }

}
