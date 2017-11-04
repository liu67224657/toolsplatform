package com.enjoyf.webcache.factory;

import com.enjoyf.webcache.bean.WebCacheUrlRule;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * Created by zhitaoshi on 2015/11/25.
 */
public interface IPageStat {
    void pageStat(String desUrl, int pv, WebCacheUrlRule webCacheUrlRule, Date beforeDate);
}
