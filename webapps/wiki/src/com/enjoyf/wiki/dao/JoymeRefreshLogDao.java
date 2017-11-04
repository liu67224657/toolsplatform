package com.enjoyf.wiki.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.JoymeRefreshLog;

public class JoymeRefreshLogDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-21 10:14:47
     * 
     * @author shenqiv0.1
     * @param freshLogId
     * @return JoymeRefreshLog
     */
    public JoymeRefreshLog queryJoymeRefreshLogbyId(Connection conn, Integer freshLogId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_refresh_log  WHERE fresh_log_id = ?";
            List objectList = new ArrayList();
            objectList.add(freshLogId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeRefreshLog joymeRefreshLog = new JoymeRefreshLog();
                joymeRefreshLog.setFreshLogId(rs.getInt("fresh_log_id"));
                joymeRefreshLog.setMachineId(rs.getInt("machine_id"));
                joymeRefreshLog.setFreshId(rs.getInt("fresh_id"));
                joymeRefreshLog.setOperationTime(rs.getTimestamp("operation_time"));
                return joymeRefreshLog;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    public int queryMaxRefreshLogId(Connection conn, int machineId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT MAX(fresh_id) as maxId FROM joyme_refresh_log  WHERE machine_id = ?";
            List objectList = new ArrayList();
            objectList.add(machineId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                return rs.getString("maxId") == null ? 0 : Integer.parseInt(rs.getString("maxId"));
            }
            return 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * date 2013-08-21 10:14:47
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertJoymeRefreshLog(Connection conn, JoymeRefreshLog bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_refresh_log(machine_id,fresh_id,operation_time) VALUES (?,?,?)";
            Object[] objects = new Object[] { bean.getMachineId(), bean.getFreshId(), bean.getOperationTime() };
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-21 10:14:47
     * 
     * @author shenqiv0.1
     * @param bean
     * @param freshLogId
     * @return int 1 success, 0 failed
     */
    public int updateJoymeRefreshLog(Connection conn, JoymeRefreshLog bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_refresh_log SET $updateStr  WHERE fresh_log_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getFreshLogId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-21 10:14:47
     * 
     * @author shenqiv0.1
     * @param freshLogId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymeRefreshLog(Connection conn, Integer freshLogId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_refresh_log  WHERE fresh_log_id = ?";
            List objectList = new ArrayList();
            objectList.add(freshLogId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

}