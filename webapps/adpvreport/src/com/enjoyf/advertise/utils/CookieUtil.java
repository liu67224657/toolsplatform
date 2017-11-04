/**
 * cookie的增、删、查工具类
 */
package com.enjoyf.advertise.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    public static final String COOKIE_KEY_ADVPID = "adv.pid";
    public static final String COOKIE_KEY_PV_gbid = "pv.gbid";
    public static final String COOKI_KEY_JSESSIONID = "JSESSIONID";
    public static final String COOKI_KEY_PV_SCN = "pv.scn";
    public static final String COOKI_KEY_PV_EV = "pv.ev";
    public static final String COOKI_KEY_PV_ET = "pv.et";
    public static final String COOKI_KEY_PV_OS = "pv.os";
    public static final String COOKI_KEY_PV_INTTIME = "int";
    public static final String REQUEST_HEADER_REFERER = "referer";


    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies == null || name == null || name.length() == 0) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())) {
                return cookies[i];
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies == null || name == null || name.length() == 0) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

}
