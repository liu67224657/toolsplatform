package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/24.
 */
public class WebCacheCdnType implements Serializable {

    private static Map<Integer, WebCacheCdnType> map = new LinkedHashMap<Integer, WebCacheCdnType>();

    public static WebCacheCdnType NON = new WebCacheCdnType(-1, "");
    public static WebCacheCdnType ALY = new WebCacheCdnType(0, "aly");//阿里云
    public static WebCacheCdnType QN = new WebCacheCdnType(1, "qn");//七牛

    private int code;
    private String desc;

    public WebCacheCdnType(int c, String d) {
        this.code = c;
        this.desc = d;
        map.put(code, this);
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static WebCacheCdnType getByCode(int c) {
        return map.get(c);
    }

    public static Collection<WebCacheCdnType> getAll() {
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
        if (code != ((WebCacheCdnType) o).code) return false;
        return true;
    }

}
