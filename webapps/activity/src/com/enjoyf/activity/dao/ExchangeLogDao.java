package com.enjoyf.activity.dao;

import com.enjoyf.activity.bean.goods.ExchangeLog;
import com.enjoyf.activity.bean.sign.Signlog;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ExchangeLogDao extends BaseJDBCDAOImpl {

    /**
     * date 2016-10-11 14:02:43
     *
     * @param id
     * @return ExchangeLog
     * @author shenqiv0.1
     */
    public ExchangeLog queryExchangeLogbyId(Connection conn, Long id) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM exchange_log  WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return rsToObjet(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2016-10-11 14:02:44
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertExchangeLog(Connection conn, ExchangeLog bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO exchange_log(goods_item_id,goods_id,goods_item_value,exchange_time,profileid,create_time,game_id,good_category) VALUES (?,?,?,?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getGoodsItemId(), bean.getGoodsId(), bean.getGoodsItemValue(), bean.getExchangeTime(), bean.getProfileid(), bean.getCreateTime(), bean.getGameId(),bean.getGoodsCategory()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2016-10-11 14:02:44
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateExchangeLog(Connection conn, ExchangeLog bean) throws JoymeDBException {
        try {
            String sql = "UPDATE exchange_log SET $updateStr  WHERE id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2016-10-11 14:02:44
     *
     * @param id
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteExchangeLog(Connection conn, Long id) throws JoymeDBException {
        try {
            String sql = "DELETE FROM exchange_log  WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    public List<ExchangeLog> getExchangeGoodsPage(Connection conn, String openid, String goodsCategoryExchange, int pSize, int pNum) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String countSql = "SELECT * from exchange_log  where profileid = ? and good_category = ? order by create_time desc limit " + pSize*pNum + ","+ pSize;
            List objectList = new ArrayList();
            objectList.add(openid);
            objectList.add(goodsCategoryExchange);
            dbean = this.executeBindingQuery(conn, countSql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObjet(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private ExchangeLog rsToObjet(ResultSet rs) throws SQLException {
        ExchangeLog exchangeLog = new ExchangeLog();
        exchangeLog.setGoodsItemId(rs.getLong("goods_item_id"));
        exchangeLog.setGoodsId(rs.getLong("goods_id"));
        exchangeLog.setGoodsItemValue(rs.getString("goods_item_value"));
        exchangeLog.setExchangeTime(rs.getDate("exchange_time"));
        exchangeLog.setProfileid(rs.getString("profileid"));
        exchangeLog.setCreateTime(rs.getTimestamp("create_time"));
        exchangeLog.setGameId(rs.getString("game_id"));
        exchangeLog.setId(rs.getLong("id"));
        exchangeLog.setGoodsCategory(rs.getString("good_category"));
        return exchangeLog;

    }

    public Long getTotalExchangeLog(Connection conn, String openid, String goodsCategoryExchange) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String countSql = "SELECT count(*) from exchange_log  where profileid = ? and good_category = ?";
            List objectList = new ArrayList();
            objectList.add(openid);
            objectList.add(goodsCategoryExchange);
            long count = 0;
            dbean = this.executeBindingQuery(conn, countSql, objectList.toArray());
            ResultSet countRs = dbean.getRs();
            if (countRs.next()) {
                count = countRs.getLong(1);
            }
            return count;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public ExchangeLog queryExchangeLogbyParam(Connection conn, String openid, Long goodsId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM exchange_log  WHERE profileid = ? and goods_id = ?";
            List objectList = new ArrayList();
            objectList.add(openid);
            objectList.add(goodsId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return rsToObjet(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
}