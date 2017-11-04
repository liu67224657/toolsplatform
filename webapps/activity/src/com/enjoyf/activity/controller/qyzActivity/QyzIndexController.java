package com.enjoyf.activity.controller.qyzActivity;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.Contants;
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
 * Created by wengangsai on 2016/8/01 0029.
 */

@RequestMapping("/activity/qyz")
@Controller
public class QyzIndexController extends BaseRestSpringController {

    private static ActivityCountryService service = new ActivityCountryService();
    private  static String activityCode = "qyz";
    private static  String qyzGameKey = "activity-qyz-gameKey";
    /**
     * 初始化增加用户
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/page")
    public ModelAndView initUserInfo(HttpServletRequest request,
                                     HttpServletResponse response) {
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage =  WeixinUtil.getMapMessage(Contants.QYZ_APPID, Contants.QYZ_SECRET, request, response);
        String openid = (String) mapMessage.get("openid");
        String map = (String) request.getParameter("map");
        if (!StringUtil.isEmpty(openid)) {
            try {
                String ip = getIp(request);
                ActivityCountry  activityCountry  = buildActivityCountry(openid,mapMessage,ip);
                //activityCountry.setNickname(SystemUtil.getSubNickname(activityCountry.getNickname(),4));
                mapMessage.put("activityCountry", activityCountry);
                if (!StringUtil.isEmpty(map)){
                    mapMessage.put("map",map);
                }
            } catch (JoymeDBException e) {
                e.printStackTrace();
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("/jsp/qyz/index", mapMessage);
    }

    private ActivityCountry buildActivityCountry(String openid,Map<String, Object> mapMessage,String ip) throws JoymeServiceException, JoymeDBException {
        ActivityCountry activityCountry = service.getActivityCountry(null, openid,activityCode);
        WeixinUser weixinUser = (WeixinUser) mapMessage.get("weixinUser");
        //初始化个人信息
        if (activityCountry == null) {
            activityCountry = new ActivityCountry();
            activityCountry.setProfileid(openid);
            activityCountry.setCountry(String.valueOf(0));
            activityCountry.setCreate_date(new Date());
            activityCountry.setScore(0);
            activityCountry.setActivity_code(activityCode);
            activityCountry.setValid_status(ValidStatus.VALID);
            activityCountry.setCreate_ip(ip);
            if (weixinUser != null) {
                activityCountry.setNickname(weixinUser.getNickname());
                activityCountry.setHeadimgurl(weixinUser.getHeadimgurl());
            }
            service.insertActivityCountry(null, activityCountry);
        } else {
            //TODO 微信存在第一次获取用户信息失败的情况，需要第二次修改一下
            if (StringUtil.isEmpty(activityCountry.getNickname()) || StringUtil.isEmpty(activityCountry.getHeadimgurl())) {
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
        return activityCountry;
    }


}
