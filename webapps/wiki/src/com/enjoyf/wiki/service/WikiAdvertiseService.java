package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.WikiAdvertise;
import com.enjoyf.wiki.dao.WikiAdvertiseDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WikiAdvertiseService {

    private static WikiAdvertiseDao subDao = new WikiAdvertiseDao();

    public WikiAdvertiseService() {
    }

    /**
     * date 2013-08-22 16:18:06
     *
     * @param wikiAdvertiseId
     * @return WikiAdvertise
     * @author shenqiv0.1
     */
    public WikiAdvertise queryWikiAdvertisebyId(Connection conn, Integer wikiAdvertiseId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryWikiAdvertisebyId(conn, wikiAdvertiseId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public List queryWikiAdvertise(Connection conn) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryWikiAdvertise(conn);
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
     * date 2013-08-23 17:02:31
     *
     * @param wikiAdvertiseKey
     * @param wikiAdvertiseContextPath
     * @return List <wikiAdvertise>
     * @author shenqiv0.1
     */
    public List queryWikiAdvertise(Connection conn, String wikiAdvertiseKey, String wikiAdvertiseContextPath) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryWikiAdvertise(conn, wikiAdvertiseKey, wikiAdvertiseContextPath);
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
     * date 2013-08-22 16:18:06
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertWikiAdvertise(Connection conn, WikiAdvertise bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertWikiAdvertise(conn, bean);
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
     * date 2013-08-22 16:18:06
     *
     * @param bean
     * @param
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateWikiAdvertise(Connection conn, WikiAdvertise bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateWikiAdvertise(conn, bean);
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
     * date 2013-08-22 16:18:06
     *
     * @param wikiAdvertiseId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteWikiAdvertise(Connection conn, Integer wikiAdvertiseId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteWikiAdvertise(conn, wikiAdvertiseId);
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
     * date 2013-08-23 10:39:36
     *
     * @param wikiAdvertiseKey
     * @param wikiAdvertiseContextPath
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteWikiAdvertise(Connection conn, String wikiAdvertiseKey, String wikiAdvertiseContextPath) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteWikiAdvertise(conn, wikiAdvertiseKey, wikiAdvertiseContextPath);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeDBException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }


}