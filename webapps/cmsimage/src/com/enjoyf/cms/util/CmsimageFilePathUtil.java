package com.enjoyf.cms.util;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.container.SEOContainer;
import com.enjoyf.cms.filter.URLFilter;
import com.enjoyf.cms.service.SeoConfigService;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhimingli on 2015/8/10.
 */
public class CmsimageFilePathUtil {

    /**
     * //根据外网访问地址，获取文件存储路径
     *
     * @param \http://hs.joyme.com
     * @return d:/cmsimage-cache/hs/vip/hs/ or /opt/cmsimage-cache/hs/vip/hs
     */
    public static String getPath(String clearpage) {
        URL url = null;
        String file = "";
        String onlinePath = null;
        try {
            String fullUrl = getFullURL(clearpage);
            //
            if (!clearpage.contains("/index.html")) {
                fullUrl = fullUrl.replace("/index.html", "");
            }
            url = new URL(fullUrl);
            int domainType = CmsUrlUtil.checkDomain(url);
            String realPath = null;
            String secondDomainName = null;
            if (domainType == CmsUrlUtil.HOST_WWW) {
                // www站的方法
                realPath = getRealPath(url);
                onlinePath = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext() + realPath;
            } else if (domainType == CmsUrlUtil.HOST_SUBDOMAIN) {
                // 二级域名的方案
                secondDomainName = getSecondDomainName(url);
                realPath = getSecondDomainRealPath(url);
                realPath = StringUtil.removeStartCharacter(realPath, "/");

                StringBuffer sb = getSecondDomainName(realPath, secondDomainName);

                onlinePath = sb.toString();
            } else if (domainType == CmsUrlUtil.HOST_MOBILE) {
                // m站的方案
                realPath = getRealPath(url);
                onlinePath = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext() + "/m" + realPath;
            }
            String savePath = getSavePath(new URL(onlinePath));

            realPath = StringUtil.removeStartCharacter(savePath, "/");
            file = PropertiesContainer.getCacheFolder() + "/" + (secondDomainName == null ? "default" : secondDomainName) + "/" + realPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    public static String getFullURL(String path) {
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

    public static String getRealPath(URL url) {
        try {
            String path = url.getPath();
            String tmppath = StringUtil.removeStartCharacter(path, "/");
            String[] replaceContext = PropertiesContainer.getReplaceContext();
            for (int i = 0; i < replaceContext.length; i++) {
                if (tmppath.startsWith(replaceContext[i])) {
                    return tmppath.replaceFirst(replaceContext[i], "");
                }
            }
            return path;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSecondDomainName(URL url) {
        String host = url.getHost();
        String[] hosts = host.split("\\.");
        String domain = "";
        //域名策略 *.joyme.com  joyme.*.com   m.*.joyme.com
        if (hosts[1].equalsIgnoreCase("joyme")) {
            domain = hosts[0];
        } else {
            domain = hosts[1];
        }
        //读数据库配置即可 不需要转换
//        if ("hswiki".equalsIgnoreCase(domain)) {
//            domain = "hs";
//        }
        return domain;
    }

    /**
     * 获得二级域名的realpath
     *
     * @param url
     * @return
     */
    public static String getSecondDomainRealPath(URL url) {
        String path = url.getPath();
        return path;
    }

    public static StringBuffer getSecondDomainName(String realPath, String secondDomainName) {
        StringBuffer sb = new StringBuffer();
        sb.append("http://");
        sb.append(PropertiesContainer.getphpCmsDomain());
        sb.append("/");
        sb.append(PropertiesContainer.getphpProjectContext());
        sb.append("/vip/");
        sb.append(secondDomainName);
        sb.append("/");
        sb.append(realPath);
        return sb;
    }

    public static String getSavePath(URL url) {
        String path = url.getPath();
        path = StringUtil.removeStartCharacter(path, "/");
        return path.replaceFirst("article/", "/");
    }

    public static Map<String, String> genOriginalPath(String reqUrl) throws MalformedURLException {
        Map<String, String> map = null;
        String originalPath = null;
        URL url = new URL(reqUrl);
        String transferPath = reqUrl.substring(0, reqUrl.lastIndexOf("/")) + "/";
        do {
            if (SEOContainer.transferMap.get(transferPath) != null) {
                originalPath = SEOContainer.transferMap.get(transferPath).toString();
            }
            //没匹配到  去掉后面一层目录 继续循环
            if (StringUtil.isEmpty(originalPath)) {
                transferPath = StringUtil.removeLastCharacter(transferPath, "/");
                transferPath = transferPath.substring(0, transferPath.lastIndexOf("/")) + "/";
                //最后剩下  http://不进行匹配  跳出
                if (!transferPath.contains(url.getHost())) {
                    break;
                }
            } else {
                map = new HashMap<String, String>();
                map.put("transferPath", transferPath);
                map.put("originalPath", originalPath);
                break;
            }

        } while (transferPath.indexOf("/") > 0);
        return map;
    }

    public static Map<String, String> genTransferPath(String srcUrl) throws MalformedURLException {
        Map<String, String> map = null;
        String transferPath = null;
        URL url = new URL(srcUrl);
        String originalPath = srcUrl.substring(0, srcUrl.lastIndexOf("/"));
        do {
            if (SEOContainer.originalMap.get(originalPath) != null) {
                transferPath = SEOContainer.originalMap.get(originalPath).toString();
            }
            //没匹配到  去掉后面一层目录 继续循环
            if (StringUtil.isEmpty(transferPath)) {
                originalPath = StringUtil.removeLastCharacter(originalPath, "/");
                originalPath = originalPath.substring(0, originalPath.lastIndexOf("/")) + "/";
                //最后剩下  http://不进行匹配  跳出
                if (!originalPath.contains(url.getHost())) {
                    break;
                }
            } else {
                map = new HashMap<String, String>();
                map.put("transferPath", transferPath);
                map.put("originalPath", originalPath);
                break;
            }

        } while (originalPath.indexOf("/") > 0);
        return map;
    }
}