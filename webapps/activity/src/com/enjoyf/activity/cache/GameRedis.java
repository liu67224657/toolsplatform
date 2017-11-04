package com.enjoyf.activity.cache;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.framework.redis.RedisManager;

/**
 * @author <a href=mailto:wengangsai@staff.joyme.com>wengangsai</a>,Date:16/9/7
 */
public class GameRedis {

    private RedisManager redisManager = null;


    public GameRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public boolean existsGame(String key) {
        return redisManager.keyExists(key);
    }


    public void setGame(String key,String value) {
        redisManager.set(key,value);
    }
    public String getGameLevel(String key) {
        return redisManager.get(key);
    }
}