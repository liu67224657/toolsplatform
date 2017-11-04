package com.enjoyf.cms.service;

import java.sql.Timestamp;

import com.enjoyf.cms.bean.JoymeRefreshLog;
import com.enjoyf.cms.dao.JoymeRefreshDao;
import com.enjoyf.cms.dao.JoymeRefreshLogDao;
import com.enjoyf.framework.jdbc.bean.ConnectionBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

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
    public JoymeRefreshLog queryJoymeRefreshLogbyId(ConnectionBean conn, Integer freshLogId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
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
    public int queryMaxRefreshLogId(ConnectionBean conn, int machineId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
            int maxId = subDao.queryMaxRefreshLogId(conn, machineId);
            // 如果是新建立的，则maxId为0
            if (maxId == 0) {
                maxId = joymeRefreshDao.queryMaxJoymeRefreshId(conn);
                JoymeRefreshLog bean = new JoymeRefreshLog();
                bean.setFreshId(maxId);
                bean.setMachineId(machineId);
                bean.setOperationTime(new Timestamp(System.currentTimeMillis()));
                this.insertJoymeRefreshLog(conn, bean);
                conn.getWriteConnection().commit();
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
    public int insertJoymeRefreshLog(ConnectionBean conn, JoymeRefreshLog bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
            if(bean.getFreshId() != 0)
                return subDao.insertJoymeRefreshLog(conn, bean);
            return 1;
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
    public int updateJoymeRefreshLog(ConnectionBean conn, JoymeRefreshLog bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
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
    public int deleteJoymeRefreshLog(ConnectionBean conn, Integer freshLogId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
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