package com.enjoyf.activity.service;

import com.enjoyf.activity.bean.question.Question;
import com.enjoyf.activity.dao.QuestionDao;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

import java.sql.Connection;

public class QuestionService {

   private static QuestionDao subDao = new QuestionDao();
   /**
    * date 2016-09-07 20:55:51
    * @author shenqiv0.1
    * @param questionId
    * @return Question 
    */
public Question queryQuestionbyId(Connection conn,  String questionId) throws JoymeDBException, JoymeServiceException {
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.queryQuestionbyId(conn,  questionId);
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
    * date 2016-09-07 20:55:51
    * @author shenqiv0.1
    * @param bean
    * @return int 1 success, 0 failed
    */
public int insertQuestion(Connection conn,  Question bean) throws JoymeDBException, JoymeServiceException{
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.insertQuestion(conn , bean);
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
    * date 2016-09-07 20:55:51
    * @author shenqiv0.1
    * @param bean
    * @return int 1 success, 0 failed
    */
public int updateQuestion(Connection conn,  Question bean) throws JoymeDBException, JoymeServiceException{
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.updateQuestion(conn , bean);
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
    * date 2016-09-07 20:55:52
    * @author shenqiv0.1
    * @param questionId
    * @return int 1 success, 0 failed
    */
public int deleteQuestion(Connection conn,  String questionId) throws JoymeDBException, JoymeServiceException{
  boolean isCloseConn = (conn != null) ? false : true;
  try {
    if (conn == null)
      conn = subDao.getConnection();
      return subDao.deleteQuestion(conn , questionId);
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