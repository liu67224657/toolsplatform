package com.enjoyf.activity.controller.weixinop.qyz;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.bean.point.Point;
import com.enjoyf.activity.bean.point.PointLog;
import com.enjoyf.activity.bean.sign.Sign;
import com.enjoyf.activity.bean.sign.Signlog;
import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.facade.GoodsFacade;
import com.enjoyf.activity.facade.PointFacade;
import com.enjoyf.activity.facade.SignFacade;
import com.enjoyf.activity.facade.UserinfoFacade;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.util.PointReason;
import com.enjoyf.activity.weixin.AccessToken;
import com.enjoyf.activity.weixin.WeixinUser;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.DateUtil;
import com.enjoyf.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
@RequestMapping("/weixinop/qyzGoods")
@Controller
public class QyzGoodsIndexController extends BaseRestSpringController {

    private static ActivityCountryService service = new ActivityCountryService();
    private static String activityCode = "qyz";
    private static GoodsFacade goodsFacade = new GoodsFacade();
    private static PointFacade pointFacade = new PointFacade();

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
        String openid = request.getParameter("openid");
        //WeixinUtil.getMapMessage(Contants.QYZ_APPID, Contants.QYZ_SECRET, request, response);
        Map<String, Object> mapMessage = Maps.newHashMap();
        if (!StringUtil.isEmpty(openid)) {
            try {
                WeixinUser weixinUser = WeixinUtil.getWeixinUser(Contants.QYZ_APPID_TEST, Contants.QYZ_SECRET_TEST, openid);
                String ip = getIp(request);
                ActivityCountry activityCountry = this.buildActivityCountry(openid, weixinUser, ip);
                int totalScore = pointFacade.getPointValueByProfileId(openid, activityCode);
                if (activityCountry != null) {
                    mapMessage.put("activityCountry", activityCountry);
                    mapMessage.put("openid", openid);
                    mapMessage.put("weixinUser", weixinUser);
                    mapMessage.put("totalScore", String.valueOf(totalScore));
                }
            } catch (JoymeDBException e) {
                e.printStackTrace();
            } catch (JoymeServiceException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("/jsp/qyz/goodsIndex", mapMessage);
    }

    @RequestMapping("/getGoodsPage")
    @ResponseBody
    public String getGoodsPage(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "openid", required = true) String openid,
                               @RequestParam(value = "pSize", required = true, defaultValue = "10") String pSize,
                               @RequestParam(value = "pNum", required = true, defaultValue = "1") String pNum) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            int pageNum = Integer.valueOf(pNum);
            if (pageNum > 0) {
                pageNum = pageNum - 1;
            } else if (pageNum < 0) {
                pageNum = 0;
            }
            int pageSize = Integer.valueOf(pSize);
            List<Goods> goodsList = goodsFacade.getGoodsPage(Contants.QYZ_APPID_TEST, Contants.GOODS_CATEGORY_EXCHANGE, pageSize, pageNum);
            if (goodsList != null && goodsList.size() > 0) {
                Long totalPage = goodsFacade.getTotalGoodsPage(Contants.QYZ_APPID_TEST, Contants.GOODS_CATEGORY_EXCHANGE);
                List<Map<String, String>> signlogs = Lists.newArrayList();
                for (Goods goods : goodsList) {
                    Map<String, String> map = Maps.newHashMap();
                    map.put("goodsId", String.valueOf(goods.getGoodsId()));
                    map.put("goodsName", goods.getGoodsName());
                    map.put("imagePath", goods.getImagePath());
                    map.put("requireScore", String.valueOf(goods.getRequireScore()));
                    map.put("surplusNum", String.valueOf(goods.getSurplusNum()));
                    map.put("description", goods.getDescription());
                    signlogs.add(map);
                }
                if (totalPage > (pageSize * (pageNum + 1))) {
                    jsonObject.put("hasMore", "1");
                } else {
                    jsonObject.put("hasMore", "0");
                }
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "success");
                jsonObject.put("result", signlogs);
            } else {
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "error");
                jsonObject.put("result", "");
            }

        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping("/getUserPoint")
    @ResponseBody
    public String getUserPoint(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "openid", required = true) String openid) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            if (!StringUtil.isEmpty(openid)) {
                int userPoint = pointFacade.getPointValueByProfileId(openid, activityCode);
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "success");
                jsonObject.put("result", userPoint);
            } else {
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "error");
                jsonObject.put("result", "");
            }

        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping("/exchangeGoods")
    @ResponseBody
    public String exchangeGoods(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(value = "openid", required = true) String openid,
                                @RequestParam(value = "goodId", required = true) String goodId) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            if (!StringUtil.isEmpty(openid) && !StringUtil.isEmpty(goodId)) {
                goodsFacade.exchangeGoods(openid, goodId, activityCode, jsonObject);
            } else {
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "error");
                jsonObject.put("result", "");
            }
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private ActivityCountry buildActivityCountry(String openid, WeixinUser weixinUser, String ip) throws JoymeServiceException, JoymeDBException {
        ActivityCountry activityCountry = service.getActivityCountry(null, openid, activityCode);
        //初始化个人信息
        if (activityCountry == null) {
            activityCountry = new ActivityCountry();
            activityCountry.setProfileid(openid);
            activityCountry.setCountry(String.valueOf(0));
            activityCountry.setCreate_date(new Date());
            activityCountry.setScore(0);
            activityCountry.setActivity_code(activityCode);
            activityCountry.setValid_status(ValidStatus.VALID);
            activityCountry.setCreate_ip(ip);
            if (weixinUser != null) {
                activityCountry.setNickname(weixinUser.getNickname());
                activityCountry.setHeadimgurl(weixinUser.getHeadimgurl());
            }
            service.insertActivityCountry(null, activityCountry);
        } else {
            //TODO 微信存在第一次获取用户信息失败的情况，需要第二次修改一下
            if (StringUtil.isEmpty(activityCountry.getNickname()) || StringUtil.isEmpty(activityCountry.getHeadimgurl())) {
                if (weixinUser != null) {
                    ActivityCountry updateCountry = new ActivityCountry();
                    updateCountry.setNickname(weixinUser.getNickname());
                    updateCountry.setHeadimgurl(weixinUser.getHeadimgurl());
                    int updateResult = service.updateActivityCountry(null, updateCountry, openid, activityCode);
                    if (updateResult > 0) {
                        activityCountry = service.getActivityCountry(null, openid, activityCode);
                    }
                }
            }
        }
        return activityCountry;
    }

    @RequestMapping("/getAccessToken")
    @ResponseBody
    public String getAccessToken(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam(value = "appid", required = true) String appid,
                                 @RequestParam(value = "appSecret", required = true) String appSecret) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            if (!StringUtil.isEmpty(appid) && !StringUtil.isEmpty(appSecret)){
                AccessToken accessToken = WeixinUtil.getAccessToken(appid, appSecret);
                jsonObject.put("accessToken", accessToken.getToken());
            }else {
                jsonObject.put("accessToken", "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping("/getUserTotalPoint")
    @ResponseBody
    public String getUserTotalPoint(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestParam(value = "openid", required = true) String openid) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            if (!StringUtil.isEmpty(openid)) {
                int userPoint = pointFacade.getPointValueByProfileId(openid, activityCode);
                jsonObject.put("point", userPoint);
            } else {
                jsonObject.put("point", "0");
            }
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping("/decreasePoint")
    @ResponseBody
    public String decreasePoint(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(value = "openid", required = true) String openid,
                                @RequestParam(value = "score", required = true) String score) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            if (!StringUtil.isEmpty(openid) && !StringUtil.isEmpty(score)) {
                int totalPoint = pointFacade.getPointValueByProfileId(openid, activityCode);
                int intScore = Integer.parseInt(score);
                if (totalPoint > intScore) {
                    PointLog pointLog = new PointLog();
                    pointLog.setProfileid(openid);
                    pointLog.setCreateTime(new Timestamp(new Date().getTime()));
                    pointLog.setGamecode(activityCode);
                    pointLog.setPoint(-intScore);
                    pointLog.setReason(PointReason.SCORE);
                    pointFacade.increasePoint(pointLog);
                    jsonObject.put("desc", "成功");
                    jsonObject.put("status", "ok");
                } else {
                    jsonObject.put("desc", "分数不足");
                    jsonObject.put("status", "error");
                }
            } else {
                jsonObject.put("desc", "用户OPENID为空或者分数为空");
                jsonObject.put("status", "error");
            }
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
