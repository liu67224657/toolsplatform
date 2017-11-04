package com.enjoyf.activity.cache;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.StringUtil;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class PointRedis {

    private RedisManager redisManager = null;

    private static final String PREFIX = "activity_point";
    private static final String KEY_POINT = PREFIX + "_up_";

    public PointRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public boolean existsPoint(String pointId) {
        return redisManager.keyExists(KEY_POINT + pointId);
    }

    public boolean increasePoint(int point, String pointId) {
        redisManager.incr(KEY_POINT + pointId, point, -1);
        return true;
    }

    public int getPoint(String pointId) {
        String point = redisManager.get(KEY_POINT + pointId);

        if (StringUtil.isEmpty(point)) {
            return -1;
        }
        try {
            return Integer.parseInt(point);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}