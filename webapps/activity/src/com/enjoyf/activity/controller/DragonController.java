package com.enjoyf.activity.controller;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wengangsai on 2016/8/01 0029.
 */

@RequestMapping("/activity/dragon")
@Controller
public class DragonController extends BaseRestSpringController {
    private static ActivityCountryService service = new ActivityCountryService();
    private  static String activityCode = "yulong";
    /**
     * 确定选择的国家，第一次增加用户
     *
     * @param request
     * @param response
     * @param openId
     * @param countryCode
     * @return
     */
    @RequestMapping("/confirmCountry")
    @ResponseBody
    public String confirmCountry(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam(value = "openId", required = true) String openId,
                                 @RequestParam(value = "countryCode", required = true) String countryCode) {

        JSONObject jsonObject = new JSONObject();

        String flag = null;
        try {
            //初始化需要先查询是否存在
            ActivityCountry activityCountry = service.getActivityCountry(null, openId,activityCode);
            if (activityCountry != null) {
                ActivityCountry updateCountry = new ActivityCountry();
                updateCountry.setCountry(countryCode);
                service.updateActivityCountry(null, updateCountry, openId,activityCode);
                flag = "ok";
                jsonObject.put("rs", "1");
            } else {
                flag = "error";
                jsonObject.put("rs", "0");
            }
            jsonObject.put("msg", "success");
            jsonObject.put("result", flag);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


}
