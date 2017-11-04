package com.enjoyf.mcms.service;

import java.sql.Connection;
import java.util.List;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.JoymePoint;
import com.enjoyf.mcms.dao.JoymePointDao;

public class JoymePointService {

    private static JoymePointDao subDao = new JoymePointDao();

    /**
     * date 2013-07-31 18:35:25
     * 
     * @author shenqiv0.1
     * @param specPointId
     * @return JoymePoint
     */
    public JoymePoint queryJoymePointbyId(Connection conn, Integer specPointId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymePointbyId(conn, specPointId);
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
     * date 2013-07-31 18:35:25
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public long insertJoymePoint(Connection conn, JoymePoint bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymePoint(conn, bean);
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
     * date 2013-07-31 18:35:25
     * 
     * @author shenqiv0.1
     * @param bean
     * @param specPointId
     * @return int 1 success, 0 failed
     */
    public int updateJoymePoint(Connection conn, JoymePoint bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymePoint(conn, bean);
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
     * 通过htmlFile更改is_active
     * @param conn
     * @param htmlPath
     * @return
     * @throws Exception
     */
    public int updateJoymePointIsActiveByHtmlFile(Connection conn, String htmlPath) throws Exception{
        boolean isCloseConn = (conn != null) ? false : true;
        	try {
        	    if (conn == null)
        	        conn = subDao.getConnection();
        	    return subDao.updateJoymePointIsActiveByHtmlFile(conn, htmlPath);
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
     * date 2013-07-31 18:35:25
     * 
     * @author shenqiv0.1
     * @param specPointId
     * @return int 1 success, 0 failed
     */
    public int deleteJoymePoint(Connection conn, Integer specPointId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymePoint(conn, specPointId);
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
     * 通过archiveId删除joyme_point
     * @param conn
     * @param archiveId
     * @return
     * @throws Exception
     */
    public int deleteJoymePointByArchiveId(Connection conn, Integer archiveId) throws Exception{
        boolean isCloseConn = (conn != null) ? false : true;
        	try {
        	    if (conn == null)
        	        conn = subDao.getConnection();
        	    return subDao.deleteJoymePointByArchiveId(conn, archiveId);
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
     * 通过名字获得
     * 
     * @param conn
     * @param archiveId
     * @param specPointName
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public JoymePoint queryJoymePointByName(Connection conn, Integer archiveId, String specPointName) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymePointByName(conn, archiveId, specPointName);
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
     * 通过htmlfile获得pointlist
     * 
     * @param conn
     * @param htmlFile
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public List queryJoymePointByHtmlFile(Connection conn, String htmlFile) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymePointByHtmlFile(conn, htmlFile);
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
     * 通过archiveId寻找
     * @param conn
     * @param archiveId
     * @return
     * @throws Exception
     */
    public List queryJoymePointByArchiveId(Connection conn,  Integer archiveId) throws Exception{
        boolean isCloseConn = (conn != null) ? false : true;
        	try {
        	    if (conn == null)
        	        conn = subDao.getConnection();
        	    return subDao.queryJoymePointByArchiveId(conn, archiveId);
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
     * 插入joymepoint ,如果有了是更改
     * @param conn
     * @param archiveId
     * @param point
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public long insertJoymePointByName(Connection conn, int archiveId, JoymePoint point) throws JoymeDBException, JoymeServiceException {
        JoymePoint joymepoint1 = this.queryJoymePointByName(conn, archiveId, point.getSpecPointName());
        if (joymepoint1 == null) {
            return insertJoymePoint(conn, point);
        } else {
            point.setSpecPointId(joymepoint1.getSpecPointId());
            updateJoymePoint(conn, point);
            return joymepoint1.getSpecPointId();
        }
    }

}
