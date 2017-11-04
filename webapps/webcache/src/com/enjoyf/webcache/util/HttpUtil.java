package com.enjoyf.webcache.util;

import com.enjoyf.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/8/26
 * Description:
 */
public class HttpUtil {

    /**
     * 优先从param取，如果没有从header取param>header
     *
     * @param request
     * @return
     */
    public static String getValueByRequest(HttpServletRequest request, String key, String defaultValue) {
        String value = request.getHeader(key);

        String channelByRequest = request.getParameter(key);
        if (!StringUtil.isEmpty(channelByRequest)) {
            value = channelByRequest;
        }

        if (StringUtil.isEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }

}
