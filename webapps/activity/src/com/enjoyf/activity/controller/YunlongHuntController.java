package com.enjoyf.activity.controller;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.dto.YulongHuntDto;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.controller.base.ActivityConstants;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.CookieUtil;
import com.enjoyf.activity.weixin.WeixinUser;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by zhimingli on 2016/8/24 0024.
 * 御龙在天第二个h5,寻宝
 */
@Controller
@RequestMapping("/activity/yulonghunt")
public class YunlongHuntController extends BaseRestSpringController {

    private static String activityCode = "yulonghunt";

    private ActivityCountryService service = new ActivityCountryService();

    //腾讯游戏官方互动 Gongzhonghao_2@qq.com TXgongzhonghao003

    private static String APPID = "wx8e5bbc9c79b5686d";
    private static String SECRET = "283d3e3623688395dffccf290b173090";

    //着迷测试服务号
//    private static String APPID = "wx758f0b2d30620771";
//    private static String SECRET = "b58cd348c7f5908055e5e691eed45c39";

    //蓝点的key
    private static String BLUE_KEY = ActivityConstants.KEY_PREFIX + "yulong_blue_";

    //国家的key
    private static String COUNTRY_KEY = ActivityConstants.KEY_PREFIX + "yulong_country_";

    //个人的initpage
    private static String INITPAGE_KEY = ActivityConstants.KEY_PREFIX + "yulong_initpage_";

    //测试的步数
    private static String TEST_KEY = ActivityConstants.KEY_PREFIX + "yulong_test_";

    //国家的步数
    private static Map<Integer, String> countryPointMap = new HashMap<Integer, String>();

    //蓝点的步数
    private static List<Integer> bluePointList = new ArrayList<Integer>();

    static {
        countryPointMap.put(5, "yizhou");//益州
        countryPointMap.put(10, "yuzhou");//豫州
        countryPointMap.put(15, "jingzhou");//荆州
        countryPointMap.put(20, "qingzhou");//青州
        countryPointMap.put(25, "yangzhou");//扬州
        countryPointMap.put(36, "end");//终极

        bluePointList.add(1);
        bluePointList.add(2);
        bluePointList.add(6);
        bluePointList.add(7);
        bluePointList.add(9);
        bluePointList.add(13);
        bluePointList.add(14);
        bluePointList.add(16);
        bluePointList.add(18);
        bluePointList.add(23);
        bluePointList.add(24);
        bluePointList.add(26);
        bluePointList.add(27);
        bluePointList.add(29);
        bluePointList.add(31);
        bluePointList.add(32);
        bluePointList.add(34);
        bluePointList.add(35);
    }


