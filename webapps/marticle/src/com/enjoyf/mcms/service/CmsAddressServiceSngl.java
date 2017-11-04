package com.enjoyf.mcms.service;

import com.enjoyf.mcms.bean.CmsAddress;
import com.enjoyf.mcms.container.ConfigContainer;

import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-2-21
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 */
public class CmsAddressServiceSngl {
    private static CmsAddressService cmsAddressService;

    public static CmsAddressService getInstance() {
        if (cmsAddressService == null) {
            synchronized (CmsAddressServiceSngl.class) {
                if (cmsAddressService == null) {
                    try {
                        cmsAddressService = new CmsAddressService(ConfigContainer.getMongoDBIp(), ConfigContainer.getMongoDBPort(), ConfigContainer.getMongoDBMaxConns() + "");
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return cmsAddressService;
    }

    private CmsAddressServiceSngl(CmsAddress cmsAddress) {

    }
}
