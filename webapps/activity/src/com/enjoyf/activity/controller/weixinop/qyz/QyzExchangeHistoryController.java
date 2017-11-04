package com.enjoyf.activity.controller.weixinop.qyz;
/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.ActivityCountry;
import com.enjoyf.activity.bean.goods.ExchangeLog;
import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.controller.base.BaseRestSpringController;
import com.enjoyf.activity.facade.GoodsFacade;
import com.enjoyf.activity.facade.PointFacade;
import com.enjoyf.activity.facade.UserinfoFacade;
import com.enjoyf.activity.service.ActivityCountryService;
import com.enjoyf.activity.service.ExchangeLogService;
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
import java.util.List;
import java.util.Map;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
@RequestMapping("/weixinop/qyzExchange")
@Controller
public class QyzExchangeHistoryController extends BaseRestSpringController {

    private static GoodsFacade goodsFacade = new GoodsFacade();
    private static ExchangeLogService exchangeLogService = new ExchangeLogService();

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
        Map<String, Object> mapMessage = Maps.newHashMap();
        mapMessage.put("openid",openid);
        return new ModelAndView("/jsp/qyz/goodsExchangeHistory", mapMessage);
    }

    @RequestMapping("/getExchangeGoodsPage")
    @ResponseBody
    public String getExchangeGoodsPage(HttpServletRequest request,
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
            List<ExchangeLog> exchangeLogList = exchangeLogService.getExchangeGoodsPage(null,openid, Contants.GOODS_CATEGORY_EXCHANGE, pageSize, pageNum);
            if (exchangeLogList != null && exchangeLogList.size()>0){
                Long totalPage = exchangeLogService.getTotalExchangeLog(null,openid, Contants.GOODS_CATEGORY_EXCHANGE);
                jsonObject.put("rs", "1");
                jsonObject.put("msg", "success");
                jsonObject.put("result", this.buildExchangeLogs(exchangeLogList));
                if (totalPage>(pageSize*(pageNum+1))){
                    jsonObject.put("hasMore", "1");
                }else {
                    jsonObject.put("hasMore", "0");
                }
            }else {
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
    private  List<Map<String, String>> buildExchangeLogs(List<ExchangeLog> exchangeLogList) throws JoymeDBException {
        List<Map<String, String>> exchangeLogs = Lists.newArrayList();
        for (ExchangeLog exchangeLog : exchangeLogList) {
            Goods goods = goodsFacade.getGoods(exchangeLog.getGameId(),exchangeLog.getGoodsId());
            Map<String, String> map = Maps.newHashMap();
            map.put("goodsName", goods.getGoodsName());
            map.put("imagePath", goods.getImagePath());
            map.put("requireScore", String.valueOf(goods.getRequireScore()));
            map.put("exchangeTime", DateUtil.DateToString(exchangeLog.getCreateTime(), DateUtil.PATTERN_DATE_MINUTES));
            if (goods.getGoodType().equals("R")){
                map.put("description",goods.getDescription());
            }else {
                map.put("description","<h2>兑换成功!</h2><p>您获得的激活码为：</p><font>"+exchangeLog.getGoodsItemValue()+"</font><p>请进入青云志手游公众号【下载游戏】进行激活</p>");
            }
            exchangeLogs.add(map);
        }
        return exchangeLogs;
    }

}
