package com.enjoyf.activity.controller.mgf;

import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.service.UserinfoService;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;
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
import java.util.Map;

/**
 * Created by wengangsai on 2016/8/01 0029.
 */

@RequestMapping("/activity/mgf")
@Controller
public class MgfIndexController extends BaseRestSpringController {

    private static ActivityCountryService service = new ActivityCountryService();
    private static UserinfoService userinfoService = new UserinfoService();
    private  static String activityCode = "mgf";
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
        Map<String, Object> mapMessage = Maps.newConcurrentMap();

        return new ModelAndView("/jsp/mgf/index", mapMessage);
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
                              @RequestParam(value = "username", required = true) String username,
                              @RequestParam(value = "company", required = true) String company,
                              @RequestParam(value = "duty", required = true) String duty,
                              @RequestParam(value = "telephone", required = true) String telephone,
                              @RequestParam(value = "representativeWork", required = true) String representativeWork) throws JSONException, JoymeServiceException, JoymeDBException {
        JSONObject jsonObject = new JSONObject();
        String currentTime = String.valueOf(System.currentTimeMillis());
        String openid = MD5Util.Md5(currentTime);
        Userinfo userinfo = userinfoService.queryUserinfobyUserInfo(null, username);
        if (userinfo == null) {
            userinfo = new Userinfo();
            userinfo.setCreateTime(new Date());
            userinfo.setCompany(company);
            userinfo.setRepresentativeWork(representativeWork);
            userinfo.setDuty(duty);
            userinfo.setProfileid(openid);
            userinfo.setTelephone(telephone);
            userinfo.setUsername(username);
            userinfoService.insertUserinfo(null, userinfo);
        } else {
            userinfo.setCompany(company);
            userinfo.setRepresentativeWork(representativeWork);
            userinfo.setDuty(duty);
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

}
