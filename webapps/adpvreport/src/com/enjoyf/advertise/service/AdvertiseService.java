package com.enjoyf.advertise.service;


import com.enjoyf.advertise.bean.AdvertiseBounceRate;
import com.enjoyf.advertise.bean.AdvertisePageView;
import com.enjoyf.advertise.bean.AdvertisePageViewCount;
import com.enjoyf.advertise.bean.AdvertiseReferReadTimes;
import com.enjoyf.advertise.bean.AdvertiseUserView;
import com.enjoyf.advertise.dao.AdvertisePageViewCountDao;
import com.enjoyf.advertise.dao.AdvertiseBounceRateDao;
import com.enjoyf.advertise.dao.AdvertisePageViewDao;
import com.enjoyf.advertise.dao.AdvertiseReferReadTimesDao;
import com.enjoyf.advertise.dao.AdvertiseUserViewDao;

import java.net.UnknownHostException;

public class AdvertiseService {

    private AdvertisePageViewDao advertiseDao;
    private AdvertiseUserViewDao advertiseUserViewDao;
    private AdvertiseReferReadTimesDao advertiseReferReadTimesDao;
    private AdvertisePageViewCountDao pageViewCountDao;
    private AdvertiseBounceRateDao advertiseBounceRateDao;


    public AdvertiseService(String mongoDBIP, int mongoDBPort, String s) throws UnknownHostException {
        advertiseDao = new AdvertisePageViewDao(mongoDBIP, mongoDBPort, s);
        advertiseUserViewDao = new AdvertiseUserViewDao(mongoDBIP, mongoDBPort, s);
        advertiseReferReadTimesDao = new AdvertiseReferReadTimesDao(mongoDBIP, mongoDBPort, s);
        pageViewCountDao = new AdvertisePageViewCountDao(mongoDBIP, mongoDBPort, s);
        advertiseBounceRateDao = new AdvertiseBounceRateDao(mongoDBIP, mongoDBPort, s);
    }

    public void reportAdvertisePageView(AdvertisePageView advertisePageView) {
        advertiseDao.insertAdvertisePageView(advertisePageView);
    }

    public void reportAdvertiseUserView(AdvertiseUserView advertiseUserView) {
        advertiseUserViewDao.insertUserView(advertiseUserView);
    }


    public void reportAdvertiseReferReadTimes(AdvertiseReferReadTimes advertiseReferReadTimes) {
        advertiseReferReadTimesDao.insertAdvertiseReferReadTimes(advertiseReferReadTimes);
    }

    public void increasePvCount(AdvertisePageViewCount pvCount) {
        pageViewCountDao.increaseUserView(pvCount);
    }

     public void reportAdvertiseBounceRate(AdvertiseBounceRate advertiseBounceRate) {
        advertiseBounceRateDao.insertAdvertiseBounceRate(advertiseBounceRate);
    }
}
