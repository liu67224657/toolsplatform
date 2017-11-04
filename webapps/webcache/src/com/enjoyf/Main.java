package com.enjoyf;/**
 * Created by ericliu on 16/8/24.
 */

import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.util.HttpResult;
import com.enjoyf.webcache.bean.PageStat;
import com.enjoyf.webcache.bean.WebCacheSrcType;
import com.enjoyf.webcache.util.WebCacheUtil;

import java.util.Date;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/8/24
 */
public class Main {
    private static String url = "http://stat.joyme.com/";
    private static String token = "698978c46986cbd2c66b4df017ef1308";

    public static void main(String[] args) {
        HttpClientManager httpClientManager = new HttpClientManager();
//        HttpResult idSiteResult = httpClientManager.get(url, new HttpParameter[]{
//                new HttpParameter("module", "API"),
//                new HttpParameter("method", "SitesManager.getAllSites"),
//                new HttpParameter("format", "JSON"),
//                new HttpParameter("token_auth", token)
//        }, "UTF-8");
//
//
//        System.out.println(idSiteResult.getResult());
//

//        HttpResult pageUrlsResult = httpClientManager.get(url, new HttpParameter[]{
//                new HttpParameter("module", "API"),
//                new HttpParameter("method", "Actions.getPageUrls"),
//                new HttpParameter("idSite", 12),
//                new HttpParameter("filter_limit", "-1"),
//                new HttpParameter("period", "day"),
//                new HttpParameter("date", "today"),
//                new HttpParameter("format", "JSON"),
//                new HttpParameter("flat", "1"),
//                new HttpParameter("token_auth", token)
//        }, "UTF-8");
//
//        System.out.println(pageUrlsResult.getResult());

        System.out.println(PageStat.buildStatId(String.valueOf(WebCacheUtil.getArchiveId("http://www.joyme.com/xinwen/201608/24152631.html?1472093256")), WebCacheSrcType.CMS));

        System.out.println(new Date(1472095200004l));
    }
}
