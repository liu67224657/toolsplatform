package com.enjoyf.activity.facade;

import com.enjoyf.activity.bean.ResultCodeConstants;
import com.enjoyf.activity.bean.UserResultCodeConstants;
import com.enjoyf.activity.bean.dto.UserDTO;
import com.enjoyf.activity.bean.invite.InviteInfo;
import com.enjoyf.activity.cache.InviteRedis;
import com.enjoyf.activity.container.PropertiesContainer;
import com.enjoyf.activity.weixin.WeixinUser;
import com.enjoyf.activity.weixin.WeixinUtil;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.Pagination;
import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:2017/1/3
 */
public class InviteFacade {

    private InviteRedis inviteRedis = new InviteRedis(PropertiesContainer.getRedisManager());


    public String buildInvite(String srcOpenId, String activityName, String jsonCallback, String appId, String secred, HttpServletRequest request, HttpServletResponse response) {
        Map invitedMapMessage = WeixinUtil.getMapMessage(appId, secred, request, response);
        if (!invitedMapMessage.containsKey("weixinUser")) {
            LogService.errorSystemLog("weixinUser empty " + invitedMapMessage.get("openid") + " " + invitedMapMessage);
            return UserResultCodeConstants.USER_INFO_EMPTY.getJsonString(jsonCallback);
        }
        WeixinUser weixinUser = (WeixinUser) invitedMapMessage.get("weixinUser");

        String destOpenId = weixinUser.getOpenid();
        if (srcOpenId.equals(destOpenId)) {
            LogService.errorSystemLog("same user " + invitedMapMessage.get("openid") + " " + invitedMapMessage);
            return UserResultCodeConstants.INVITED_SAME_USER.getJsonString(jsonCallback);
        }

        String inviteId = InviteInfoUtil.generatorInviteInfoId(srcOpenId, destOpenId, activityName);
        boolean exists = inviteRedis.existsDestProfileId(srcOpenId, activityName, inviteId);
        if (exists) {
            LogService.errorSystemLog("user has exists " + invitedMapMessage.get("openid") + " " + invitedMapMessage);
            return UserResultCodeConstants.INVITED_USER_HASEXISTS.getJsonString(jsonCallback);
        }
        InviteInfo inviteInfo = new InviteInfo();
        inviteInfo.setActvitiyName(activityName);
        inviteInfo.setDestIcon(weixinUser.getHeadimgurl());
        inviteInfo.setDestNick(weixinUser.getNickname());
        inviteInfo.setDestProfileId(destOpenId);
        inviteInfo.setProfileId(srcOpenId);
        inviteInfo.setCreateTime(new Date());
        inviteInfo.setInviteId(inviteId);
        inviteRedis.setInviteObj(inviteInfo);
        inviteRedis.addInviteIdList(srcOpenId, activityName, inviteInfo.getCreateTime().getTime(), inviteInfo.getInviteId());

        return ResultCodeConstants.SUCCESS.getJsonString(jsonCallback);
    }

    public String queryInviteInfo(String appId, String secred, String activityName, Pagination pagination, String jsonCallback, HttpServletRequest request, HttpServletResponse response) {
        Map invitedMapMessage = WeixinUtil.getMapMessage(appId, secred, request, response);
        if (!invitedMapMessage.containsKey("weixinUser")) {
            LogService.errorSystemLog("weixinUser empty " + invitedMapMessage.get("openid") + " " + invitedMapMessage);
            return UserResultCodeConstants.USER_INFO_EMPTY.getJsonString(jsonCallback);
        }

        WeixinUser weixinUser = (WeixinUser) invitedMapMessage.get("weixinUser");
        UserDTO userDTO = new UserDTO();
        userDTO.setHead(weixinUser.getHeadimgurl());
        userDTO.setNick(weixinUser.getNickname());
        userDTO.setProfileId(weixinUser.getOpenid());

        String openId = (String) invitedMapMessage.get("openid");
        Set<String> inviteIdList = inviteRedis.queryInviteIdList(openId, activityName, pagination);
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();
        for (String id : inviteIdList) {
            InviteInfo inviteInfo = inviteRedis.getInviteObj(id);

            if (inviteInfo != null) {
                UserDTO inviteDTO = new UserDTO();
                inviteDTO.setHead(inviteInfo.getDestIcon());
                inviteDTO.setNick(inviteInfo.getDestNick());
                inviteDTO.setProfileId(inviteInfo.getDestProfileId());
                userDTOList.add(inviteDTO);
            }
        }

        Map result = new HashMap();
        result.put("user", weixinUser);
        result.put("inviteds", userDTOList);


        String timestamp = (String) invitedMapMessage.get("timestamp");
        String nonceStr = (String) invitedMapMessage.get("nonceStr");
        String signature = (String) invitedMapMessage.get("signature");
        String openid = (String) invitedMapMessage.get("openid");

        result.put("appId", appId);
        result.put("timestamp", StringUtil.isEmpty(timestamp) ? "" : timestamp);
        result.put("nonceStr", StringUtil.isEmpty(nonceStr) ? "" : nonceStr);
        result.put("signature", StringUtil.isEmpty(signature) ? "" : signature);
        result.put("openid", StringUtil.isEmpty(openid) ? "" : openid);

        JSONObject jsonObject = ResultCodeConstants.SUCCESS.getJsonObject();
        jsonObject.put("result", result);

        return ResultCodeConstants.resultCheckCallback(jsonObject, jsonCallback);
    }

    public long getInviteListCount(String srcOpenId, String activityName) {
        return inviteRedis.counterInviteIdList(srcOpenId, activityName);
    }
}
