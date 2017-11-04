package com.enjoyf.mcms.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HTTPUtil {

    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String COOKIE_HOME_PREF = "home.pref";
    private static final String COOKIE_HOME_PREF_MOBILE = "m";
    private static final String COOKIE_HOME_PREF_WWW = "w";

    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级反向代理
        if (null != ip && !"".equals(ip.trim())) {
            StringTokenizer st = new StringTokenizer(ip, ",");
            String ipTmp = "";
            if (st.countTokens() > 1) {
                while (st.hasMoreTokens()) {
                    ipTmp = st.nextToken();
                    if (ipTmp != null && ipTmp.length() != 0
                            && !"unknown".equalsIgnoreCase(ipTmp)) {
                        ip = ipTmp;
                        break;
                    }
                }
            }
        }
        return ip;
    }

    public static String getCookieValue(HttpServletRequest request, String key) {
        String value = null;
        if (request != null && request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(key)) {
                    value = c.getValue();
                    break;
                }
            }
        }
        return value;
    }

    public static void setCookie(HttpServletResponse resp, Cookie sessionCookie) {
        resp.addCookie(sessionCookie);
    }

    public static void setCookie(HttpServletResponse resp, String name, String value) {
        resp.addCookie(new Cookie(name, value));
    }

    public static void removeCookie(HttpServletResponse resp, Cookie c) {
        c.setValue(null);
        c.setMaxAge(0);
        resp.addCookie(c);
    }

    public static String getRedr(HttpServletRequest request) {
        String redr = "";
        if (request.getMethod().equalsIgnoreCase("get")) {
            redr = getRequestedUrl(request);
        } else {
            redr = request.getHeader("referer");
        }

        redr = StringUtil.nullToblank(redr);

        try {
            redr = URLEncoder.encode(redr, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        return redr;
    }

    public static String getRequestedUrl(HttpServletRequest request) {
        StringBuffer urlBuf = new StringBuffer(16);

        urlBuf.append(request.getRequestURL());

        if (!StringUtil.isEmpty(request.getQueryString())) {
            urlBuf.append("?").append(request.getQueryString());
        }

        return urlBuf.toString();
    }

    public static String getRequestedURIQueryString(HttpServletRequest request) {
        StringBuffer urlBuf = new StringBuffer();

        urlBuf.append(request.getRequestURI());

        if (!StringUtil.isEmpty(request.getQueryString())) {
            urlBuf.append("?").append(request.getQueryString());
        }

        return urlBuf.toString();
    }

    public static String getURIQueryString(String url) {
        StringBuffer urlBuf = new StringBuffer();

        try {
            URL urlObj = new URL(url);

            if (urlObj != null) {
                urlBuf.append(urlObj.getFile());
            }
        } catch (Exception e) {
            //
        }

        return urlBuf.toString();
    }

    public static String getServerBaseUrl(HttpServletRequest request, String url) {
        StringBuffer sb = new StringBuffer();
        sb.append(request.getServletContext().getRealPath(url));
        return sb.toString();
    }

    public static void writeJson(HttpServletResponse response, String jsonStr) throws IOException {
        response.setContentType("text/plain");

        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(jsonStr);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }


    public static Map<String, String> getJParam(String JParam) {
        if (StringUtil.isEmpty(JParam)) {
            return Collections.EMPTY_MAP;
        }
        Map<String, String> map = new HashMap<String, String>();
        String[] jparam = JParam.split("; ");
        for (String j : jparam) {
            String[] keyValue = j.split("=");
            if (keyValue != null && keyValue.length == 2) {
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }

    /**
     * 优先级  paramter-->head-->jParam
     *
     * @param request
     * @param key
     * @return
     */
    public static String getParam(HttpServletRequest request, String key) {
        Map<String, String> jParam = getJParam(request.getHeader("JParam"));

        String value = request.getParameter(key);
        if (!StringUtil.isEmpty(value)) {
            return value;
        }
        value = request.getHeader(key);
        if (!StringUtil.isEmpty(value)) {
            return value;
        }

        if (!CollectionUtil.isEmpty(jParam)) {
            value = jParam.get(key);
        }

        return value;
    }

}
