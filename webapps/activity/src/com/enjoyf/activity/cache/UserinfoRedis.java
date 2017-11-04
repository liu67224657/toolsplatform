package com.enjoyf.activity.cache;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.StringUtil;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class UserinfoRedis {

    private RedisManager redisManager = null;

    private static final String PREFIX = "activity_qyz_userinfo";
    private static final String YZM = "activity_qyz_userinfo_yzm_";
    public UserinfoRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    //判断是否存在手机号
    public boolean existsUserTel(String profileid) {
        return redisManager.keyExists(PREFIX + profileid);
    }
    //暂存手机号
    public boolean setUserTel(String profileid, String telephone) {
        redisManager.set(PREFIX + profileid,telephone);
        return true;
    }
    //从缓存中获取手机号
    public String getUserTel(String profileid) {
        return  redisManager.get(PREFIX + profileid);
    }
    //从缓存中删除手机号
    public void delUserTel(String profileid) {
          redisManager.remove(PREFIX + profileid);
    }

    //判断是否存在手机号验证码
    public boolean existsUserTelYzm(String profileid) {
        return redisManager.keyExists(YZM + profileid);
    }
    //暂存手机号验证码
    public boolean setUserTelYzm(String profileid, String yzm) {
        redisManager.set(YZM + profileid,yzm,10 * 60);
        return true;
    }
    //从缓存中获取手机号验证码
    public String getUserTelYzm(String profileid) {
        return  redisManager.get(YZM + profileid);
    }
    //从缓存中删除手机号验证码
    public void delUserTelYzm(String profileid) {
        redisManager.remove(YZM + profileid);
    }
}