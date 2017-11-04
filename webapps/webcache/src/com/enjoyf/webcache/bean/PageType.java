package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/24.
 */
public class PageType implements Serializable {

    private static Map<Integer, PageType> map = new LinkedHashMap<Integer, PageType>();

    public static PageType DEFAULT = new PageType(0, "");//
    public static PageType CMS = new PageType(1, "cms");//
    public static PageType WIKI = new PageType(2, "wiki");//

    private int code;
    private String desc;

    public PageType(int c, String d) {
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

    public static PageType getByCode(int c) {
        return map.get(c);
    }

    public static Collection<PageType> getAll() {
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
        if (code != ((PageType) o).code) return false;
        return true;
    }

}
