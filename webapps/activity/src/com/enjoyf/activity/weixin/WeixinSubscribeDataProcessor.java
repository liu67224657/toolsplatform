package com.enjoyf.activity.weixin;

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
public class WeixinSubscribeDataProcessor implements WeixinDataProcessor {
    private static final Logger logger = LoggerFactory
            .getLogger(WeixinSubscribeDataProcessor.class);

    /**
     * 关注回复
     *
     * @param requestMap
     * @return
     */
    @Override
    public String processRequest(Map<String, String> requestMap) {
        String respMessage = "";

        String respContent = ""; //默认回复

        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");

        // // 回复文本消息
        RespTextMessage textMessage = new RespTextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);

        StringBuffer sb = new StringBuffer();

        sb.append("HI！感谢你关注《青云志》电视剧唯一正版同名手游。首部资料片《「再」见碧瑶》浓情上线，剧情走向由你决定！").append("\n\n");
        sb.append("还有海量游戏福利及明星周边等你来拿！").append("\n\n\n");
        sb.append("①回复【青云志手游】，查看常见问题~").append("\n");
        sb.append("②回复【新资料片】，查看新资料片详情及服务器常见问题。~").append("\n");
        sb.append("③回复【积分】，查看积分获取及兑换方法~").append("\n");
        sb.append("④回复【问题+您遇到的问题】，我们会尽快为您解答~").append("\n");
        sb.append("如需人工服务，请通过以下方式联系客服反馈您的问题或建议：").append("\n");
        sb.append("青云君后台回复（周一到周五 9:00~18:00）").append("\n");
        sb.append("客服QQ：800017419（周一到周日 9:00~21:00）").append("\n\n\n");
        textMessage.setContent(sb.toString());
        respMessage = MessageUtil.textMessageToXml(textMessage);
        // logger.info(this.getClass().getName() + " Return respMessage:" + respMessage);

        return respMessage;
    }
}
