package com.enjoyf.activity.controller.duiba;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.duibasdk.cn.com.duiba.credits.sdk.CreditTool;
import com.enjoyf.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:2017/1/10
 */
@Controller
@RequestMapping("/duiba/api/activity")
public class DuibaApiActivityController {

    private ActivityCountryService service = new ActivityCountryService();


    //活动id
    private static String ACTIVITYCODE = "duiba2017011";


    @RequestMapping("/page")
    public ModelAndView auth(HttpServletRequest request) {
        String redirect = request.getParameter("dbredirect");  //从客户端请求中获取dbredirect参数的值，将其赋给redirect

        Map<String, String> map = new HashMap<String, String>();
        if (redirect != null && !"".equals(redirect)) {
            map.put("redirect", redirect);
        }

        String appKey = "2Ens1pg59nUi31EdcDkUe3QC5w3H";    //在兑吧后台获取
        String appSecret = "298QL4cZeS8z7oDwjT3y82jSFQJm";    //签名密钥，发送到注册邮箱中，需妥善保管
        String timestamp = String.valueOf(new Date().getTime());    //以北京时间为准，单位为毫秒


        //定义活动的ID,这样每次不用修改activity项目，直接修改static文件即可
        String activity_code = request.getParameter("activitycode");
        if (!StringUtil.isEmpty(activity_code)) {
            //对吧账号
            //账号：332884736@qq.com
            //密码：xwtlove8438
            ACTIVITYCODE = activity_code;
            appKey = "oy4j164mgPN5bYRC6sGv1aT7KY9";    //在兑吧后台获取
            appSecret = "aSgav7H2ur7MLx3Sd8hY2CRMf4n";
        }

        String uid = request.getParameter("openid");


        //验证用户是否有效，无效置空
        if (!StringUtil.isEmpty(uid)) {
            try {
                ActivityCountry activityCountry = service.getActivityCountry(null, uid, ACTIVITYCODE);
                if (activityCountry == null) {
                    uid = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                uid = "";
            }
        }

        map.put("uid", uid);    //uid为区分用户的唯一标识符，可使用用户id或者设备号。
        map.put("credits", "0");    //credits参数默认写死为0
        map.put("timestamp", timestamp);

        CreditTool tool = new CreditTool(appKey, appSecret);

        String url = "http://www.duiba.com.cn/autoLogin/autologin?";
        String autoLoginUrl = tool.buildUrlWithSign(url, map);

        return new ModelAndView("redirect:" + autoLoginUrl);
    }
}
