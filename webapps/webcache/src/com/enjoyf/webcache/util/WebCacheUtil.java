package com.enjoyf.webcache.util;

import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.cache.UrlRuleCache;
import com.enjoyf.webcache.container.PropertiesContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/9/29.
 */
public class WebCacheUtil {

    private static Logger logger = LoggerFactory.getLogger(WebCacheUtil.class);

    public static String getCacheFile(String path, String host) {
        String file = PropertiesContainer.getInstance().getCacheFolder() + "/" + (host.contains(":") ? host.substring(0, host.indexOf(":")) : host);

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        //防止  参数
        if (path.indexOf("?") > 0) {
            path = path.substring(0, path.indexOf("?"));
        }
//        //数字ID   后缀直接做文件名即可   ugcwiki汉字title做md5
//        Matcher matcher = pattern.matcher(path);
//        if (!matcher.matches()) {
//            try {
//                path = URLDecoder.decode(path, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            String title = path.substring(path.lastIndexOf("/") + 1, path.length());
//            path = path.substring(0, path.lastIndexOf("/")) + "/" + MD5Util.Md5(title);
//        }
        file += path;
        return file;
    }

    public static boolean checkNeedRefreshCache(File cacheFile) {
        //没缓存  需要抓取
        if (!cacheFile.exists()) {
            return true;
        }
//        //缓存超过 一天   需要更新缓存
//        if (cacheFile.lastModified() != 0 && System.currentTimeMillis() - cacheFile.lastModified() > PropertiesContainer.DISABLE_TIME) {
//            return true;
//        }
        //如果缓存文件没有有效的内容  重新抓
        if (cacheFile.length() != 0 && cacheFile.length() < 100) {
            return true;
        }
        return false;
    }

    /**
     * 通过访问的request url 转换出 源站的src url
     * 按照url的"/"目录，从后往前倒着 一层一层 匹配
     *
     * @param reqUrl
     * @return
     */
    public static Map<String, String> genSrcRule(String reqUrl) {
        if (StringUtil.isEmpty(reqUrl)) {
            return null;
        }
        //按照 url path 从右向左匹配
        if (!reqUrl.startsWith("http://") && !reqUrl.startsWith("https://")) {
            reqUrl += "http://";
        }
        Map<String, String> map = null;
        try {
            String srcRule = null;
            URL url = new URL(reqUrl);
            String desRule = reqUrl;
            if (!url.getPath().endsWith("/") && !url.getPath().contains(".")) {
                desRule += "/";
            }
            do {
                srcRule = UrlRuleCache.getSrcRuleByDesRule(desRule);
                //没匹配到  去掉后面一层目录 继续循环
                if (StringUtil.isEmpty(srcRule)) {
                    desRule = StringUtil.removeLastCharacter(desRule, "/");
                    if (desRule.indexOf("/") > 0) {
                        desRule = desRule.substring(0, desRule.lastIndexOf("/")) + "/";
                    }
                    //最后剩下  http://不进行匹配  跳出
                    if (!desRule.contains(url.getHost())) {
                        break;
                    }
                } else {
                    if (StringUtil.isEmpty(srcRule)) {
                        return null;
                    }
                    map = new HashMap<String, String>();
                    map.put("srcRule", srcRule);
                    map.put("desRule", desRule);
                    break;
                }

            } while (desRule.indexOf("/") > 0);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.info("WebCacheUtil genSrcRule occur MalformedURLException.reqUrl:" + reqUrl);
        }
        return map;
    }

    /**
     * 通过源站的src url 转换出  输出的 request Url todo rename getRuleMapByDesUrl
     * 按照url的"/"目录，从后往前倒着 一层一层 匹配
     *
     * @param desUrl
     * @return
     */
    public static Map<String, String> genDesRule(String desUrl) {
        Map<String, String> map = null;
        if (StringUtil.isEmpty(desUrl)) {
            return null;
        }
        if (!desUrl.startsWith("http://") && !desUrl.startsWith("https://")) {
            return map;
        }
        String desRule = null;
        try {
            URL url = new URL(desUrl);
            String srcRule = desUrl;
            if (!srcRule.endsWith("/") && !srcRule.contains(".")) {
                srcRule += "/";
            }
            do {
                desRule = UrlRuleCache.getDesRuleBySrcRule(srcRule);
                //没匹配到  去掉后面一层目录 继续循环
                if (StringUtil.isEmpty(desRule)) {
                    srcRule = StringUtil.removeLastCharacter(srcRule, "/");
                    srcRule = srcRule.substring(0, srcRule.lastIndexOf("/")) + "/";
                    //最后剩下  http://不进行匹配  跳出
                    if (!srcRule.contains(url.getHost())) {
                        break;
                    }
                } else {
                    if (StringUtil.isEmpty(desRule)) {
                        return null;
                    }
                    map = new HashMap<String, String>();
                    map.put("srcRule", srcRule);
                    map.put("desRule", desRule);
                    break;
                }

            } while (srcRule.indexOf("/") > 0);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.info("WebCacheUtil genDesRule occur MalformedURLException.href:" + desUrl);
        }
        return map;
    }

