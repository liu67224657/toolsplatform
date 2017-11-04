package com.enjoyf.webcache.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/24.
 */
public class ActStatus implements Serializable {

    private static Map<Integer, ActStatus> map = new LinkedHashMap<Integer, ActStatus>();

    public static ActStatus REMOVE = new ActStatus(0, "无效");//删除
    public static ActStatus USED = new ActStatus(1, "有效");//使用
    public static ActStatus REVIEW = new ActStatus(2, "预发布");//预发布

    public static ActStatus ING = new ActStatus(3, "进行中");//
    public static ActStatus ERROR = new ActStatus(4, "错误");//

    private int code;
    private String desc;

    public ActStatus(int c, String d) {
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

    public static ActStatus getByCode(int c) {
        return map.get(c);
    }

    public Collection<ActStatus> getAll() {
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
        if (code != ((ActStatus) o).code) return false;
        return true;
    }

}
