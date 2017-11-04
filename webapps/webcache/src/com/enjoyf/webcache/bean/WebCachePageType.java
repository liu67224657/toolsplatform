package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/24.
 */
public class WebCachePageType implements Serializable {

    private static Map<Integer, WebCachePageType> map = new LinkedHashMap<Integer, WebCachePageType>();

    public static WebCachePageType NO_NUMERIC = new WebCachePageType(0, "非数字ID");//删除
    public static WebCachePageType NUMBER = new WebCachePageType(1, "数字ID");//使用

    private int code;
    private String desc;

    public WebCachePageType(int c, String d) {
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

    public static WebCachePageType getByCode(int c) {
        return map.get(c);
    }

    public static Collection<WebCachePageType> getAll() {
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
        if (code != ((WebCachePageType) o).code) return false;
        return true;
    }

}
