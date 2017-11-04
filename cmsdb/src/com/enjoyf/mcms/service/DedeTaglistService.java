package com.enjoyf.mcms.service;

import com.enjoyf.framework.jdbc.bean.PageQueryBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.dao.DedeTaglistDao;

import java.sql.Connection;
import java.util.List;

public class DedeTaglistService {

    private static DedeTaglistDao subDao = new DedeTaglistDao();

    /**
     * date 2013-11-13 18:10:00
     *
     * @return List <dedeTaglist>
     * @author shenqiv0.1
     */
    public PageQueryBean queryDedeTaglist(Connection conn, int tid, int pageNum, String plat) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeTaglist(conn, tid, pageNum, plat);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public List queryTagArchiveList(Connection conn, List list1) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryTagArchiveList(conn, list1);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public String queryDedeTagName(Connection conn, Integer id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeTagName(conn, id);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public java.util.List queryDedeTagByAid(java.sql.Connection conn, java.lang.Integer aid) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeTagByAid(conn, aid);
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