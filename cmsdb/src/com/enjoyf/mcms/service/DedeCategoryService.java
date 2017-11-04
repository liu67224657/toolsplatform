package com.enjoyf.mcms.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.DedeCategory;
import com.enjoyf.mcms.dao.DedeCategoryDao;

import java.sql.Connection;
import java.util.List;

public class DedeCategoryService {

    private static DedeCategoryDao subDao = new DedeCategoryDao();

    /**
     * date 2014-06-20 17:29:30
     *
     * @param id
     * @return DedeCategory
     * @author shenqiv0.1
     */
    public DedeCategory queryDedeCategorybyId(Connection conn, Integer id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeCategorybyId(conn, id);
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
         * date 2014-06-20 17:29:30
         *
         * @return DedeCategory
         * @author shenqiv0.1
         */
        public List<DedeCategory> queryDedeCategoryList(Connection conn) throws JoymeDBException, JoymeServiceException {
            boolean isCloseConn = (conn != null) ? false : true;
            try {
                if (conn == null)
                    conn = subDao.getConnection();
                return subDao.queryDedeCategoryList(conn);
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
     * date 2014-06-20 17:29:30
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertDedeCategory(Connection conn, DedeCategory bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertDedeCategory(conn, bean);
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
     * date 2014-06-20 17:29:30
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateDedeCategory(Connection conn, DedeCategory bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateDedeCategory(conn, bean);
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
     * date 2014-06-20 17:29:30
     *
     * @param id
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteDedeCategory(Connection conn, Integer id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteDedeCategory(conn, id);
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