package com.enjoyf.activity.util;

import com.enjoyf.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by zhimingli on 2016/8/2 0002.
 */
public class HTTPUtil {
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

    /**
     * 优先级  paramter-->head
     *
     * @param request
     * @param key
     * @return
     */
    public static String getParam(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        if (!StringUtil.isEmpty(value)) {
            return value;
        }
        value = request.getHeader(key);
        if (!StringUtil.isEmpty(value)) {
            return value;
        }
        return value;
    }
}
