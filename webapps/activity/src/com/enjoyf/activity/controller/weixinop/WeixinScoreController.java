package com.enjoyf.activity.controller.weixinop;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.bean.question.Question;
import com.enjoyf.activity.bean.sign.Sign;
import com.enjoyf.activity.controller.weixinop.question.DailyQuestionProcessor;
import com.enjoyf.activity.controller.weixinop.qyz.*;
import com.enjoyf.activity.controller.weixinop.sign.DailySignProcessor;
import com.enjoyf.activity.facade.GoodsFacade;
import com.enjoyf.activity.facade.GoodsItemFacade;
import com.enjoyf.activity.facade.QuestionFacade;
import com.enjoyf.activity.facade.SignFacade;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.util.CookieUtil;
import com.enjoyf.activity.util.ExcelHelper;
import com.enjoyf.activity.util.QuestionUtil;
import com.enjoyf.activity.weixin.WeixinSubscribeDataProcessor;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.activity.weixin.util.MessageUtil;
import com.enjoyf.util.CollectionUtil;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/weixinop/question")
@Controller
public class WeixinScoreController {

    private SignFacade signFacade = new SignFacade();
    private QuestionFacade questionFacade = new QuestionFacade();
    private Logger logger = LoggerFactory.getLogger(WeixinScoreController.class);

    private final static String TOKEN_FOR_WEIXIN = "233up9823401asdjfsfassqyz";
    private static final String GAMECODE = "qyz";   //

    @RequestMapping(method = RequestMethod.GET, value = "/validate" )
    @ResponseBody
    public String jsonPartVote(HttpServletRequest request ,HttpServletResponse response,
                               @RequestParam(value = "timestamp", required = true) String timestamp,
                               @RequestParam(value = "nonce", required = true) String nonce,
                               @RequestParam(value = "echostr", required = true) String echostr) {
        List<String> inputKeyValues = new ArrayList<String>();

        inputKeyValues.add(TOKEN_FOR_WEIXIN);
        inputKeyValues.add(nonce);
        inputKeyValues.add(timestamp);

        Collections.sort(inputKeyValues);

        String returnValue = echostr;

        return returnValue;
    }

