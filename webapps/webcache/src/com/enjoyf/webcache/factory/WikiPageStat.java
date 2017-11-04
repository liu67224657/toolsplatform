package com.enjoyf.webcache.factory;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.HttpClientManager;
import com.enjoyf.util.HttpParameter;
import com.enjoyf.util.HttpResult;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.bean.PageStat;
import com.enjoyf.webcache.bean.WebCacheClientType;
import com.enjoyf.webcache.bean.WebCacheSrcType;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.cache.PageStatCache;
import com.enjoyf.webcache.cache.PageStatCacheV2;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.service.PageStatService;
import com.enjoyf.webcache.util.WebCacheUtil;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/11/25.
 */
public class WikiPageStat extends AbstractPageStat {

    private static PageStatCacheV2 pageStatCache = new PageStatCacheV2();
    private static PageStatService pageStatService = new PageStatService();

    @Override
    public void pageStat(String desUrl, int pv, WebCacheUrlRule webCacheUrlRule, Date beforeDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//todo 不需要了 用pageStatCacheV2
        Date date = beforeDate == null ? new Date() : beforeDate;

        String wikiId = WebCacheUtil.getWikiId(desUrl);
        if (StringUtil.isEmpty(wikiId)) {
            return;
        }
        String wikiKey = webCacheUrlRule.getPageKey();
        if (StringUtil.isEmpty(wikiKey)) {
            wikiKey = WebCacheUtil.getWikiKey(desUrl);
        }
        if (StringUtil.isEmpty(wikiKey)) {
            return;
        }
        String pageId = wikiKey + "|" + wikiId;

        WebCacheSrcType pageType = webCacheUrlRule.getSrcType();
        if (!WebCacheSrcType.WIKI.equals(pageType)) {
            return;
        }
        WebCacheClientType clientType = webCacheUrlRule.getClientType();

        String statId = PageStat.buildStatId(String.valueOf(pageId), pageType);

//        JSONObject jsonObject = JSONObject.fromObject(piwikJsonObj);
//        int pvNum = jsonObject.getInt("nb_hits");

        LogService.infoSystemLog("====piwikJob pvNum=" + pv + "," + pageId, true);

        pageStatCache.putPageStatCache(statId,date, clientType, pv);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("statId", statId);
        jsonObj.put("pageId", pageId);
        jsonObj.put("pageType", pageType.getCode());
        jsonObj.put("clientType", clientType.getCode());

        pageStatCache.putPageStatCachePool(jsonObj.toString(), df.format(date));
    }
}
