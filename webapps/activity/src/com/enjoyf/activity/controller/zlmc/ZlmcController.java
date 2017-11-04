package com.enjoyf.activity.controller.zlmc;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.sign.Signlog;
import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.service.UserinfoService;
import com.enjoyf.activity.weixin.WeixinUser;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.DateUtil;
import com.enjoyf.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wengangsai on 2016/8/01 0029.
 */

@RequestMapping("/activity/zlmc")
@Controller
public class ZlmcController extends BaseRestSpringController {

    private static String APPID = "wx6adc249f511f2c93";
    private static String SECRET = "fc467e22b19d21a5e045f598744e5841";
    private static ActivityCountryService service = new ActivityCountryService();
    private static UserinfoService userinfoService = new UserinfoService();
    private static String activityCode = "zlmc";
    private static String activityKey = "activity-zlmc";
    private ActivityCountry activityCountry;

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
        Map<String, Object> mapMessage = WeixinUtil.getMapMessage(APPID, SECRET, request, response);
        String openid = (String) mapMessage.get("openid");
        if (!StringUtil.isEmpty(openid)) {
            try {
                String ip = getIp(request);
                ActivityCountry activityCountry = buildActivityCountry(openid, mapMessage, ip);
                mapMessage.put("activityCountry", activityCountry);
            } catch (JoymeDBException e) {
                e.printStackTrace();
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("/jsp/zlmc/index", mapMessage);
    }

    /**
     * 增加用户分数
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addUserScore")
    @ResponseBody
    public String addUserScore(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "openid", required = true) String openid,
                               @RequestParam(value = "score", required = true) String score) throws JSONException, JoymeServiceException, JoymeDBException {
        JSONObject jsonObject = new JSONObject();
        if (StringUtil.isEmpty(openid)) {
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", "微信openid不能为空");
        }
        if (StringUtil.isEmpty(score)) {
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", "分数不能为空");
        }
        ActivityCountry activityCountry = service.getActivityCountry(null, openid, activityCode);
        activityCountry.setScore(Integer.parseInt(score));
        int result = service.updateActivityCountry(null, activityCountry, openid, activityCode);

        if (result >0){
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", "更新分数成功");
        }else {
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", "更新分数失败");
        }

        return jsonObject.toString();
    }

    /**
     * 获取用户名次
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getUserOrder")
    @ResponseBody
    public String getUserOrder(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "openid", required = true) String openid) throws JSONException, JoymeServiceException, JoymeDBException {
        JSONObject jsonObject = new JSONObject();
        if (!StringUtil.isEmpty(openid)) {
            ActivityCountry activityCountry = service.getActivityCountry(null, openid, activityCode);
            if (activityCountry != null) {
                Long userOrder = service.findCountBigThanScore(null, new Date(), activityCode, activityCountry.getScore());
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "success");
                jsonObject.put("result", userOrder);
            } else {
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "error");
                jsonObject.put("result", 0);
            }
        } else {
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", 0);
        }
        return jsonObject.toString();
    }

    /**
     * 增加用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addUserInfo")
    @ResponseBody
    public String addUserInfo(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam(value = "openid", required = true) String openid,
                              @RequestParam(value = "username", required = true) String username,
                              @RequestParam(value = "nickname", required = true) String nickname,
                              @RequestParam(value = "telephone", required = true) String telephone,
                              @RequestParam(value = "address", required = true) String address) throws JSONException, JoymeServiceException, JoymeDBException {
        JSONObject jsonObject = new JSONObject();
        if (StringUtil.isEmpty(openid)) {
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", "增加用户信息失败");
            return jsonObject.toString();
        }
        Userinfo userinfo = userinfoService.queryUserinfobyId(null, openid);

        if (userinfo == null) {
            userinfo = new Userinfo();
            userinfo.setCreateTime(new Date());
            userinfo.setAddress(address);
            userinfo.setNickname(nickname);
            userinfo.setProfileid(openid);
            userinfo.setTelephone(telephone);
            userinfo.setUsername(username);
            userinfoService.insertUserinfo(null, userinfo);
        } else {
            userinfo.setAddress(address);
            userinfo.setNickname(nickname);
            userinfo.setProfileid(openid);
            userinfo.setTelephone(telephone);
            userinfo.setUsername(username);
            userinfoService.updateUserinfo(null, userinfo);
        }
        jsonObject.put("rs", "1");
        jsonObject.put("msg", "success");
        jsonObject.put("result", userinfo);
        return jsonObject.toString();
    }

    /**
     * 获取用户排行信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findRanking")
    @ResponseBody
    public String findRanking(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam(value = "topNum", required = true) String topNum) throws JSONException, JoymeServiceException, JoymeDBException {
        JSONObject jsonObject = new JSONObject();
        if (!StringUtil.isEmpty(topNum)) {
            List<ActivityCountry> activityCountryList = service.findRanking(null, activityCode, Integer.parseInt(topNum));
            List<Map<String,String>> activityCountrys = Lists.newArrayList();
            int order = 1;
            for (ActivityCountry activityCountry : activityCountryList){
                Map<String,String> map = Maps.newHashMap();
                map.put("nickname", activityCountry.getNickname());
                map.put("order",String.valueOf(order));
                map.put("score",String.valueOf(activityCountry.getScore()));
                map.put("headimgurl",activityCountry.getHeadimgurl());
                activityCountrys.add(map);
                order++;
            }
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", activityCountrys);
        } else {
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", "没有排行信息");
        }

        return jsonObject.toString();
    }

    private ActivityCountry buildActivityCountry(String openid, Map<String, Object> mapMessage, String ip) throws JoymeServiceException, JoymeDBException {
        ActivityCountry activityCountry = service.getActivityCountry(null, openid, activityCode);
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
                    int updateResult = service.updateActivityCountry(null, updateCountry, openid, activityCode);
                    if (updateResult > 0) {
                        activityCountry = service.getActivityCountry(null, openid, activityCode);
                    }
                }
            }
        }
        return activityCountry;
    }


}
