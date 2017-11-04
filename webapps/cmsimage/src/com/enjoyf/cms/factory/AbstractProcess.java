package com.enjoyf.cms.factory;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.util.CmsimageFilePathUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * Created by zhitaoshi on 2015/9/18.
 */
public class AbstractProcess implements IProcess {
    @Override
    public void process(String requestURL, ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        chain.doFilter(req, resp);
        return;
    }

    protected String genOldRule(URL url) {
        String srcUrl = "";
        String host = url.getHost();
        String path = url.getPath();
        //去掉 path中的 /article
        String urlKey = CmsimageFilePathUtil.getSecondDomainName(url);
        String temPath = CmsimageFilePathUtil.getRealPath(url);
        if (host.contains("youku")) {
            if (path.startsWith("/android")) {
                //优酷安卓  youku.joyme.com/android/gamecenter   ---   article.joyme.com/article/android/vip/youku/gamecenter
                srcUrl = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext() + "/android/vip/youku" + temPath.replaceFirst("/android/", "/");
            } else {
                //优酷IOS  youku.joyme.com/gamecenter   ---   article.joyme.com/article/pc/vip/youku/gamecenter
                srcUrl = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext() + "/pc/vip/youku" + path;
            }
        } else if (host.contains("wiki")) {
            if (host.startsWith("m.wiki")) {
                //m.wiki.joyme.com/hs   ---   article.joyme.com/article/<pc>/vip/hs
                srcUrl = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext() + "/wap/vip" + path;
            } else {
                //wiki.joyme.com/hs
                srcUrl = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext() + "/pc/vip" + path;
            }
        } else if (host.startsWith("www.joyme")) {
            // www.joyme.com/news   ---   article.joyme.com/article/<pc>/news
            srcUrl = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext() + "/pc" + temPath;
        } else if (host.startsWith("m.joyme")) {
            //m.joyme.com/news
            srcUrl = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext() + "/wap" + temPath;
        } else {
            // 二级域名的方案
            if (host.startsWith("m." + urlKey + ".joyme")) {
                srcUrl = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext() + "/wap/vip/" + urlKey + temPath;
            } else if (host.startsWith(urlKey + ".joyme")) {
                srcUrl = "http://" + PropertiesContainer.getphpCmsDomain() + "/" + PropertiesContainer.getphpProjectContext() + "/pc/vip/" + urlKey + temPath;
            }
        }
        return srcUrl;
    }
}
