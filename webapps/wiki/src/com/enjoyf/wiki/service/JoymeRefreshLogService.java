package com.enjoyf.wiki.service;

import java.sql.Connection;
import java.sql.Timestamp;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.JoymeRefreshLog;
import com.enjoyf.wiki.dao.JoymeRefreshDao;
import com.enjoyf.wiki.dao.JoymeRefreshLogDao;

public class JoymeRefreshLogService {

    private static JoymeRefreshLogDao subDao = new JoymeRefreshLogDao();
    private static JoymeRefreshDao joymeRefreshDao = new JoymeRefreshDao();

    /**
     * date 2013-08-21 10:16:25
     * 
     * @author shenqiv0.1
     * @param freshLogId
     * @return JoymeRefreshLog
     */
    public JoymeRefreshLog queryJoymeRefreshLogbyId(Connection conn, Integer freshLogId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeRefreshLogbyId(conn, freshLogId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * 查到自己最大的ID
     * 
     * @param conn
     * @param machineId
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public int queryMaxRefreshLogId(Connection conn, int machineId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            int maxId = subDao.queryMaxRefreshLogId(conn, machineId);
            // 如果是新建立的，则maxId为0
            if (maxId == 0) {
                maxId = joymeRefreshDao.queryMaxJoymeRefreshId(conn);
                JoymeRefreshLog bean = new JoymeRefreshLog();
                bean.setFreshId(maxId);
                bean.setMachineId(machineId);
                bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
                this.insertJoymeRefreshLog(conn, bean);
                conn.commit();
            }

            return maxId;
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * date 2013-08-21 10:16:25
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertJoymeRefreshLog(Connection conn, JoymeRefreshLog bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymeRefreshLog(conn, bean);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * date 2013-08-21 10:16:25
     * 
     * @author shenqiv0.1
     * @param bean
     * @param freshLogId
     * @return int 1 success, 0 failed
     */
    public int updateJoymeRefreshLog(Connection conn, JoymeRefreshLog bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymeRefreshLog(conn, bean);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    /**
     * date 2013-08-21 10:16:25
     * 
     * @author shenqiv0.1
     * @param freshLogId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymeRefreshLog(Connection conn, Integer freshLogId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymeRefreshLog(conn, freshLogId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

}