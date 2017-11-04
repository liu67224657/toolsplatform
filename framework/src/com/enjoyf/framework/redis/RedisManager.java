package com.enjoyf.framework.redis;

import com.enjoyf.util.StringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 14/11/18
 * Description:
 */
public class RedisManager {

    private static final String KEY_REDIS_HOST = "redis.host";
    private static final String KEY_REDIS_MAXACTIVE = "redis.maxactvie";
    private static final String KEY_REDIS_MAXWAIT = "redis.maxwait";
    private static final String KEY_REDIS_MAXIDEL = "redis.idel";
    private static final String KEY_REDIS_PASSWORD = "redis.password";

    public static final String RANGE_ORDERBY_DESC = "desc";

    public static final String RANGE_ORDERBY_ASC = "asc";

    protected JedisPool pool;


    public RedisManager(RedisConfig config) {
        init(config);
    }


    private void init(RedisConfig config) {
        String host = config.getHost();
        int maxActive = config.getMaxActive();
        int maxWait = config.getMaxWait();
        int maxIdel = config.getMaxIdel();
        String password=config.getPassowrd();

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxActive(maxActive);
        poolConfig.setMaxIdle(maxIdel);
        poolConfig.setMaxWait(maxWait);

        String[] hosts = host.split(":");

        if (StringUtil.isEmpty(password)) {
            pool = new JedisPool(poolConfig, hosts[0], Integer.parseInt(hosts[1]), 100000);
        }else{
            System.out.println("passowrd is :" +password);
            pool = new JedisPool(poolConfig, hosts[0], Integer.parseInt(hosts[1]), 100000,password);
        }
    }

    public JedisPool getPool() {
        return pool;
    }

    public boolean keyExists(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.exists(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public List<String> lrange(String key, int startIdx, int endIdx) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.lrange(key, startIdx, endIdx);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public String lindex(String key, int i) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.lindex(key, i);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public Long lrem(String key, int count, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.lrem(key, count, value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public void lpush(String key, String[] array) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            redis.lpush(key, array);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }


    public void lpush(String key, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            redis.lpush(key, value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }


    public void rpush(String key, String[] array) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            redis.rpush(key, array);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    @Deprecated
    public void rpush(String key, List<String> list) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            for (String string : list) {
                redis.rpush(key, string);
            }
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public void rpush(String key, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            redis.rpush(key, value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public String lpop(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.lpop(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public String rpop(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.rpop(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public List<String> brpop(int timeOut, String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.brpop(timeOut, key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public String get(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.get(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public void set(String key, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            redis.set(key, value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public void set(String key, String value, int timeOutSec) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            redis.set(key, value);

            if (timeOutSec > 0) {
                redis.expire(key, timeOutSec);
            }
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public Long remove(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.del(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public long length(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.llen(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public void sadd(String key, String[] list) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            redis.sadd(key, list);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public void sadd(String key, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            redis.sadd(key, value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public String spop(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.spop(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public String srandmember(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.srandmember(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public Set<String> smembers(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.smembers(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public boolean sismember(String key,String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.sismember(key,value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }


    /**
     * @param key
     * @param value
     * @param timeOutSec 秒
     */
    public Long incr(String key, long value, int timeOutSec) {
        Jedis redis = null;
        long inc = 0;
        try {
            redis = pool.getResource();

            inc = redis.incrBy(key, value);

            //todo 设置顺序
            if (timeOutSec > 0) {
                redis.expire(key, timeOutSec);
            }

        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
        return inc;
    }


    ////////////////////
    public void zadd(String key, double score, String value, int timeOutSec) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            redis.zadd(key, score, value);

            //todo 设置顺序
            if (timeOutSec > 0) {
                redis.expire(key, timeOutSec);
            }

        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    /**
     * @param key
     * @param start
     * @param end
     * @param orderBy ASC or DESC default asc
     * @return
     */
    public Set<String> zrange(String key, long start, long end, String orderBy) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            if (orderBy.equalsIgnoreCase(RANGE_ORDERBY_DESC)) {
                return redis.zrevrange(key, start, end);
            } else {
                return redis.zrange(key, start, end);
            }
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public long zcard(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.zcard(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public Long zrem(String key, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.zrem(key, value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public Double zincrby(String key, double incScore, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();

            return redis.zincrby(key, incScore, value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }
    public Long zrevrank(String key, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.zrevrank(key,value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }
    public Long zrank(String key, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.zrank(key,value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }
    public Double zscore(String key, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.zscore(key,value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }
    public boolean srem(String key, String url) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.srem(key, url) > 0l;
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public void sadd(String key, String value, int timeSec) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            redis.sadd(key, value);
            if(timeSec > 0){
                redis.expire(key, timeSec);
            }
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public long scard(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.scard(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }


    public Long hset(String key, String field, String value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.hset(key, field, value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public Long hincrBy(String key, String field, long value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.hincrBy(key, field, value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }
    public String hmset(String key, Map<String, String> value) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.hmset(key, value);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public Map<String, String> hgetAll(String key) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.hgetAll(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public List<String> hmget(String key, String... fields) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.hmget(key, fields);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    public String hget(String key, String field) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.hget(key, field);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }


    public long delHash(String key, String... fields) {
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.hdel(key, fields);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }
    
    /**
     * 获取指定key的所有键
     * @param key
     * @return
     */
    public Set<String> hgetAllKeys(String key){
        Jedis redis = null;
        try {
            redis = pool.getResource();
            return redis.hkeys(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }

    /**
     * 获取指定key的值
     * @param key
     * @return
     */
    public List<String> hgetAllValues(String key){
        Jedis redis = null;
        try {
            redis = pool.getResource();
            
            return redis.hvals(key);
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }
    
    /**
     * redis 管道Sadd
     * @return
     */
    public void hsetByPipeline(String key, Map<String,String> values){
        Jedis redis = null;
        try {
            redis = pool.getResource();
            Pipeline pipeline=redis.pipelined();
            for (String keystr : values.keySet()) {
				pipeline.hset(key, keystr, values.get(keystr));
			}
            pipeline.exec();
            
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }
    
    /**
     * redis 事物sadd
     * @return
     */
    public void saddByTransaction(String key, List<String> values){
        Jedis redis = null;
        try {
            redis = pool.getResource();
            long flag=redis.incr(key+"flag");
            if (flag>=1) {
				return;
			}
            Transaction transaction=redis.multi();

			for (String value : values) {
				transaction.sadd(key, value);
			}
			transaction.exec();
        } finally {
            RedisUtil.releaseJedis(pool, redis);
        }
    }
    
}
