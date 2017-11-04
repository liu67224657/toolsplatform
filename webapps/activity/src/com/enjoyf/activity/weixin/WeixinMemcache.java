package com.enjoyf.activity.weixin;

import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.framework.memcached.MemCachedManager;

/**
 * Created by zhimingli on 2016/8/1 0001.
 */
public class WeixinMemcache {

    private static final long TIME_OUT_SEC = 60l * 100l;

    private static final String KEY_PREFIX = "api_activity";

    private static final String GET_ACCESSTOKEN = "_weixin_access_token_";

    private static final String GET_TICKET = "get_ticket";

    private static final String GET_USER = "get_user";

    private MemCachedManager manager = null;

    public WeixinMemcache() {
        manager = PropertiesContainer.getMemCachedManager();
    }

    public void putAccessToken(String appId, AccessToken accessToken) {
        manager.put(KEY_PREFIX + GET_ACCESSTOKEN + appId, accessToken, TIME_OUT_SEC);
    }

    public AccessToken getAccessToken(String appId) {
        Object token = manager.get(KEY_PREFIX + GET_ACCESSTOKEN + appId);
        if (token == null) {
            return null;
        }
        return (AccessToken) token;
    }


    public void putTicket(String access_token, String ticket) {
        manager.put(KEY_PREFIX + GET_TICKET + access_token, ticket, TIME_OUT_SEC);
    }

    public String getTicket(String access_token) {
        Object ticket = manager.get(KEY_PREFIX + GET_TICKET + access_token);
        if (ticket == null) {
            return null;
        }
        return (String) ticket;
    }


    public void putUser(String openid, WeixinUser weixinUser) {
        manager.put(KEY_PREFIX + GET_USER + openid, weixinUser, TIME_OUT_SEC);
    }

    public WeixinUser getUser(String openid) {
        Object ticket = manager.get(KEY_PREFIX + GET_USER + openid);
        if (ticket == null) {
            return null;
        }
        return (WeixinUser) ticket;
    }


}
