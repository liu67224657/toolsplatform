package com.enjoyf.activity.controller.weixinop;


import com.enjoyf.activity.bean.point.PointLog;
import com.enjoyf.activity.bean.question.Question;
import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.activity.facade.PointFacade;
import com.enjoyf.activity.facade.QuestionFacade;
import com.enjoyf.activity.facade.SignFacade;
import com.enjoyf.activity.facade.UserinfoFacade;
import com.enjoyf.activity.util.PointReason;
import com.enjoyf.activity.util.sendsns.SMSSenderSngl;
import com.enjoyf.activity.util.sendsns.SMSsender;
import com.enjoyf.activity.weixin.WeixinDataProcessor;
import com.enjoyf.activity.weixin.util.MessageUtil;
import com.enjoyf.activity.weixin.util.resp.Article;
import com.enjoyf.activity.weixin.util.resp.RespNewsMessage;
import com.enjoyf.activity.weixin.util.resp.RespTextMessage;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: pengxu
 * Date: 14-7-22
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */
public class WeixinTextDataProcessor implements WeixinDataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(WeixinTextDataProcessor.class);
    private static final String APPID = "wx6adc249f511f2c93";   //订阅号
    private static final String SECRET = "fc467e22b19d21a5e045f598744e5841 ";   //订阅号
    private static final String GAMECODE = "qyz";   //
    private SignFacade signFacade = new SignFacade();
    private QuestionFacade questionFacade = new QuestionFacade();
    private UserinfoFacade userinfoFacade = new UserinfoFacade();
    private PointFacade pointFacade = new PointFacade();
    private SMSsender smSsender = SMSSenderSngl.get();
    @Override
    public String processRequest(Map<String, String> requestMap) {
        String respMessage = "";
        String respContent = ""; //默认回复

        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");

        String content = requestMap.get("Content"); //文本消息内容
        //  System.out.println("openid=" + fromUserName + ",content=" + content);
        // // 回复文本消息
        RespTextMessage textMessage = new RespTextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);


        //回复图文消息
        RespNewsMessage resNewsMessage = new RespNewsMessage();
        resNewsMessage.setFromUserName(toUserName);
        resNewsMessage.setToUserName(fromUserName);
        resNewsMessage.setCreateTime(new Date().getTime());
        resNewsMessage.setFuncFlag(0);
        resNewsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

        String token = MD5Util.Md5(UUID.randomUUID().toString());
        //判断是否都是数字，都是数字是手机验证码
        String reg = "^[0-9]*[1-9][0-9]*$";
        //绑定手机号
        if (Pattern.compile(reg).matcher(content).find()){//TODO
            StringBuffer sb = new StringBuffer();
            if(content.length()==11){//绑定的手机号
                String telReg = "^[1][3,4,5,8,7][0-9]{9}$";
                if (Pattern.compile(telReg).matcher(content).find()){
                    //1、暂存手机号和验证码
                    userinfoFacade.setUserTel(fromUserName,content);
                    //2、生成验证码存储并存储缓存
                    //生成验证码
                    String  yzm = String.valueOf((int)(Math.random()*9000+1000));//
                    String validCodeDesc = "【青云志手游】亲爱的玩家，您的验证码是" + yzm + "，验证码将于10分钟后过期，请尽快使用。";
                    smSsender.sendMessage(content,validCodeDesc);
                    userinfoFacade.setUserTelYzm(fromUserName,yzm);
                    sb.append("这位仙友，请将您手机上收到的验证码回复给公众号完成关联！").append("\n");
                }else {
                    sb.append("您输入的手机号码格式不正确，请核对后重新输入！").append("\n");
                }
            }else if (content.length()==4){//验证码
                String cacheYzm = userinfoFacade.getUserTelYzm(fromUserName);
                if (!StringUtil.isEmpty(cacheYzm) && content.equals(cacheYzm)){
                    boolean addFlag = true;
                    try {
                        Userinfo userinfo = userinfoFacade.getUserinfo(fromUserName);

                        if (userinfo == null){
                            userinfo = new Userinfo();
                            userinfo.setProfileid(fromUserName);
                            String telephone = userinfoFacade.getUserTel(fromUserName);
                            userinfo.setTelephone(telephone);
                            userinfoFacade.saveUserinfo(userinfo,true);
                            //increasePoint
                            Date date = new Date();
                            PointLog pointLog = new PointLog();
                            pointLog.setCreateTime(new Timestamp(date.getTime()));
                            pointLog.setGamecode("qyz");
                            pointLog.setPoint(20);
                            pointLog.setProfileid(fromUserName);
                            pointLog.setReason(PointReason.BIND_TEL);
                            pointFacade.increasePoint(pointLog);
                        }else {
                            addFlag = false;
                            String telephone = userinfoFacade.getUserTel(fromUserName);
                            userinfo.setTelephone(telephone);
                            userinfoFacade.updateUserinfo(null,userinfo);
                        }

                    } catch (JoymeServiceException e) {
                        e.printStackTrace();
                    } catch (JoymeDBException e) {
                        e.printStackTrace();
                    }
                    if (addFlag){
                        sb.append("恭喜您手机绑定成功，并获得了20点积分，积分可以在积分商城兑换丰富的礼品，可别怪青云君没有提醒你呦\\(^o^)/。").append("\n");
                    }else {
                        sb.append("恭喜您的手机关联成功！").append("\n");
                    }

                }else {
                    sb.append("您输入的验证码不正确，请重新输入。").append("\n");
                }

            }else {
                sb.append("您输入的验证码不正确，请重新输入。").append("\n");
            }
            respContent = sb.toString();
        }else if ("绑定手机".equals(content)) {
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
        }else if ("签到".equals(content)) {
            try {
                respContent = signFacade.checkSign(GAMECODE, fromUserName);
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            } catch (JoymeDBException e) {
                e.printStackTrace();
            }
        } else if (content.trim().toLowerCase().contains("bug") || content.contains("进不了")||content.contains("进不去")||
                content.contains("装不了")||content.contains("玩不了")||content.contains("闪退")||content.contains("卡")) {
            StringBuffer sb = new StringBuffer();
            sb.append("您好，请发送您的机型以及游戏下载渠道，我们将反馈给技术人员。").append("\n");
            sb.append("如需人工帮助，请联系客服：").append("\n");
            sb.append("青云君后台回复（周一到周五 9:00~18:00）").append("\n");
            sb.append("官方QQ客服：800017419（周一到周日 9:00~21:00）").append("\n");
            respContent = sb.toString();
        }  else if ("每日一题".equals(content)) {
            try {
                respContent = questionFacade.getQuestionText(GAMECODE, fromUserName);
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            } catch (JoymeDBException e) {
                e.printStackTrace();
            }
        } else if (content.equals("积分")) {
            List<Article> articleList = new ArrayList<Article>();
            Article article = new Article();
            article.setTitle("<<青云志>>手游微信积分说明");
            article.setPicUrl("http://static.joyme.com/wechat/activity/qyz/tpmmexport1473334334286.jpeg");
            article.setUrl("http://mp.weixin.qq.com/s?__biz=MzIwNjE1NzczMw==&mid=2247485918&idx=3&sn=259ad3911623f2365c89e7e8e15ffc90&chksm=9724ab14a05322025286c986f3133f228c5ebaa46cc501a6394aac52cde41e36a5fd2e9c6841&scene=0#wechat_redirect");
            article.setDescription("");
            articleList.add(article);
            resNewsMessage.setArticleCount(articleList.size());
            resNewsMessage.setArticles(articleList);
            // respMessage是返回值， 这里面的是单图文回复
            respMessage = MessageUtil
                    .newsMessageToXml(resNewsMessage);
            return respMessage;
        } else if (content.equals("积分商城")) {
            List<Article> articleList = new ArrayList<Article>();
            Article article = new Article();
            article.setTitle("<<青云志>>积分商城");
            article.setPicUrl("http://static.joyme.com/wechat/activity/qyz/tpmmexport1473334334286.jpeg");
            article.setUrl("http://wx.hryouxi.com/jeewx/webpage/shangcheng/index.html?openId=" + fromUserName);
            article.setDescription("");
            articleList.add(article);
            resNewsMessage.setArticleCount(articleList.size());
            resNewsMessage.setArticles(articleList);
            // respMessage是返回值， 这里面的是单图文回复
            respMessage = MessageUtil
                    .newsMessageToXml(resNewsMessage);
            return respMessage;
        } else if (content.equals("名人堂")) {
            List<Article> articleList = new ArrayList<Article>();
            Article article = new Article();
            article.setTitle("《青云志》手游至尊名人堂震撼开启！");
            article.setPicUrl("http://static.joyme.com/mobile/cj/images/mrtzzkq.jpg");
            article.setUrl("http://hezuo.joyme.com/new/?c=qyz&a=index&openid=" + fromUserName);
            article.setDescription("");
            articleList.add(article);
            resNewsMessage.setArticleCount(articleList.size());
            resNewsMessage.setArticles(articleList);
            // respMessage是返回值， 这里面的是单图文回复
            respMessage = MessageUtil
                    .newsMessageToXml(resNewsMessage);
            return respMessage;
        } else if (content.equals("我的积分")) {
            int totalScore = 0;
            try {
                totalScore = signFacade.getPointValueByProfileId(fromUserName,GAMECODE);
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            } catch (JoymeDBException e) {
                e.printStackTrace();
            }
            StringBuffer sb = new StringBuffer();
            sb.append("少侠，您目前的可用积分为"+totalScore+"。另外【积分商城】等一大波福利正在路上，届时有各种礼包、手游精美周边，代言人签名照，手机话费、充电宝、耳机、iPhone6s、iPad等等福利等你来拿！");
            respContent = sb.toString();
        } /*else if (content.contains("投票")) {
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
        } */else if (content.contains("青云志手游")) {
            List<Article> articleList = new ArrayList<Article>();
            Article article = new Article();
            article.setTitle("热门问题解答(持续更新)");
            article.setPicUrl("http://static.joyme.com/wechat/activity/qyz/qyzsymmexport1473334307078.jpeg");
            article.setUrl("http://mp.weixin.qq.com/s?__biz=MzIwNjE1NzczMw==&mid=2247485918&idx=6&sn=64129c9e52c3c0f368f1c34704dd2558&chksm=9724ab14a053220210f09074c0a5e2f2bffb45ee8498952f09ca09e3b95336a3cd23a04a230b&scene=0#wechat_redirect");
            article.setDescription("关于《青云志》手游热点问题的回复");
            articleList.add(article);
            resNewsMessage.setArticleCount(articleList.size());
            resNewsMessage.setArticles(articleList);
            // respMessage是返回值， 这里面的是单图文回复
            respMessage = MessageUtil
                    .newsMessageToXml(resNewsMessage);
            return respMessage;
        } else if (content.contains("下载")) {
            StringBuffer sb = new StringBuffer();
            sb.append("《青云志》手游将于9月13日11点全平台公测，IOS客户端将于12日晚开放下载，请耐心等待。安卓客户端下载地址：http://qyz.laohu.com/download1/");
            respContent = sb.toString();
        } else if (content.contains("家族争霸")) {
            StringBuffer sb = new StringBuffer();
            sb.append("《青云志》第一届“超凡家族”评选活动开启，猛戳<a href=\"http://mp.weixin.qq.com/s?__biz=MzIwNjE1NzczMw==&mid=2247486384&idx=1&sn=b6754e4bab7fb53d3f9da95bce2d313a&chksm=9724a97aa053206c354e6994a7fcc011eb131982c2b73b9d102a5a9ebe392579a412047648ee&scene=0#wechat_redirect\">【超凡家族】</a>参与活动。");
            respContent = sb.toString();
        }else if (content.equals("新资料片")) {
            List<Article> articleList = new ArrayList<Article>();
            Article article = new Article();
            article.setTitle("「再」见碧瑶资料片详情&服务器常见问题答疑");
            article.setPicUrl("http://static.joyme.com/mobile/qyz/images/qingyunzhi.jpg");
            article.setUrl("http://mp.weixin.qq.com/s?__biz=MzIwNjE1NzczMw==&mid=2247486514&idx=2&sn=8ca95b30929c033b04476a1dd6c82ca9&chksm=9724aef8a05327ee9372e4245bcb622d6ff269f12805fe37b01e3a3255e7f97d830e510a2140&scene=0#wechat_redirect");
            article.setDescription("");
            articleList.add(article);
            resNewsMessage.setArticleCount(articleList.size());
            resNewsMessage.setArticles(articleList);
            // respMessage是返回值， 这里面的是单图文回复
            respMessage = MessageUtil
                    .newsMessageToXml(resNewsMessage);
            return respMessage;
        }else if (content.equals("Joyme内部测试Joyme")) {
            try {
                Question question = questionFacade.getQuestionByGameCode(GAMECODE);
                if (question != null && question.getCorrectAnswer().equalsIgnoreCase(content)) {
                    questionFacade.saveQuestionLog(question, fromUserName, true, content);
                    int totalScore = signFacade.getPointValueByProfileId(fromUserName, GAMECODE);
                    respContent = fromUserName + "<-1->" + totalScore;
                    questionFacade.removeUserTempQuestion(fromUserName);
                } else {
                    questionFacade.saveQuestionLog(question, fromUserName, false, content);
                    int totalScore = signFacade.getPointValueByProfileId(fromUserName, GAMECODE);
                    respContent = fromUserName + "<-2->" + totalScore;
                    questionFacade.removeUserTempQuestion(fromUserName);
                }
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            } catch (JoymeDBException e) {
                e.printStackTrace();
            }

        } else {
            try {
                String questionLogId = questionFacade.getUserTempQuestion(fromUserName);
                if (StringUtil.isEmpty(questionLogId)) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("您好，有任何问题请直接输入，我们将尽快给您回复。").append("\n");
                    sb.append("如需人工帮助，请联系客服：").append("\n");
                    sb.append("青云君后台回复（周一到周五 9:00~18:00）").append("\n");
                    sb.append("客服QQ：800017419（周一到周日 9:00~21:00）").append("\n\n\n");
                    sb.append("①回复【青云志手游】，查看常见问题~").append("\n");
                    sb.append("②回复【积分】，查看积分获取及兑换方法~").append("\n");
                    sb.append("③回复【问题+您遇到的问题】，我们会尽快为您解答~").append("\n");

                    respContent = sb.toString();
                } else {
                    Question question = questionFacade.getQuestionByGameCode(GAMECODE);
                    if (question != null && question.getCorrectAnswer().equalsIgnoreCase(content)) {
                        questionFacade.saveQuestionLog(question, fromUserName, true, content);
                        int totalScore = signFacade.getPointValueByProfileId(fromUserName, GAMECODE);
                        respContent = "少侠，恭喜您回答正确，获取10积分作为奖励。您当前的可用积分为" + totalScore +
                                "分。积分可以用于兑换丰富的奖励，可别说青云君没提醒你呦＼^O^／~微信内回复“积分”可以了解详细内容呦~";
                        questionFacade.removeUserTempQuestion(fromUserName);
                    } else {
                        questionFacade.saveQuestionLog(question, fromUserName, false, content);
                        int totalScore = signFacade.getPointValueByProfileId(fromUserName, GAMECODE);
                        respContent = "少侠，很遗憾您的答案不对诶。看在少侠您这么努力的份上，还是送您5积分安慰一下您吧。您当前的可用积分为" + totalScore +
                                "分。微信内回复“积分”可以了解详细内容哟~";
                        questionFacade.removeUserTempQuestion(fromUserName);
                    }
                }
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            } catch (JoymeDBException e) {
                e.printStackTrace();
            }
        }


        textMessage.setContent(respContent);
        respMessage = MessageUtil.textMessageToXml(textMessage);

        return respMessage;
    }

    public static void main(String[] args) {
        try {
           /* WeixinTextThread thread = new WeixinTextThread("oiGijjl4LZpK_e29seanhLDkR1J4", "大话西游", APPID, SECRET, redisManager);
            thread.start();*/
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
