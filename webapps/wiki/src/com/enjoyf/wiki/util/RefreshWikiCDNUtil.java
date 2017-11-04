package com.enjoyf.wiki.util;

import com.aliyun.api.cdn.cdn20141111.response.RefreshObjectCachesResponse;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.cdn.AliYunCDNRefresh;
import com.taobao.api.ApiException;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/7/21
 * Description:
 */
public class RefreshWikiCDNUtil {

    public static String refreshUrl(String url) {
        String result = "";
        try {
            RefreshObjectCachesResponse response = AliYunCDNRefresh.refreshCDN(url, AliYunCDNRefresh.CDN_TYPE_FILE);
            result = response.getRefreshTaskId();
        } catch (Exception e) {
            LogService.errorSystemLog("refreshWiki error.e",e);
        }

        return result;
    }

    public static String refreshWiki(String wikiHost) {
        String result = "";
        try {
            RefreshObjectCachesResponse response = AliYunCDNRefresh.refreshCDN(wikiHost, AliYunCDNRefresh.CDN_TYPE_DIRECTORY);
            result = response.getRefreshTaskId();
        } catch (Exception e) {
            LogService.errorSystemLog("refreshWiki error.e",e);
        }

        return result;
    }

}
