package com.enjoyf.mcms.service;

import java.sql.Connection;
import java.util.List;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.mcms.dao.JoymeChannelDao;

public class JoymeChannelService {
    private static JoymeChannelDao subDao = new JoymeChannelDao();

    /**
     * date 2013-08-07 10:56:55
     * 
     * @author shenqiv0.1
     * @param channelId
     * @return List <joymeChannel>
     */
    public List queryJoymeChannel(Connection conn) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeChannel(conn);
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
