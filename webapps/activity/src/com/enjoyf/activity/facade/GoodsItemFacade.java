package com.enjoyf.activity.facade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.cache.GoodsItemRedis;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.controller.weixinop.WeixinScoreController;
import com.enjoyf.activity.service.GoodsItemService;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.util.CollectionUtil;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;

public class GoodsItemFacade {

    private Logger logger = LoggerFactory.getLogger(WeixinScoreController.class);

    private GoodsItemService goodsItemService = new GoodsItemService();

    private GoodsItemRedis goodsItemRedis = new GoodsItemRedis(PropertiesContainer.getRedisManager());

    private static final int DEFAULT_GOODSITEM_NUM = 500;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 是否已领取过
     *
     * @param gameId
     * @param profileId
     * @param extendKey
     * @return
     */
    public Set<String> isReceivedGoodsItem(String gameId, long goodsId, String profileId, String extendKey) {
        if (!goodsItemRedis.hasExchangeKey(gameId, goodsId, profileId, extendKey)) {//如果键值不存在，则说明没有领取过
            return null;
        }
        Set<String> goodsItemSet = goodsItemRedis.getExchangeGoodsItem(gameId, goodsId, profileId, extendKey);
        try {
            if (CollectionUtil.isEmpty(goodsItemSet)) {
                List<GoodsItem> goodsItemsFromExchangeLog = goodsItemService.queryGoodsItemFromExchangeLog(profileId, gameId, goodsId);
                if (!CollectionUtil.isEmpty(goodsItemsFromExchangeLog)) {
                    goodsItemSet = new HashSet<String>();
                    for (GoodsItem goodsItem : goodsItemsFromExchangeLog) {
                        Date exchangeTime = goodsItem.getExchangeTime();
                        goodsItemSet.add(dateFormat.format(exchangeTime));
                    }
                }
            }

        } catch (JoymeDBException e) {
            logger.error("GoodsItemFacade.isReceivedGoodsItem exception:" + e.getMessage());
            return null;
        }
        return goodsItemSet;
    }

    /**
     * 通用获取激活码
     *
     * @param gameId
     * @param profileId
     * @return
     * @throws JoymeDBException
     */
    public GoodsItem getGoodsItem(String gameId, long goodsId, String profileId, String goodsCategory) throws JoymeDBException {
        return getGoodsItem(gameId, goodsId, profileId, DEFAULT_GOODSITEM_NUM, null, goodsCategory);
    }

    /**
     * 按时间段领取激活码（如隔天，隔月。。。）
     *
     * @param gameId
     * @param profileId
     * @param extendKey(隔天：20160929，隔月：201609)
     * @return
     * @throws JoymeDBException
     */
    public GoodsItem getGoodsItem(String gameId, long goodsId, String profileId, String extendKey, String goodsCategory) throws JoymeDBException {
        return getGoodsItem(gameId, goodsId, profileId, DEFAULT_GOODSITEM_NUM, extendKey, goodsCategory);
    }

    /**
     * 从时间段内释放指定数量的激活码中获取
     *
     * @param gameId
     * @param profileId
     * @param num(该游戏每天释放激活码数,)
     * @param extendKey(隔天：20160929，隔月：201609)
     * @return
     * @throws JoymeDBException
     */
    public GoodsItem getGoodsItem(String gameId, long goodsId, String profileId, int num, String extendKey, String goodsCategory) throws JoymeDBException {
        boolean hasKey = goodsItemRedis.existsKey(gameId, goodsId, extendKey);
        if (!hasKey) {//如果不存在键，则创建缓存数据
            createCache(gameId, goodsId, num, extendKey);
        }
        long goodsItemId = goodsItemRedis.getGoodsItemId(gameId, goodsId, extendKey);

        if (goodsItemId > 0l) {
            GoodsItem goodsItem = goodsItemRedis.getGoodsItem(gameId, goodsId, goodsItemId);
            if (null != goodsItem) {
                Date nowDate = new Date();
                goodsItemRedis.putExchangeGoodsItem(gameId, goodsId, profileId, goodsItemId, dateFormat.format(nowDate));

                goodsItemService.updateGoodsItemStatus(goodsItemId, profileId);
                goodsItemRedis.removeGoodsItem(gameId, goodsId, goodsItemId);

                goodsItem.setExchangeTime(nowDate);
                goodsItem.setProfileId(profileId);
                goodsItemService.insertExchangeGoodsItem(goodsItem, goodsCategory);

                return goodsItem;
            }
        }
        return null;
    }

    /**
     * 查看激活码是否已领完
     *
     * @param gameId
     * @param goodsId
     * @param extendKey
     * @return
     */
    public boolean hasSurplusGoodsItem(String gameId, long goodsId, String extendKey) {
        boolean exists = goodsItemRedis.existsKey(gameId, goodsId, extendKey);
        if (exists) {
            long items = goodsItemRedis.countGoodsItemId(gameId, goodsId, extendKey);
            if (items > 0l) {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * 创建激活码缓存
     *
     * @param gameId
     * @param num
     * @param extendKey
     * @throws JoymeDBException
     */
    private void createCache(String gameId, long goodsId, int num, String extendKey) throws JoymeDBException {
        if (num > 0) {
            Pagination pagination = new Pagination(num, 1, num);
            PageRows<GoodsItem> pageRows = goodsItemService.queryGoodsItems(gameId, goodsId, pagination, true);
            if (!CollectionUtil.isEmpty(pageRows.getRows())) {
                goodsItemRedis.putGoodsItem(gameId, goodsId, pageRows.getRows());
                List<Long> goodsItemsList = new ArrayList<Long>();
                for (GoodsItem goodsItem : pageRows.getRows()) {
                    goodsItemsList.add(goodsItem.getGoodsItemId());
                }
                goodsItemRedis.putGoodsItemId(gameId, goodsItemsList, goodsId, 0, extendKey);
            }
        }
    }

    public GoodsItem getGoodsItemForQyz(String gameId, long goodsId, String openid, String goodsCategory) throws JoymeDBException {
        GoodsItem goodsItem = goodsItemService.getGoodsItemByOpenIdAndGameId(gameId, openid,goodsId);
        if (goodsItem == null) {
            return this.getGoodsItem(gameId, goodsId, openid, goodsCategory);
        } else {
            return goodsItem;
        }

    }

    public GoodsItem getGoodsItemForQyzScore(String gameId, long goodsId, String openid, String goodsCategory) throws JoymeDBException {
        GoodsItem goodsItem = goodsItemService.getGoodsItemByGoodIdAndGameId(goodsId, openid);
        if (goodsItem == null) {
            return this.getGoodsItem(gameId, goodsId, openid, goodsCategory);
        } else {
            return goodsItem;
        }

    }
    public void saveGoodsItem(GoodsItem goodsItem) throws JoymeDBException {
        goodsItemService.insertGoodsItem(goodsItem);
    }

    public GoodsItem getGoodsItemByGameitemvalue(long goodid, String goodsItemValue) throws JoymeDBException {
        return goodsItemService.getGoodsItemByGameitemvalue(goodid, goodsItemValue);
    }

    public void pushGoodsItem(Long goods_id, GoodsItem goodsItem) {
        goodsItemRedis.pushGoodsItem(goods_id, goodsItem);
    }


}
