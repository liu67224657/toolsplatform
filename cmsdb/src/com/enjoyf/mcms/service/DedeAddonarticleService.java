package com.enjoyf.mcms.service;

import java.sql.Connection;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.DedeAddonarticle;
import com.enjoyf.mcms.dao.DedeAddonarticleDao;

public class DedeAddonarticleService {

    private static DedeAddonarticleDao subDao = new DedeAddonarticleDao();

    /**
     * date 2013-08-02 15:10:05
     * 
     * @author shenqiv0.1
     * @param aid
     * @return DedeAddonarticle
     */
    public DedeAddonarticle queryDedeAddonarticlebyId(Connection conn, String tableName, int aid) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeAddonarticlebyId(conn, tableName, aid);
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
     * date 2013-08-02 15:10:05
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertDedeAddonarticle(Connection conn, DedeAddonarticle bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertDedeAddonarticle(conn, bean);
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
     * date 2013-08-02 15:10:06
     * 
     * @author shenqiv0.1
     * @param bean
     * @param aid
     * @return int 1 success, 0 failed
     */
    public int updateDedeAddonarticle(Connection conn, DedeAddonarticle bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateDedeAddonarticle(conn, bean);
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
     * date 2013-08-02 15:10:06
     * 
     * @author shenqiv0.1
     * @param aid
     * @return int 1 success, 0 failed
     */
    public int deleteDedeAddonarticle(Connection conn, Object aid) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteDedeAddonarticle(conn, aid);
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