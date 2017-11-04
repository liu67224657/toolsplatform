package com.enjoyf.activity.service;

import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.activity.dao.UserinfoDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

import java.sql.Connection;

public class UserinfoService {

    private static UserinfoDao subDao = new UserinfoDao();

    /**
     * date 2016-09-20 10:19:27
     *
     * @param profileid
     * @return Userinfo
     * @author shenqiv0.1
     */
    public Userinfo queryUserinfobyId(Connection conn, String profileid) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryUserinfobyId(conn, profileid);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public Userinfo queryUserinfobyUserInfo(Connection conn, String username) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryUserinfobyUserInfo(conn, username);
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
     * date 2016-09-20 10:19:27
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertUserinfo(Connection conn, Userinfo bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertUserinfo(conn, bean);
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
     * date 2016-09-20 10:19:28
     *
     * @param bean
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateUserinfo(Connection conn, Userinfo bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateUserinfo(conn, bean);
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
     * date 2016-09-20 10:19:28
     *
     * @param profileid
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteUserinfo(Connection conn, String profileid) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteUserinfo(conn, profileid);
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