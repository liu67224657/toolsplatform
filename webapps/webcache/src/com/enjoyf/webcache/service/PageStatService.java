package com.enjoyf.webcache.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.webcache.bean.PageStat;
import com.enjoyf.webcache.dao.PageStatDao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhitaoshi on 2015/11/20.
 */
public class PageStatService {

    private static PageStatDao pageStatDao = new PageStatDao();

    public static PageStat getPageStatById(Connection conn, String statId) throws JoymeServiceException, JoymeDBException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = pageStatDao.getConnection();
            return pageStatDao.getPageStat(conn, statId);

        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                pageStatDao.closeConnection(conn);
        }
    }

    public static List<PageStat> queryPageStatByIdSet(Connection conn, List<String> idList) throws JoymeServiceException, JoymeDBException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = pageStatDao.getConnection();
            return pageStatDao.queryPageStatByIdSet(conn, idList);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                pageStatDao.closeConnection(conn);
        }
    }

    public static int insertPageStat(Connection conn, PageStat pageStat) throws JoymeServiceException, JoymeDBException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = pageStatDao.getConnection();
            return pageStatDao.insertPageStat(conn, pageStat);

        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                pageStatDao.closeConnection(conn);
        }
    }

    public static int increasePageStat(Connection conn, PageStat pageStat) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = pageStatDao.getConnection();
            return pageStatDao.increasePageStat(conn, pageStat);

        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                pageStatDao.closeConnection(conn);
        }
    }

    public static int updatePageStatByColumn(Connection conn, Map<String, Integer> map, PageStat pageStat) throws JoymeServiceException, JoymeDBException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = pageStatDao.getConnection();
            return pageStatDao.updatePageStatByColumn(conn, map, pageStat);

        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                pageStatDao.closeConnection(conn);
        }
    }

    public static PageRows<PageStat> queryPageStatByPage(Connection conn, Map<String, Object> paramMap, Pagination page) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = pageStatDao.getConnection();
            return pageStatDao.queryPageStatByPage(conn, paramMap, page);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                pageStatDao.closeConnection(conn);
        }
    }

    public static List<PageStat> queryPageStatByOrder(Connection conn, Map<String, Integer> param, String orderColumn, String order, Pagination page) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = pageStatDao.getConnection();
            return pageStatDao.queryPageStatByOrder(conn, param, orderColumn, order, page);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                pageStatDao.closeConnection(conn);
        }
    }
}
