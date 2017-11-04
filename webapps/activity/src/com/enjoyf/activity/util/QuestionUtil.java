package com.enjoyf.activity.util;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.util.DateUtil;
import com.enjoyf.util.MD5Util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class QuestionUtil {

    public static String getQuestionId(String gamecode, Date date) {
        return MD5Util.Md5(gamecode + DateUtil.formatDateToString(date, DateUtil.YYYYMMDD_FORMAT));
    }
    public static String getQuestionLogId(String profileId, Date date) {
        return MD5Util.Md5(profileId + DateUtil.formatDateToString(date, DateUtil.YYYYMMDD_FORMAT));
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(getQuestionLogId("ofllgwI6Lrtqu_DsvCKz5LXU8p8k",date));

    }
}
