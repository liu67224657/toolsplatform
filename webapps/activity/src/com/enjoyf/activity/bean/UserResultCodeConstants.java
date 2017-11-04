package com.enjoyf.activity.bean;

public class UserResultCodeConstants  extends ResultCodeConstants{

    public static final UserResultCodeConstants USER_INFO_EMPTY = new UserResultCodeConstants(BASE_USER_RESCODE+1, "user.info.empty");

    public static final UserResultCodeConstants INVITED_SAME_USER = new UserResultCodeConstants(BASE_USER_RESCODE+2, "invite.same.user");

    public static final UserResultCodeConstants INVITED_USER_HASEXISTS= new UserResultCodeConstants(BASE_USER_RESCODE+3, "invited.user.hasexists");

    public UserResultCodeConstants(int code, String msg) {
        super(code, msg);
    }
}
