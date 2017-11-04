package com.enjoyf.activity.service;import com.enjoyf.activity.bean.sign.Signlog;import com.enjoyf.activity.dao.SignlogDao;import com.enjoyf.framework.jdbc.exception.JoymeDBException;import com.enjoyf.framework.jdbc.exception.JoymeServiceException;import java.sql.Connection;import java.util.Date;import java.util.List;public class SignlogService {    private static SignlogDao subDao = new SignlogDao();    /**     * date 2016-09-07 15:52:39     *     * @param signlogId     * @return Signlog     * @author shenqiv0.1     */    public Signlog querySignlogbyId(Connection conn, String signlogId) throws JoymeDBException, JoymeServiceException {        boolean isCloseConn = (conn != null) ? false : true;        try {            if (conn == null)                conn = subDao.getConnection();            return subDao.querySignlogbyId(conn, signlogId);        } catch (JoymeDBException e) {            throw e;        } catch (Exception e) {            throw new JoymeServiceException(e);        } finally {            if (conn != null && isCloseConn)                subDao.closeConnection(conn);        }    }    /**     * date 2016-09-07 15:52:39     *     * @param bean     * @return int 1 success, 0 failed     * @author shenqiv0.1     */    public int insertSignlog(Connection conn, Signlog bean) throws JoymeDBException, JoymeServiceException {        boolean isCloseConn = (conn != null) ? false : true;        try {            if (conn == null)                conn = subDao.getConnection();            return subDao.insertSignlog(conn, bean);        } catch (JoymeDBException e) {            throw e;        } catch (Exception e) {            throw new JoymeServiceException(e);        } finally {            if (conn != null && isCloseConn)                subDao.closeConnection(conn);        }    }    /**     * date 2016-09-07 15:52:39     *     * @param bean     * @return int 1 success, 0 failed     * @author shenqiv0.1     */    public int updateSignlog(Connection conn, Signlog bean) throws JoymeDBException, JoymeServiceException {        boolean isCloseConn = (conn != null) ? false : true;        try {            if (conn == null)                conn = subDao.getConnection();            return subDao.updateSignlog(conn, bean);        } catch (JoymeDBException e) {            throw e;        } catch (Exception e) {            throw new JoymeServiceException(e);        } finally {            if (conn != null && isCloseConn)                subDao.closeConnection(conn);        }    }    /**     * date 2016-09-07 15:52:39     *     * @param signlogId     * @return int 1 success, 0 failed     * @author shenqiv0.1     */    public int deleteSignlog(Connection conn, String signlogId) throws JoymeDBException, JoymeServiceException {        boolean isCloseConn = (conn != null) ? false : true;        try {            if (conn == null)                conn = subDao.getConnection();            return subDao.deleteSignlog(conn, signlogId);        } catch (JoymeDBException e) {            throw e;        } catch (Exception e) {            throw new JoymeServiceException(e);        } finally {            if (conn != null && isCloseConn)                subDao.closeConnection(conn);        }    }    public List<Signlog> getSignLogByDate(Connection conn, String openid,String gamecode, Date startDate, Date endDate) throws JoymeServiceException, JoymeDBException {        boolean isCloseConn = (conn != null) ? false : true;        try {            if (conn == null)                conn = subDao.getConnection();            return subDao.getSignLogByDate(conn, openid,gamecode,  startDate, endDate);        } catch (JoymeDBException e) {            throw e;        } catch (Exception e) {            throw new JoymeServiceException(e);        } finally {            if (conn != null && isCloseConn)                subDao.closeConnection(conn);        }    }   /* public Integer querySignlogTotal(Connection conn, String openid) throws JoymeDBException, JoymeServiceException {        boolean isCloseConn = (conn != null) ? false : true;        try {            if (conn == null)                conn = subDao.getConnection();            return subDao.querySignlogTotal(conn, openid);        } catch (JoymeDBException e) {            throw e;        } catch (Exception e) {            throw new JoymeServiceException(e);        } finally {            if (conn != null && isCloseConn)                subDao.closeConnection(conn);        }    }*/    public List<Signlog> getSignlogPage(Connection conn, String openid,String gamecode, int pageSize, int pageNum) throws JoymeDBException, JoymeServiceException{        boolean isCloseConn = (conn != null) ? false : true;        try {            if (conn == null)                conn = subDao.getConnection();            return subDao.getSignlogPage(conn, openid,gamecode,pageSize, pageNum);        } catch (JoymeDBException e) {            throw e;        } catch (Exception e) {            throw new JoymeServiceException(e);        } finally {            if (conn != null && isCloseConn)                subDao.closeConnection(conn);        }    }}