package com.enjoyf.activity.util;/**
 * Created by ericliu on 16/9/7.
 */

import com.enjoyf.util.MD5Util;

/**
 * @author <a href=mailto:ericliu@fivewh.com>ericliu</a>,Date:16/9/7
 */
public class PointUtil {
    public static String getPointId(String profileId, String gameCode) {
        return MD5Util.Md5(profileId + gameCode);
    }

    public static void main(String[] args) {
        System.out.println(getPointId("ofllgwOYVVEO4MjnNLNPBG_ZDrc0","qyz"));
    }
}
