package com.enjoyf.activity.controller.weixinop.qyz;

import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.activity.facade.UserinfoFacade;
import com.enjoyf.activity.weixin.WeixinDataProcessor;
import com.enjoyf.activity.weixin.util.MessageUtil;
import com.enjoyf.activity.weixin.util.resp.RespTextMessage;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;
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
public class BindTelephoneProcessor implements WeixinDataProcessor {
    private static final Logger logger = LoggerFactory
            .getLogger(BindTelephoneProcessor.class);
    private UserinfoFacade userinfoFacade = new UserinfoFacade();
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
        try {
            Userinfo userinfo = userinfoFacade.getUserinfo(fromUserName);
            if (userinfo != null && !StringUtil.isEmpty(userinfo.getTelephone())){
                sb.append("这位仙友，您已经关联过手机，关联号码为").append(userinfo.getTelephone()).append(",如需更换，请重新输入手机号进行关联");
            }else {
                sb.append("这位仙友，请输入“您的手机号码”进行关联（示例：12345678912）。关联手机后可以获得积分奖励。青云君友情提示积分可以用来在积分商城中兑换奖励哦！");
            }
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JoymeDBException e) {
            e.printStackTrace();
        }

        respContent = sb.toString();

        textMessage.setContent(respContent);
        respMessage = MessageUtil.textMessageToXml(textMessage);
        return respMessage;
    }
}
