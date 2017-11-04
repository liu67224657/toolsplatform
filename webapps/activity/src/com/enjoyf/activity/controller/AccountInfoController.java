package com.enjoyf.activity.controller;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.SystemUtil;
import com.enjoyf.activity.weixin.WeixinUser;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by wengangsai on 2016/8/01 0029.
 */

@RequestMapping("/activity/accountInfo")
@Controller
public class AccountInfoController {

    private static String APPID = "wx8e5bbc9c79b5686d";
    private static String SECRET = "283d3e3623688395dffccf290b173090";
    private static ActivityCountryService service = new ActivityCountryService();
    private  static String activityCode = "yulong";
    /**
     * 查询客户相关信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findUserInfo")
    public ModelAndView findUserInfo(HttpServletRequest request,
                                     HttpServletResponse response) {
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage = WeixinUtil.getMapMessage(APPID, SECRET, request, response);
        try {
            String openId = (String) mapMessage.get("openid");
            ActivityCountry activityCountry = service.getActivityCountry(null, openId,activityCode);


            //获取失败的情况下，重新修改用户信息
            if (activityCountry != null) {
                if (StringUtil.isEmpty(activityCountry.getNickname()) || StringUtil.isEmpty(activityCountry.getHeadimgurl())) {
                    WeixinUser weixinUser = (WeixinUser) mapMessage.get("weixinUser");
                    if (weixinUser != null) {
                        ActivityCountry updateCountry = new ActivityCountry();
                        updateCountry.setNickname(weixinUser.getNickname());
                        updateCountry.setHeadimgurl(weixinUser.getHeadimgurl());
                        int updateResult = service.updateActivityCountry(null, updateCountry, openId,activityCode);
                        if (updateResult > 0) {
                            activityCountry = service.getActivityCountry(null, openId,activityCode);
                        }
                    }
                }
            }

            String countryScore = PropertiesContainer.getRedisManager().get("activity-" + activityCountry.getCountry() + "-Score");
            mapMessage.put("countryName", PropertiesContainer.getRedisManager().get("activity-" + activityCountry.getCountry() + "-Name"));
            if (StringUtil.isEmpty(countryScore)){
                mapMessage.put("countryScore", "0");
            }else {
                mapMessage.put("countryScore", countryScore);
            }
            String times = PropertiesContainer.getRedisManager().get("activity-" + openId + "-Times");
            if (StringUtil.isEmpty(times)) {
                times = String.valueOf(3);
            } else {
                times = String.valueOf(3 - Integer.parseInt(times));
            }
            int countryStar = 0;
            if (!StringUtil.isEmpty(countryScore)) {
                countryStar = Integer.parseInt(countryScore);
            }
            mapMessage.put("userStar", getStar(countryStar));
            mapMessage.put("usedTimes", times);
            activityCountry.setNickname(SystemUtil.getSubNickname(activityCountry.getNickname(),6));
            mapMessage.put("activityCountry", activityCountry);
            mapMessage.put("openid", openId);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/jsp/taozhuang", mapMessage);
    }


    @RequestMapping("/addUserScore")
    @ResponseBody
    public String addUserScore(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "openId", required = true) String openId) {
        JSONObject jsonObject = new JSONObject();
        //生成随机提升的战力
        Random random = new Random();
        int score = random.nextInt(500);
        try {
            //判断是否可以提升战力
            boolean flag = service.checkAddScoreTime(openId);
            if (flag) {
                //同时提升国家战力和提升个人战力
                ActivityCountry activityCountry = service.getActivityCountry(null, openId,activityCode);
                if (activityCountry != null) {
                    PropertiesContainer.getRedisManager().incr("activity-" + activityCountry.getCountry() + "-Score", score, 3600 * 24 * 30);
                    activityCountry.setScore(activityCountry.getScore() + score);
                }
                service.updateActivityCountry(null, activityCountry, openId,activityCode);
            }
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", String.valueOf(score));
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    /**
     * 获取国家战力排行
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getCountryScore")
    @ResponseBody
    public String getCountryScore(HttpServletRequest request,
                                  HttpServletResponse response) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 1; i <= 5; i++) {
            String countryScore = PropertiesContainer.getRedisManager().get("activity-" + i + "-Score");
            if (!StringUtil.isEmpty(countryScore)) {
                map.put(i, Integer.parseInt(countryScore));
            } else {
                map.put(i, 0);
            }
        }
        jsonObject.put("rs", "1");
        jsonObject.put("msg", "success");
        jsonObject.put("result", buildCourtryOrder(map));
        return jsonObject.toString();
    }

    private List<String> buildCourtryOrder(Map<Integer, Integer> map) {
        List<String> countryScoreList = Lists.newArrayList();
        Set<Integer> keySet = Sets.newHashSet();
        for (int i = 0; i < 5; i++) {
            countryScoreList.add(getMaxScoreCourtry(keySet, map));
        }
        return countryScoreList;
    }

    private String getMaxScoreCourtry(Set<Integer> keySet, Map<Integer, Integer> map) {
        Integer key = 0, value = 0;
        for (Iterator<Integer> it = map.keySet().iterator(); it.hasNext(); ) {
            Integer tempKey = it.next();
            Integer tempValue = map.get(tempKey);
            if (keySet.contains(tempKey)) {
                continue;
            } else if (key == 0 || (value < tempValue)) {
                key = tempKey;
                value = tempValue;
            }
        }
        keySet.add(key);
        return key + "-" + buildDisplayScore(String.valueOf(value)) + "-" + getStar(value);
    }


    /**
     * 根据战力生成显示数据 比如：12345 ： 1.2万 1234 :1234
     *
     * @param score
     * @return
     */
    private String buildDisplayScore(String score) {
        String showCountryScore;
        int length = score.length();
        if (length >= 5) {
            String prefix = score.substring(0, length-4);
            String suffix = score.substring(length-4, length);
            String suffixFirst = suffix.substring(0, 1);
            String suffixSecond = suffix.substring(1, 2);
            int intSuffix = Integer.parseInt(suffixFirst);
            if (Integer.parseInt(suffixSecond) >= 5) {
                intSuffix += 1;
            }
            showCountryScore = prefix + "." + intSuffix + "万";
        } else {
            showCountryScore = score;
        }
        return showCountryScore;
    }

