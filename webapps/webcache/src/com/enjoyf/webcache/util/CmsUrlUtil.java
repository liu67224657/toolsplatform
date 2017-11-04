package com.enjoyf.webcache.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-6-11
 * Time: 上午10:45
 * To change this template use File | Settings | File Templates.
 */
public class CmsUrlUtil {
    public static final int HOST_WWW = 0;

    public static final int HOST_MOBILE = 1;

    public static final int HOST_SUBDOMAIN = 2;


    /**
     * 判断域名是主站还是m站还是SubDomain
     *
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static int checkDomain(URL url) throws MalformedURLException {
        String host = url.getHost();
        if (host.startsWith("www") ||
                host.startsWith("localhost") ||
                host.startsWith("192") || host.startsWith("172")) {
            return HOST_WWW;
        }

        if (host.startsWith("m")) {
            return HOST_MOBILE;
        }

        return HOST_SUBDOMAIN;
    }

}
