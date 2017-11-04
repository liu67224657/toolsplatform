package com.enjoyf.activity.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;

public class GoodsDao extends BaseJDBCDAOImpl {

    private static final String TABLE_NAME = "goods";

    public Goods getGoodsById(final long goodsId) throws JoymeDBException {
        DataBean dbean = null;

        Connection conn = null;
        try {

            conn = this.getConnection();

            String sql = "SELECT * FROM " + TABLE_NAME + "  WHERE goods_id = ?";

            Object[] parameter = {goodsId};

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

    public PageRows<Goods> queryGoods(String gameId, Pagination pagination, Boolean isPage, ValidStatus validStatus)
            throws JoymeDBException {
        PageRows<Goods> pageRows = new PageRows<Goods>();
        DataBean dbean = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            String sql = "SELECT * FROM " + TABLE_NAME + " where status='" + validStatus.getCode() + "' and game_id='"
                    + gameId + "'";
            if (pagination != null) {
                sql += " LIMIT ?,?";
            }
            Object[] parameter = null;

            if (pagination != null) {
                parameter = new Object[2];
                parameter[0] = pagination.getStartRowIdx();
                parameter[1] = pagination.getPageSize();
            }
            dbean = this.executeBindingQuery(conn, sql, parameter);

            List<Goods> retList = new ArrayList<Goods>();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObject(rs));
            }
            if (isPage) {
                String countSql = "SELECT COUNT(goods_id) FROM " + TABLE_NAME + " where status='"
                        + validStatus.getCode() + "' and game_id='" + gameId + "'";
                dbean = this.executeBindingQuery(conn, countSql, null);
                ResultSet countRs = dbean.getRs();
                if (countRs.next()) {
                    pagination.setTotalRows(countRs.getInt(1));
                }
            }

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

    public int updateGoodsStatus(final long goodsItemId, ValidStatus validStatus) throws JoymeDBException {
        Connection conn = null;
        try {
            conn = this.getConnection();
            String sql = "UPDATE goods_item SET status=?  WHERE goods_item_id = ?";
            Object[] parameter = {validStatus.getCode(), goodsItemId};
            return this.executeBindingUpdate(conn, sql, parameter, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }

    public int insertGoods(Goods goods) throws JoymeDBException {
        Connection conn = null;
        try {
            conn = this.getConnection();
            String sql = "INSERT goods(goods_name,game_id,require_score,image_path,good_type,good_category,total_num,exchange_num,surplus_num,description,create_time,expire_time,status) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Object[] parameter = {goods.getGoodsName(), goods.getGameId(), goods.getRequireScore(), goods.getImagePath(), goods.getGoodType(), goods.getGoodCategory(), goods.getTotalNum(), goods.getExchangeNum(), goods.getSurplusNum(), goods.getDescription(), goods.getCreateTime(),
                    goods.getExpireTime(), goods.getStatus().getCode()};

            return this.executeBindingUpdate(conn, sql, parameter, false, false);

        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }

    private Goods rsToObject(ResultSet rs) throws SQLException {

        Goods goods = new Goods();
        goods.setGoodsId(rs.getLong("goods_id"));
        goods.setGoodsName(rs.getString("goods_name"));
        goods.setDescription(rs.getString("description"));
        goods.setRequireScore(rs.getInt("require_score"));
        goods.setExchangeNum(rs.getInt("exchange_num"));
        goods.setGoodCategory(rs.getString("good_category"));
        goods.setGoodType(rs.getString("good_type"));
        goods.setImagePath(rs.getString("image_path"));
        goods.setSurplusNum(rs.getInt("surplus_num"));
        goods.setTotalNum(rs.getInt("total_num"));
        goods.setCreateTime(rs.getTimestamp("create_time"));
        goods.setStatus(ValidStatus.getByCode(rs.getString("status")));
        goods.setGameId(rs.getString("game_id"));
        goods.setExpireTime(rs.getTimestamp("expire_time"));

        return goods;

    }

    public List<Goods> queryGoodsPage(String gameId, String goodsCategory, int pageSize, int pageNum) throws JoymeDBException{
        Connection conn = null;
        DataBean dbean = null;
        try {
            conn = this.getConnection();
            Date date = new Date();
            String countSql = "SELECT * from goods  where game_id = ? and good_category = ? and expire_time>=? order by create_time asc limit " + pageSize*pageNum + ","+ pageSize;
            List objectList = new ArrayList();
            objectList.add(gameId);
            objectList.add(goodsCategory);
            objectList.add(date);
            dbean = this.executeBindingQuery(conn, countSql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                retList.add(rsToObject(rs));
            }
            return retList;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public int updateGoods(Connection conn,Goods goods) throws JoymeDBException{
        try {
            String sql = "UPDATE goods SET $updateStr  WHERE goods_id = ? ";

            List objectList = new ArrayList();

            List columnList = goods.getNotNullColumnList();

            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));

            objectList.add(goods.getGoodsId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);

        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public Goods queryGoodsByName(String goodsName) throws JoymeDBException {
        DataBean dbean = null;

        Connection conn = null;
        try {

            conn = this.getConnection();

            String sql = "SELECT * FROM " + TABLE_NAME + "  WHERE goods_name = ?";

            Object[] parameter = {goodsName};

            dbean = this.executeBindingQuery(conn, sql, parameter);

            ResultSet rs = dbean.getRs();

            if (rs.next()) {
                return rsToObject(rs);
            }
            return null;

        } catch (SQLException e) {
            throw new JoymeDBException(e);
        } catch (JoymeDBException e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
            if (null != conn) {
                closeConnection(conn);
            }
        }
    }

    public Long getTotalGoodsPage(Connection conn, String gameId, String goodsCategory) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String countSql = "SELECT count(*) from goods  where game_id = ?  and good_category = ?";
            List objectList = new ArrayList();
            objectList.add(gameId);
            objectList.add(goodsCategory);
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
}
