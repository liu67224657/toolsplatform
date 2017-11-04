package com.enjoyf.activity.cache;

import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.StringUtil;

import java.util.*;

public class GoodsItemRedis {

    private RedisManager redisManager;

    private static final String KEY_PREFIX = "goodsitem_";

    private static final String KEY_GOODS_ITEM_HASH = KEY_PREFIX + "hash_";

    private static final String KEY_GOODS_ITEM_GET_LIST = KEY_PREFIX + "list_";

    private static final String KEY_EXCHANGE_GOODS_ITEM = "exchange_";

    private static final String KEY_ACTIVIIVTY_GOODS_ITEM = "activity_goodsitem_";

    private static final String KEY_ACTIVIIVTY_GET_GOODS_ITEM_BY_GOODID_PROFILEID = "activity_goodsitem_bygoodid_profileid";

    private static final int EXCHANE_GOODSITEM_TIMEOUT = 60 * 60 * 24 * 40;

    public GoodsItemRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    /**
     * 缓存goodsitem
     *
     * @param
     */
    public void putGoodsItem(String gameId, long goodsId, List<GoodsItem> goodsItemList) {
        Map<String, String> map = new HashMap<String, String>();
        String key = getGoodsItemKey(KEY_GOODS_ITEM_HASH, gameId, goodsId, null);
        for (GoodsItem goodsItem : goodsItemList) {
            map.put(String.valueOf(goodsItem.getGoodsItemId()), goodsItem.toJson());
        }
        redisManager.hsetByPipeline(key, map);

    }

    public GoodsItem getGoodsItem(String gameId, long goodsId, long goodsItemId) {
        GoodsItem goodsItem = null;
        String key = getGoodsItemKey(KEY_GOODS_ITEM_HASH, gameId, goodsId, null);
        String data = redisManager.hget(key, String.valueOf(goodsItemId));
        if (!StringUtil.isEmpty(data)) {
            goodsItem = GoodsItem.toObject(data);
        }

        return goodsItem;
    }

    public long removeGoodsItem(String gameId, long goodsId, long goodsItemId) {
        String key = getGoodsItemKey(KEY_GOODS_ITEM_HASH, gameId, goodsId, null);
        return redisManager.delHash(key, String.valueOf(goodsItemId));
    }

    /**
     * 上线兑换码ID入缓存
     *
     * @param gameId
     * @param goodsItemIds
     * @param goodsId
     * @param timeout
     * @param extendKey
     */
    public void putGoodsItemId(String gameId, List<Long> goodsItemIds, long goodsId, int timeout, String extendKey) {
        String key = getGoodsItemKey(KEY_GOODS_ITEM_GET_LIST, gameId, goodsId, extendKey);
        try {//开启事物，防止多个客户端同时提交数据
            List<String> values = new ArrayList<String>();
            for (Long giid : goodsItemIds) {
                values.add(String.valueOf(giid));
            }
            redisManager.saddByTransaction(key, values);
        } catch (Exception e) {
            System.out.println("putGoodsItemId exception:" + e.getMessage());
        }
        /*if (goodsItemIds.size() == 1) {
            redisManager.sadd(key, String.valueOf(goodsItemIds.get(0)), timeout);
        } else {

        }*/
    }

    /**
     * 判断是否存在兑换码列表key
     *
     * @param gameId
     * @param goodsId
     * @param extendKey
     * @return
     */
    public boolean existsKey(String gameId, long goodsId, String extendKey) {
        String key = getGoodsItemKey(KEY_GOODS_ITEM_GET_LIST, gameId, goodsId, extendKey);
        return redisManager.keyExists(key);
    }

    /**
     * 随机取出一个兑换码
     *
     * @param gameId
     * @param goodsId
     * @param extendKey
     * @return
     */
    public long getGoodsItemId(String gameId, long goodsId, String extendKey) {
        long goodsItemId = 0l;
        String key = getGoodsItemKey(KEY_GOODS_ITEM_GET_LIST, gameId, goodsId, extendKey);
        String data = redisManager.spop(key);
        if (!StringUtil.isEmpty(data)) {
            goodsItemId = Long.valueOf(data);
        }
        return goodsItemId;
    }

    /**
     * 统计兑换码数量
     *
     * @param gameId
     * @param goodsId
     * @param extendKey
     * @return
     */
    public long countGoodsItemId(String gameId, long goodsId, String extendKey) {
        String key = getGoodsItemKey(KEY_GOODS_ITEM_GET_LIST, gameId, goodsId, extendKey);
        return redisManager.scard(key);
    }

    /**
     * 存放已兑换的兑换码信息
     *
     * @param gameId
     * @param goodsId
     * @param profileId
     * @param goodsItemId
     * @param extendKey
     */
    public void putExchangeGoodsItem(String gameId, long goodsId, String profileId, long goodsItemId, String extendKey) {
        String key = getGoodsItemKey(KEY_EXCHANGE_GOODS_ITEM, gameId, goodsId, null) + "_" + profileId;
        redisManager.sadd(key, extendKey, EXCHANE_GOODSITEM_TIMEOUT);
    }

    /**
     * 取已经兑换过的兑换码的时间
     *
     * @param gameId
     * @param goodsId
     * @param profileId
     * @param extendKey
     * @return
     */
    public Set<String> getExchangeGoodsItem(String gameId, long goodsId, String profileId, String extendKey) {
        String key = getGoodsItemKey(KEY_EXCHANGE_GOODS_ITEM, gameId, goodsId, null) + "_" + profileId;
        return redisManager.smembers(key);

    }

    /**
     * 判断是否有领取过兑换码的key
     *
     * @param gameId
     * @param goodsId
     * @param profileId
     * @param extendKey
     * @return
     */
    public boolean hasExchangeKey(String gameId, long goodsId, String profileId, String extendKey) {
        String key = getGoodsItemKey(KEY_EXCHANGE_GOODS_ITEM, gameId, goodsId, null) + "_" + profileId;
        return redisManager.keyExists(key);
    }

    private String getGoodsItemKey(String prefix, String gameId, long goodsId, String extendKey) {
        return KEY_PREFIX + prefix + gameId + "_" + goodsId + (StringUtil.isEmpty(extendKey) ? "" : extendKey);
    }


    ///插入数据的时候往redis里面丢数据
    public void pushGoodsItem(Long goods_id, GoodsItem goodsItem) {
        redisManager.lpush(KEY_ACTIVIIVTY_GOODS_ITEM + goods_id, goodsItem.toJson());
    }


    public GoodsItem popGoodsItem(Long goods_id) {
        GoodsItem goodsItem = null;

        String goodsItemJson = redisManager.rpop(KEY_ACTIVIIVTY_GOODS_ITEM + goods_id);
        if (!StringUtil.isEmpty(goodsItemJson)) {
            goodsItem = GoodsItem.toObject(goodsItemJson);
        }
        return goodsItem;
    }


    public void setGoodsItem(Long goods_id, String profileid, GoodsItem goodsItem) {
        redisManager.set(KEY_ACTIVIIVTY_GET_GOODS_ITEM_BY_GOODID_PROFILEID + goods_id + "_" + profileid, goodsItem.toJson());
    }


    public GoodsItem getGoodsItem(Long goods_id, String profileid) {
        GoodsItem goodsItem = null;

        String goodsItemJson = redisManager.get(KEY_ACTIVIIVTY_GET_GOODS_ITEM_BY_GOODID_PROFILEID + goods_id + "_" + profileid);
        if (!StringUtil.isEmpty(goodsItemJson)) {
            goodsItem = GoodsItem.toObject(goodsItemJson);
        }
        return goodsItem;
    }


}
