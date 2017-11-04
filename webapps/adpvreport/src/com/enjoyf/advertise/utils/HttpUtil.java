package com.enjoyf.advertise.utils;

import com.enjoyf.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-3-14 上午10:34
 * Description:
 */
public class HttpUtil {
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

    public static String getRequestedUrl(HttpServletRequest request) {
        StringBuffer urlBuf = new StringBuffer(16);

        urlBuf.append(request.getRequestURL());

        if (!StringUtil.isEmpty(request.getQueryString())) {
            urlBuf.append("?").append(request.getQueryString());
        }

        return urlBuf.toString();
    }
}