    @RequestMapping("/page")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) {
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage = null;
        mapMessage = WeixinUtil.getMapMessage(APPID, SECRET, request, response);
        //根据openid获取用户选择的国家
        //    mapMessage.put("openid", "oAcNYuFJ9MKVDbet_eJ9U_kUXSuA");
        String openid = (String) mapMessage.get("openid");

//        if (StringUtil.isEmpty(openid)) {
//            openid = "oAcNYuFJ9MKVDbet_eJ9U_kUXSuA";
//            mapMessage.put("openid", openid);
//        }
        boolean firstIn = false;
        if (!StringUtil.isEmpty(openid)) {
            try {
                ActivityCountry activityCountry = service.getActivityCountry(null, openid, activityCode);

                //初始化国家
                if (activityCountry == null) {
                    activityCountry = new ActivityCountry();
                    activityCountry.setProfileid(openid);
                    activityCountry.setCountry(new YulongHuntDto().toJson());
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
                    firstIn = true;
                    service.insertActivityCountry(null, activityCountry);
                } else {
                    if (StringUtil.isEmpty(activityCountry.getNickname()) || StringUtil.isEmpty(activityCountry.getHeadimgurl())) {
                        WeixinUser weixinUser = (WeixinUser) mapMessage.get("weixinUser");
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
                mapMessage.put("activityCountry", activityCountry);
                Map<String, String> map = YulongHuntDto.fromMap(activityCountry.getCountry());
                mapMessage.put("yizhou", StringUtil.isEmpty(map.get("yizhou")) ? "" : map.get("yizhou"));
                mapMessage.put("yuzhou", StringUtil.isEmpty(map.get("yuzhou")) ? "" : map.get("yuzhou"));
                mapMessage.put("jingzhou", StringUtil.isEmpty(map.get("jingzhou")) ? "" : map.get("jingzhou"));
                mapMessage.put("qingzhou", StringUtil.isEmpty(map.get("qingzhou")) ? "" : map.get("qingzhou"));
                mapMessage.put("yangzhou", StringUtil.isEmpty(map.get("yangzhou")) ? "" : map.get("yangzhou"));
                mapMessage.put("end", StringUtil.isEmpty(map.get("end")) ? "" : map.get("end"));
            } catch (JoymeDBException e) {
                e.printStackTrace();
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            }
        }


        //是否是第一次进入
        mapMessage.put("firstIn", firstIn);

        //之前是否进入过蓝点
        String blueKey = PropertiesContainer.getRedisManager().get(BLUE_KEY + openid);
        mapMessage.put("blueKey", blueKey);

        //从0开始，然后本地存储也会放一份
        //CookieUtil.deleteALLCookies(request, response);
        CookieUtil.deleteCookies(request, response, "_formCookie_" + openid);


        CookieUtil.setCookie(request, response, "_formLocalStorage_" + openid, "0", 6000);

        String inpage = PropertiesContainer.getRedisManager().get(INITPAGE_KEY + openid);
        if (StringUtil.isEmpty(inpage)) {
            inpage = "0";
        }
        mapMessage.put("inpage", inpage);

        return new ModelAndView("/jsp/yulonghunt", mapMessage);
    }

    @ResponseBody
    @RequestMapping("/click")
    public String click(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        String openid = request.getParameter("openId");
        String end_bot = request.getParameter("end_bot");//现在的步数
        //产生的随机数
        int randomNum = 1 + (int) (Math.random() * 6);

        String num = request.getParameter("num");
        if (!StringUtil.isEmpty(num)) {
            randomNum = Integer.valueOf(num);
        }

        //方便测试
        String testNum = PropertiesContainer.getRedisManager().get(TEST_KEY);
        if (!StringUtil.isEmpty(testNum) && Integer.valueOf(testNum) > 0) {
            randomNum = Integer.valueOf(testNum);
        }

        //现在的步数
        int nowBot = StringUtil.isEmpty(end_bot) ? 0 : Integer.valueOf(end_bot) + Integer.valueOf(randomNum);

        try {
            //如果步数是在国家的话，需要从redis中抛出一个cdk给用户
            String counreyCode = countryPointMap.get(nowBot);

            //如果现在的步数是蓝点的话，需要记录
            if (bluePointList.contains(nowBot)) {
                PropertiesContainer.getRedisManager().set(BLUE_KEY + openid, String.valueOf(nowBot));
            }

            String cdk = "";
            //要根据现在的步数和产生的步数，做逻辑处理
            if (!StringUtil.isEmpty(counreyCode)) {

                ActivityCountry activityCountry = service.getActivityCountry(null, openid, activityCode);
                if (activityCountry != null) {

                    String country = activityCountry.getCountry();

                    Map<String, String> dtoMap = YulongHuntDto.fromMap(country);
                    if (StringUtil.isEmpty(dtoMap.get(counreyCode))) {
                        cdk = PropertiesContainer.getRedisManager().rpop(COUNTRY_KEY + counreyCode);
                        if (!StringUtil.isEmpty(cdk)) {
                            country = country.replace("\"" + counreyCode + "\":\"\"", "\"" + counreyCode + "\":\"" + cdk + "\"");

                            ActivityCountry newCountrey = new ActivityCountry();
                            newCountrey.setCountry(YulongHuntDto.fromJson(country).toJson());
                            service.updateActivityCountry(null, newCountrey, openid, activityCode);
                        }
                    }
                }
            }

            jsonObject.put("msg", "success");
            jsonObject.put("rs", 1);
            jsonObject.put("result", randomNum);
            jsonObject.put("counreycode", !StringUtil.isEmpty(cdk) ? counreyCode : "");

            PropertiesContainer.getRedisManager().incr(INITPAGE_KEY + openid, 1, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    @ResponseBody
    @RequestMapping("/my")
    public String my(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        String openid = request.getParameter("openId");
        try {
            //
            YulongHuntDto dto = null;
            ActivityCountry activityCountry = service.getActivityCountry(null, openid, activityCode);
            if (activityCountry != null) {
                String country = activityCountry.getCountry();
                if (!StringUtil.isEmpty(country)) {
                    dto = YulongHuntDto.fromJson(country);
                }
            }

            //
            String defaultVal = "寻得此处龙脉，即可获得CDK";
            if (dto != null && StringUtil.isEmpty(dto.getYizhou())) {
                dto.setYizhou(defaultVal);
            }

            if (dto != null && StringUtil.isEmpty(dto.getYangzhou())) {
                dto.setYangzhou(defaultVal);
            }

            if (dto != null && StringUtil.isEmpty(dto.getYuzhou())) {
                dto.setYuzhou(defaultVal);
            }

            if (dto != null && StringUtil.isEmpty(dto.getJingzhou())) {
                dto.setJingzhou(defaultVal);
            }

            if (dto != null && StringUtil.isEmpty(dto.getQingzhou())) {
                dto.setQingzhou(defaultVal);
            }

            if (dto != null && StringUtil.isEmpty(dto.getEnd())) {
                dto.setEnd(defaultVal);
            }
            jsonObject.put("msg", "success");
            jsonObject.put("rs", 1);
            jsonObject.put("result", dto == null ? "" : dto.toJson());
            PropertiesContainer.getRedisManager().incr(INITPAGE_KEY + openid, 1, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping("/popcode")
    public String popcode(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        String openid = request.getParameter("openId");
        String counreyCode = request.getParameter("counreyCode");
        String cdk = "";
        try {
            ActivityCountry activityCountry = service.getActivityCountry(null, openid, activityCode);
            if (activityCountry != null) {
                String country = activityCountry.getCountry();

                Map<String, String> dtoMap = YulongHuntDto.fromMap(country);
                if (StringUtil.isEmpty(dtoMap.get(counreyCode))) {
                    cdk = PropertiesContainer.getRedisManager().rpop(COUNTRY_KEY + counreyCode);
                    if (!StringUtil.isEmpty(cdk)) {
                        country = country.replace("\"" + counreyCode + "\":\"\"", "\"" + counreyCode + "\":\"" + cdk + "\"");

                        ActivityCountry newCountrey = new ActivityCountry();
                        newCountrey.setCountry(YulongHuntDto.fromJson(country).toJson());
                        service.updateActivityCountry(null, newCountrey, openid, activityCode);
                    }
                }
            }

            jsonObject.put("msg", "success");
            jsonObject.put("rs", 1);
            jsonObject.put("counreycode", !StringUtil.isEmpty(cdk) ? counreyCode : "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping("/setnum")
    public String setnum(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        //方便测试
        JSONObject jsonObject = new JSONObject();
        try {
            String num = request.getParameter("num");
            if (!StringUtil.isEmpty(num) && Integer.valueOf(num) > 0) {
                PropertiesContainer.getRedisManager().set(TEST_KEY, num);
            } else {
                PropertiesContainer.getRedisManager().set(TEST_KEY, "");
            }
            jsonObject.put("msg", "success");
            jsonObject.put("rs", 1);
            jsonObject.put("resutl", PropertiesContainer.getRedisManager().get(TEST_KEY));
        } catch (Exception e) {
            jsonObject.put("rs", 0);
            jsonObject.put("msg", "fail");
            jsonObject.put("resutl", PropertiesContainer.getRedisManager().get(TEST_KEY));
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping("/getnum")
    public String getnum() throws JSONException {
        //方便测试
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "success");
        jsonObject.put("rs", 1);
        jsonObject.put("resutl", PropertiesContainer.getRedisManager().get(TEST_KEY));
        return jsonObject.toString();
    }


}
