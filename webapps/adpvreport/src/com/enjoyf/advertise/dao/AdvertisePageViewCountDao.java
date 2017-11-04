package com.enjoyf.advertise.dao;

import com.enjoyf.advertise.bean.AdvertisePageViewCount;
import com.enjoyf.util.TableUtil;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdvertisePageViewCountDao {
    private String KEY_COLLECTION = "advertise_pvcount_";
    private static String KEY_TABLE_SUFFIX_FMT = "yyyyMM";

    private Mongo mongo = null;
    private DB db = null;

    public AdvertisePageViewCountDao(String host, int port, String maxConns) throws UnknownHostException {
        if (mongo == null) {
            System.setProperty("MONGO.POOLSIZE", maxConns);
            mongo = new Mongo(host, port);
        }
        db = mongo.getDB("page_view");
    }

    public AdvertisePageViewCountDao(String host, int port) throws UnknownHostException {
        if (mongo == null) {
            new AdvertisePageViewCountDao(host, port, "50");
        }
    }

    public AdvertisePageViewCountDao(String host, int port, int maxExecutableThread, int maxWaitingThread) throws UnknownHostException {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoOptions mongoOptions = new MongoOptions();
        mongo = new Mongo(serverAddress, mongoOptions);
    }

    public void increaseUserView(AdvertisePageViewCount pvCount) {
        DBCollection collection = db.getCollection(getCollection(pvCount.getCreateTime()));
        collection.createIndex(new BasicDBObject("advertise_id", 1).append("stat_date", -1));
        Map map = new HashMap();
        map.put("sessionid", pvCount.getSessionId());
        DBObject query = new BasicDBObject(map);

        boolean isExists = collection.findOne(query) != null;

        if (isExists) {
            DBObject number = new BasicDBObject().append("$inc", new BasicDBObject("count", 1));
            collection.update(query, number, true, false);
        } else {
            collection.insert(entryToDBObject(pvCount));
        }
    }

    protected BasicDBObject entryToDBObject(AdvertisePageViewCount entry) {
        BasicDBObject dbObject = new BasicDBObject();

        dbObject.put("advertise_id", entry.getPublishId());
        dbObject.put("sessionid", entry.getSessionId());
        dbObject.put("stat_date", entry.getStatDate());
        dbObject.put("count", entry.getCount());
        dbObject.put("create_time", entry.getCreateTime().getTime());
        dbObject.put("ip", entry.getCreateIp());
        dbObject.put("area", entry.getArea());
        return dbObject;
    }


    private String getCollection(Date date) {
        return KEY_COLLECTION + TableUtil.getTableDateSuffix(date, KEY_TABLE_SUFFIX_FMT);
    }

}
