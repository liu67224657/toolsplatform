package com.enjoyf.cms.service;

import java.util.List;

import com.enjoyf.cms.bean.JoymeRefresh;
import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.cms.dao.JoymeRefreshDao;
import com.enjoyf.framework.jdbc.bean.ConnectionBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

public class JoymeRefreshService {

    private static JoymeRefreshDao subDao = new JoymeRefreshDao();

    /**
     * date 2013-08-21 09:30:10
     *
     * @param freshId
     * @return JoymeRefresh
     * @author shenqiv0.1
     */
    public JoymeRefresh queryJoymeRefreshbyId(ConnectionBean conn, Integer freshId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
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
    public List queryJoymeRefreshItem(ConnectionBean conn, int machineId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
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
     * @param bean
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int insertJoymeRefresh(ConnectionBean conn, JoymeRefresh bean) throws JoymeDBException, JoymeServiceException {

        //添加打redis中
        PropertiesContainer.getNamingService().pushAllServQueue(PropertiesContainer.getService().getServiceType(), bean.getFreshContent());

        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
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
     * @param freshId
     * @return int 1 success, 0 failed
     * @author shenqiv0.1
     */
    public int updateJoymeRefresh(ConnectionBean conn, JoymeRefresh bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
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
    public int deleteJoymeRefresh(ConnectionBean conn, Integer freshId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getRWConnection();
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
