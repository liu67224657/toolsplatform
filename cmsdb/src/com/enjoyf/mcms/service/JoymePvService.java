package com.enjoyf.mcms.service;

import java.sql.Connection;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.JoymePv;
import com.enjoyf.mcms.dao.JoymePvDao;

public class JoymePvService {

    private static JoymePvDao subDao = new JoymePvDao();

    /**
     * date 2013-09-02 15:29:09
     * 
     * @author shenqiv0.1
     * @param id
     * @return JoymePv
     */
    public JoymePv queryJoymePvbyId(Connection conn, Integer id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymePvbyId(conn, id);
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
     * @param conn
     * @param joymePvChannel
     * @param joymePvDate
     * @return
     * @throws JoymeDBException
     * @throws JoymeServiceException
     */
    public JoymePv queryJoymePv(Connection conn,  String joymePvChannel, String joymePvDate) throws JoymeDBException, JoymeServiceException{
        boolean isCloseConn = (conn != null) ? false : true;
        try {
          if (conn == null)
            conn = subDao.getConnection();
            return subDao.queryJoymePv(conn,  joymePvChannel, joymePvDate);
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
     * date 2013-09-02 15:29:09
     * 
     * @author shenqiv0.1
     * @param bean
     * @return int 1 success, 0 failed
     */
    public int insertJoymePv(Connection conn, JoymePv bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.insertJoymePv(conn, bean);
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
     * date 2013-09-02 15:29:10
     * 
     * @author shenqiv0.1
     * @param bean
     * @param id
     * @return int 1 success, 0 failed
     */
    public int updateJoymePv(Connection conn, JoymePv bean) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.updateJoymePv(conn, bean);
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
     * date 2013-09-02 15:29:10
     * 
     * @author shenqiv0.1
     * @param id
     * @return int 1 success, 0 failed
     */
    public int deleteJoymePv(Connection conn, Integer id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.deleteJoymePv(conn, id);
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