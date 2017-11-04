package com.enjoyf.adminuser.service;

import java.sql.Connection;
import java.util.List;

import com.enjoyf.adminuser.bean.JoymeUserProperties;
import com.enjoyf.adminuser.dao.JoymeUserPropertiesDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

public class JoymeUserPropertiesService{

   private static JoymeUserPropertiesDao subDao = new JoymeUserPropertiesDao();
   /**
    * date 2013-09-16 17:50:02
    * @author shenqiv0.1
    * @param userPropertiesId
    * @return JoymeUserProperties 
    */
public JoymeUserProperties queryJoymeUserPropertiesbyId(Connection conn,  Integer userPropertiesId) throws JoymeDBException, JoymeServiceException{
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.queryJoymeUserPropertiesbyId(conn,  userPropertiesId);
  } catch (JoymeDBException e) { 
      throw e;
  } catch (Exception e) {
      throw new JoymeServiceException(e);
  } finally {
      if (conn != null && isCloseConn) 
          subDao.closeConnection(conn);
  }
}

public List queryJoymeUserPropertiesByUserId(Connection conn,  Integer userId) throws JoymeDBException, JoymeServiceException{
    boolean isCloseConn = (conn != null) ? false : true;
    try {
      if (conn == null)
        conn = subDao.getConnection();
        return subDao.queryJoymeUserPropertiesByUserId(conn,  userId);
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
    * date 2013-09-16 17:50:02
    * @author shenqiv0.1
    * @param bean
    * @return int 1 success, 0 failed
    */
public int insertJoymeUserProperties(Connection conn,  JoymeUserProperties bean) throws JoymeDBException, JoymeServiceException{
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.insertJoymeUserProperties(conn , bean);
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
    * date 2013-09-16 17:50:02
    * @author shenqiv0.1
    * @param bean
    * @return int 1 success, 0 failed
    */
public int updateJoymeUserProperties(Connection conn,  JoymeUserProperties bean) throws JoymeDBException, JoymeServiceException{
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.updateJoymeUserProperties(conn , bean);
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
    * date 2013-09-16 17:50:02
    * @author shenqiv0.1
    * @param userPropertiesId
    * @return int 1 success, 0 failed
    */
public int deleteJoymeUserProperties(Connection conn,  Integer userPropertiesId) throws JoymeDBException, JoymeServiceException{
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.deleteJoymeUserProperties(conn , userPropertiesId);
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