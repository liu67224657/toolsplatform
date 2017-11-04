package com.enjoyf.activity.cache;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.question.Question;
import com.enjoyf.framework.redis.RedisManager;
import com.enjoyf.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class QuestionRedis {

    private RedisManager redisManager = null;

    private static final String PREFIX = "actvity_question_";
    private static final String KEY_QUESTIONLOG = PREFIX + "ql_";
    private static final String KEY_QUESTION = PREFIX + "q_";
    private static final String KEY_USER_QUESTION = PREFIX + "user_";
    private static final int TIME_OUT = 3600 * 24 * 2;

    public QuestionRedis(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public boolean setQuestionLogId(String questionLogId) {
        redisManager.set(KEY_QUESTIONLOG + questionLogId, questionLogId, TIME_OUT);
        return true;
    }

    public boolean isAnswer(String questionLogId) {
        return redisManager.keyExists(KEY_QUESTIONLOG + questionLogId);
    }

    public void setUserTempQuestion(String profileId ) {
        redisManager.set(KEY_USER_QUESTION + profileId, profileId,60*5);
    }
    public String getUserTempQuestion(String profileId) {
        return redisManager.get(KEY_USER_QUESTION + profileId);
    }

    public void setQuestionByGameCode(String gameCode, Question question) {
        redisManager.set(KEY_QUESTION + "_" + question.getQuestionId() + "_" + gameCode, new Gson().toJson(question));
    }

    public Question getQuestionByGameCode(String gameCode,String questionId) {
        String questionObj = redisManager.get(KEY_QUESTION + "_" + questionId + "_" + gameCode);
        if (StringUtil.isEmpty(questionObj)) {
            return null;
        }
        try {
            return new Gson().fromJson(questionObj, Question.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public void removeUserTempQuestion(String profileId) {
        redisManager.remove(KEY_USER_QUESTION + profileId);
    }
}