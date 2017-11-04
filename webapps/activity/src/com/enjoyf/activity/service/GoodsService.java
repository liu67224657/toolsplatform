package com.enjoyf.activity.service;

import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.dao.GoodsDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;

import java.sql.Connection;
import java.util.List;

public class GoodsService {

	private GoodsDao goodsDao=new GoodsDao();
	
	public Goods getGoodsById(final long goodsId) throws JoymeDBException {
		return goodsDao.getGoodsById(goodsId);
	}

	public PageRows<Goods> queryGoods(String gameId, Pagination pagination, Boolean isPage, ValidStatus validStatus)
			throws JoymeDBException {
		return goodsDao.queryGoods(gameId, pagination, isPage, validStatus);
	}

	public int updateGoodsStatus(final long goodsItemId, ValidStatus validStatus) throws JoymeDBException {
		return goodsDao.updateGoodsStatus(goodsItemId, validStatus);
	}

	public int insertGoods(Goods goods) throws JoymeDBException {
		return goodsDao.insertGoods(goods);
	}

    public List<Goods> queryGoodsPage(String gameId, String goodsCategory, int pageSize, int pageNum) throws JoymeDBException {
    	return goodsDao.queryGoodsPage(gameId,goodsCategory,pageSize,pageNum);
    }

	public int updateGoods(Connection conn, Goods goods) throws JoymeDBException, JoymeServiceException {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = goodsDao.getConnection();
			return goodsDao.updateGoods(conn,goods);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				goodsDao.closeConnection(conn);
		}
	}

    public Goods queryGoodsByName(Connection conn,String goodsName) throws JoymeDBException, JoymeServiceException{
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = goodsDao.getConnection();
			return goodsDao.queryGoodsByName(goodsName);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				goodsDao.closeConnection(conn);
		}
    }

	public Long getTotalGoodsPage(Connection conn,String gameId, String goodsCategory) throws JoymeDBException, JoymeServiceException {
		boolean isCloseConn = (conn != null) ? false : true;
		try {
			if (conn == null)
				conn = goodsDao.getConnection();
			return goodsDao.getTotalGoodsPage(conn,gameId,goodsCategory);
		} catch (JoymeDBException e) {
			throw e;
		} catch (Exception e) {
			throw new JoymeServiceException(e);
		} finally {
			if (conn != null && isCloseConn)
				goodsDao.closeConnection(conn);
		}
	}
}
