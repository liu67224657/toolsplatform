package com.enjoyf.activity.facade;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.activity.bean.point.PointLog;
import com.enjoyf.activity.bean.sign.Sign;
import com.enjoyf.activity.bean.sign.Signlog;
import com.enjoyf.activity.cache.SignRedis;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.service.SignService;
import com.enjoyf.activity.service.SignlogService;
import com.enjoyf.activity.util.PointReason;
import com.enjoyf.activity.util.SignUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.CollectionUtil;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.google.common.collect.Lists;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class SignFacade {

    private SignService signService = new SignService();
    private SignlogService signlogService = new SignlogService();
    private PointFacade pointFacade = new PointFacade();
    private SignRedis signRedis = new SignRedis(PropertiesContainer.getRedisManager());

    /**
     * 签到逻辑
     *
     * @param sign
     * @param profileId
     * @return
     * @throws JoymeServiceException
     */
    public boolean sign(Sign sign, String profileId,int point) throws JoymeServiceException, JoymeDBException {
        Date date = new Date();
        String signLogId = SignUtil.getSignLogId(profileId, sign.getSignId(), date);
        if (signRedis.isSign(signLogId)) {
            return false;
        } else {
            signRedis.setSignLogId(signLogId);
            Signlog signlog = new Signlog();
            signlog.setSignlogId(signLogId);
            signlog.setSignId(sign.getSignId());
            signlog.setCreateTime(new Timestamp(date.getTime()));
            signlog.setGamecode(sign.getGamecode());
            signlog.setPoint(point);
            signlog.setProfileId(profileId);
            signlog.setSignDate(date);
            signlog.setSignId(sign.getSignId());
            signlogService.insertSignlog(null, signlog);
        }

        //increasePoint
        PointLog pointLog = new PointLog();
        pointLog.setCreateTime(new Timestamp(date.getTime()));
        pointLog.setGamecode(sign.getGamecode());
        pointLog.setPoint(point);
        pointLog.setProfileid(profileId);
        pointLog.setReason(PointReason.SIGN);
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
    public Sign getSignByGame(String gameCode) throws JoymeServiceException, JoymeDBException {
        Sign sign = signRedis.getSignByGameCode(gameCode);
        if (sign == null) {
            List signList = signService.querySignByGameCode(null, gameCode);
            if (!CollectionUtil.isEmpty(signList)) {
                sign = (Sign) signList.get(0);
                signRedis.setSignByGameCode(sign.getGamecode(), sign);
            }
        }
        return sign;
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
    public String checkSign(String gamecode,String profileId)throws JoymeServiceException, JoymeDBException{
        Sign sign = getSignByGame(gamecode);
        String respContent ="";
        if (sign != null){
            boolean signFlag = this.sign(sign,profileId,sign.getPoint());
            int totalScore = this.getPointValueByProfileId(profileId,gamecode);
            if (signFlag){
                respContent = "亲爱的玩家，恭喜签到成功，获取10积分作为奖励。您当前的可用积分为" + totalScore +
                        "分。积分可用于抽奖，更有积分商城、积分兑换等超值活动即将上线，可别说青云君没提醒你呦＼^O^／~。微信内回复“积分”可以了解详细内容呦~";
            }else {
                respContent = "亲爱的玩家，您今天已经完成签到了哦，请明天再来吧！微信内回复“积分”可以了解详细内容呦~";
            }
        }else {
            respContent = "亲爱的玩家，您今天已经完成签到了哦，请明天再来吧！微信内回复“积分”可以了解详细内容呦~";
        }
        return respContent;
    }

    public Signlog getSignLog(String signLogId) throws JoymeServiceException, JoymeDBException {
        return  signlogService.querySignlogbyId(null,signLogId);
    }

    public List<Signlog> getSignLogByDate(String openid,String gamecode, Date startDate, Date endDate) throws JoymeServiceException, JoymeDBException {
       return signlogService.getSignLogByDate(null, openid, gamecode,  startDate, endDate);
    }

    public List<Signlog> getSignlogPage(String openid,String gamecode, int pageSize, int pageNum)throws JoymeServiceException, JoymeDBException {
        /*PageRows<Signlog> returnObj = new PageRows<Signlog>();
        Integer total = signlogService.querySignlogTotal(null,openid);
        Pagination pagination = new Pagination(total, pageNum, pageSize);*/
        List<Signlog> signlogList = signlogService.getSignlogPage(null,openid,gamecode,pageSize,pageNum);

        return signlogList;
    }

    public boolean checkSign(Sign sign, String openid, Integer point) throws JoymeServiceException, JoymeDBException {

        Date date = new Date();
        String signLogId = SignUtil.getSignLogId(openid, sign.getSignId(), date);
        if (signRedis.isSign(signLogId)) {
            return true;
        } else {
            Signlog signlog = signlogService.querySignlogbyId(null,signLogId);
            if (signlog==null){
                return false;
            }else {
                return true;
            }
        }

    }
}

