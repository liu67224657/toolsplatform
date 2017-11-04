package com.enjoyf.activity.controller.qyzActivity;

import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.weixin.WeixinUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by wengangsai on 2016/8/01 0029.
 */

@RequestMapping("/activity/qyzEnd")
@Controller
public class QyzEndController extends BaseRestSpringController {

    /**
     * 游戏过关
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/game")
    public ModelAndView game(HttpServletRequest request,
                                     HttpServletResponse response){
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage = WeixinUtil.getMapMessage(Contants.QYZ_APPID, Contants.QYZ_SECRET, request, response);
        return new ModelAndView( "/jsp/qyz/endGame", mapMessage);
    }


}
