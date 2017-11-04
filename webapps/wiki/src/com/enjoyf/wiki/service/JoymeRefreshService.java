package com.enjoyf.wiki.service;

import java.sql.Connection;
import java.util.List;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.JoymeRefresh;
import com.enjoyf.wiki.container.PropertiesContainer;
import com.enjoyf.wiki.dao.JoymeRefreshDao;

public class JoymeRefreshService {

    private static JoymeRefreshDao subDao = new JoymeRefreshDao();

    /**
     * date 2013-08-21 09:30:10
     *
     * @param freshId
     * @return JoymeRefresh
     * @author shenqiv0.1
     */
    public JoymeRefresh queryJoymeRefreshbyId(Connection conn, Integer freshId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeRefreshbyId(conn, freshId);
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
     * 查询自己的信息
     *
     * @param conn
     * @param machineId
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public List queryJoymeRefreshItem(Connection conn, int machineId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeRefreshItem(conn, machineId);
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
     * date 2013-08-21 09:30:10
     *
     * insert redis and insert db log
     *
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeRefresh(Connection conn, JoymeRefresh bean) throws JoymeDBException, JoymeServiceException {
        //todo
        PropertiesContainer.getInstance().getNamingService().pushAllServQueue(PropertiesContainer.getInstance().getService().getServiceType(), bean.getFreshContent());

        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymeRefresh(conn, bean);
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
     * date 2013-08-21 09:30:10
     *
     * @param bean
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeRefresh(Connection conn, JoymeRefresh bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymeRefresh(conn, bean);
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
     * date 2013-08-21 09:30:10
     *
     * @param freshId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int deleteJoymeRefresh(Connection conn, Integer freshId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymeRefresh(conn, freshId);
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
