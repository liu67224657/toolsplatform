package com.enjoyf.activity.bean.point;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 */
public class GoodsItemResultType implements Serializable {

    private static Map<Integer, GoodsItemResultType> map = new LinkedHashMap<Integer, GoodsItemResultType>();
    public static GoodsItemResultType SYSTEM_ERROR = new GoodsItemResultType(new Integer(-1000));//系统错误
    public static GoodsItemResultType PARAM_EMPTY = new GoodsItemResultType(new Integer(-1001));//参数不能为空
    public static GoodsItemResultType GOODS_IS_NULL = new GoodsItemResultType(new Integer(-1002));//活动不存在

    //the initialize status
    public static GoodsItemResultType HAS_END = new GoodsItemResultType(new Integer(-2));//已结束
    public static GoodsItemResultType NOT_START = new GoodsItemResultType(new Integer(-1));//未开始
    public static GoodsItemResultType FAILED = new GoodsItemResultType(new Integer(0));//无礼包
    public static GoodsItemResultType SUCCESS = new GoodsItemResultType(new Integer(1));//成功
    public static GoodsItemResultType SUCCESS_RECEIVE = new GoodsItemResultType(new Integer(2));//已经领取过
    private Integer code;

    private GoodsItemResultType(Integer code) {
        this.code = code;
        map.put(this.code, this);
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "GoodItemType code:" + code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof GoodsItemResultType) {
            return code.equals(((GoodsItemResultType) obj).getCode());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    public static GoodsItemResultType getByCode(Integer i) {
        if (i == null || i == 0) {
            return null;
        }

        return map.get(i);
    }

    public static Collection<GoodsItemResultType> getAll() {
        return map.values();
    }
}
