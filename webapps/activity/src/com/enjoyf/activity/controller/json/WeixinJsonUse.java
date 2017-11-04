package com.enjoyf.activity.controller.json;

import com.google.common.base.Strings;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhimingli on 2016/11/9 0009.
 * 封转微信账号密码
 */
public class WeixinJsonUse implements Serializable {

    private static Map<String, WeixinJsonUse> map = new HashMap<String, WeixinJsonUse>();

    //着迷测试服务号
    private static WeixinJsonUse JOMYE_TEST = new WeixinJsonUse("wx758f0b2d30620771", "b58cd348c7f5908055e5e691eed45c39");

    //芜湖joyme服务号
    private static WeixinJsonUse JOMYE_COM_WUHU = new WeixinJsonUse("wx2621eecc821ca71a", "ef5c57e630726e64f39b8b16b380bb90");

    //joyme网络正式
    private static WeixinJsonUse JOMYE_COM = new WeixinJsonUse("wxe6eafdebe1a95bd5", "d878b4b82eb36fe487e909cf5886542e");


    private String appid;
    private String secret;


    public WeixinJsonUse(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
        map.put(appid, this);
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public static WeixinJsonUse getByCode(String c) {
        if (Strings.isNullOrEmpty(c)) {
            return null;
        }

        return map.get(c);
    }

    public static Collection<WeixinJsonUse> getAll() {
        return map.values();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public static void main(String[] args) {
        System.out.println(getByCode("wx758f0b2d30620771"));
        System.out.println(getByCode("wx2621eecc821ca71a"));
        System.out.println(getByCode("wxe6eafdebe1a95bd5"));

    }
}
