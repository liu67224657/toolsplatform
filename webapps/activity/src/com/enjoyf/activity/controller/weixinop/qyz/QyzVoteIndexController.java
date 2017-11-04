package com.enjoyf.activity.controller.weixinop.qyz;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.facade.GoodsFacade;
import com.enjoyf.activity.facade.PointFacade;
import com.enjoyf.activity.facade.QyzVoteFacade;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.weixin.WeixinUser;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
@RequestMapping("/weixinop/qyzVote")
@Controller
public class QyzVoteIndexController extends BaseRestSpringController {

    private static QyzVoteFacade qyzVoteFacade = new QyzVoteFacade();

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
        //通过微信获取一些公共信息\
        Map<String, Object> mapMessage = WeixinUtil.getMapMessage(Contants.QYZ_APPID,Contants.QYZ_SECRET,request,response);
        String openid = (String)mapMessage.get("openid");
        String fromPath = (String) request.getParameter("fromPath");
        //1、获取家族的总积分列表
        for (int i = 1; i <= 50; i++) {
            String value = qyzVoteFacade.getTotalPollsByFamilyCode(Contants.QYZ_APPID, "family"+ String.valueOf(i));
            mapMessage.put("family" + i, value);
        }
        if (!StringUtil.isEmpty(openid)) {
            //this.getMapMessage(Contants.QYZ_APPID_DYH,Contants.QYZ_SECRET_DYH,request,mapMessage);
            //2、用户是当然是否投票
            boolean checkFlag = qyzVoteFacade.checkVote(openid);
            mapMessage.put("voteFlag", checkFlag);
            mapMessage.put("openid", openid);
        }
        if (!StringUtil.isEmpty(fromPath)){
            mapMessage.remove("openid");
        }
        return new ModelAndView("/jsp/qyz/qyzVote", mapMessage);
    }


    @RequestMapping("/addPoll")
    @ResponseBody
    public String addPoll(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(value = "openid", required = true) String openid,
                          @RequestParam(value = "familyCode", required = true) String familyCode) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            if (!StringUtil.isEmpty(openid)) {
                boolean checkFlag = qyzVoteFacade.checkVote(openid);
                //1、已投
                if (checkFlag) {
                    jsonObject.put("rs", "2");
                } else {//2、投票
                    qyzVoteFacade.incrFamilyPoll(Contants.QYZ_APPID, familyCode, openid, 1L);
                    jsonObject.put("rs", "1");
                }
                jsonObject.put("msg", "success");
                jsonObject.put("result", "投票成功");
            } else {
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "error");
                jsonObject.put("result", "服务器异常");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping("/addFamilyPoll")
    @ResponseBody
    public String addFamilyPoll(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(value = "familyCode", required = true) String familyCode,
                                @RequestParam(value = "polls", required = true) String polls) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            if (!StringUtil.isEmpty(polls)){
                qyzVoteFacade.addFamilyPoll(Contants.QYZ_APPID, "family" + familyCode, polls);
                jsonObject.put("msg", "success");
                jsonObject.put("result", "批量增加成功");
            }else {
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "fail");
                jsonObject.put("result", "票数不能为空");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
