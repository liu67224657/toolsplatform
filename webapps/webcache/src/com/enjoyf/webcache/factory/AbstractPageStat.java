package com.enjoyf.webcache.factory;

import com.enjoyf.webcache.bean.WebCacheUrlRule;

import java.util.Date;

/**
 * Created by zhitaoshi on 2015/11/25.
 */
public abstract class AbstractPageStat implements IPageStat {

    @Override
    public void pageStat(String desUrl, int pv, WebCacheUrlRule webCacheUrlRule, Date beforeDate) {
    }
}
