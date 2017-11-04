package com.enjoyf.cms.factory;

import com.enjoyf.cms.bean.Sitemap;
import com.enjoyf.cms.bean.SitemapOutRule;
import com.enjoyf.cms.quartz.SiteMapJob;
import com.enjoyf.cms.service.SitemapFetcherService;
import com.enjoyf.cms.util.CmsimageFilePathUtil;
import org.quartz.SchedulerException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/9/18.
 */
public class SiteMapProcess extends AbstractProcess {
    @Override
    public void process(String requestURL, ServletRequest req, ServletResponse resp, FilterChain chain, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        URL url = new URL(requestURL);
        String host = url.getHost();
        String path = url.getPath();
        //注意改生成缓存的地方
        String filePath = SitemapFetcherService.getPath(host, path);
        File fileXml = new File(filePath);
        if (fileXml.exists()) {
            SitemapFetcherService.displayFileXML(response, fileXml, false);
        } else {
            Thread thread = new SiteMapThread(requestURL);
            thread.start();
            response.setStatus(500);
            chain.doFilter(req, resp);
        }
        return;
    }

    class SiteMapThread extends Thread {
        private String siteMapUrl;

        public SiteMapThread(String requestURL) {
            this.siteMapUrl = requestURL;
        }

        @Override
        public void run() {
            try {
                String transferPath = null;
                String originalPath = null;
                Map<String, String> map = CmsimageFilePathUtil.genOriginalPath(siteMapUrl);
                //article域名源地址
                URL url = new URL(siteMapUrl);
                String srcUrl = "";
                if (map != null) {
                    transferPath = map.get("transferPath");
                    originalPath = map.get("originalPath");
                    srcUrl = siteMapUrl.replace(transferPath, originalPath);
                } else {
                    srcUrl = genOldRule(url);
                }
                SitemapOutRule outRule = null;
                if (srcUrl.contains("/article/")) {
                    if (srcUrl.contains("/vip/")) {
                        outRule = SitemapOutRule.ARTICLE_VIP;
                    } else {
                        outRule = SitemapOutRule.ARTICLE;
                    }
                } else if (srcUrl.contains("wiki.joyme")) {
                    if (srcUrl.contains("m.wiki.joyme")) {
                        outRule = SitemapOutRule.M_WIKI;
                    } else {
                        outRule = SitemapOutRule.WIKI;
                    }
                }else{
                    outRule = SitemapOutRule.DEFAULT;
                }
                //article
                srcUrl = srcUrl.replace("/article/", "/");
                SitemapFetcherService.genXmlFile(siteMapUrl, srcUrl, outRule);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
