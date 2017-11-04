package com.enjoyf.mcms.util;

import javax.servlet.http.HttpServletRequest;

public class AppUtil {
    public final static String ANDROID = "android";
    public final static String IPHONE = "iphone";
    public final static String IPAD = "ipad";
    
    private static final String[] MOBILE_SPECIFIC_SUBSTRING = { "iPad", "iPhone", "Android" };
    private static final String[] MOBILE_SPECIFIC_SUBSTRING_ANDROID = { "Android" };
    private static final String[] MOBILE_SPECIFIC_SUBSTRING_IOS = { "iPad", "iPhone" };
    private static final String[] MOBILE_SPECIFIC_SUBSTRING_IPHONE = { "iPhone" };
    private static final String[] MOBILE_SPECIFIC_SUBSTRING_IPAD = { "ipad" };

    /**
     * 检查是否是移动端
     * 
     * @param request
     * @return
     */
    public static boolean checkMobile(HttpServletRequest request) {
        return isContainHeader(request, MOBILE_SPECIFIC_SUBSTRING);
    }

    public static boolean checkIsAndroid(HttpServletRequest request) {
        return isContainHeader(request, MOBILE_SPECIFIC_SUBSTRING_ANDROID);
    }

    public static boolean checkIsIos(HttpServletRequest request) {
        return isContainHeader(request, MOBILE_SPECIFIC_SUBSTRING_IOS);
    }

    public static boolean checkIsIphone(HttpServletRequest request) {
        return isContainHeader(request, MOBILE_SPECIFIC_SUBSTRING_IPHONE);
    }

    public static boolean checkIsIpad(HttpServletRequest request) {
        return isContainHeader(request, MOBILE_SPECIFIC_SUBSTRING_IPAD);
    }

    private static boolean isContainHeader(HttpServletRequest request, String[] words) {
        String userAgent = request.getHeader("user-agent");
        if (userAgent != null) {
            for (String mobile : words) {
                if (userAgent.contains(mobile) || userAgent.toUpperCase().contains(mobile.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getPlatForm(HttpServletRequest request){
        if(checkIsAndroid(request))
            return ANDROID;
        if(checkIsIpad(request))
            return IPAD;
        if(checkIsIphone(request))
            return IPHONE;
        return ANDROID;
    }
    
}
