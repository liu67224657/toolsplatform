package com.enjoyf.activity.util;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.util.DateUtil;
import com.enjoyf.util.MD5Util;

import java.util.Date;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class SignUtil {

    public static String getSignLogId(String profileId, long signId, Date date) {
        return MD5Util.Md5(profileId + signId + DateUtil.formatDateToString(date, DateUtil.YYYYMMDD_FORMAT));
    }

    public static void main(String[] args) {
        System.out.println(SignUtil.getSignLogId("opRUav2PvKcq4g874cjiEhSvAxNM",1000,new Date()));;
    }
}
