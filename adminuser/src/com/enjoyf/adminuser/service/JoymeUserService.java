package com.enjoyf.adminuser.service;

import com.enjoyf.adminuser.bean.JoymeUser;
import com.enjoyf.adminuser.dao.JoymeUserDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

import java.sql.Connection;

public class JoymeUserService {

    private static JoymeUserDao subDao = new JoymeUserDao();
    public final static String DATABASE_NAME = "joyme_admin_user";

    /**
     * date 2013-09-16 15:27:45
     *
     * @param userId
     * @return JoymeUser
     * @author shenqiv0.1
     */
    public JoymeUser queryJoymeUserbyId(Connection conn, Integer userId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeUserbyId(conn, userId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    public JoymeUser queryJoymeUserByUserName(Connection conn, String userName, String userPassword) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeUserByUserName(conn, userName, userPassword);
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
     * date 2013-09-16 15:27:45
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeUser(Connection conn, JoymeUser bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymeUser(conn, bean);
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
     * date 2013-09-16 15:27:45
     *
     * @param bean
     * @param userId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeUser(Connection conn, JoymeUser bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymeUser(conn, bean);
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
     * date 2013-09-16 15:27:45
     *
     * @param userId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeUser(Connection conn, Integer userId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymeUser(conn, userId);
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