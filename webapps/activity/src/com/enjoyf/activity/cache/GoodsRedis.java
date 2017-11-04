package com.enjoyf.activity.cache;

import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.StringUtil;

public class GoodsRedis {

    private String KEY_PREFIX = "goods_";

    private RedisManager redisManager = null;

    private String KEY_PREFIX_GOODS = "activiy_goods_";


    public GoodsRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public void putGoodsCache(Goods goods) {
        redisManager.hset(KEY_PREFIX + goods.getGameId(), String.valueOf(goods.getGoodsId()), goods.toJson());
    }

    public Goods getGoodsCache(String gameId, long goodsId) {
        Goods goods = null;
        String data = redisManager.hget(KEY_PREFIX + gameId, String.valueOf(goodsId));
        if (!StringUtil.isEmpty(data)) {
            goods = Goods.toObject(data);
        }
        return goods;
    }

    public long removeGoodsCache(String gameId, long goodsId) {
        return redisManager.delHash(KEY_PREFIX + gameId, String.valueOf(goodsId));
    }


    ///////////////////////////////////////////////
    public void setGoodsId(Long goods_id, Goods goods) {
        redisManager.set(KEY_PREFIX_GOODS + goods_id, goods.toJson());
    }

    public Goods getGoodsId(Long goods_id) {
        Goods goods = null;
        String goodStr = redisManager.get(KEY_PREFIX_GOODS + goods_id);
        if (!StringUtil.isEmpty(goodStr)) {
            goods = Goods.toObject(goodStr);
        }
        return goods;
    }

    public void removeGoodsId(Long goods_id) {
        redisManager.remove(KEY_PREFIX_GOODS + goods_id);
    }
    ////////////////////////


}
