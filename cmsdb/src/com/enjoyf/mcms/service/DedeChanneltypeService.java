package com.enjoyf.mcms.service;

import java.sql.Connection;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.dao.DedeChanneltypeDao;

public class DedeChanneltypeService{
    private DedeChanneltypeDao subDao = new DedeChanneltypeDao();
    
    public String queryDedeChanneltypebyId(Connection conn, Integer id) throws JoymeDBException, JoymeServiceException{
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeChanneltypebyId(conn, id);
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
