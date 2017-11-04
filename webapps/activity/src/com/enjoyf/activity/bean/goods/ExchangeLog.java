package com.enjoyf.activity.bean.goods;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExchangeLog {
    private Long goodsItemId;
    private Long goodsId;
    private String goodsItemValue;
    private Date exchangeTime;
    private String profileid;
    private String goodsCategory;
    private Timestamp createTime;
    private String gameId;
    private Long id;

    public Long getGoodsItemId() {
        return goodsItemId;
    }

    public void setGoodsItemId(Long goodsItemId) {
        this.goodsItemId = goodsItemId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsItemValue() {
        return goodsItemValue;
    }

    public void setGoodsItemValue(String goodsItemValue) {
        this.goodsItemValue = goodsItemValue;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public String getProfileid() {
        return profileid;
    }

    public void setProfileid(String profileid) {
        this.profileid = profileid;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public List getNotNullColumnList() {
        List columnList = new ArrayList();
        if (goodsItemId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("goods_item_id");
            bean.setObj(goodsItemId);
            columnList.add(bean);
        }
        if (goodsId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("goods_id");
            bean.setObj(goodsId);
            columnList.add(bean);
        }
        if (goodsItemValue != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("goods_item_value");
            bean.setObj(goodsItemValue);
            columnList.add(bean);
        }
        if (goodsCategory != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("good_category");
            bean.setObj(goodsCategory);
            columnList.add(bean);
        }
        if (exchangeTime != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("exchange_time");
            bean.setObj(exchangeTime);
            columnList.add(bean);
        }
        if (profileid != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("profileid");
            bean.setObj(profileid);
            columnList.add(bean);
        }
        if (createTime != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("create_time");
            bean.setObj(createTime);
            columnList.add(bean);
        }
        if (gameId != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("game_id");
            bean.setObj(gameId);
            columnList.add(bean);
        }
        return columnList;
    }
}


