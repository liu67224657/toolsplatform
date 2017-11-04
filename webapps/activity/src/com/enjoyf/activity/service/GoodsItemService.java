package com.enjoyf.activity.service;

import java.util.List;

import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.dao.GoodsItemDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;

public class GoodsItemService {

    private GoodsItemDao goodsItemDao = new GoodsItemDao();

    public GoodsItem getGoodsItemById(final long goodsItemId) throws JoymeDBException {
        return goodsItemDao.getGoodsItemById(goodsItemId);
    }

    public GoodsItem getGoodsItemById(long goodid, String profileid) throws JoymeDBException {
        return goodsItemDao.getGoodsItem(goodid, profileid);
    }

    public GoodsItem getGoodsItemByGameitemvalue(long goodid, String goodsItemValue) throws JoymeDBException {
        return goodsItemDao.getGoodsItemByGameitemvalue(goodid, goodsItemValue);
    }

    public PageRows<GoodsItem> queryGoodsItems(final String gameId, final long goodsId, Pagination pagination, Boolean isPage) throws JoymeDBException {
        return goodsItemDao.queryGoodsItems(gameId, goodsId, pagination, isPage);
    }

    public int updateGoodsItemStatus(final long goodsItemId, final String profileId) throws JoymeDBException {
        return goodsItemDao.updateGoodsItemStatus(goodsItemId, profileId);
    }

    public int insertExchangeGoodsItem(GoodsItem goodsItem, String goodsCategory) throws JoymeDBException {
        return goodsItemDao.insertExchangeGoodsItem(goodsItem, goodsCategory);
    }

    public List<GoodsItem> queryGoodsItemFromExchangeLog(final String profileId, final String gameId, final long goodsId) throws JoymeDBException {
        return goodsItemDao.queryGoodsItemFromExchangeLog(profileId, gameId, goodsId);
    }

    public GoodsItem getGoodsItemByOpenIdAndGameId(String gameId, String openid,long goodsId) throws JoymeDBException {
        return goodsItemDao.getGoodsItemByOpenIdAndGameId(gameId, openid,goodsId);
    }

    public GoodsItem getGoodsItemByGoodIdAndGameId(Long goodid, String openid) throws JoymeDBException {
        return goodsItemDao.getGoodsItemByGoodIdAndGameId(goodid, openid);
    }
    public int insertGoodsItem(GoodsItem goodsItem) throws JoymeDBException {
        return goodsItemDao.insertGoodsItem(goodsItem);
    }
}
