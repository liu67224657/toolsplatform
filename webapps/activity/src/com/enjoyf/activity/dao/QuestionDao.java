package com.enjoyf.activity.dao;

import com.enjoyf.activity.bean.question.Question;
import com.enjoyf.framework.jdbc.bean.DataBean;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.impl.BaseJDBCDAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends BaseJDBCDAOImpl {

   /**
    * date 2016-09-07 20:55:50
    * @author shenqiv0.1
    * @param questionId
    * @return Question 
    */
public Question queryQuestionbyId(Connection conn,  String questionId) throws JoymeDBException {
  DataBean dbean = null;
  try {
      String sql = "SELECT * FROM question  WHERE question_id = ?";
      List objectList = new ArrayList();
      objectList.add(questionId);
      dbean = this.executeBindingQuery(conn, sql, objectList.toArray());
      List retList = new ArrayList();
      ResultSet rs = dbean.getRs();
      while (rs.next()) {
           Question question = new Question();
           question.setQuestionId(rs.getString("question_id"));
           question.setGamecode(rs.getString("gamecode"));
           question.setTitle(rs.getString("title"));
           question.setAnswers(rs.getString("answers"));
           question.setCorrectAnswer(rs.getString("correct_answer"));
           question.setPublishDate(rs.getDate("publish_date"));
           return question;
      }
    return null;
  } catch (Exception e) {
      throw new JoymeDBException(e);
  } finally {
      this.cleanup(dbean, false);
  }
}


   /**
    * date 2016-09-07 20:55:50
    * @author shenqiv0.1
    * @param bean
    * @return int 1 success, 0 failed
    */
public int insertQuestion(Connection conn,  Question bean)  throws JoymeDBException{
   try{
      String sql = "INSERT INTO question(question_id,gamecode,title,answers,correct_answer,publish_date) VALUES (?,?,?,?,?,?)";
      Object[] objects = new Object[] {bean.getQuestionId(),bean.getGamecode(),bean.getTitle(),bean.getAnswers(),bean.getCorrectAnswer(),bean.getPublishDate()}; 
      return this.executeBindingUpdate(conn, sql, objects, false, false);
 }catch (Exception e) { 
      throw new JoymeDBException(e);
 }
}


   /**
    * date 2016-09-07 20:55:50
    * @author shenqiv0.1
    * @param bean
    * @return int 1 success, 0 failed
    */
public int updateQuestion(Connection conn,  Question bean)  throws JoymeDBException{
  try{
        String sql = "UPDATE question SET $updateStr  WHERE question_id = ?";
        List objectList = new ArrayList();
        List columnList = bean.getNotNullColumnList();
        sql = sql.replace("$updateStr", this.setUpdate(columnList, objectList));
      objectList.add(bean.getQuestionId());
      return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
      }catch (Exception e) { 
        throw new JoymeDBException(e);
    }
   }


   /**
    * date 2016-09-07 20:55:50
    * @author shenqiv0.1
    * @param questionId
    * @return int 1 success, 0 failed
    */
public int deleteQuestion(Connection conn,  String questionId)  throws JoymeDBException{
   try{
      String sql = "DELETE FROM question  WHERE question_id = ?";
  List objectList = new ArrayList();
      objectList.add(questionId);
      return this.executeBindingUpdate(conn, sql, objectList.toArray(), false, false);
 }catch (Exception e) { 
      throw new JoymeDBException(e);
 }
}


}