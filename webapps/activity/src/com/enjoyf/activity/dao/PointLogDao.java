package com.enjoyf.activity.dao;

import com.enjoyf.activity.bean.point.PointLog;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PointLogDao extends BaseJDBCDAOImpl {

    /**
     * date 2016-09-07 16:45:11
     *
     * @param pointlogId
     * @return PointLog
     * @author shenqiv0.1
     */
    public PointLog queryPointLogbyId(Connection conn, Long pointlogId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM point_log  WHERE pointlog_id = ?";
            List objectList = new ArrayList();
            objectList.add(pointlogId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                PointLog pointLog = new PointLog();
                pointLog.setPointlogId(rs.getLong("pointlog_id"));
                pointLog.setProfileid(rs.getString("profileid"));
                pointLog.setGamecode(rs.getString("gamecode"));
                pointLog.setReason(rs.getInt("reason"));
                pointLog.setPoint(rs.getInt("point"));
                pointLog.setCreateTime(rs.getTimestamp("create_time"));
                return pointLog;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2016-09-07 16:45:12
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertPointLog(Connection conn, PointLog bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO point_log(profileid,gamecode,reason,point,create_time) VALUES (?,?,?,?,?)";
            Object[] objects = new Object[]{bean.getProfileid(), bean.getGamecode(), bean.getReason(), bean.getPoint(), bean.getCreateTime()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2016-09-07 16:45:12
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updatePointLog(Connection conn, PointLog bean) throws JoymeDBException {
        try {
            String sql = "UPDATE point_log SET $updateStr  WHERE pointlog_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getPointlogId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2016-09-07 16:45:12
     *
     * @param pointlogId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deletePointLog(Connection conn, Long pointlogId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM point_log  WHERE pointlog_id = ?";
            List objectList = new ArrayList();
            objectList.add(pointlogId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    public int querySumPointLogbyId(Connection conn, String profileId, String gameCode) throws JoymeDBException, JoymeServiceException {
        DataBean dbean = null;
        try {
            String sql = "SELECT SUM(point) FROM point_log  WHERE profileid = ? and gamecode=?";
            List objectList = new ArrayList();
            objectList.add(profileId);
            objectList.add(gameCode);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
        return 0;
    }


}