package com.enjoyf.activity.container;

import com.enjoyf.framework.memcached.MemCachedConfig;
import com.enjoyf.framework.memcached.MemCachedManager;
import com.enjoyf.framework.redis.RedisConfig;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.StringUtil;

import java.util.Properties;

/**
 * Created by zhimingli on 2016/8/1 0001.
 */
public class PropertiesContainer {

    private static RedisManager redisManager;

    public static Properties prop = new Properties();

    private static MemCachedManager memCachedManager;

    private static String DOMAIN = "";


    private PropertiesContainer() {

    }

    public static RedisManager getRedisManager() {
        if (redisManager == null) {
            synchronized (PropertiesContainer.class) {
                if (redisManager == null) {
                    String host = prop.getProperty("redis_host");
                    int maxActivity = Integer.parseInt(prop.getProperty("redis_maxactivity"));
                    int maxWait = Integer.parseInt(prop.getProperty("redis_maxwait"));
                    int maxIdel = Integer.parseInt(prop.getProperty("redis_maxidel"));
                    String passowrd = prop.getProperty("redis_password");
                    RedisConfig redisConfig = new RedisConfig(host, maxActivity, maxWait, maxIdel, passowrd);
                    redisManager = new RedisManager(redisConfig);
                }
            }
        }
        return redisManager;
    }


    public static MemCachedManager getMemCachedManager() {
        if (memCachedManager == null) {
            synchronized (PropertiesContainer.class) {
                if (memCachedManager == null) {
                    memCachedManager = new MemCachedManager(new MemCachedConfig(prop));
                }
            }
        }
        return memCachedManager;
    }

    public static String getDOMAIN() {
        if (StringUtil.isEmpty(DOMAIN)) {
            DOMAIN = (String) prop.get("domain");
        }
        return DOMAIN;
    }
}
