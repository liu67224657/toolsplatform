package com.enjoyf.advertise.service;

import com.enjoyf.advertise.props.Properties;

import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-2-21
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 */
public class AdvertiseServiceSngl {
    //定义全局变量
    private static AdvertiseService advertiseService;

    public static AdvertiseService getInstance() {
        if (advertiseService == null) {
            //加锁
            synchronized (AdvertiseServiceSngl.class) {
                if (advertiseService == null) {
                    try {
                        advertiseService = new AdvertiseService(Properties.getMongoDBIP(), Properties.getMongoDBPort(), Properties.getMongoDBMaxConns() + "");
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return advertiseService;
    }
    //构造器
    private AdvertiseServiceSngl(AdvertiseService advertiseService) {

    }
}
