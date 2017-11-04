package com.enjoyf.activity.dao;

import com.enjoyf.activity.bean.point.Point;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PointDao extends BaseJDBCDAOImpl {

    /**
     * date 2016-09-07 16:32:16
     *
     * @param pointId
     * @return Point
     * @author shenqiv0.1
     */
    public Point queryPointbyId(Connection conn, String pointId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM point  WHERE point_id = ?";
            List objectList = new ArrayList();
            objectList.add(pointId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            List retList = new ArrayList();
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                Point point = new Point();
                point.setPointId(rs.getString("point_id"));
                point.setProfileid(rs.getString("profileid"));
                point.setGamecode(rs.getString("gamecode"));
                point.setPoint(rs.getInt("point"));
                return point;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }


    /**
     * date 2016-09-07 16:32:16
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertPoint(Connection conn, Point bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO point(point_id,profileid,gamecode,point) VALUES (?,?,?,?)";
            Object[] objects = new Object[]{bean.getPointId(), bean.getProfileid(), bean.getGamecode(), bean.getPoint()};
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


    /**
     * date 2016-09-07 16:32:16
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updatePoint(Connection conn, Point bean) throws JoymeDBException {
        try {
            String sql = "UPDATE point SET $updateStr  WHERE point_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getPointId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    public int increasePoint(Connection conn, int point,String point_id) throws JoymeDBException {
        try {
            String sql = "UPDATE point SET point=point+?  WHERE point_id = ?";
            List objectList = new ArrayList();
            objectList.add(point);
            objectList.add(point_id);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }



    /**
     * date 2016-09-07 16:32:16
     *
     * @param pointId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deletePoint(Connection conn, String pointId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM point  WHERE point_id = ?";
            List objectList = new ArrayList();
            objectList.add(pointId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }


}