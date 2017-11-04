package com.enjoyf.activity.bean.goods;
import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Goods implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long  goodsId;
	private String  goodsName;
	private String  gameId;
	private Integer  requireScore;
	private String  imagePath;
	private String  goodType;
    private String  goodCategory;
	private Integer  totalNum;
	private Integer  exchangeNum;
	private Integer  surplusNum;
	private String  description;
	private Date createTime;
	private Date  expireTime;
	private ValidStatus status;

	public Long getGoodsId(){
		return goodsId;
	}

	public void setGoodsId(Long goodsId){
		this.goodsId = goodsId;
	}

	public String getGoodsName(){
		return goodsName;
	}

	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}

	public String getGameId(){
		return gameId;
	}

	public void setGameId(String gameId){
		this.gameId = gameId;
	}

	public Integer getRequireScore(){
		return requireScore;
	}

	public void setRequireScore(Integer requireScore){
		this.requireScore = requireScore;
	}

	public String getImagePath(){
		return imagePath;
	}

	public void setImagePath(String imagePath){
		this.imagePath = imagePath;
	}

	public String getGoodType(){
		return goodType;
	}

	public void setGoodType(String goodType){
		this.goodType = goodType;
	}

    public String getGoodCategory(){
        return goodCategory;
    }

    public void setGoodCategory(String goodCategory){
        this.goodCategory = goodCategory;
    }

	public Integer getTotalNum(){
		return totalNum;
	}

	public void setTotalNum(Integer totalNum){
		this.totalNum = totalNum;
	}

	public Integer getExchangeNum(){
		return exchangeNum;
	}

	public void setExchangeNum(Integer exchangeNum){
		this.exchangeNum = exchangeNum;
	}

	public Integer getSurplusNum(){
		return surplusNum;
	}

	public void setSurplusNum(Integer surplusNum){
		this.surplusNum = surplusNum;
	}

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getExpireTime(){
		return expireTime;
	}

	public void setExpireTime(Date expireTime){
		this.expireTime = expireTime;
	}

	public ValidStatus getStatus(){
		return status;
	}

	public void setStatus(ValidStatus status){
		this.status = status;
	}


	public List getNotNullColumnList(){
		List columnList = new ArrayList();
		if(goodsName != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("goods_name");
			bean.setObj(goodsName);
			columnList.add(bean);
		}
		if(gameId != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("game_id");
			bean.setObj(gameId);
			columnList.add(bean);
		}
		if(requireScore != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("require_score");
			bean.setObj(requireScore);
			columnList.add(bean);
		}
		if(imagePath != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("image_path");
			bean.setObj(imagePath);
			columnList.add(bean);
		}
		if(goodType != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("good_type");
			bean.setObj(goodType);
			columnList.add(bean);
		}
        if(goodCategory != null) {
            NotNullColumnBean bean = new NotNullColumnBean();
            bean.setColumnName("good_category");
            bean.setObj(goodCategory);
            columnList.add(bean);
        }
		if(totalNum != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("total_num");
			bean.setObj(totalNum);
			columnList.add(bean);
		}
		if(exchangeNum != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("exchange_num");
			bean.setObj(exchangeNum);
			columnList.add(bean);
		}
		if(surplusNum != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("surplus_num");
			bean.setObj(surplusNum);
			columnList.add(bean);
		}
		if(description != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("description");
			bean.setObj(description);
			columnList.add(bean);
		}
		if(createTime != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("create_time");
			bean.setObj(createTime);
			columnList.add(bean);
		}
		if(expireTime != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("expire_time");
			bean.setObj(expireTime);
			columnList.add(bean);
		}
		if(status != null) {
			NotNullColumnBean bean = new NotNullColumnBean();
			bean.setColumnName("status");
			bean.setObj(status.getCode());
			columnList.add(bean);
		}
		return columnList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String toJson(){
		return new Gson().toJson(this);
	}
	public static Goods toObject(String json){
		return new Gson().fromJson(json, Goods.class);
	}
}

