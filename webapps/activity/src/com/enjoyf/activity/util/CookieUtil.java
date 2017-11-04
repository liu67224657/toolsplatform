
package com.enjoyf.activity.util;


import com.enjoyf.activity.container.PropertiesContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {


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

    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response, String name, String value) {
        // 默认时间是0x278d00（一个月）
        //2*16^5+7*16^4+8*16^3+13*16^2
        //2097152+458752+32768+3328=2592000
        //24*60*60*30=2592000
        setCookie(request, response, name, value, 0x278d00);
    }

    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(request, response, name, value, "." + PropertiesContainer.getDOMAIN(), maxAge);
    }


    public static void setCookie(HttpServletRequest request,
                                 HttpServletResponse response, String name, String value, String domain, int maxAge) {
        Cookie cookie = new Cookie(name, value == null ? "" : value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(getPath(request));
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    public static String getPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return (path == null || path.length() == 0) ? "/" : path;
    }


    public static void deleteALLCookies(HttpServletRequest request,
                                        HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie _cookie = cookies[i];
                _cookie.setMaxAge(0);
                _cookie.setPath(getPath(request));
                _cookie.setValue(null);
                response.addCookie(_cookie);
            }
        }
    }


    public static void deleteCookies(HttpServletRequest request,
                                     HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie _cookie = cookies[i];
                if (name.equals(_cookie.getName())) {
                    _cookie.setMaxAge(0);
                    _cookie.setPath(getPath(request));
                    _cookie.setValue(null);
                    response.addCookie(_cookie);
                }
            }
        }
    }


}
