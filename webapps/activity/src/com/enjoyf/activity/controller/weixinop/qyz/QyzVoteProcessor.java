package com.enjoyf.activity.controller.weixinop.qyz;

import com.enjoyf.activity.weixin.WeixinDataProcessor;
import com.enjoyf.activity.weixin.util.MessageUtil;
import com.enjoyf.activity.weixin.util.resp.Article;
import com.enjoyf.activity.weixin.util.resp.RespNewsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: pengxu
 * Date: 14-7-22
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */
public class QyzVoteProcessor implements WeixinDataProcessor {
    private static final Logger logger = LoggerFactory
            .getLogger(QyzVoteProcessor.class);
    @Override
    public String processRequest(Map<String, String> requestMap) {
        String respMessage = "";

        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        //回复图文消息
        RespNewsMessage resNewsMessage = new RespNewsMessage();
        resNewsMessage.setFromUserName(toUserName);
        resNewsMessage.setToUserName(fromUserName);
        resNewsMessage.setCreateTime(new Date().getTime());
        resNewsMessage.setFuncFlag(0);
        resNewsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("青云志第一届家族争霸赛投票火热进行中！");
        article.setPicUrl("http://static.joyme.com/mobile/cms/wxqyz/vote/images/vote.png");
        article.setUrl("http://t.cn/RVTTFXq");
        article.setDescription("");
        articleList.add(article);
        resNewsMessage.setArticleCount(articleList.size());
        resNewsMessage.setArticles(articleList);
        // respMessage是返回值， 这里面的是单图文回复
        respMessage = MessageUtil
                .newsMessageToXml(resNewsMessage);
        return respMessage;
    }
}
