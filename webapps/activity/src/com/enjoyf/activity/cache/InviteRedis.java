package com.enjoyf.activity.cache;

import com.enjoyf.activity.bean.invite.InviteInfo;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;

import java.util.List;
import java.util.Set;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:2017/1/3
 */
public class InviteRedis {

    private RedisManager redisManager = null;

    private static final String PREFIX = "activity_invite";
    private static final String KEY_INVITE_OBJ = PREFIX + "_obj_";
    private static final String KEY_INVITE_LIST = PREFIX + "_list_";

    public InviteRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    ///////////////
    public void setInviteObj(InviteInfo info) {
        redisManager.set(KEY_INVITE_OBJ + info.getInviteId(), info.toJson());
    }

    public InviteInfo getInviteObj(String inviteId) {
        return InviteInfo.fromJson(redisManager.get(KEY_INVITE_OBJ+inviteId));
    }

    public boolean delInviteObj(String inviteId) {
        return redisManager.remove(KEY_INVITE_OBJ+inviteId) > 0;
    }

    /////////////
    public void addInviteIdList(String srcProfile, String activityName, long createTimestamp, String inviteId) {
        redisManager.zadd(getKeyInviteList(srcProfile, activityName), createTimestamp, inviteId, -1);
    }

    public Set<String> queryInviteIdList(String srcProfile, String activityName, Pagination page) {
        return redisManager.zrange(getKeyInviteList(srcProfile, activityName), page.getStartRowIdx(), page.getEndRowIdx(), RedisManager.RANGE_ORDERBY_DESC);
    }

    public long counterInviteIdList(String srcProfile, String activityName) {
        return redisManager.zcard(getKeyInviteList(srcProfile, activityName));
    }

    public boolean existsDestProfileId(String srcProfile, String activityName,String inviteId) {
        return redisManager.zscore(getKeyInviteList(srcProfile, activityName),inviteId)!=null;
    }

    private String getKeyInviteList(String srcProfile, String activityName) {
        return KEY_INVITE_LIST + srcProfile + activityName;
    }
    /////////////
}
