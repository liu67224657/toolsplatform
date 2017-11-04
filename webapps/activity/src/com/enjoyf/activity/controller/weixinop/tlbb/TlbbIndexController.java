package com.enjoyf.activity.controller.weixinop.tlbb;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ResultCodeConstants;
import com.enjoyf.activity.bean.sign.Sign;
import com.enjoyf.activity.bean.sign.Signlog;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.facade.SignFacade;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.service.UserinfoService;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.DateUtil;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.sf.json.JSONObject;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
@RequestMapping("/weixinop/tlbb")
@Controller
public class TlbbIndexController {

    private SignFacade signFacade = new SignFacade();
    private static String activityCode = "tlbb";
    private static String activityKey = "activity-tlbb";

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
        Map<String, Object> mapMessage = WeixinUtil.getMapMessage(Contants.TLBB_APPID, Contants.TLBB_SECRET, request, response);
        String openid = (String) mapMessage.get("openid");
        if (!StringUtil.isEmpty(openid)) {

        }
        return new ModelAndView("/jsp/tlbb/index", mapMessage);
    }

    @RequestMapping("/sign")
    @ResponseBody
    public String sign(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(value = "openid", required = true) String openid) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        Map<String, String> map = Maps.newHashMap();
        try {
            Sign sign = signFacade.getSignByGame(activityCode);
            boolean signFlag = signFacade.sign(sign, openid, sign.getPoint());
            if (signFlag) {
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "success");
            } else {
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "error");
            }
            jsonObject.put("result", map);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    @RequestMapping("/checkSign")
    @ResponseBody
    public String checkSign(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(value = "openid", required = true) String openid) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        Map<String, String> map = Maps.newHashMap();
        try {
            Sign sign = signFacade.getSignByGame(activityCode);
            boolean signFlag = signFacade.checkSign(sign, openid, sign.getPoint());
            if (signFlag) {
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "success");
            } else {
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "error");
            }
            jsonObject.put("result", map);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    @RequestMapping("/getAllDate")
    @ResponseBody
    public String getAllDate(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam(value = "openid", required = true) String openid) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.DAY_OF_MONTH, 1);
            startCalendar.set(Calendar.HOUR_OF_DAY, 0);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.SECOND, 0);
            Date startDate = startCalendar.getTime();

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DATE));
            endCalendar.set(Calendar.HOUR_OF_DAY, 23);
            endCalendar.set(Calendar.MINUTE, 59);
            endCalendar.set(Calendar.SECOND, 59);
            Date endDate = endCalendar.getTime();
            int endDay = endCalendar.getActualMaximum(Calendar.DATE);
            List<Signlog> signlogList = signFacade.getSignLogByDate(openid, activityCode, startDate, endDate);
            Set<Integer> signDateSet = Sets.newHashSet();
            if (signlogList != null && signlogList.size() > 0) {
                Calendar cal = Calendar.getInstance();
                for (Signlog signlog : signlogList) {
                    cal.setTime(signlog.getSignDate());
                    signDateSet.add(cal.get(Calendar.DAY_OF_MONTH));
                }
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 1; i <= endDay; i++) {
                if (signDateSet.contains(i)){
                    sb.append("1").append(";");
                }else {
                    sb.append("0").append(";");
                }

            }
            jsonObject.put("result", sb.toString().substring(0, sb.length() - 1));
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping("/getSignlogPage")
    @ResponseBody
    public String getSignlogPage(HttpServletRequest request,
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
            List<Signlog> signlogList = signFacade.getSignlogPage(openid, activityCode, pageSize, pageNum);
            if (signlogList != null && signlogList.size() > 0) {
                List<Map<String, String>> signlogs = Lists.newArrayList();
                for (Signlog signlog : signlogList) {
                    Map<String, String> map = Maps.newHashMap();
                    map.put("createTime", DateUtil.DateToString(signlog.getCreateTime(), DateUtil.PATTERN_DATE_MINUTES));
                    signlogs.add(map);
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
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping("/getSignDates")
    @ResponseBody
    public String getSignDates(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "openid", required = true) String openid) {
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.DAY_OF_MONTH, 1);
            startCalendar.set(Calendar.HOUR_OF_DAY, 0);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.SECOND, 0);
            Date startDate = startCalendar.getTime();

            List<Signlog> signloggList = signFacade.getSignLogByDate(openid, activityCode, startDate, new Date());
            jsonObject.put("rs", "1");
            if (signloggList == null || signloggList.size() == 0) {
                jsonObject.put("msg", "success");
                jsonObject.put("result", "0");
            } else {
                jsonObject.put("result", String.valueOf(signloggList.size()));
                jsonObject.put("msg", "success");
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
