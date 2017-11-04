package com.enjoyf.activity.controller.qyzActivity;

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.facade.GameFacade;
import com.enjoyf.activity.facade.GoodsFacade;
import com.enjoyf.activity.facade.GoodsItemFacade;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.MD5Util;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Map;

/**
 * Created by wengangsai on 2016/8/01 0029.
 */

@RequestMapping("/activity/qyzGame")
@Controller
public class QyzGameController extends BaseRestSpringController {

    private static ActivityCountryService service = new ActivityCountryService();
    private static String activityCode = "qyzActivity";
    private static String activityPrefix = "activity-qyzActivity";
    private static GameFacade gameFacade = new GameFacade();
    private static GoodsFacade goodsFacade = new GoodsFacade();
    private static GoodsItemFacade goodsItemFacade = new GoodsItemFacade();
    /**
     * 游戏过关
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/game")
    public ModelAndView game(HttpServletRequest request,
                             HttpServletResponse response) {
        //通过微信获取一些公共信息
        Map<String, Object> mapMessage = WeixinUtil.getMapMessage(Contants.QYZ_APPID, Contants.QYZ_SECRET, request, response);
        String nextPage = null;
        try {
            String openid = (String) mapMessage.get("openid");
            String game = request.getParameter("game");
            ActivityCountry activityCountry = service.getActivityCountry(null, openid, activityCode);
            if (activityCountry != null) {
                mapMessage.put("activityCountry", activityCountry);
            }
            nextPage = buildNextPage(game);
        } catch (JoymeDBException e) {
            e.printStackTrace();
        } catch (JoymeServiceException e) {
            e.printStackTrace();
        }
        String pageUrl = "/jsp/qyz/" + nextPage;
        return new ModelAndView(pageUrl, mapMessage);
    }

    /**
     * 游戏过关
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/saveGame")
    @ResponseBody
    public String saveGame(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(value = "openid", required = true) String openid,
                           @RequestParam(value = "gameLevel", required = true) String gameLevel) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            String gameId = MD5Util.Md5(openid + activityCode + gameLevel);
            if (gameLevel.equals("1")) {
                String redisKey = activityPrefix + "_" + openid + "_first";
                gameFacade.saveGame(gameLevel, openid, gameId, redisKey);
            } else if (gameLevel.equals("2")) {
                String redisKey = activityPrefix + "_" + openid + "_second";
                gameFacade.saveGame(gameLevel, openid, gameId, redisKey);
            } else if (gameLevel.equals("3")) {
                String redisKey = activityPrefix + "_" + openid + "_third";
                gameFacade.saveGame(gameLevel, openid, gameId, redisKey);
            }

            Map<String, String> map = gameFacade.getGameList(activityPrefix, openid, activityCode);
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", map);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", "");
        }
        return jsonObject.toString();
    }

    /**
     * 获取所有通关信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gameList")
    @ResponseBody
    public String getAllGame(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam(value = "openid", required = true) String openid) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        try {
            Map<String, String> map = gameFacade.getGameList(activityPrefix, openid, activityCode);
            jsonObject.put("rs", "1");
            jsonObject.put("msg", "success");
            jsonObject.put("result", map);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", "");
        }
        return jsonObject.toString();
    }

    private String buildNextPage(String gameLevel) throws JoymeServiceException, JoymeDBException {

        if (gameLevel.equals("1")) {
            return "gameFirst";
        } else if (gameLevel.equals("2")) {
            return "gameSecond";
        } else if (gameLevel.equals("3")) {
            return "gameThird";
        } else if (gameLevel.equals("over")) {
            return "endGame";
        } else if (gameLevel.equals("puzzle")) {
            return "puzzle";
        } else {
            return "gameFirst";
        }

    }

    @RequestMapping("/importData")
    @ResponseBody
    public String importData(HttpServletRequest request,
                             HttpServletResponse response) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        try {
            File file = new File("e:/test/weixin_cdk.txt");
            String encoding = "GBK";
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String cdkCode = null;
                Goods goods = goodsFacade.getGoods("wx6adc249f511f2c93",3l);
                while ((cdkCode = bufferedReader.readLine()) != null) {
                    GoodsItem goodsItem = new GoodsItem();
                    goodsItem.setCreateTime(new Date());
                    goodsItem.setGoodsItemValue(cdkCode);
                    goodsItem.setGameId(goods.getGameId());
                    goodsItem.setStatus(ValidStatus.VALID.getCode());
                    goodsItem.setGoodsId(goods.getGoodsId());
                    goodsItemFacade.saveGoodsItem(goodsItem);
                }
                read.close();
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "success");
                jsonObject.put("result", "ok");
            }
        } catch (Exception e) {

        }
        return jsonObject.toString();
    }


    }
