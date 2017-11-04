package com.enjoyf.activity.controller.weixinop.qyz;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.facade.GoodsFacade;
import com.enjoyf.activity.facade.PointFacade;
import com.enjoyf.activity.facade.UserinfoFacade;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
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
import java.util.List;
import java.util.Map;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
@RequestMapping("/weixinop/qyzUserinfo")
@Controller
public class QyzScoreUserinfoController extends BaseRestSpringController {

    private static ActivityCountryService service = new ActivityCountryService();
    private static String activityCode = "qyzScore";
    private static PointFacade pointFacade = new PointFacade();
    private static UserinfoFacade userinfoFacade = new UserinfoFacade();

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
        Map<String, Object> mapMessage = Maps.newHashMap();
        String openid = request.getParameter("openid");
        if (!StringUtil.isEmpty(openid)) {
            try {
                mapMessage.put("openid", openid);
                ActivityCountry activityCountry = service.getActivityCountry(null, openid, activityCode);
                Userinfo userinfo = userinfoFacade.getUserinfo(openid);
                String tempNickname = null;
                if (activityCountry != null) {
                    mapMessage.put("activityCountry", activityCountry);
                    tempNickname = activityCountry.getNickname();
                }
                if (userinfo != null) {
                    mapMessage.put("userinfo", userinfo);
                    if (!StringUtil.isEmpty(userinfo.getNickname())) {
                        tempNickname = userinfo.getNickname();
                    }
                }
                int totalScore = pointFacade.getPointValueByProfileId(openid, activityCode);
                mapMessage.put("totalScore", totalScore);
                mapMessage.put("tempNickname", tempNickname);
            } catch (JoymeDBException e) {
                e.printStackTrace();
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("/jsp/qyz/goodsUserinfo", mapMessage);
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
                              @RequestParam(value = "nickname", required = true) String nickname,
                              @RequestParam(value = "telephone", required = true) String telephone,
                              @RequestParam(value = "address", required = true) String address) throws JSONException, JoymeServiceException, JoymeDBException {
        JSONObject jsonObject = new JSONObject();
        Userinfo userinfo = userinfoFacade.getUserinfo(openid);
        if (userinfo == null) {
            userinfo = new Userinfo();
            userinfo.setCreateTime(new Date());
            userinfo.setProfileid(openid);
            userinfo.setTelephone(telephone);
            userinfo.setUsername(nickname);
            userinfo.setNickname(nickname);
            userinfo.setAddress(address);
            userinfoFacade.saveUserinfo(userinfo, false);
        } else {
            userinfo.setProfileid(openid);
            userinfo.setTelephone(telephone);
            userinfo.setNickname(nickname);
            userinfo.setUsername(nickname);
            userinfo.setAddress(address);
            userinfoFacade.updateUserinfo(null, userinfo);
        }
        jsonObject.put("rs", "1");
        jsonObject.put("msg", "success");
        jsonObject.put("result", userinfo);
        return jsonObject.toString();
    }

}
