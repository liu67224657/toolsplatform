package com.enjoyf.crwalwiki.service;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.enjoyf.crwalwiki.bean.CrwalLog;
import com.enjoyf.crwalwiki.dao.CrwalLogDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

public class CrwalLogService {

    private static CrwalLogDao subDao = new CrwalLogDao();

    /**
     * date 2013-08-24 14:37:29
     * 
     * @author shenqiv0.1
     * @param id
     * @return CrwalLog
     */
    public CrwalLog queryCrwalLogbyId(Connection conn, Integer id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryCrwalLogbyId(conn, id);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public CrwalLog queryOldestNoCrwalLog(Connection conn) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryOldestNoCrwalLog(conn);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }
    
    public List queryLastItems(Connection conn) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryLastItems(conn);
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
     * date 2013-08-24 14:37:29
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertCrwalLog(Connection conn, CrwalLog bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertCrwalLog(conn, bean);
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
     * date 2013-08-24 14:37:29
     * 
     * @author shenqiv0.1
     * @param bean
     * @param id
     * @return int 1 success, 0 failed
     */
    public int updateCrwalLog(Connection conn, CrwalLog bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateCrwalLog(conn, bean);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public int updateCrwalLogRunning(Connection conn, int id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateCrwalLogRunning(conn, id);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public int updateCrwalLogFinish(Connection conn,int isFinish, int id, String downloadUrl) throws Exception{
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateCrwalLogFinish(conn, isFinish, id , downloadUrl);
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
     * date 2013-08-24 14:37:29
     * 
     * @author shenqiv0.1
     * @param id
     * @return int 1 success, 0 failed
     */
    public int deleteCrwalLog(Connection conn, Integer id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteCrwalLog(conn, id);
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
     * add all location crwal log
     * @param request
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public void insertAllLocationCrwalLog(HttpServletRequest request) throws JoymeDBException, JoymeServiceException {
        String key = request.getParameter("key").trim();
        CrwalLog bean = new CrwalLog();
        bean.setCrwalKey(key);
        bean.setIsFinish(0);
        bean.setIsRunning(0);
        bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
        bean.setCrwalType(1);
        this.insertCrwalLog(null, bean);
    }
    
    /**
     * add pages crwal log
     * @param request
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public void insertPagesLocationCrwalLog(HttpServletRequest request) throws JoymeDBException, JoymeServiceException {
        if(request.getParameter("urls") == null)
            return;
        
        String pageString = request.getParameter("urls");
        CrwalService service = new CrwalService();
        String key = service.parseKey(pageString);
        
        if(key == null)
            return;
        
        CrwalLog bean = new CrwalLog();
        bean.setCrwalKey(key);
        bean.setIsFinish(0);
        bean.setIsRunning(0);
        bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
        bean.setCrwalType(2);
        bean.setPageUrls(request.getParameter("urls"));
        this.insertCrwalLog(null, bean);
    }
    
    public void queryLastCrwalLog(HttpServletRequest request) throws JoymeDBException, JoymeServiceException{
        List lastLogList = this.queryLastItems(null);
        request.setAttribute("lastLogList", lastLogList);
    }

}