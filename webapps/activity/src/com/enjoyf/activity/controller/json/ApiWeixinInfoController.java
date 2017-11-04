package com.enjoyf.activity.controller.json;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.weixin.WeixinUser;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhimingli on 2016/8/24 0024.
 * 返回jsoup
 */

@Controller
@RequestMapping("/activity/json/api")
public class ApiWeixinInfoController extends BaseRestSpringController {

    private ActivityCountryService service = new ActivityCountryService();

    //着迷测试服务号
    private static String APPID = "wx758f0b2d30620771";
    private static String SECRET = "b58cd348c7f5908055e5e691eed45c39";


    //芜湖joyme服务号
    private static String WH_APPID = "wx2621eecc821ca71a";
    private static String WH_SECRET = "ef5c57e630726e64f39b8b16b380bb90";


    //joyme网络正式
    private static String JOYME_APPID = "wxe6eafdebe1a95bd5";
    private static String JOYME_SECRET = "d878b4b82eb36fe487e909cf5886542e";


    /**
     * 只有服务号可以使用
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/wxinfo")
    public String wxinfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        String code = request.getParameter("code");
        String msg = "fail";
        String rs = "0";

        String appid = request.getParameter("appid");
        if (StringUtil.isEmpty(appid) || StringUtil.isEmpty(code)) {
            try {
                jsonObject.put("rs", rs);
                jsonObject.put("msg", "appid.or.code.is.null");
                return jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //TODO 如果是测试的话，可以直接传appid和secret
        //TODO 生成环境不能使用
        String secret = request.getParameter("secret");
        if (!StringUtil.isEmpty(appid) && !StringUtil.isEmpty(secret)) {
            APPID = appid;
            SECRET = secret;
        }

        //如果appid是芜湖Joyme
        if (appid.equals(WH_APPID)) {
            APPID = WH_APPID;
            SECRET = WH_SECRET;
        } else if (appid.equals(JOYME_APPID)) {
            APPID = JOYME_APPID;
            SECRET = JOYME_SECRET;
        }

        try {
            if (!StringUtil.isEmpty(code)) {
                Map<String, Object> mapMessage = WeixinUtil.getMapMessage(APPID, SECRET, request, response);


                //是否需要保存用户信息到activity_country表
                saveActivityCountry(request, mapMessage);

                msg = "success";
                rs = "1";
                jsonObject.put("result", new Gson().toJson(mapMessage));
            }
            jsonObject.put("msg", msg);
            jsonObject.put("rs", rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("wxinfo--===>" + jsonObject.toString());
        return "wxinfo([" + jsonObject.toString() + "])";
    }


    /**
     * 只是获取一些修改分享信息时使用这个方法即可
     * 去除了判断code
     * 订阅号、服务号都可以使用
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/sharewxinfo")
    public String sharewxinfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        String msg = "fail";
        String rs = "0";

        String appid = request.getParameter("appid");
        if (StringUtil.isEmpty(appid)) {
            try {
                jsonObject.put("rs", rs);
                jsonObject.put("msg", "appid.or.code.is.null");
                return jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        String secret = request.getParameter("secret");
        if (!StringUtil.isEmpty(appid) && !StringUtil.isEmpty(secret)) {
            APPID = appid;
            SECRET = secret;
        }

        //如果appid是芜湖Joyme
        if (appid.equals(WH_APPID)) {
            APPID = WH_APPID;
            SECRET = WH_SECRET;
        } else if (appid.equals(JOYME_APPID)) {
            APPID = JOYME_APPID;
            SECRET = JOYME_SECRET;
        }

        try {
            Map<String, Object> mapMessage = WeixinUtil.getMapMessage(APPID, SECRET, request, response);
            jsonObject.put("result", new Gson().toJson(mapMessage));
            jsonObject.put("msg", "success");
            jsonObject.put("rs", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "wxinfo([" + jsonObject.toString() + "])";
    }


    //是否要保存用户信息
    private void saveActivityCountry(HttpServletRequest request, Map<String, Object> mapMessage) {
        String saveuser = request.getParameter("saveuser");
        String activitycode = request.getParameter("activitycode");
        if (!StringUtil.isEmpty(saveuser) && !StringUtil.isEmpty(activitycode)) {
            String openid = (String) mapMessage.get("openid");

            if (!StringUtil.isEmpty(openid)) {
                ActivityCountry activityCountry = null;
                try {
                    activityCountry = service.getActivityCountry(null, openid, activitycode);
                    if (activityCountry == null) {
                        WeixinUser weixinUser = (WeixinUser) mapMessage.get("weixinUser");

                        activityCountry = new ActivityCountry();
                        activityCountry.setProfileid(openid);
                        activityCountry.setCountry(String.valueOf(0));
                        activityCountry.setCreate_date(new Date());
                        activityCountry.setScore(0);
                        activityCountry.setActivity_code(activitycode);
                        activityCountry.setValid_status(ValidStatus.VALID);
                        activityCountry.setCreate_ip(getIp(request));
                        if (weixinUser != null) {
                            activityCountry.setNickname(weixinUser.getNickname());
                            activityCountry.setHeadimgurl(weixinUser.getHeadimgurl());
                        }
                        service.insertActivityCountry(null, activityCountry);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
