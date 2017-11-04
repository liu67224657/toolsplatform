package com.enjoyf.activity.controller.weixinop.sign;

import com.enjoyf.activity.facade.SignFacade;
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
public class DailySignProcessor implements WeixinDataProcessor {
    private static final Logger logger = LoggerFactory
            .getLogger(DailySignProcessor.class);
    private SignFacade signFacade = new SignFacade();
    private static final String GAMECODE = "qyz";   //
    @Override
    public String processRequest(Map<String, String> requestMap) {
        String respMessage = "";



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
        String respContent = "";
        try {
            respContent = signFacade.checkSign(GAMECODE,fromUserName);
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JoymeDBException e) {
            e.printStackTrace();
        }

        textMessage.setContent(respContent);
        respMessage = MessageUtil.textMessageToXml(textMessage);
        return respMessage;
    }
}
