package com.enjoyf.activity.dao;

import com.enjoyf.activity.bean.sign.Sign;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SignDao extends BaseJDBCDAOImpl {

    /**
     * date 2016-09-07 15:17:46
     *
     * @param signId
     * @return Sign
     * @author shenqiv0.1
     */
    public Sign querySignbyId(Connection conn, Long signId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM sign  WHERE sign_id = ?";
            List objectList = new ArrayList();
            objectList.add(signId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                return rsToObject(rs);
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2016-09-07 15:17:46
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertSign(Connection conn, Sign bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO sign(point,gamecode,signname) VALUES (?,?,?)";
            Object[] objects = new Object[]{bean.getPoint(), bean.getGamecode(), bean.getSignname()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2016-09-07 15:17:46
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateSign(Connection conn, Sign bean) throws JoymeDBException {
        try {
            String sql = "UPDATE sign SET $updateStr  WHERE sign_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getSignId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2016-09-07 15:17:46
     *
     * @param signId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteSign(Connection conn, Long signId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM sign  WHERE sign_id = ?";
            List objectList = new ArrayList();
            objectList.add(signId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    public List querySignByGameCode(Connection conn, String gamecode) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM sign where gamecode=?";
            List objectList = new ArrayList();
            objectList.add(gamecode);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
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

    private Sign rsToObject(ResultSet rs) throws SQLException {
        Sign sign = new Sign();
        sign.setSignId(rs.getLong("sign_id"));
        sign.setPoint(rs.getInt("point"));
        sign.setGamecode(rs.getString("gamecode"));
        sign.setSignname(rs.getString("signname"));
        sign.setSuccessText(rs.getString("success_text"));
        sign.setFailedText(rs.getString("failed_text"));
        return sign;
    }
}