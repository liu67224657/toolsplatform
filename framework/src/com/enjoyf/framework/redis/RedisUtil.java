package com.enjoyf.framework.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 14/11/18
 * Description:
 */
public class RedisUtil {
    public static void releaseJedis(JedisPool pool,Jedis redis){
        if (redis!=null && pool!=null) {
            pool.returnResource(redis);
        }
    }
}
