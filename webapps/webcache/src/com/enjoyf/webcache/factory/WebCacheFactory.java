package com.enjoyf.webcache.factory;


import com.enjoyf.webcache.bean.WebCacheChannel;
import com.enjoyf.webcache.bean.WebCacheClientType;
import com.enjoyf.webcache.bean.WebCacheSrcType;

import java.util.HashMap;
import java.util.Map;

public class WebCacheFactory {

    private static Map<Integer, IWebCache> map = new HashMap<Integer, IWebCache>();
    static {
        map.put(WebCacheSrcType.CMS.getCode(), new CmsCache());
        map.put(WebCacheSrcType.WIKI.getCode(), new WikiCache());
    }

    public static IWebCache getFactory(WebCacheSrcType srcType) {
        if(map.containsKey(srcType.getCode())){
            return map.get(srcType.getCode());
        }else {
            return new DefaultWebCache();
        }
    }
}
