package com.enjoyf.activity.controller.weixinop.tlbb;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.sign.Sign;
import com.enjoyf.activity.facade.SignFacade;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;
import com.google.common.collect.Maps;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
@RequestMapping("/weixinop/signHistory")
@Controller
public class TlbbSignHistoryController {
    private  static String activityCode = "tlbb";
    private SignFacade signFacade = new SignFacade();
    /**
     * 初始化增加用户
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/integral")
    public ModelAndView initUserInfo(HttpServletRequest request,
                                     HttpServletResponse response) {
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage =  WeixinUtil.getMapMessage(Contants.TLBB_APPID, Contants.TLBB_SECRET, request, response);
        String openid = (String) mapMessage.get("openid");
        if (!StringUtil.isEmpty(openid)) {

        }
        return new ModelAndView("/jsp/tlbb/integral", mapMessage);
    }
    @RequestMapping("/getTotalScore")
    @ResponseBody
    public String getTotalScore(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(value = "openid", required = true) String openid) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            int totalScore = signFacade.getPointValueByProfileId(openid,activityCode);
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", totalScore);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
