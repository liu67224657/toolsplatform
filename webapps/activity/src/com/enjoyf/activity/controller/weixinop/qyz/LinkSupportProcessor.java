package com.enjoyf.activity.controller.weixinop.qyz;

import com.enjoyf.activity.facade.QuestionFacade;
import com.enjoyf.activity.weixin.WeixinDataProcessor;
import com.enjoyf.activity.weixin.util.MessageUtil;
import com.enjoyf.activity.weixin.util.resp.RespTextMessage;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
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
public class LinkSupportProcessor implements WeixinDataProcessor {
    private static final Logger logger = LoggerFactory
            .getLogger(LinkSupportProcessor.class);

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

        sb.append("人工服务时间:").append("\n\n");
        sb.append("周一至周日 9:00-21:00 ").append("\n");
        sb.append("\n\n\n");
        sb.append("客服QQ: 800017419");

        respContent = sb.toString();

        textMessage.setContent(respContent);
        respMessage = MessageUtil.textMessageToXml(textMessage);
        return respMessage;
    }
}
