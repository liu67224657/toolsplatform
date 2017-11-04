package com.enjoyf.activity.bean;

public class GoodsResultCodeConstants extends ResultCodeConstants{

    public static final GoodsResultCodeConstants NOT_MATCH_CONDITION = new GoodsResultCodeConstants(BASE_GOODS_RESCODE+1, "not.match.condition");

    public static final GoodsResultCodeConstants ITEM_EMPTY = new GoodsResultCodeConstants(BASE_GOODS_RESCODE+2, "item.empty");


    public GoodsResultCodeConstants(int code, String msg) {
        super(code, msg);
    }
}
