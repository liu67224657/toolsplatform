package com.enjoyf.activity.facade;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.point.PointLog;
import com.enjoyf.activity.bean.question.Question;
import com.enjoyf.activity.bean.question.QuestionLog;
import com.enjoyf.activity.bean.sign.Sign;
import com.enjoyf.activity.bean.sign.Signlog;
import com.enjoyf.activity.cache.QuestionRedis;
import com.enjoyf.activity.cache.SignRedis;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.service.QuestionLogService;
import com.enjoyf.activity.service.QuestionService;
import com.enjoyf.activity.service.SignService;
import com.enjoyf.activity.service.SignlogService;
import com.enjoyf.activity.util.PointReason;
import com.enjoyf.activity.util.QuestionUtil;
import com.enjoyf.activity.util.SignUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.CollectionUtil;
import com.enjoyf.util.StringUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class QuestionFacade {

    private QuestionService questionService = new QuestionService();
    private QuestionLogService questionLogService = new QuestionLogService();
    private PointFacade pointFacade = new PointFacade();
    private QuestionRedis questionRedis = new QuestionRedis(PropertiesContainer.getRedisManager());
    /**
     * 保存问题答案
     *
     * @param question
     * @param profileId
     * @return
     * @throws JoymeServiceException
     */
    public boolean saveQuestionLog(Question question, String profileId,boolean flag,String answer) throws JoymeServiceException, JoymeDBException {
        int score =0;
        if (flag)
            score = 10;
        else
            score = 5;
        Date date = new Date();
        String questionLogId = QuestionUtil.getQuestionLogId(profileId,date);
        QuestionLog questionLog = questionLogService.queryQuestionLogbyId(null,questionLogId);
        if (questionLog != null) {
            return false;
        } else {
            questionLog = new QuestionLog();
            questionLog.setAnswer(answer);
            questionLog.setAnswerDate(date);
            questionLog.setCreateTime(new Timestamp(date.getTime()));
            questionLog.setGamecode(question.getGamecode());
            if (flag){
                questionLog.setIscorrect(1);
            }else {
                questionLog.setIscorrect(0);
            }
            questionLog.setQuestionid(question.getQuestionId());
            questionLog.setPoint(score);
            questionLog.setProfileid(profileId);
            questionLog.setQuestionlogId(questionLogId);
            questionLogService.insertQuestionLog(null,questionLog);
            questionRedis.setQuestionLogId(questionLogId);
        }

        //increasePoint
        PointLog pointLog = new PointLog();
        pointLog.setCreateTime(new Timestamp(date.getTime()));
        pointLog.setGamecode(question.getGamecode());
        pointLog.setPoint(score);
        pointLog.setProfileid(profileId);
        pointLog.setReason(PointReason.QUESTION);
        pointFacade.increasePoint(pointLog);
        return true;
    }

    /**
     * getSignByGameCode first getby redis if isNull getby db and save redis
     *
     * @param gameCode
     * @return
     * @throws JoymeServiceException
     * @throws JoymeDBException
     */
    public Question getQuestionByGameCode(String gameCode) throws JoymeServiceException, JoymeDBException {
        Date date = new Date();
        String questionId = QuestionUtil.getQuestionId(gameCode,date);
        Question question = questionRedis.getQuestionByGameCode(gameCode,questionId);
        if (question == null ){
            question = questionService.queryQuestionbyId(null,questionId);
            if (question != null)
                questionRedis.setQuestionByGameCode(gameCode,question);
        }
        return question;
    }
    /**
     * getSignByGameCode first getby redis if isNull getby db and save redis
     *
     * @param profileId
     * @param gameCode
     * @return
     * @throws JoymeServiceException
     * @throws JoymeDBException
     */
    public Integer getPointValueByProfileId(String profileId, String gameCode) throws JoymeServiceException, JoymeDBException {

        return pointFacade.getPointValueByProfileId(profileId,gameCode);
    }
    public String getQuestionText(String gamecode,String profileId)throws JoymeServiceException, JoymeDBException{
        StringBuffer buffer = new StringBuffer();
        boolean answerFlag = checkQuestionAnswer(profileId,gamecode);
        if (answerFlag){
            buffer.append("少侠，您今日已经回答过题了哦。请明天再来吧~");
        }else {
            Question question = this.getQuestionByGameCode(gamecode);
           if (question != null){
               buffer.append(question.getTitle()).append("\n\n");
               String[] answerArray = question.getAnswers().split(";");
               for (String answer : answerArray){
                   buffer.append(answer.trim()).append("\n");
               }
               questionRedis.setQuestionByGameCode(gamecode,question);
               questionRedis.setUserTempQuestion(profileId);
           }else {
               buffer.append("少侠，您今日已经回答过题了哦。请明天再来吧~");
           }
        }
        return buffer.toString();
    }
    public boolean checkQuestionAnswer(String profileId, String gamecode) throws JoymeServiceException, JoymeDBException{
        Date date = new Date();
        String questionLogId = QuestionUtil.getQuestionLogId(profileId,date);
        if (questionRedis.isAnswer(questionLogId))
            return true;
        else
            return false;
    }
    public String getUserTempQuestion(String profileId){
       return questionRedis.getUserTempQuestion(profileId);
    }

    public void removeUserTempQuestion(String profileId) {
        questionRedis.removeUserTempQuestion(profileId);
    }

    public void saveQuestion(Question question) throws JoymeServiceException, JoymeDBException {
        questionService.insertQuestion(null,question);
        questionRedis.setQuestionByGameCode(question.getGamecode(),question);
    }

    public Question getQuestionById(String questionId) throws JoymeServiceException, JoymeDBException {
        return questionService.queryQuestionbyId(null,questionId);
    }
}

