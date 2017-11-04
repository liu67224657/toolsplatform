package com.enjoyf.activity.controller.weixinop.tlbb;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.sign.Sign;
import com.enjoyf.activity.bean.sign.Signlog;
import com.enjoyf.activity.facade.SignFacade;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.DateUtil;
import com.enjoyf.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
@RequestMapping("/weixinop/tlbbRule")
@Controller
public class TlbbRuleController {

    /**
     * 跳转规则页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/rule")
    public ModelAndView initUserInfo(HttpServletRequest request,
                                     HttpServletResponse response) {
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage =  WeixinUtil.getMapMessage(Contants.TLBB_APPID, Contants.TLBB_SECRET, request, response);

        return new ModelAndView("/jsp/tlbb/rule", mapMessage);
    }

}