    //注意：这个方法会排除列表页
    public static int getArchiveId(String url) {
        if (url.indexOf("?") > 0) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.indexOf("#") > 0) {
            url = url.substring(0, url.indexOf("#"));
        }
        String[] urls = url.split("/");
        int archiveId = 0;
        String number = "";
        for (int i = 0; i < urls.length; i++) {
            String item = urls[i];
            if (item.endsWith(".html")) {
                item = item.replaceAll(".html", "");
                int position = item.indexOf("_");
                if (position >= 0) {
                    item = item.substring(0, position);
                }
            }
            if (StringUtil.isNumeric(item)) {
                number += item;
            }
        }

        if (number.length() > 8 && number.startsWith("20")) {
            number = number.substring(8, number.length());
            if (number.length() > String.valueOf(Integer.MAX_VALUE).length()) {
                return 0;
            }
            archiveId = Integer.parseInt(number);
        }

        return archiveId;
    }

    public static void main(String[] args) {
        String url = "http://www.joyme.com/news/asiangames/list_240_60.html";
        System.out.println(getArchiveId(url));
        url = "http://www.joyme.com/news/asiangames/201502/0369288.html";
        System.out.println(getArchiveId(url));
        url = "http://www.joyme.com/news/asiangames/201502/0369288_2.html";
        System.out.println(getArchiveId(url));
        url = "http://www.joyme.com/news/newpicture/201605/04134591.html#image3";
        System.out.println(getArchiveId(url));
    }

    public static String getWikiId(String url) {
        String wikiId = "";
        try {
            if (StringUtil.isEmpty(url)) {
                return "";
            }
            if (url.indexOf("?") >= 0) {
                url = url.substring(0, url.indexOf("?"));
            }
            if (url.endsWith(".shtml")) {
                url = url.replaceAll(".shtml", "");
            }
            wikiId = url.substring(url.lastIndexOf("/") + 1, url.length());
            if (wikiId.contains("%")) {
                wikiId = URLDecoder.decode(wikiId, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return wikiId;
        }
    }

    public static String getWikiKey(String url) {
        try {
            if (StringUtil.isEmpty(url)) {
                return "";
            }
            String wikiKey = "";
            URL reqUrl = new URL(url);
            if (reqUrl.getHost().startsWith("wiki.joyme.") || reqUrl.getHost().startsWith("m.wiki.joyme.") || reqUrl.getHost().startsWith("www.joyme.")) {
                String path = reqUrl.getPath();
                if (path.lastIndexOf("/") == 0) {
                    wikiKey = path.substring(1, path.length());
                } else {
                    wikiKey = path.substring(0, path.lastIndexOf("/"));
                    wikiKey = wikiKey.substring(wikiKey.lastIndexOf("/") + 1, wikiKey.length());
                }
            } else {
                wikiKey = reqUrl.getHost().substring(0, reqUrl.getHost().indexOf("."));
            }
            return wikiKey;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSiteMapPath(String host, String path) {
        String filePath = host;
        if (!StringUtil.isEmpty(path)) {
            if (path.startsWith("/")) {
                filePath += path;
            } else {
                filePath += "/" + path;
            }
        }
        return "/opt/servicedata/sitemap/" + filePath;
    }


    public static String genUrlByRuleurl(String srcHref, String desRule, String srcRule, String host) {
        String returnHref = srcHref;
        try {
            //配置的规则命中 直接替换
            if (!StringUtil.isEmpty(desRule) && !StringUtil.isEmpty(srcRule) && srcHref.contains(srcRule)) {
                returnHref = srcHref.replace(srcRule, desRule);
            } else {
                //没命中 匹配一次
                String temDesRule = null;
                String tmpSrcRule = null;
                Map<String, String> map = WebCacheUtil.genDesRule(srcHref);
                //匹配到 替换
                if (map != null) {
                    temDesRule = map.get("desRule");
                    tmpSrcRule = map.get("srcRule");
                    returnHref = srcHref.replace(tmpSrcRule, temDesRule);
                } else {
                    //没匹配到的代码逻辑（这段代码只是为了兼容以前的一些就规则）
                    if (srcHref.contains("/vip/youku/")) {
                        if (srcHref.contains("/article/android/vip/youku/")) {
                            srcHref = srcHref.substring(srcHref.indexOf("/article/android/vip/youku/") + "/article/android/vip/youku/".length(), srcHref.length());
                            returnHref = (desRule.contains("https://") ? "https://" : "http://") + host + "/android" + srcHref;
                        } else if (srcHref.contains("/article/pc/vip/youku/")) {
                            srcHref = srcHref.substring(srcHref.indexOf("/article/pc/vip/youku/") + "/article/pc/vip/youku/".length(), srcHref.length());
                            returnHref = (desRule.contains("https://") ? "https://" : "http://") + host + srcHref;
                        }
                    } else if (srcHref.contains("/article/")) {
                        if (srcHref.contains("/vip/")) {
                            srcHref = srcHref.substring(srcHref.indexOf("/vip/") + "/vip/".length(), srcHref.length());
                            if (srcHref.indexOf("/") > 0) {
                                srcHref = srcHref.substring(srcHref.indexOf("/"), srcHref.length());
                            } else {
                                srcHref = "/index.html";
                            }
                        } else {
                            srcHref = srcHref.substring(srcHref.indexOf("/article/") + "/article/".length() - 1, srcHref.length());
                        }
                        returnHref = (desRule.contains("https://") ? "https://" : "http://") + host + srcHref;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnHref;
    }


}
