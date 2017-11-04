package com.enjoyf.mcms.service;

import com.enjoyf.mcms.bean.CmsAddress;
import com.enjoyf.mcms.dao.CmsAddressDao;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;


/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  14-2-18 下午3:43
 * Description:
 */
public class CmsAddressService {
    private CmsAddressDao cmsAddressDao;

//    private static CmsAddressService cmsAddressService;

//    public static synchronized CmsAddressService getInstance() {
//        if (cmsAddressService == null) {
//            synchronized (CmsAddressService.class) {
//                try {
//                    cmsAddressService = new CmsAddressService(ConfigContainer.getMongoDBIp(), ConfigContainer.getMongoDBPort(), ConfigContainer.getMongoDBMaxConns() + "");
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
//            }
//        }
//        return cmsAddressService;
//    }

    public CmsAddressService(String mongoIP, int mongoPort, String connNum) throws UnknownHostException {
        this.cmsAddressDao = new CmsAddressDao(mongoIP, mongoPort, connNum);
    }

    public CmsAddress getCmsAddress(int aid) throws Exception {
        return cmsAddressDao.getCmsAddress(aid);
    }

    public Map<Integer, CmsAddress> queryCmsAddressByAidList(List<Integer> aidList) throws Exception {
        return cmsAddressDao.queryCmsAddressByAidList(aidList);
    }
}
