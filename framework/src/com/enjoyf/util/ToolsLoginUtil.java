package com.enjoyf.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhimingli on 2015/5/19.
 */
public class ToolsLoginUtil {
    public static final String TOOLS_COOKIEKEY_MESSAGE = "t_jm_message";
    public static final String TOOLS_COOKIEKEY_ENCRYPT = "t_jm_encrypt";
    public static final String TOOLS_COOKIEKEY_SECRET_KEY = "yh87&sw2";
    public static final String TOOLS_COOKIEKEY_SECRET_KEY_DEV = "7ejw!9d#";
    public static final String TOOLS_COOKIEKEY_SECRET_KEY_ALPHA = "8F5&JL3";
    public static final String TOOLS_COOKIEKEY_SECRET_KEY_BETA = "#4g%klwe";

    private static Long COOKIE_VALIDITY = 7 * 24 * 60 * 60 * 1000L;


    private static String getToolsCookeySecretKey(String host) {
        if (host.contains("dev") || host.contains("test") || host.contains("127.0.0.1")) {
            return TOOLS_COOKIEKEY_SECRET_KEY_DEV;
        } else if (host.contains("alpha")) {
            return TOOLS_COOKIEKEY_SECRET_KEY_ALPHA;
        } else if (host.contains("beta")) {
            return TOOLS_COOKIEKEY_SECRET_KEY_BETA;
        }
        return TOOLS_COOKIEKEY_SECRET_KEY;
    }

    public static boolean isLogin(HttpServletRequest request, Integer roidid) {
        String messgae = CookieUtil.getCookieValue(request, TOOLS_COOKIEKEY_MESSAGE);
        String encrypt = CookieUtil.getCookieValue(request, TOOLS_COOKIEKEY_ENCRYPT);
        if (StringUtil.isEmpty(messgae) || StringUtil.isEmpty(encrypt)) {
            return false;
        }

        String utfMesage = null;
        try {
            utfMesage = java.net.URLDecoder.decode(messgae, "utf-8");
            String marticleEncrypt = MD5Util.Md5(getToolsCookeySecretKey(request.getHeader("Host")) + messgae);

            if (!encrypt.equals(marticleEncrypt)) {
                return false;
            }

            String roid[] = utfMesage.split("\\|");
            //时间是否过期
            if (roid.length >= 5) {
                long setCookieTime = Long.valueOf(roid[4]);
                if (System.currentTimeMillis() - setCookieTime > COOKIE_VALIDITY) {
                    return false;
                }
            }


            //判断角色
            String roidArr[] = roid[0].split(",");
            Set<Integer> roidSet = new HashSet<Integer>();
            for (String id : roidArr) {
                roidSet.add(Integer.valueOf(id));
            }
            if (roidSet.contains(1) || roidSet.contains(roidid)) {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        //1|1|sysadmin|管理员|1436590640558
        //1|1|sysadmin|管理员|1436590640558

        System.out.println(MD5Util.Md5("7ejw!9d#1|1|sysadmin|管理员|1436590853597"));
    }
}
