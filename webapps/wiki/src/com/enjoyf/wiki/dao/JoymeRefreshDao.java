package com.enjoyf.wiki.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;
import com.enjoyf.wiki.bean.JoymeRefresh;

public class JoymeRefreshDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-21 09:27:25
     * 
     * @author shenqiv0.1
     * @param freshId
     * @return JoymeRefresh
     */
    public JoymeRefresh queryJoymeRefreshbyId(Connection conn, Integer freshId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_refresh  WHERE fresh_id = ?";
            List objectList = new ArrayList();
            objectList.add(freshId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                JoymeRefresh joymeRefresh = getJoymeFresh(rs);
                return joymeRefresh;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }
    
    /**
     * 获得最大的刷新ID
     * @param conn
     * @return
     * @throws JoymeDBException
     */
    public int queryMaxJoymeRefreshId(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT MAX(fresh_id) AS maxId FROM joyme_refresh;";
            dbean = this.executeBindingQuery(conn, sql, null);
            ResultSet rs = dbean.getRs();
            if (rs.next()) {
                return rs.getString("maxId") == null ? 0 : rs.getInt("maxId");
            }
            return 0;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * 查询自己没有执行的。
     * @param conn
     * @param machineId
     * @return
     * @throws JoymeDBException
     */
    public List queryJoymeRefreshItem(Connection conn , int maxFreshId) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM joyme_refresh WHERE fresh_id > ?";
            List objectList = new ArrayList();
            objectList.add(maxFreshId);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            List list = new ArrayList();
            while (rs.next()) {
                JoymeRefresh joymeRefresh = getJoymeFresh(rs);
                list.add(joymeRefresh);
            }
            return list;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private JoymeRefresh getJoymeFresh(ResultSet rs) throws SQLException {
        JoymeRefresh joymeRefresh = new JoymeRefresh();
        joymeRefresh.setFreshId(rs.getInt("fresh_id"));
        joymeRefresh.setFreshContent(rs.getString("fresh_content"));
        joymeRefresh.setMachineId(rs.getInt("machine_id"));
        joymeRefresh.setOperationTime(rs.getTimestamp("operation_time"));
        joymeRefresh.setOperationUser(rs.getString("operation_user"));
        return joymeRefresh;
    }

    /**
     * date 2013-08-21 09:27:26
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertJoymeRefresh(Connection conn, JoymeRefresh bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO joyme_refresh(fresh_content,machine_id,operation_time,operation_user) VALUES (?,?,?,?)";
            Object[] objects = new Object[] { bean.getFreshContent(), bean.getMachineId(), bean.getOperationTime(), bean.getOperationUser() };
            // return this.executeBindingUpdate(conn, sql, objects, false,
            // false);
            return (int)this.getLongKeyFromExecuteBindingUpdate(conn, sql, objects, true);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-21 09:27:26
     * 
     * @author shenqiv0.1
     * @param bean
     * @param freshId
     * @return int 1 success, 0 failed
     */
    public int updateJoymeRefresh(Connection conn, JoymeRefresh bean) throws JoymeDBException {
        try {
            String sql = "UPDATE joyme_refresh SET $updateStr  WHERE fresh_id = ?";
            List objectList = new ArrayList();
            List columnList = bean.getNotNullColumnList();
            sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
            objectList.add(bean.getFreshId());
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-21 09:27:26
     * 
     * @author shenqiv0.1
     * @param freshId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymeRefresh(Connection conn, Integer freshId) throws JoymeDBException {
        try {
            String sql = "DELETE FROM joyme_refresh  WHERE fresh_id = ?";
            List objectList = new ArrayList();
            objectList.add(freshId);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

}
