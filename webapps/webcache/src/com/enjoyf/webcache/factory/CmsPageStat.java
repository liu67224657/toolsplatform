package com.enjoyf.webcache.factory;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.webcache.bean.PageStat;
import com.enjoyf.webcache.bean.WebCacheClientType;
import com.enjoyf.webcache.bean.WebCacheSrcType;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.cache.PageStatCacheV2;
import com.enjoyf.webcache.service.PageStatService;
import com.enjoyf.webcache.util.WebCacheUtil;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhitaoshi on 2015/11/25.
 */
public class CmsPageStat extends AbstractPageStat {

    private static PageStatCacheV2 pageStatCache = new PageStatCacheV2();
    private static PageStatService pageStatService = new PageStatService();

    @Override
    public void pageStat(String desUrl, int pv, WebCacheUrlRule webCacheUrlRule, Date beforeDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = beforeDate == null ? new Date() : beforeDate;

        int pageId = WebCacheUtil.getArchiveId(desUrl);
        if (pageId <= 0) {
            return;
        }
        WebCacheSrcType pageType = webCacheUrlRule.getSrcType();
        if (!WebCacheSrcType.CMS.equals(pageType)) {
            return;
        }
        WebCacheClientType clientType = webCacheUrlRule.getClientType();

        String statId = PageStat.buildStatId(String.valueOf(pageId), pageType);

//        JSONObject jsonObject = JSONObject.fromObject(piwikJsonObj);
//        int pvNum = jsonObject.getInt("nb_hits");

        LogService.infoSystemLog("====piwikJob pvNum=" + pv + "," + pageId, true);

        pageStatCache.setPageStatCacheByTimeOut(statId, new Date(), clientType, pv);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("statId", statId);
        jsonObj.put("pageId", String.valueOf(pageId));
        jsonObj.put("pageType", pageType.getCode());
        jsonObj.put("clientType", clientType.getCode());

        pageStatCache.putPageStatCachePool(jsonObj.toString(), df.format(date));
    }
}
