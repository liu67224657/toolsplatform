package com.enjoyf.activity.facade;

import com.enjoyf.activity.bean.point.PointLog;
import com.enjoyf.activity.bean.zlmc.Userinfo;
import com.enjoyf.activity.cache.PointRedis;
import com.enjoyf.activity.cache.UserinfoRedis;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.service.PointLogService;
import com.enjoyf.activity.service.UserinfoService;
import com.enjoyf.activity.util.PointUtil;
import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;

import java.sql.Connection;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class UserinfoFacade {
    private UserinfoService userinfoService = new UserinfoService();
    private UserinfoRedis userinfoRedis = new UserinfoRedis(PropertiesContainer.getRedisManager());



    public void   setUserTel(String profileId,String tel) {
          userinfoRedis.setUserTel(profileId,tel);
    }
    public String getUserTel(String profileId) {
        return userinfoRedis.getUserTel(profileId);
    }
    public void   delUserTel(String profileId) {
        userinfoRedis.delUserTel(profileId);
    }

    public String  getUserTelYzm(String profileId) {
        return   userinfoRedis.getUserTelYzm(profileId);
    }

    public void   setUserTelYzm(String profileId,String yzm) {
        userinfoRedis.setUserTelYzm(profileId,yzm);
    }
    public void   delUserTelYzm(String profileId) {
        userinfoRedis.delUserTelYzm(profileId);
    }
    public void   saveUserinfo(Userinfo userinfo,boolean delFlag) throws JoymeServiceException, JoymeDBException {
        userinfoService.insertUserinfo(null,userinfo);
        if (delFlag){
            this.delUserTel(userinfo.getProfileid());
            this.delUserTelYzm(userinfo.getProfileid());
        }
    }

    public Userinfo   getUserinfo(String profileId) throws JoymeServiceException, JoymeDBException {
        return  userinfoService.queryUserinfobyId(null,profileId);
    }

    public void updateUserinfo(Connection conn, Userinfo userinfo) throws JoymeServiceException, JoymeDBException {
        userinfoService.updateUserinfo(conn,userinfo);
    }
}
