package com.enjoyf.adminuser.service;

import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.enjoyf.adminuser.bean.JoymeProperties;
import com.enjoyf.adminuser.dao.JoymePropertiesDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

public class JoymePropertiesService {

    private static JoymePropertiesDao subDao = new JoymePropertiesDao();

    /**
     * date 2013-09-29 12:37:04
     * 
     * @author shenqiv0.1
     * @param joymeType
     * @param joymeFatherPropId
     * @return List <joymeProperties>
     */
    public List queryJoymeProperties(Connection conn, Integer joymeType, Integer joymeFatherPropId) throws JoymeDBException, JoymeServiceException {
        boolean isCloseConn = (conn != null) ? false : true;
        try {
            if (conn == null)
                conn = subDao.getConnection();
            return subDao.queryJoymeProperties(conn, joymeType, joymeFatherPropId);
        } catch (JoymeDBException e) {
            throw e;
        } catch (Exception e) {
            throw new JoymeServiceException(e);
        } finally {
            if (conn != null && isCloseConn)
                subDao.closeConnection(conn);
        }
    }

    public Map getAllPropertiesTypemap(int joymeType) throws Exception {
        Connection conn = subDao.getConnection();
        Map map = new LinkedHashMap();
        try {
            List fatherList = this.queryJoymeProperties(conn, joymeType, 0);
            for (Iterator iterator = fatherList.iterator(); iterator.hasNext();) {
                JoymeProperties properties = (JoymeProperties) iterator.next();
                int id = properties.getJoymePropertiesId();
                List subList = this.queryJoymeProperties(conn, joymeType, id);
                map.put(properties.getJoymePropertiesKeyName(), subList);
            }
            return map;
        } finally {
            subDao.closeConnection(conn);
        }
    }
}