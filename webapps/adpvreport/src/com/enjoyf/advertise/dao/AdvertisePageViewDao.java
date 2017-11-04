package com.enjoyf.advertise.dao;

import com.enjoyf.advertise.bean.AdvertisePageView;
import com.enjoyf.util.TableUtil;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Date;

public class AdvertisePageViewDao {
    //定义collection集合名（相当于mysql的表名）
    private String KEY_COLLECTION = "advertise_pv_";
    private static String KEY_TABLE_SUFFIX_FMT = "yyyyMM";

    private Mongo mongo = null;
    private DB db = null;

    //链接MongoDB的Coneetion
    public AdvertisePageViewDao(String host, int port, String maxConns) throws UnknownHostException {
        if (mongo == null) {
            System.setProperty("MONGO.POOLSIZE", maxConns);
            mongo = new Mongo(host, port);
        }
        db = mongo.getDB("page_view");
    }

    //构造器
    public AdvertisePageViewDao(String host, int port) throws UnknownHostException {
        if (mongo == null) {
            new AdvertisePageViewDao(host, port, "50");
        }
    }

    //构造器
    public AdvertisePageViewDao(String host, int port, int maxExecutableThread, int maxWaitingThread) throws UnknownHostException {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoOptions mongoOptions = new MongoOptions();
        mongo = new Mongo(serverAddress, mongoOptions);
    }

    //录入操作
    public void insertAdvertisePageView(AdvertisePageView advertisePageView) {
        //通过时间获得 advertise_pv_yyyyMM 的Collection集合
        DBCollection collection = db.getCollection(getCollection(advertisePageView.getCreateTime()));
        collection.createIndex(new BasicDBObject("advertise_id", 1).append("stat_date", -1));
        //db的insert录入
        collection.insert(entryToDBObject(advertisePageView));
    }

    //新建一个MongoDB的键值对形式 <KEY,VALUE> 的 BasicDBObject 对象，将 AdvertisePageView 对象的属性值 作为 BasicDBObject 对象的 VALUE
    protected BasicDBObject entryToDBObject(AdvertisePageView entry) {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("host", entry.getHost());
        dbObject.put("advertise_id", entry.getPublishId());
        dbObject.put("sessionid", entry.getSessionId());
        dbObject.put("url", entry.getUrl());
        dbObject.put("globalid", entry.getGlobalId());
        dbObject.put("stat_date", entry.getStatDate());
        dbObject.put("create_time", entry.getCreateTime().getTime());
        dbObject.put("ip", entry.getCreateIp());
        dbObject.put("refer", entry.getRefer());
        dbObject.put("area", entry.getArea());
        return dbObject;
    }

    //获得Collection的方法
    private String getCollection(Date date) {
        return KEY_COLLECTION + TableUtil.getTableDateSuffix(date, KEY_TABLE_SUFFIX_FMT);
    }

}
