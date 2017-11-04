package com.enjoyf.mcms.service;

import java.sql.Connection;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.JoymeSpec;
import com.enjoyf.mcms.dao.JoymeSpecDao;

public class JoymeSpecService {

    private static JoymeSpecDao subDao = new JoymeSpecDao();

    /**
     * date 2013-08-02 22:11:14
     * 
     * @author shenqiv0.1
     * @param specId
     * @return JoymeSpec
     */
    public JoymeSpec queryJoymeSpecbyId(Connection conn, Integer specId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeSpecbyId(conn, specId);
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
     * 
     * @param conn
     * @param archiveId
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public JoymeSpec queryJoymeSpecByArchiveId(Connection conn,  Integer archiveId) throws JoymeDBException, JoymeServiceException{
        boolean isCloseConn = (conn != null) ? false : true;
        try {
          if (conn == null)
            conn = subDao.getConnection();
            return subDao.queryJoymeSpecByArchiveId(conn, archiveId);
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
     * 通过filePath获得JoymeSpec
     * @param conn
     * @param filePath
     * @return
     * @throws Exception
     */
    public JoymeSpec queryJoymeSpecByFilePath(Connection conn,  String filePath) throws Exception {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
          if (conn == null)
            conn = subDao.getConnection();
            return subDao.queryJoymeSpecByFilePath(conn, filePath);
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
     * date 2013-08-02 22:11:14
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public long insertJoymeSpec(Connection conn, JoymeSpec bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymeSpec(conn, bean);
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
     * date 2013-08-02 22:11:14
     * 
     * @author shenqiv0.1
     * @param bean
     * @param specId
     * @return int 1 success, 0 failed
     */
    public int updateJoymeSpec(Connection conn, JoymeSpec bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymeSpec(conn, bean);
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
     * date 2013-08-02 22:11:14
     * 
     * @author shenqiv0.1
     * @param specId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymeSpec(Connection conn, Integer specId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymeSpec(conn, specId);
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