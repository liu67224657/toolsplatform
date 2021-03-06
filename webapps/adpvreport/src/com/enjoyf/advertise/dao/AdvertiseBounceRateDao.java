package com.enjoyf.advertise.dao;

import com.enjoyf.advertise.bean.AdvertiseBounceRate;
import com.enjoyf.util.TableUtil;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Date;

public class AdvertiseBounceRateDao {
    private String KEY_COLLECTION = "advertise_bouncerate_";
    private static String KEY_TABLE_SUFFIX_FMT = "yyyyMM";

    private Mongo mongo = null;
    private DB db = null;

    public AdvertiseBounceRateDao(String host, int port, String maxConns) throws UnknownHostException {
        if (mongo == null) {
            System.setProperty("MONGO.POOLSIZE", maxConns);
            mongo = new Mongo(host, port);
        }
        db = mongo.getDB("page_view");
    }

    public AdvertiseBounceRateDao(String host, int port) throws UnknownHostException {
        if (mongo == null) {
            new AdvertiseBounceRateDao(host, port, "50");
        }
    }

    public AdvertiseBounceRateDao(String host, int port, int maxExecutableThread, int maxWaitingThread) throws UnknownHostException {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoOptions mongoOptions = new MongoOptions();
        mongo = new Mongo(serverAddress, mongoOptions);
    }

    public void insertAdvertiseBounceRate(AdvertiseBounceRate advertiseBounceRate) {
        DBCollection collection = db.getCollection(getCollection(advertiseBounceRate.getCreateTime()));
        collection.createIndex(new BasicDBObject("advertise_id", 1).append("stat_date", -1));
        collection.insert(entryToDBObject(advertiseBounceRate));
    }

    protected BasicDBObject entryToDBObject(AdvertiseBounceRate entry) {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("url", entry.getUrl());
        dbObject.put("advertise_id", entry.getPublishId());
        dbObject.put("sessionid", entry.getSessionId());
        dbObject.put("globalid", entry.getGlobalId());
        dbObject.put("create_time", entry.getCreateTime().getTime());
        dbObject.put("ip", entry.getCreateIp());
        dbObject.put("stat_date", entry.getStatDate());
        dbObject.put("area", entry.getArea());
        return dbObject;
    }

    private String getCollection(Date date) {
        return KEY_COLLECTION + TableUtil.getTableDateSuffix(date, KEY_TABLE_SUFFIX_FMT);
    }
}
