package com.enjoyf.webcache.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.webcache.bean.RefreshTimerUrl;
import com.enjoyf.webcache.dao.RefreshTimerDao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by zhitaoshi on 2016/5/12.
 */
public class RefreshTimerService {

    private static RefreshTimerDao refreshTimerDao = new RefreshTimerDao();

    public List<RefreshTimerUrl> queryRefreshUrl(Connection conn) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = refreshTimerDao.getConnection();
            return refreshTimerDao.queryRefreshUrl(conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                refreshTimerDao.closeConnection(conn);
        }
    }

    public boolean createRefreshUrl(Connection conn, RefreshTimerUrl refreshTimerUrl) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = refreshTimerDao.getConnection();
            return refreshTimerDao.createRefreshUrl(conn, refreshTimerUrl) > 0;
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                refreshTimerDao.closeConnection(conn);
        }
    }

    public boolean delRefreshUrl(Connection conn, String urlId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = refreshTimerDao.getConnection();
            return refreshTimerDao.delRefreshUrl(conn, urlId) > 0;
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                refreshTimerDao.closeConnection(conn);
        }
    }

    public static RefreshTimerUrl getRefreshUrl(Connection conn, String urlId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = refreshTimerDao.getConnection();
            return refreshTimerDao.getRefreshUrl(conn, urlId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                refreshTimerDao.closeConnection(conn);
        }
    }
}
