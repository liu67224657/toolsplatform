package com.enjoyf.activity.weixin.util.resp;

/**
 * Created by IntelliJ IDEA.
 * User: pengxu
 * Date: 14-5-14
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
public class RespTextMessage extends RespBaseMessage{
    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
