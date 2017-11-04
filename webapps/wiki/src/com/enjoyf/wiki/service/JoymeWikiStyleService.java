package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.JoymeWikiStyleSimpleBean;
import com.enjoyf.wiki.bean.JoymeWikiStyle;
import com.enjoyf.wiki.dao.JoymeWikiStyleDao;

import java.sql.Connection;

public class JoymeWikiStyleService {

    private static JoymeWikiStyleDao subDao = new JoymeWikiStyleDao();

    /**
     * date 2013-10-17 12:20:08
     *
     * @param joymeWikiStyleId
     * @return JoymeWikiStyle
     * @author shenqiv0.1
     */
    public JoymeWikiStyle queryJoymeWikiStylebyId(Connection conn, Integer joymeWikiStyleId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeWikiStylebyId(conn, joymeWikiStyleId);
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
     * date 2013-10-17 12:20:08
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeWikiStyle(Connection conn, JoymeWikiStyle bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymeWikiStyle(conn, bean);
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
     * date 2013-10-17 12:20:08
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeWikiStyle(Connection conn, JoymeWikiStyle bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymeWikiStyle(conn, bean);
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
     * date 2013-10-17 12:20:08
     *
     * @param joymeWikiStyleId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeWikiStyle(Connection conn, Integer joymeWikiStyleId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymeWikiStyle(conn, joymeWikiStyleId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public JoymeWikiStyle queryJoymeWikiStyle(Connection conn, String joymeWikiKey, Integer joymeWikiIsindex, String joymeWikiChannel, String contextPath) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeWikiStyle(conn, joymeWikiKey, joymeWikiIsindex, joymeWikiChannel, contextPath);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


    public void saveOrUpdateJoymeWikiStyle(Connection conn, JoymeWikiStyle bean) throws Exception {
        JoymeWikiStyleSimpleBean version = getJoymeWikiStyleVersion(conn, bean.getJoymeWikiKey(), bean.getJoymeWikiIsindex(), bean.getJoymeWikiChannel(), bean.getContextPath());
        if (version != null) {
            bean.setJoymeWikiStyleId(version.getJoymeWikiStyleId());
            updateJoymeWikiStyle(conn, bean);
        } else {
            insertJoymeWikiStyle(conn, bean);
        }

    }

    public JoymeWikiStyleSimpleBean getJoymeWikiStyleVersion(Connection conn, String joymeWikiKey, Integer joymeWikiIsindex, String joymeWikiChannel, String contextPath) throws JoymeServiceException, JoymeDBException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.getJoymeWikiStyleVersion(conn, joymeWikiKey, joymeWikiIsindex, joymeWikiChannel, contextPath);
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