package com.enjoyf.webcache.factory;

import com.enjoyf.webcache.bean.WebCacheSrcType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/11/25.
 */
public class PageStatFactory {

    private static Map<Integer, IPageStat> map = new HashMap<Integer, IPageStat>();
    static {
        map.put(WebCacheSrcType.CMS.getCode(), new CmsPageStat());
        map.put(WebCacheSrcType.WIKI.getCode(), new WikiPageStat());
    }

    public static IPageStat factory(WebCacheSrcType srcType){
        if(map.containsKey(srcType.getCode())){
            return map.get(srcType.getCode());
        }else {
            return new DefaultPageStat();
        }
    }

}