    /**
     * 根据国家信息获取个人战力排行
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getUserScore")
    @ResponseBody
    public String getUserScore(HttpServletRequest request,
                               HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        List<String> userScoreList = Lists.newArrayList();
        try {
            List activityCountryList = service.queryAllActivityCountry(null);
            if (activityCountryList != null && activityCountryList.size() > 0) {
                for (int i = 0; i < activityCountryList.size(); i++) {
                    ActivityCountry activityCountry = (ActivityCountry) activityCountryList.get(i);
                    if (activityCountry != null ) {
                        userScoreList.add(SystemUtil.getSubNickname(activityCountry.getNickname(),6) + "-" + buildDisplayScore(String.valueOf(activityCountry.getScore())) + "-" + activityCountry.getHeadimgurl());
                    }
                }
            }
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", userScoreList);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @RequestMapping("/getUserTaozhuang")
    @ResponseBody
    public String getUserTaozhuang(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(value = "openId", required = true) String openId) {
        JSONObject jsonObject = new JSONObject();
        Map<String, String> map = Maps.newHashMap();
        try {
            ActivityCountry activityCountry = service.getActivityCountry(null, openId,activityCode);
            String countryScore = PropertiesContainer.getRedisManager().get("activity-" + activityCountry.getCountry() + "-Score");
            int score = StringUtil.isEmpty(countryScore) ? 0 : Integer.parseInt(countryScore);
            map.put("score", String.valueOf(score));
            calculateTaoZhuang(map, score);
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
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

    /**
     * 根据分数计算当前套装和下一套装和需要战力
     *
     * @param map
     * @param score
     */
    private void calculateTaoZhuang(Map<String, String> map, int score) {
        String currentTaozhuang = null;
        String nextTaozhuang = null;
        String requireScore = null;
        if (score >= 0 && score < 3000) {
            currentTaozhuang = "0星套";
            nextTaozhuang = "3星套";
            requireScore = "3000";
        } else if (score >= 3000 && score < 10000) {
            currentTaozhuang = "3星套";
            nextTaozhuang = "6星套";
            requireScore = "1W";
        } else if (score >= 10000 && score < 30000) {
            currentTaozhuang = "6星套";
            nextTaozhuang = "9星套";
            requireScore = "3W";
        } else if (score >= 30000 && score < 100000) {
            currentTaozhuang = "9星套";
            nextTaozhuang = "12星套";
            requireScore = "10W";
        } else if (score >= 100000 && score < 1000000) {
            currentTaozhuang = "12星套";
            nextTaozhuang = "15星套";
            requireScore = "100W";
        } else if (score >= 1000000 && score < 5000000) {
            currentTaozhuang = "15星套";
            nextTaozhuang = "18星套";
            requireScore = "500W";
        } else if (score >= 5000000) {
            currentTaozhuang = "18星套";
        }
        map.put("currentTaozhuang", currentTaozhuang);
        map.put("nextTaozhuang", nextTaozhuang);
        map.put("requireScore", requireScore);
    }

    private String getStar(int score) {
        if (score >= 0 && score < 3000) {
            return "0";
        } else if (score >= 3000 && score < 10000) {
            return "3";
        } else if (score >= 10000 && score < 30000) {
            return "6";
        } else if (score >= 30000 && score < 100000) {
            return "9";
        } else if (score >= 100000 && score < 1000000) {
            return "12";
        } else if (score >= 1000000 && score < 5000000) {
            return "15";
        } else if (score >= 5000000) {
            return "18";
        }
        return "0";
    }

}
