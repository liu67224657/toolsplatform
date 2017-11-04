package com.enjoyf.activity.cache;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.DateUtil;
import com.enjoyf.util.StringUtil;

import java.util.Date;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class QyzVoteRedis {

    private RedisManager redisManager = null;

    private static final String PREFIX = "activity_qyz_vote_";

    private static final String PERSON_PREFIX = PREFIX  + "person_";


    public QyzVoteRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public void incrFamilyPoll(String appid, String familyCode,String openid, long polls) {
       String value = redisManager.hget(PREFIX + appid,familyCode);
        if (StringUtil.isEmpty(value)){
            redisManager.hset(PREFIX + appid,familyCode,String.valueOf(polls));
        }else {
            //为家族增加投票
            redisManager.hincrBy(PREFIX + appid,familyCode,polls);
        }
        //添加个人投票记录
        Date date = new Date();
        String strDate = "_" + DateUtil.formatDateToString(date,DateUtil.PATTERN_DATE);
        redisManager.set(PERSON_PREFIX + openid + strDate,String.valueOf(polls),60*60*24);
    }

    public String getTotalPollsByFamilyCode(String appid, String familyCode) {
        String value = redisManager.hget(PREFIX + appid,familyCode);
        return StringUtil.isEmpty(value)?"0":value;
    }

    public int getUserVote(String openid) {
        //添加个人投票记录
        Date date = new Date();
        String strDate = "_" + DateUtil.formatDateToString(date,DateUtil.PATTERN_DATE);
        String value = redisManager.get(PERSON_PREFIX + openid + strDate);
        return StringUtil.isEmpty(value)?0:Integer.parseInt(value);
    }

    public void addFamilyPoll(String appid, String familyCode, String polls) {
        //为家族增加投票
        redisManager.hincrBy(PREFIX + appid,familyCode,Long.parseLong(polls));
    }
}