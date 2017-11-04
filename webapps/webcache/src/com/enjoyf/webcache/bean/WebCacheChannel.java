package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/24.
 */
public class WebCacheChannel implements Serializable {

    private static Map<Integer, WebCacheChannel> map = new LinkedHashMap<Integer, WebCacheChannel>();

    public static WebCacheChannel PC = new WebCacheChannel(0, "pc");//
    public static WebCacheChannel WAP = new WebCacheChannel(1, "wap");//
    public static WebCacheChannel YOUKU = new WebCacheChannel(2, "youku");//

    private int code;
    private String desc;

    public WebCacheChannel(int c, String d) {
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

    public static WebCacheChannel getByCode(int c) {
        return map.get(c);
    }

    public static Collection<WebCacheChannel> getAll() {
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
        if (code != ((WebCacheChannel) o).code) return false;
        return true;
    }

}
