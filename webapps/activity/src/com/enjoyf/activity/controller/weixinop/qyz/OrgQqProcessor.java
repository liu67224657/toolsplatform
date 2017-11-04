package com.enjoyf.activity.controller.weixinop.qyz;

import com.enjoyf.activity.weixin.WeixinDataProcessor;
import com.enjoyf.activity.weixin.util.MessageUtil;
import com.enjoyf.activity.weixin.util.resp.RespTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: pengxu
 * Date: 14-7-22
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */
public class OrgQqProcessor implements WeixinDataProcessor {
    private static final Logger logger = LoggerFactory
            .getLogger(OrgQqProcessor.class);
    @Override
    public String processRequest(Map<String, String> requestMap) {
        String respMessage = "";

        String respContent = ""; //默认回复

        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        // 回复文本消息
        RespTextMessage textMessage = new RespTextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);

        StringBuffer sb = new StringBuffer();

        sb.append("《青云志》 手游官方①群: 391515824 (已满)").append("\n\n");
        sb.append("《青云志》 手游官方②群: 574383561 (已满)").append("\n\n");
        sb.append("《青云志》 手游官方③群: 521810657 (已满)").append("\n\n");
        sb.append("《青云志》 手游官方④群: 160431359 (推荐)").append("\n\n");
        sb.append("《青云志》 手游IOS官方群: 532787171 (推荐)");

        respContent = sb.toString();

        textMessage.setContent(respContent);
        respMessage = MessageUtil.textMessageToXml(textMessage);
        return respMessage;
    }
}
