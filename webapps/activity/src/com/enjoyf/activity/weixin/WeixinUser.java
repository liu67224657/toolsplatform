package com.enjoyf.activity.weixin;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by zhimingli on 2016/8/1 0001.
 */
public class WeixinUser implements Serializable {
    private String openid;//用户的标识，对当前公众号唯一
    private String nickname;//用户的昵称


    //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，
    // 0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
    private String headimgurl;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
