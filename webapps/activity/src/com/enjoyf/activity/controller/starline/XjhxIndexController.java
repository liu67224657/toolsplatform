package com.enjoyf.activity.controller.starline;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.SystemUtil;
import com.enjoyf.activity.weixin.WeixinUser;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.DateUtils;
import com.enjoyf.util.StringUtil;
import com.google.common.collect.Lists;
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
 * Created by wengangsai on 2016/8/01 0029.
 */

@RequestMapping("/activity/xjhx")
@Controller
public class XjhxIndexController extends BaseRestSpringController {

    private static String APPID = "wx2621eecc821ca71a";
    private static String SECRET = "ef5c57e630726e64f39b8b16b380bb90";
    /**
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/page")
    public ModelAndView findUserInfo(HttpServletRequest request,
                                     HttpServletResponse response) {
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage =  WeixinUtil.getMapMessage(APPID, SECRET, request, response);

        return new ModelAndView("/jsp/xjhx", mapMessage);
    }





}
