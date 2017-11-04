package com.enjoyf.activity.controller;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.SystemUtil;
import com.enjoyf.activity.weixin.WeixinUser;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhimingli on 2016/7/29 0029.
 */

@RequestMapping("/activity/yulong")
@Controller
public class YulongController extends BaseRestSpringController {
    //todo 不应该是static
    private static ActivityCountryService service = new ActivityCountryService();

    //腾讯游戏官方互动
    //Gongzhonghao_2@qq.com
    // TXgongzhonghao003
    private static String APPID = "wx8e5bbc9c79b5686d";
    private static String SECRET = "283d3e3623688395dffccf290b173090";
    private  static String activityCode = "yulong";
    @RequestMapping("/sharepage")
    public ModelAndView sharepage(HttpServletRequest request) {
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage = null;

        return new ModelAndView("/jsp/shareshengzhi", mapMessage);
    }

    @RequestMapping("/page")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) {
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage = null;
        mapMessage = WeixinUtil.getMapMessage(APPID, SECRET, request, response);
        //根据openid获取用户选择的国家
        String openid = (String) mapMessage.get("openid");
        if (!StringUtil.isEmpty(openid)) {
            try {
                ActivityCountry activityCountry = service.getActivityCountry(null, openid,activityCode);

                //初始化国家
                if (activityCountry == null) {
                    activityCountry = new ActivityCountry();
                    activityCountry.setProfileid(openid);
                    activityCountry.setCountry(String.valueOf(0));
                    activityCountry.setCreate_date(new Date());
                    activityCountry.setActivity_code(activityCode);
                    activityCountry.setScore(0);
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
                            int updateResult = service.updateActivityCountry(null, updateCountry, openid,activityCode);
                            if (updateResult > 0) {
                                activityCountry = service.getActivityCountry(null, openid,activityCode);
                            }
                        }
                    }
                }
                activityCountry.setNickname(SystemUtil.getSubNickname(activityCountry.getNickname(),6));
                mapMessage.put("activityCountry", activityCountry);
            } catch (JoymeDBException e) {
                e.printStackTrace();
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("/jsp/shengzhi", mapMessage);
    }

}
