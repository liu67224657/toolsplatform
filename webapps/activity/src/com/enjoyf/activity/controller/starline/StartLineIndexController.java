package com.enjoyf.activity.controller.starline;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.SystemUtil;
import com.enjoyf.activity.weixin.WeixinUser;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.DateUtils;
import com.enjoyf.util.StringUtil;
import com.google.common.collect.Lists;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by wengangsai on 2016/8/01 0029.
 */

@RequestMapping("/activity/starline")
@Controller
public class StartLineIndexController extends BaseRestSpringController {

    private static String APPID = "wx8e5bbc9c79b5686d";
    private static String SECRET = "283d3e3623688395dffccf290b173090";
    private static ActivityCountryService service = new ActivityCountryService();
    private  static String activityCode = "starline";
    private static  String starlineScoreKey = "activity-starline-scoreKey";
    /**
     * 查询客户相关信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/page")
    public ModelAndView findUserInfo(HttpServletRequest request,
                                     HttpServletResponse response) {
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage = null;
        mapMessage = WeixinUtil.getMapMessage(APPID, SECRET, request, response);
        //根据openid获取用户选择的国家
        String openid = (String) mapMessage.get("openid");
        if (!StringUtil.isEmpty(openid)) {
            try {
                ActivityCountry activityCountry = service.getActivityCountry(null, openid,activityCode);

                //初始化个人信息
                if (activityCountry == null) {
                    activityCountry = new ActivityCountry();
                    activityCountry.setProfileid(openid);
                    activityCountry.setCountry(String.valueOf(0));
                    activityCountry.setCreate_date(new Date());
                    activityCountry.setScore(0);
                    activityCountry.setActivity_code(activityCode);
                    activityCountry.setValid_status(ValidStatus.VALID);
                    activityCountry.setCreate_ip(getIp(request));
                    WeixinUser weixinUser = (WeixinUser) mapMessage.get("weixinUser");
                    if (weixinUser != null) {
                        activityCountry.setNickname(weixinUser.getNickname());
                        activityCountry.setHeadimgurl(weixinUser.getHeadimgurl());
                    }
                    service.insertActivityCountry(null, activityCountry);
                } else {
                    //TODO 微信存在第一次获取用户信息失败的情况，需要第二次修改一下
                    if (StringUtil.isEmpty(activityCountry.getNickname()) || StringUtil.isEmpty(activityCountry.getHeadimgurl())) {
                        WeixinUser weixinUser = (WeixinUser) mapMessage.get("weixinUser");
                        if (weixinUser != null) {
                            ActivityCountry updateCountry = new ActivityCountry();
                            updateCountry.setNickname(weixinUser.getNickname());
                            updateCountry.setHeadimgurl(weixinUser.getHeadimgurl());
                            //updateCountry.setScore(0);
                            int updateResult = service.updateActivityCountry(null, updateCountry, openid,activityCode);
                            if (updateResult > 0) {
                                activityCountry = service.getActivityCountry(null, openid,activityCode);
                            }
                        }
                    }
                }
                double initialScore = Double.parseDouble(String.valueOf("-" + 0 + "."+ DateUtils.convert2String(System.currentTimeMillis(),"yyyyMMddHHmmss")));
                PropertiesContainer.getRedisManager().zadd(starlineScoreKey,initialScore,openid,3600 * 24 * 30);
                activityCountry.setNickname(SystemUtil.getSubNickname(activityCountry.getNickname(),4));
                mapMessage.put("activityCountry", activityCountry);
            } catch (JoymeDBException e) {
                e.printStackTrace();
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("/jsp/starline/index", mapMessage);
    }

    /**
     * 更新分数返回排名
     * @param request
     * @param response
     * @param openId
     * @return
     */
    @RequestMapping("/findOrderByScore")
    @ResponseBody
    public String findOrderByScore(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "openId", required = true) String openId,
                               @RequestParam(value = "score", required = true) String score) {
        JSONObject jsonObject = new JSONObject();
        try {
            int intScore;
            Long userOrder;
            boolean equalFlag = true;
            if (!StringUtil.isEmpty(score)){
                //更新最新分数
                intScore = Integer.parseInt(score);
                ActivityCountry activityCountry = service.getActivityCountry(null, openId,activityCode);
                Date orderDate = new Date();
                if (activityCountry != null){
                    if (activityCountry.getScore()<intScore){
                        activityCountry.setScore(intScore);
                        activityCountry.setCreate_date(new Date());
                        service.updateActivityCountry(null,activityCountry,openId,activityCode);
                        equalFlag = false;
                    }else  if (activityCountry.getScore() == intScore) {
                        equalFlag = false;
                        orderDate = activityCountry.getCreate_date();
                    }
                    double initialScore ;
                    if (intScore == 0){
                         initialScore = Double.parseDouble(String.valueOf("-" + intScore+ "."+ DateUtils.convert2String(System.currentTimeMillis(),"yyyyMMddHHmmss")));
                    }else {
                         initialScore = Double.parseDouble(String.valueOf(-intScore+ "."+ DateUtils.convert2String(System.currentTimeMillis(),"yyyyMMddHHmmss")));
                    }
                    PropertiesContainer.getRedisManager().zadd(starlineScoreKey,initialScore,openId,3600 * 24 * 30);
                }
                //userOrder = PropertiesContainer.getRedisManager().zrank(starlineScoreKey,openId);
                userOrder = service.findCountBigThanScore(null,orderDate,activityCode,intScore);
            }else{
                userOrder = PropertiesContainer.getRedisManager().zcard(starlineScoreKey);
            }
            if (equalFlag){
                userOrder ++;
            }
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", String.valueOf(userOrder));
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    /**
     * 获取排行榜，去前100用户
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findTopHundred")
    @ResponseBody
    public String findTopHundred(HttpServletRequest request,
                                   HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        List<String> userOrderList = Lists.newArrayList();
        try {
            //通过数据库查询
            List<ActivityCountry> activityCountryList = service.findTopHundredActivityCountryByActivityCode(null,activityCode);
            if (activityCountryList != null && activityCountryList.size() > 0){
                int rank = 1;
                for (ActivityCountry activityCountry : activityCountryList){
                    userOrderList.add(rank + "---" + activityCountry.getHeadimgurl() + "---" + SystemUtil.getSubNickname(activityCountry.getNickname(),4) + "---" +  activityCountry.getScore());
                    rank ++;
                }
            }
           /* //通过redis查询，但是不能实现在排序相同的情况下先玩的人排序在前
           Set<String> userSet = PropertiesContainer.getRedisManager().zrange(starlineScoreKey,0,100, RedisManager.RANGE_ORDERBY_ASC);
            for (Iterator<String> it = userSet.iterator(); it.hasNext();){
                String openId = it.next();
                WeixinUser weixinUser = WeixinUtil.getWeixinUser(APPID,SECRET,openId);
                String nickname = null;
                String headimgurl = null;
                ActivityCountry activityCountry = null;
                if (weixinUser == null){
                    activityCountry = service.getActivityCountry(null,openId,activityCode);
                    if (activityCountry !=null){
                        nickname = SystemUtil.getSubNickname(activityCountry.getNickname(),4);
                        headimgurl = activityCountry.getHeadimgurl();
                    }
                }else {
                    nickname = SystemUtil.getSubNickname(weixinUser.getNickname(),4);
                    headimgurl = weixinUser.getHeadimgurl();
                }
                Double score = PropertiesContainer.getRedisManager().zscore(starlineScoreKey,openId);
                Long rank = PropertiesContainer.getRedisManager().zrank(starlineScoreKey,openId);
                userOrderList.add(rank+1 + "-" + headimgurl + "-" + nickname + "-" +  Math.abs(score.intValue()));
            }*/
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", userOrderList);
        }  catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }



}
