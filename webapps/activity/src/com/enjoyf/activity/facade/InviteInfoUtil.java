package com.enjoyf.activity.facade;

import com.enjoyf.util.MD5Util;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:2017/1/3
 */
public class InviteInfoUtil {
    public static String generatorInviteInfoId(String srcProfileId, String destProfileId, String activityName) {
        return MD5Util.Md5(srcProfileId + destProfileId + activityName);
    }
}
