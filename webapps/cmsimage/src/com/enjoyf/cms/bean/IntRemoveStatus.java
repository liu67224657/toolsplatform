package com.enjoyf.cms.bean;

import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhitaoshi on 2015/7/24.
 */
public class IntRemoveStatus implements Serializable {

    private static Map<Integer, IntRemoveStatus> map = new LinkedHashMap<Integer, IntRemoveStatus>();

    public static IntRemoveStatus REMOVE = new IntRemoveStatus(0, "无效");//删除
    public static IntRemoveStatus USED = new IntRemoveStatus(1, "有效");//使用
    public static IntRemoveStatus REVIEW = new IntRemoveStatus(2, "预发布");//预发布

    public static IntRemoveStatus ING = new IntRemoveStatus(3, "进行中");//
    public static IntRemoveStatus ERROR = new IntRemoveStatus(4, "错误");//

    private int code;
    private String desc;

    public IntRemoveStatus(int c, String d) {
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

    public static IntRemoveStatus getByCode(int c) {
        return map.get(c);
    }

    public Collection<IntRemoveStatus> getAll() {
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
        if (code != ((IntRemoveStatus) o).code) return false;
        return true;
    }

}
