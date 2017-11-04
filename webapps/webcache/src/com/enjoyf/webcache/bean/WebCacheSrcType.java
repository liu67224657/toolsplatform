package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/24.
 */
public class WebCacheSrcType implements Serializable {

    private static Map<Integer, WebCacheSrcType> map = new LinkedHashMap<Integer, WebCacheSrcType>();

    public static WebCacheSrcType DEFAULT = new WebCacheSrcType(0, "");//
    public static WebCacheSrcType CMS = new WebCacheSrcType(1, "cms");//
    public static WebCacheSrcType WIKI = new WebCacheSrcType(2, "wiki");//

    private int code;
    private String desc;

    public WebCacheSrcType(int c, String d) {
        this.code = c;
        this.desc = d;
        map.put(c, this);
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static WebCacheSrcType getByCode(int c) {
        return map.get(c);
    }

    public static Collection<WebCacheSrcType> getAll() {
        return map.values();
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (code != ((WebCacheSrcType) o).code) return false;
        return true;
    }

}
