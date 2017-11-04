package com.enjoyf.crwalwiki.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enjoyf.crwalwiki.bean.CrwalLog;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

public class CrwalLogDao extends BaseJDBCDAOImpl {

    /**
     * date 2013-08-24 14:37:28
     * 
     * @author shenqiv0.1
     * @param id
     * @return CrwalLog
     */
    public CrwalLog queryCrwalLogbyId(Connection conn, Integer id) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM crwal_log WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                CrwalLog crwalLog = getCrwalLog(rs);
                return crwalLog;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    private CrwalLog getCrwalLog(ResultSet rs) throws SQLException {
        CrwalLog crwalLog = new CrwalLog();
        crwalLog.setId(rs.getInt("id"));
        crwalLog.setCrwalKey(rs.getString("crwal_key"));
        crwalLog.setCreateTime(rs.getTimestamp("create_time"));
        crwalLog.setIsRunning(rs.getInt("is_running"));
        crwalLog.setIsFinish(rs.getInt("is_finish"));
        crwalLog.setDownloadUrl(rs.getString("download_url"));
        crwalLog.setCrwalType(rs.getInt("crwal_type"));
        crwalLog.setPageUrls(rs.getString("page_urls"));
        return crwalLog;
    }

    /**
     * date 2013-08-24 14:37:28
     * 
     * @author shenqiv0.1
     * @param id
     * @return CrwalLog
     */
    public CrwalLog queryOldestNoCrwalLog(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM crwal_log  WHERE is_running = 0 AND is_finish = 0 LIMIT 0 , 1 ";
            dbean = this.executeBindingQuery(conn, sql, null);
            ResultSet rs = dbean.getRs();
            while (rs.next()) {
                CrwalLog crwalLog = getCrwalLog(rs);
                return crwalLog;
            }
            return null;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * date 2013-08-24 14:37:28
     * 
     * @author shenqiv0.1
     * @param id
     * @return CrwalLog
     */
    public List queryLastItems(Connection conn) throws JoymeDBException {
        DataBean dbean = null;
        try {
            String sql = "SELECT * FROM crwal_log  ORDER BY id DESC LIMIT 0 , 30 ";
            dbean = this.executeBindingQuery(conn, sql, null);
            ResultSet rs = dbean.getRs();
            List list = new ArrayList();
            while (rs.next()) {
                CrwalLog crwalLog = getCrwalLog(rs);
                list.add(crwalLog);
            }
            return list;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            this.cleanup(dbean, false);
        }
    }

    /**
     * date 2013-08-24 14:37:28
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertCrwalLog(Connection conn, CrwalLog bean) throws JoymeDBException {
        try {
            String sql = "INSERT INTO crwal_log(crwal_key,create_time,is_running,is_finish,download_url,crwal_type,page_urls) VALUES (?,?,?,?,?,?,?)";
            Object[] objects = new Object[] {bean.getCrwalKey(),bean.getCreateTime(),bean.getIsRunning(),bean.getIsFinish(),bean.getDownloadUrl(),bean.getCrwalType(),bean.getPageUrls()}; 
            return this.executeBindingUpdate(conn, sql, objects, false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-24 14:37:29
     * 
     * @author shenqiv0.1
     * @param bean
     * @param id
     * @return int 1 success, 0 failed
     */
    public int updateCrwalLog(Connection conn, CrwalLog bean) throws JoymeDBException {
        try {
            String sql = "UPDATE crwal_log SET $updateStr  WHERE id = ?";
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
     * date 2013-08-24 14:37:29
     * 
     * @author shenqiv0.1
     * @param bean
     * @param id
     * @return int 1 success, 0 failed
     */
    public int updateCrwalLogRunning(Connection conn, int id) throws JoymeDBException {
        try {
            String sql = "UPDATE crwal_log SET is_running = 1 WHERE id = ?";
            return this.executeBindingUpdate(conn, sql, new Object[] { id }, true, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-24 14:37:29
     * 
     * @author shenqiv0.1
     * @param bean
     * @param id
     * @return int 1 success, 0 failed
     */
    public int updateCrwalLogFinish(Connection conn, int isFinish, int id, String downloadUrl) throws JoymeDBException {
        try {
            String sql = "UPDATE crwal_log SET is_finish = ? , download_url = ? WHERE id = ?";
            return this.executeBindingUpdate(conn, sql, new Object[] { isFinish, downloadUrl, id }, true, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

    /**
     * date 2013-08-24 14:37:29
     * 
     * @author shenqiv0.1
     * @param id
     * @return int 1 success, 0 failed
     */
    public int deleteCrwalLog(Connection conn, Integer id) throws JoymeDBException {
        try {
            String sql = "DELETE FROM crwal_log  WHERE id = ?";
            List objectList = new ArrayList();
            objectList.add(id);
            return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
        } catch (Exception e) {
            throw new JoymeDBException(e);
        }
    }

}