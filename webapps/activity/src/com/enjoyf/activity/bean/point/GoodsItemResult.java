package com.enjoyf.activity.bean.point;


import com.enjoyf.util.JsonBinder;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by zhimingli on 2016/6/13 0013.
 */
public class GoodsItemResult implements Serializable {
    private GoodsItemResultType goodsItemResultType;

    private String goods_item;


    public GoodsItemResultType getGoodsItemResultType() {
        return goodsItemResultType;
    }

    public void setGoodsItemResultType(GoodsItemResultType goodsItemResultType) {
        this.goodsItemResultType = goodsItemResultType;
    }

    public String getGoods_item() {
        return goods_item;
    }

    public void setGoods_item(String goods_item) {
        this.goods_item = goods_item;
    }

    public String toJson() {
        return JsonBinder.buildNormalBinder().toJson(this);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

