package com.enjoyf.activity.service;

import com.enjoyf.activity.bean.question.QuestionLog;
import com.enjoyf.activity.dao.QuestionLogDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

import java.sql.Connection;

public class QuestionLogService {

   private static QuestionLogDao subDao = new QuestionLogDao();
   /**
    * date 2016-09-07 20:58:13
    * @author shenqiv0.1
    * @param questionlogId
    * @return QuestionLog 
    */
public QuestionLog queryQuestionLogbyId(Connection conn,  String questionlogId) throws JoymeDBException, JoymeServiceException {
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.queryQuestionLogbyId(conn,  questionlogId);
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
    * date 2016-09-07 20:58:13
    * @author shenqiv0.1
    * @param bean
    * @return int 1 success, 0 failed
    */
public int insertQuestionLog(Connection conn,  QuestionLog bean) throws JoymeDBException, JoymeServiceException{
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.insertQuestionLog(conn , bean);
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
    * date 2016-09-07 20:58:13
    * @author shenqiv0.1
    * @param bean
    * @param questionlogId
    * @return int 1 success, 0 failed
    */
public int updateQuestionLog(Connection conn,  QuestionLog bean) throws JoymeDBException, JoymeServiceException{
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.updateQuestionLog(conn , bean);
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
    * date 2016-09-07 20:58:13
    * @author shenqiv0.1
    * @param questionlogId
    * @return int 1 success, 0 failed
    */
public int deleteQuestionLog(Connection conn,  String questionlogId) throws JoymeDBException, JoymeServiceException{
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.deleteQuestionLog(conn , questionlogId);
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