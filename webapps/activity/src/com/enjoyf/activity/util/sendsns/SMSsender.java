package com.enjoyf.activity.util.sendsns;

/**
 * Created by zhimingli on 2016/8/9 0009.
 */
public interface SMSsender {
    public SendResult sendMessage(String phone, String content);
}
