package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/24.
 */
public class IntStatus implements Serializable {

    private static Map<Integer, IntStatus> map = new LinkedHashMap<Integer, IntStatus>();

    public static IntStatus REMOVE = new IntStatus(0, "无效");//删除
    public static IntStatus USED = new IntStatus(1, "有效");//使用
    public static IntStatus REVIEW = new IntStatus(2, "预发布");//预发布

    public static IntStatus ING = new IntStatus(3, "进行中");//
    public static IntStatus ERROR = new IntStatus(4, "错误");//

    private int code;
    private String desc;

    public IntStatus(int c, String d) {
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

    public static IntStatus getByCode(int c) {
        return map.get(c);
    }

    public Collection<IntStatus> getAll() {
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
        if (code != ((IntStatus) o).code) return false;
        return true;
    }

}
