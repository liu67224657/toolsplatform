package com.enjoyf.activity.bean.question;

import com.enjoyf.framework.jdbc.bean.NotNullColumnBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question {
    private String  questionId;
    private String  gamecode;
    private String  title;
    private String  answers;
    private String  correctAnswer;
    private Date publishDate;

 public String getQuestionId(){
    return questionId;
  }

 public void setQuestionId(String questionId){
    this.questionId = questionId;
  }

 public String getGamecode(){
    return gamecode;
  }

 public void setGamecode(String gamecode){
    this.gamecode = gamecode;
  }

 public String getTitle(){
    return title;
  }

 public void setTitle(String title){
    this.title = title;
  }

 public String getAnswers(){
    return answers;
  }

 public void setAnswers(String answers){
    this.answers = answers;
  }

 public String getCorrectAnswer(){
    return correctAnswer;
  }

 public void setCorrectAnswer(String correctAnswer){
    this.correctAnswer = correctAnswer;
  }

 public Date getPublishDate(){
    return publishDate;
  }

 public void setPublishDate(Date publishDate){
    this.publishDate = publishDate;
  }


public List getNotNullColumnList(){
  List columnList = new ArrayList();
   if(questionId != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("question_id");
    bean.setObj(questionId);
    columnList.add(bean);
   }
   if(gamecode != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("gamecode");
    bean.setObj(gamecode);
    columnList.add(bean);
   }
   if(title != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("title");
    bean.setObj(title);
    columnList.add(bean);
   }
   if(answers != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("answers");
    bean.setObj(answers);
    columnList.add(bean);
   }
   if(correctAnswer != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("correct_answer");
    bean.setObj(correctAnswer);
    columnList.add(bean);
   }
   if(publishDate != null) {
    NotNullColumnBean bean = new NotNullColumnBean();
    bean.setColumnName("publish_date");
    bean.setObj(publishDate);
    columnList.add(bean);
   }
  return columnList;
  }
 }


