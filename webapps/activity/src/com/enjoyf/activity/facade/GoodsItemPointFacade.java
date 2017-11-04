package com.enjoyf.activity.facade;

import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.bean.point.GoodsItemResult;
import com.enjoyf.activity.bean.point.GoodsItemResultType;
import com.enjoyf.activity.cache.GoodsItemRedis;
import com.enjoyf.activity.cache.GoodsRedis;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.service.GoodsItemService;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;

import java.util.Date;

/**
 * 领码，1个活动，1个用户只能领取1次
 */
public class GoodsItemPointFacade {

    private GoodsRedis goodsRedis = new GoodsRedis(PropertiesContainer.getRedisManager());

    private GoodsItemRedis goodsItemRedis = new GoodsItemRedis(PropertiesContainer.getRedisManager());

    private GoodsItemService goodsItemService = new GoodsItemService();


    public GoodsItemResult getWeixinItemText(Long goodid, String openid) {
        GoodsItemResult result = new GoodsItemResult();

        Goods goods = goodsRedis.getGoodsId(goodid);

        //无礼包
        if (goods == null) {
            result.setGoodsItemResultType(GoodsItemResultType.GOODS_IS_NULL);
            return result;
        }

        //已结束
        if (goods.getExpireTime().getTime() < new Date().getTime()) {
            result.setGoodsItemResultType(GoodsItemResultType.HAS_END);
            return result;
        }


        try {
            GoodsItem goodsItem = getGoodItem(goodid, openid);
            //已经领取过了
            if (goodsItem != null) {
                result.setGoodsItemResultType(GoodsItemResultType.SUCCESS_RECEIVE);
                result.setGoods_item(goodsItem.getGoodsItemValue());
                return result;
            }


            GoodsItem goodItem = goodsItemRedis.popGoodsItem(goodid);
            if (goodItem == null) {
                //无礼包
                result.setGoodsItemResultType(GoodsItemResultType.FAILED);
            } else {
                goodItem = goodsItemService.getGoodsItemByGameitemvalue(goodid, goodItem.getGoodsItemValue());


                int intval = goodsItemService.updateGoodsItemStatus(goodItem.getGoodsItemId(), openid);

                if (intval > 0) {
                    goodItem = goodsItemService.getGoodsItemByGameitemvalue(goodid, goodItem.getGoodsItemValue());

                    goodsItemRedis.setGoodsItem(goodid, goodItem.getProfileId(), goodItem);

                    //正常领取
                    result.setGoodsItemResultType(GoodsItemResultType.SUCCESS);
                    result.setGoods_item(goodItem.getGoodsItemValue());
                } else {
                    //领取失败
                    result.setGoodsItemResultType(GoodsItemResultType.FAILED);
                }
            }

        } catch (JoymeDBException e) {
            result.setGoodsItemResultType(GoodsItemResultType.SYSTEM_ERROR);//系统错误
            e.printStackTrace();
        }
        return result;
    }

    private GoodsItem getGoodItem(Long goodid, String profileid) {
        GoodsItem goodsItem = goodsItemRedis.getGoodsItem(goodid, profileid);
        if (goodsItem == null) {
            try {
                goodsItem = goodsItemService.getGoodsItemById(goodid, profileid);
                if (goodsItem != null) {
                    goodsItemRedis.setGoodsItem(goodid, profileid, goodsItem);
                }
            } catch (JoymeDBException e) {
                e.printStackTrace();
            }
        }
        return goodsItem;
    }


}
