package com.enjoyf.activity.facade;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.goods.ExchangeLog;
import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.bean.point.PointLog;
import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.activity.cache.GoodsRedis;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.service.ExchangeLogService;
import com.enjoyf.activity.service.GoodsService;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.util.PointReason;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class GoodsFacade {


    private GoodsService goodsService = new GoodsService();
    private GoodsItemFacade goodsItemFacade = new GoodsItemFacade();
    private GoodsRedis goodsRedis = new GoodsRedis(PropertiesContainer.getRedisManager());

    private PointFacade pointFacade = new PointFacade();
    private UserinfoFacade userinfoFacade = new UserinfoFacade();
    private ExchangeLogService exchangeLogService = new ExchangeLogService();
    public void saveGoods(Goods goods) throws JoymeDBException, JoymeServiceException {
        if (goods.getGoodsId()==null)
            goodsService.insertGoods(goods);
        else
            goodsService.updateGoods(null,goods);
    }

    public Goods queryGoodsByName(String goodsName) throws JoymeDBException, JoymeServiceException {
        return goodsService.queryGoodsByName(null, goodsName);
    }

    public Goods getGoods(String gameId, long goodsId) throws JoymeDBException {
        Goods goods = goodsRedis.getGoodsCache(gameId, goodsId);
        if (null == goods) {
            goods = goodsService.getGoodsById(goodsId);
            if (null != goods) {
                goodsRedis.putGoodsCache(goods);
            }
        }
        return goods;
    }

    public List<Goods> queryGoodsOnline(String gameId) throws JoymeDBException {
        Pagination pagination = new Pagination(10, 1, 10);
        PageRows<Goods> pageRows = goodsService.queryGoods(gameId, pagination, true, ValidStatus.VALID);
        return pageRows.getRows();

    }

    public List<Goods> getGoodsPage(String gameId, String goodsCategory, int pageSize, int pageNum) throws JoymeDBException {
        return goodsService.queryGoodsPage(gameId, goodsCategory, pageSize, pageNum);
    }

    public JSONObject exchangeGoods(String openid, String goodId, String activityCode, JSONObject jsonObject) throws JoymeDBException, JoymeServiceException, JSONException {
        Goods goods = this.getGoods(Contants.QYZ_APPID, Long.parseLong(goodId));
        //分实物还是虚拟，实货
        int userScore = pointFacade.getPointValueByProfileId(openid, activityCode);
        if (goods != null) {
            if (goods.getRequireScore() > userScore) {
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "success");
                jsonObject.put("result", "<p>您的剩余积分不足!</p>");
            } else {
                ExchangeLog exchangeLog = exchangeLogService.queryExchangeLogbyParam(null,openid,goods.getGoodsId());
               if (exchangeLog ==null){
                   if (goods.getGoodType().equals("R")) {//实物商品兑换
                       exchangeRealGoods(openid, goods, userScore, activityCode, jsonObject);
                   } else if (goods.getGoodType().equals("V")) {//虚拟商品兑换
                       this.exchangeVirtualGoods(openid, goods, userScore, activityCode, jsonObject);
                   }
               }else {
                   jsonObject.put("rs", "0");
                   jsonObject.put("msg", "error");
                   jsonObject.put("result", "<p>该奖品只能兑换一次</p>");
               }
            }
        } else {
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", "<p>兑换的商品已下架</p>");
        }
        return jsonObject;
    }

    private void exchangeRealGoods(String openid, Goods goods, int userScore, String activityCode, JSONObject jsonObject) throws JoymeServiceException, JoymeDBException, JSONException {
        Userinfo userinfo = userinfoFacade.getUserinfo(openid);
        //兑换商品需求的分数大于用户分数
        if (userinfo == null || StringUtil.isEmpty(userinfo.getTelephone())) {
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", "<h2>亲爱的玩家</h2> <p>您的手机尚未绑定，无法兑换礼品哦</br>请先绑定手机！</p>");
        } else {
            boolean flag = decrGoodsNum(openid, activityCode, goods);
            if (flag) {
                this.saveExchangeLog(goods,openid);
                jsonObject.put("rs", "2");
                jsonObject.put("msg", "success");
                jsonObject.put("result", goods.getGoodsName());
            } else {
                jsonObject.put("rs", "0");
                jsonObject.put("msg", "error");
                jsonObject.put("result", "<p>抱歉，商品剩余数量不足！</p>");
            }
        }
    }

    private boolean decrGoodsNum(String openid, String activityCode, Goods goods) throws JoymeServiceException, JoymeDBException {
        boolean decrFlag = false;
        Goods newGoods = goodsService.getGoodsById(goods.getGoodsId());
        if (newGoods.getSurplusNum() > 0) {
            if (newGoods.getRequireScore()>0){
                PointLog pointLog = new PointLog();
                pointLog.setProfileid(openid);
                pointLog.setCreateTime(new Timestamp(new Date().getTime()));
                pointLog.setGamecode(activityCode);
                pointLog.setPoint(-goods.getRequireScore());
                pointLog.setReason(PointReason.BIND_TEL);
                pointFacade.increasePoint(pointLog);
            }
            newGoods.setSurplusNum(goods.getSurplusNum() - 1);
            newGoods.setExchangeNum(goods.getExchangeNum() + 1);
            goodsService.updateGoods(null, newGoods);
            decrFlag = true;
        }
        return decrFlag;
    }

    private void exchangeVirtualGoods(String openid, Goods goods, int userScore, String activityCode, JSONObject jsonObject) throws JoymeServiceException, JoymeDBException, JSONException {
        //兑换商品需求的分数大于用户分数
        GoodsItem goodItem = goodsItemFacade.getGoodsItemForQyzScore(goods.getGameId(), goods.getGoodsId(), openid, Contants.GOODS_CATEGORY_EXCHANGE);
        if (goodItem == null) {
            jsonObject.put("rs", "0");
            jsonObject.put("msg", "error");
            jsonObject.put("result", "<p>抱歉，商品剩余数量不足！</p>");
        } else {
            jsonObject.put("rs", "1");
            decrGoodsNum(openid, activityCode, goods);
            jsonObject.put("msg", "success");
            jsonObject.put("result", goodItem.getGoodsItemValue());
        }
    }

    public void setGoodsId(Long goods_id, Goods goods) {
        goodsRedis.setGoodsId(goods_id, goods);
    }

    public Long getTotalGoodsPage(String gameId, String goodsCategory) throws JoymeServiceException, JoymeDBException {
        return goodsService.getTotalGoodsPage(null,gameId,goodsCategory);
    }

    private void saveExchangeLog(Goods goods,String openid)throws JoymeServiceException, JoymeDBException{
        ExchangeLog exchangeLog = new ExchangeLog();
        exchangeLog.setGoodsCategory(goods.getGoodCategory());
        exchangeLog.setGameId(goods.getGameId());
        exchangeLog.setCreateTime(new Timestamp(new Date().getTime()));
        exchangeLog.setExchangeTime(new Date());
        exchangeLog.setGoodsId(goods.getGoodsId());
        exchangeLog.setGoodsItemId(0L);
        exchangeLog.setProfileid(openid);
        exchangeLog.setGoodsItemValue(goods.getGoodCategory());
        exchangeLogService.insertExchangeLog(null,exchangeLog);
    }
}
