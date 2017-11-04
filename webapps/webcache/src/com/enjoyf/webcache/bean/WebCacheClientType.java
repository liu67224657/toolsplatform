package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/24.
 */
public class WebCacheClientType implements Serializable {

    private static Map<Integer, WebCacheClientType> map = new LinkedHashMap<Integer, WebCacheClientType>();

    public static WebCacheClientType PC = new WebCacheClientType(0, "pc站");//
    public static WebCacheClientType M = new WebCacheClientType(1, "m站");//
    public static WebCacheClientType WANBA = new WebCacheClientType(2, "玩霸");//

    private int code;
    private String desc;

    public WebCacheClientType(int c, String d) {
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

    public static WebCacheClientType getByCode(int c) {
        return map.get(c);
    }

    public static Collection<WebCacheClientType> getAll() {
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
        if (code != ((WebCacheClientType) o).code) return false;
        return true;
    }

}
