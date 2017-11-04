package com.enjoyf.activity.bean.goods;

import java.io.Serializable;
import java.util.Date;

import com.enjoyf.activity.bean.ValidStatus;
import com.google.gson.Gson;

/**
 * 礼包、激活码封装类
 * @author huazhang
 *
 */
public class GoodsItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long goodsItemId;//

	private long goodsId;//对应的礼品id
	
	private String goodsItemValue;//码值
	
	private String gameId;//游戏
	
	private String status;//兑换状态
	
	private Date exchangeTime;//兑换时间
	
	private String profileId;//兑换人
	
	private Date createTime;

	
	public long getGoodsItemId() {
		return goodsItemId;
	}

	public void setGoodsItemId(long goodsItemId) {
		this.goodsItemId = goodsItemId;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getGoodsItemValue() {
		return goodsItemValue;
	}

	public void setGoodsItemValue(String goodsItemValue) {
		this.goodsItemValue = goodsItemValue;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public String toJson(){
		return new Gson().toJson(this);
	}
	
	public static GoodsItem toObject(String json){
		return new Gson().fromJson(json, GoodsItem.class);
	}
}
