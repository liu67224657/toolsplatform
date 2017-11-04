package com.enjoyf.activity.facade;


import com.enjoyf.activity.bean.ValidStatus;
import com.enjoyf.activity.bean.goods.ExchangeLog;
import com.enjoyf.activity.bean.goods.Goods;
import com.enjoyf.activity.bean.goods.GoodsItem;
import com.enjoyf.activity.bean.point.PointLog;
import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.activity.cache.GoodsRedis;
import com.enjoyf.activity.cache.QyzVoteRedis;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.service.ExchangeLogService;
import com.enjoyf.activity.service.GoodsService;
import com.enjoyf.activity.util.Contants;
import com.enjoyf.activity.util.PointReason;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.util.PageRows;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class QyzVoteFacade {

    private QyzVoteRedis qyzVoteRedis = new QyzVoteRedis(PropertiesContainer.getRedisManager());


    public void incrFamilyPoll(String appid,String familyCode,String openid,long polls) {
        qyzVoteRedis.incrFamilyPoll(appid, familyCode, openid, polls);
    }

    public String getTotalPollsByFamilyCode(String appid,String familyCode){
        return qyzVoteRedis.getTotalPollsByFamilyCode(appid, familyCode);
    }

    public boolean checkVote(String openid){
        boolean flag = false;
        int poll = qyzVoteRedis.getUserVote(openid);
        if (poll>0){
            flag = true;
        }
        return flag;
    }

    public void addFamilyPoll(String appid, String familyCode, String polls) {
        qyzVoteRedis.addFamilyPoll(appid, familyCode, polls);
    }
}
