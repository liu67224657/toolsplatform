package com.enjoyf.activity.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;

public class GoodsItemDao extends BaseJDBCDAOImpl {


    private static final String TABLE_NAME = "goods_item";

    private static final String TABLIE_NAME_EXCHANGE = "exchange_log";


    public GoodsItem getGoodsItemById(final long goodsItemId) throws JoymeDBException {
        DataBean dbean = null;

        Connection conn = null;
        try {

            conn = this.getConnection();

            String sql = "SELECT * FROM " + TABLE_NAME + "  WHERE goods_item_id = ?";

            Object[] parameter = {goodsItemId};

            dbean = this.executeBindingQuery(conn, sql, parameter);

            ResultSet rs = dbean.getRs();

            if (rs.next()) {
                return rsToObject(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }


    public GoodsItem getGoodsItem(long goodsid, String profileid) throws JoymeDBException {
        DataBean dbean = null;

        Connection conn = null;
        try {

            conn = this.getConnection();

            String sql = "SELECT * FROM " + TABLE_NAME + "  WHERE goods_id = ? and profileid=?";

            Object[] parameter = {goodsid, profileid};

            dbean = this.executeBindingQuery(conn, sql, parameter);

            ResultSet rs = dbean.getRs();

            if (rs.next()) {
                return rsToObject(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }

    public GoodsItem getGoodsItemByGameitemvalue(long goodsid, String gameitemvalue) throws JoymeDBException {
        DataBean dbean = null;

        Connection conn = null;
        try {

            conn = this.getConnection();

            String sql = "SELECT * FROM " + TABLE_NAME + "  WHERE goods_id = ? and goods_item_value=?";

            Object[] parameter = {goodsid, gameitemvalue};

            dbean = this.executeBindingQuery(conn, sql, parameter);

            ResultSet rs = dbean.getRs();

            if (rs.next()) {
                return rsToObject(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }

    public PageRows<GoodsItem> queryGoodsItems(final String gameId, final long goodsId, Pagination pagination, Boolean isPage) throws JoymeDBException {
        PageRows<GoodsItem> pageRows = null;
        DataBean dbean = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where status='" + ValidStatus.VALID.getCode() + "' and game_id=? and goods_id=?";
            if (pagination != null) {
                sql += " LIMIT ?,?";
            }
            Object[] parameter = null;

            if (pagination != null) {
                parameter = new Object[4];
                parameter[0] = gameId;
                parameter[1] = goodsId;
                parameter[2] = pagination.getStartRowIdx();
                parameter[3] = pagination.getPageSize();
            }
            dbean = this.executeBindingQuery(conn, sql, parameter);

            List<GoodsItem> retList = new ArrayList<GoodsItem>();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObject(rs));
            }
            if (isPage) {
                String countSql = "SELECT COUNT(goods_item_id) FROM " + TABLE_NAME + " where status='" + ValidStatus.VALID.getCode() + "' and game_id='" + gameId + "'";
                dbean = this.executeBindingQuery(conn, countSql, null);
                ResultSet countRs = dbean.getRs();
                if (countRs.next()) {
                    pagination.setTotalRows(countRs.getInt(1));
                }
            }

            pageRows = new PageRows<GoodsItem>();
            pageRows.setPage(pagination);
            pageRows.setRows(retList);
            return pageRows;
        } catch (SQLException e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean);
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }

    public int updateGoodsItemStatus(final long goodsItemId, final String profileId) throws JoymeDBException {
        Connection conn = null;
        try {
            conn = this.getConnection();
            String sql = "UPDATE goods_item SET status=?,exchange_time=?,profileid=?  WHERE goods_item_id = ?";
            Object[] data = {ValidStatus.REMOVED.getCode(), new Date(), profileId, goodsItemId};
            return this.executeBindingUpdate(conn, sql, data, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }

    public int insertExchangeGoodsItem(GoodsItem goodsItem, String goodsCategory) throws JoymeDBException {
        Connection conn = null;
        try {
            conn = this.getConnection();
            String sql = "INSERT exchange_log(goods_item_id,goods_id,goods_item_value,exchange_time,profileid,create_time,game_id,good_category) values(?,?,?,?,?,?,?,?)";
            Object[] data = {goodsItem.getGoodsItemId(), goodsItem.getGoodsId(), goodsItem.getGoodsItemValue(), goodsItem.getExchangeTime(), goodsItem.getProfileId(), new Date(), goodsItem.getGameId(), goodsCategory};

            return this.executeBindingUpdate(conn, sql, data, false, false);

        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }

    public List<GoodsItem> queryGoodsItemFromExchangeLog(final String profileId, final String gameId, final long goodsId) throws JoymeDBException {
        DataBean dbean = null;

        Connection conn = null;
        try {

            conn = this.getConnection();

            String sql = "SELECT * FROM " + TABLIE_NAME_EXCHANGE + "  WHERE profileid = ? and game_id=? and goods_id=?";

            Object[] paramters = {profileId, gameId, goodsId};

            dbean = this.executeBindingQuery(conn, sql, paramters);

            ResultSet rs = dbean.getRs();

            List<GoodsItem> list = new ArrayList<GoodsItem>();
            while (rs.next()) {
                list.add(rsExchangeToObject(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }

    private GoodsItem rsToObject(ResultSet rs) throws SQLException {

        GoodsItem goodsItem = new GoodsItem();
        goodsItem.setGoodsItemId(rs.getLong("goods_item_id"));
        goodsItem.setGoodsId(rs.getLong("goods_id"));
        goodsItem.setCreateTime(rs.getTimestamp("create_time"));
        goodsItem.setExchangeTime(rs.getTimestamp("exchange_time"));
        goodsItem.setStatus(rs.getString("status"));
        goodsItem.setGoodsItemValue(rs.getString("goods_item_value"));
        goodsItem.setGameId(rs.getString("game_id"));
        goodsItem.setProfileId(rs.getString("profileid"));

        return goodsItem;

    }

    private GoodsItem rsExchangeToObject(ResultSet rs) throws SQLException {

        GoodsItem goodsItem = new GoodsItem();
        goodsItem.setGoodsItemId(rs.getLong("goods_item_id"));
        goodsItem.setGoodsId(rs.getLong("goods_id"));
        goodsItem.setCreateTime(rs.getTimestamp("create_time"));
        goodsItem.setExchangeTime(rs.getTimestamp("exchange_time"));
        goodsItem.setGoodsItemValue(rs.getString("goods_item_value"));
        goodsItem.setGameId(rs.getString("game_id"));
        goodsItem.setProfileId(rs.getString("profileid"));

        return goodsItem;

    }

    public GoodsItem getGoodsItemByOpenIdAndGameId(String gameId, String openid,long goodsId) throws JoymeDBException {
        DataBean dbean = null;

        Connection conn = null;
        try {

            conn = this.getConnection();

            String sql = "SELECT * FROM " + TABLE_NAME + "  WHERE game_id = ? and profileid = ? and goods_id = ?";

            Object[] parameter = {gameId, openid,goodsId};

            dbean = this.executeBindingQuery(conn, sql, parameter);

            ResultSet rs = dbean.getRs();

            if (rs.next()) {
                return rsToObject(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }

    public GoodsItem getGoodsItemByGoodIdAndGameId(Long goodId, String openid) throws JoymeDBException {
        DataBean dbean = null;

        Connection conn = null;
        try {

            conn = this.getConnection();

            String sql = "SELECT * FROM " + TABLE_NAME + "  WHERE goods_id = ? and profileid = ?";

            Object[] parameter = {goodId, openid};

            dbean = this.executeBindingQuery(conn, sql, parameter);

            ResultSet rs = dbean.getRs();

            if (rs.next()) {
                return rsToObject(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }
    public int insertGoodsItem(GoodsItem goodsItem) throws JoymeDBException {
        Connection conn = null;
        try {
            conn = this.getConnection();
            String sql = "INSERT goods_item(goods_id,goods_item_value,create_time,status,game_id) values(?,?,?,?,?)";
            Object[] data = {goodsItem.getGoodsId(), goodsItem.getGoodsItemValue(), goodsItem.getCreateTime(), goodsItem.getStatus(), goodsItem.getGameId()};

            return this.executeBindingUpdate(conn, sql, data, false, false);

        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }
}