    /**
     * 处理微信积分请求
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/validate")
    public String getQuestion(HttpServletRequest request,HttpServletResponse response) {
        String xml = null;
        try {
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get("MsgType");
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                xml = new WeixinTextDataProcessor().processRequest(requestMap);    //文本消息
                //CookieUtil.setCookie(request, response, "cookie_openid", requestMap.get("FromUserName"), 7000);
                return xml;
            }
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) { //事件推送
                String eventType = requestMap.get("Event");    // 事件类型

                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    xml =  new WeixinSubscribeDataProcessor().processRequest(requestMap);   //用户关注后回复的
                    return xml;

                } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
                    String key = requestMap.get("EventKey");

                    if ("dailySign".equals(key)) {
                        xml = new DailySignProcessor().processRequest(requestMap);
                    } else if ("dailyQuestion".equals(key)) {
                        xml = new DailyQuestionProcessor().processRequest(requestMap);
                    } else if ("orgQQ".equals(key)) {
                        xml = new OrgQqProcessor().processRequest(requestMap);
                    } else if ("linkSupport".equals(key)) {
                        xml = new LinkSupportProcessor().processRequest(requestMap);
                    } else if ("bindTelephone".equals(key)) {
                        xml = new BindTelephoneProcessor().processRequest(requestMap);
                    }else if ("scoreMarket".equals(key)) {
                            xml = new ScoreMarketProcessor().processRequest(requestMap);
                    }else if ("qyzVote".equals(key)) {
                        xml = new QyzVoteProcessor().processRequest(requestMap);
                        //CookieUtil.setCookie(request, response, "cookie_openid", requestMap.get("FromUserName"), 7000);
                    }else if(key.startsWith("goodsItem")) {//key是否会等于null?
                    	String[] data=key.split("_");
                    	requestMap.put("goodsId", data[1]);
						xml = new QyzGoodsItemProcessor().processRequest(requestMap);
					}else if(key.startsWith("people")) {//key是否会等于null?
                        xml = new QyzPeopleProcessor().processRequest(requestMap);
                    }else {//TODO 处理默认请求
                        xml = "";
                    }
                }
            }

        } catch (Exception e) {
            logger.error(this.getClass().getName() + " occured ServiceExcpetion.e:", e);
            return null;
        }

        return xml;
    }

    /**
     * 导入积分和每日一题
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping( "/importData")
    public String importData(HttpServletRequest request,
                             HttpServletResponse response){
        String xml = "ok";
        String questionPath =  "D:/dailyQuestion.xlsx";
        String pointPath = "D:/pointHistory.xlsx";
        List<String> questionList = null;
        List<String> pointList = null;
        try {
            questionList = ExcelHelper.exportListFromExcel(new File(questionPath), 0);
            if (questionList!=null && questionList.size()>0){
                int i = 0;
                Calendar calendarFormat = new GregorianCalendar(1900,0,-1);
                Date startDate = calendarFormat.getTime();
                for (String questionStr :questionList) {
                    if (i > 0 ){
                        String [] questionArray = questionStr.split(ExcelHelper.SEPARATOR);
                        int dates =(int) Double.parseDouble(questionArray[0]);
                        Date publishDate = DateUtils.addDays(startDate,dates);
                        String questionId = QuestionUtil.getQuestionId(GAMECODE,publishDate);
                        Question question =  questionFacade.getQuestionById(questionId);
                        if (question==null){
                            question = new Question();
                            String title = questionArray[1];
                            String answers = questionArray[2];
                            String correctAnswer = questionArray[3];
                            question.setQuestionId(questionId);
                            question.setTitle(title);
                            question.setGamecode(GAMECODE);
                            question.setAnswers(answers);
                            question.setCorrectAnswer(correctAnswer);
                            question.setPublishDate(publishDate);
                            questionFacade.saveQuestion(question);
                        }
                    }
                    i++;
                }
            }
            pointList = null;//ExcelHelper.exportListFromExcel(new File(pointPath), 0);
            if (pointList!=null && pointList.size()>0){
                Sign sign = signFacade.getSignByGame(GAMECODE);
                Date today = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH,-1);
                Date yesterday = calendar.getTime();
                int i = 0;
                for (String pointStr :pointList) {
                    if (i>0){
                        String [] pointArray = pointStr.split(ExcelHelper.SEPARATOR);
                        String profileId = pointArray[0];
                        String point = pointArray[1];
                        signFacade.sign(sign,profileId,(int)Double.parseDouble(point));
                       /* String signLogId = SignUtil.getSignLogId(profileId,sign.getSignId(),yesterday);
                        Signlog signlog = signFacade.getSignLog(signLogId);
                        if (signlog ==null){
                            signlog = new Signlog();
                            signlog.setSignlogId(signLogId);
                            signlog.setGamecode(GAMECODE);
                            signlog.setPoint(Integer.parseInt(point));
                            signlog.setProfileId(profileId);
                            signlog.setSignDate(yesterday);
                            signlog.setSignId(sign.getSignId());
                            signlog.setCreateTime(new Timestamp(new Date().getTime()));
                            signFacade.saveSignLog(signlog);
                        }*/
                    }
                    i++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }


    /**
     * 领吗测试
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping( "/getcodetest")
    public String test(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam(value = "gameid", required = true) String gameId,
                             @RequestParam(value = "goodsid", required = true) long goodsId,
                             @RequestParam(value = "profieid", required = true) String profileId){
    	SimpleDateFormat dayFormat=new SimpleDateFormat("yyyyMMdd");
    	    
    	SimpleDateFormat monthFormat=new SimpleDateFormat("yyyyMM");
    	
    	GoodsItemFacade goodsItemFacade=new GoodsItemFacade();
    	
    	 Date date=new Date();
    	 StringBuffer sb = new StringBuffer();
         /*
          * 检查用户是否在本月已领取过
          */
         Set<String> isReceivedGoodsItemSet=goodsItemFacade.isReceivedGoodsItem(gameId, goodsId, profileId, monthFormat.format(date));
         if (!CollectionUtil.isEmpty(isReceivedGoodsItemSet)) {//本月已领取过激活码
        	 for (String exchangeTime : isReceivedGoodsItemSet) {
				if (exchangeTime.contains(monthFormat.format(date))) {
					sb.append("少侠本月已经来过了哦，给别人一点机会吧！请继续关注公众号，还有其他福利在等着你呦。");
		 			return sb.toString();
				}
			}
 		 }

         /*
          * 检查当天是否还有剩余激活码可领取
          */
         boolean hasSurplusGoodsItems=goodsItemFacade.hasSurplusGoodsItem(gameId, goodsId, dayFormat.format(date));
         if (!hasSurplusGoodsItems) {
 			sb.append("少侠您来的太晚了，今日礼包已被洗劫一空，请明天早些来抢呦。");
 			return sb.toString();
 		}
         
         GoodsItem goodsItem;
 		try {
 			goodsItem = goodsItemFacade.getGoodsItem(gameId, goodsId, profileId, 200, dayFormat.format(date), Contants.GOODS_CATEGORY_DESPATCH);
 			if (null == goodsItem) {
 				sb.append("少侠您来的太晚了，今日礼包已被洗劫一空，请明天早些来抢呦。");
 				return sb.toString();
 			}
 			
 	        sb.append("少侠今天运气不错，来的很及时啊，青云君赏赐礼包一个：");
 	        sb.append(goodsItem.getGoodsItemValue());
 	        sb.append("（此次礼包仅限官服使用）。更多礼包也会不定期更新，记得每天来看下呦。。");
 	        return sb.toString();
 		}catch (Exception e) {
			System.out.println("==========="+e.getMessage());
		}

    	return "";
    }
    
}
