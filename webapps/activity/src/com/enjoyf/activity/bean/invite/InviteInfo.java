package com.enjoyf.activity.bean.invite;

import com.enjoyf.util.StringUtil;
import com.google.gson.Gson;

import java.util.Date;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:2017/1/3
 */
public class InviteInfo {
    private String inviteId;
    private String profileId;
    private String destProfileId;
    private String destNick;
    private String destIcon;
    private String actvitiyName;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getInviteId() {
        return inviteId;
    }

    public void setInviteId(String inviteId) {
        this.inviteId = inviteId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getDestProfileId() {
        return destProfileId;
    }

    public void setDestProfileId(String destProfileId) {
        this.destProfileId = destProfileId;
    }

    public String getDestNick() {
        return destNick;
    }

    public void setDestNick(String destNick) {
        this.destNick = destNick;
    }

    public String getDestIcon() {
        return destIcon;
    }

    public void setDestIcon(String destIcon) {
        this.destIcon = destIcon;
    }

    public String getActvitiyName() {
        return actvitiyName;
    }

    public void setActvitiyName(String actvitiyName) {
        this.actvitiyName = actvitiyName;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static InviteInfo fromJson(String jsonStr) {
        if (StringUtil.isEmpty(jsonStr)) {
            return null;
        }

        return new Gson().fromJson(jsonStr, InviteInfo.class);
    }


}
