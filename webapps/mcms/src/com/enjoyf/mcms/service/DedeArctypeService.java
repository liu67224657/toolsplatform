package com.enjoyf.mcms.service;

import java.sql.Connection;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.bean.DedeArctype;
import com.enjoyf.mcms.dao.DedeArctypeDao;

public class DedeArctypeService {
    private static DedeArctypeDao subDao = new DedeArctypeDao();

    public DedeArctype queryDedeArctype(Connection conn, Integer id) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryDedeArctype(conn, id);
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
