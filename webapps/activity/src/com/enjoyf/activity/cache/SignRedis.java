package com.enjoyf.activity.cache;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.sign.Sign;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class SignRedis {

    private RedisManager redisManager = null;

    private static final String PREFIX = "activity_sign_";
    private static final String KEY_SIGNLOG = PREFIX + "sl_";
    private static final String KEY_SIGN = PREFIX + "s_";

    private static final int TIME_OUT = 3600 * 24 * 2;

    public SignRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public boolean setSignLogId(String signLogId) {
        redisManager.set(KEY_SIGNLOG + signLogId, signLogId, TIME_OUT);
        return true;
    }

    public boolean isSign(String signLogId) {
        return redisManager.keyExists(KEY_SIGNLOG + signLogId);
    }

    public void setSignByGameCode(String gameCode, Sign sign) {
        redisManager.set(KEY_SIGN + gameCode, new Gson().toJson(sign));
    }

    public Sign getSignByGameCode(String gameCode) {
        String signObj = redisManager.get(KEY_SIGN + gameCode);
        if (StringUtil.isEmpty(signObj)) {
            return null;
        }

        try {
            return new Gson().fromJson(signObj, Sign.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }
}