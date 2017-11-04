package com.enjoyf.advertise.dao;

import com.enjoyf.advertise.bean.AdvertiseUserView;
import com.enjoyf.util.TableUtil;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AdvertiseUserViewDao {
    private String KEY_COLLECTION = "advertise_uv_";
    private static String KEY_TABLE_SUFFIX_FMT = "yyyyMM";

    private Mongo mongo = null;
    private DB db = null;

    public AdvertiseUserViewDao(String host, int port, String maxConns) throws UnknownHostException {
        if (mongo == null) {
            System.setProperty("MONGO.POOLSIZE", maxConns);
            mongo = new Mongo(host, port);
        }
        db = mongo.getDB("page_view");
    }

    public AdvertiseUserViewDao(String host, int port) throws UnknownHostException {
        if (mongo == null) {
            new AdvertiseUserViewDao(host, port, "50");
        }
    }

    public AdvertiseUserViewDao(String host, int port, int maxExecutableThread, int maxWaitingThread) throws UnknownHostException {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoOptions mongoOptions = new MongoOptions();
        mongo = new Mongo(serverAddress, mongoOptions);
    }

    public void insertUserView(AdvertiseUserView advertiseUserView) {
        DBCollection collection = db.getCollection(getCollection(advertiseUserView.getCreateTime()));
        collection.createIndex(new BasicDBObject("advertise_id", 1).append("stat_date", -1));
        collection.insert(entryToDBObject(advertiseUserView));
    }

    public int statsRemainUser(Date startDate, Date endDate) {
        //mongod 自由的函数 emit(key,value),对应的是 reduce中的(key,value)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fromStr = dateFormat.format(startDate);
        String toStr = dateFormat.format(endDate);

        String mapFun = "function () { emit(this.ip, 1)}";

        String reduceFun = "function (key, values) {" +
                "var sum = 0; " +
                "for (var i = 0; i < values.length; i++) {" +
                "sum += values[i];" +
                "} return sum;}";
        DBCollection collection = db.getCollection(getCollection(new Date()));

        long now = System.currentTimeMillis();
        DBObject query = new BasicDBObject();
        query.put("stat_date", new BasicDBObject("$gte", fromStr));
        query.put("stat_date", new BasicDBObject("$lte", toStr));
        MapReduceCommand mrc = new MapReduceCommand(collection, mapFun, reduceFun, "tempdir", MapReduceCommand.OutputType.REPLACE, query);
//        DBObject object = new BasicDBObject();
//        object.put("ip", 1);
//        mrc.setSort(object);
        MapReduceOutput outPut = collection.mapReduce(mrc);

        // create our pipeline operations, first with the $match
//        DBObject query = new BasicDBObject();
//        query.put("create_time", new BasicDBObject("$gte", startDate.getTime()));
//        query.put("create_time", new BasicDBObject("$lte", endDate.getTime()));
//        DBObject match = new BasicDBObject("$match", query);


////        // build the $projection operation
//        DBObject fields = new BasicDBObject();
//        fields.put("ip", 1);
////        fields.put("count", 0);
//        DBObject project = new BasicDBObject("$project", fields);
//
//        // Now the $group operation
//        DBObject groupFields = new BasicDBObject("_id", "$ip");
//        DBObject group = new BasicDBObject("$group", groupFields);
//
//        AggregationOutput output = collection.aggregate(group, match, project);
//        System.out.println(output);
//        System.out.println("end aggregate spend:" + ((System.currentTimeMillis() - now) / 1000));


//        DBObject key = new BasicDBObject("ip", true);
//
//        BasicDBObject initial = new BasicDBObject();
//        initial.put("count", 0);
//
//        DBObject obj=collection.group(key, query, initial, reduceFun);
//        System.out.println(obj);
//        System.out.println("end aggregate spend:" + ((System.currentTimeMillis() - now) / 1000));

        //返回
        DBObject condition = new BasicDBObject();
        condition.put("value", new BasicDBObject("$gt", 1));
        return db.getCollection("tempdir").find(condition).count();
    }


    protected BasicDBObject entryToDBObject(AdvertiseUserView entry) {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("_id", entry.getCreateIp() + "_" + entry.getStatDate());
        dbObject.put("advertise_id", entry.getPublishId());
        dbObject.put("url", entry.getUrl());
        dbObject.put("globalid", entry.getGlobalId());
        dbObject.put("stat_date", entry.getStatDate());
        dbObject.put("create_time", entry.getCreateTime().getTime());
        dbObject.put("ip", entry.getCreateIp());
        dbObject.put("refer", entry.getRefer());
        dbObject.put("area", entry.getArea());
        dbObject.put("host", entry.getHost());
        return dbObject;
    }


    private String getCollection(Date date) {
        return KEY_COLLECTION + TableUtil.getTableDateSuffix(date, KEY_TABLE_SUFFIX_FMT);
    }

    public static void main(String[] args) throws UnknownHostException {
        AdvertiseUserViewDao dao = new AdvertiseUserViewDao("db001.dev", 4066, "500");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Random random = new Random(7);
//
//
        System.out.println("start init data ");
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
//        for (int i = 2000; i < 1000000; i++) {
//            int j = Math.abs(random.nextInt() % 7);
//            cal.setTime(now);
//            cal.set(Calendar.MINUTE, 0);
//            cal.set(Calendar.HOUR_OF_DAY, 0);
//            cal.set(Calendar.SECOND, 0);
//            cal.set(Calendar.MILLISECOND, 0);
//            cal.add(Calendar.DAY_OF_YEAR, -7);
////
//            cal.add(Calendar.DAY_OF_YEAR, j);
////
////            System.out.println(j);
//            AdvertiseUserView auv = new AdvertiseUserView();
//            auv.setCreateIp(Math.abs(random.nextInt() % 255) + "."
//                    + Math.abs(random.nextInt() % 255) + "."
//                    + Math.abs(random.nextInt() % 255) + "."
//                    + Math.abs(random.nextInt() % 255));
//            auv.setCreateTime(cal.getTime());
//            auv.setGlobalId("abc");
//            auv.setPublishId("xxxx-aaa-ccc");
//            auv.setUrl("abcdefg");
//            auv.setStatDate(dateFormat.format(cal.getTime()));
//
//            dao.insertUserView(auv);
////            System.out.println(cal.getTime());
//        }


        Date to = new Date();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        to = cal.getTime();

        cal.add(Calendar.DAY_OF_YEAR, -1);

        Date from = cal.getTime();
        System.out.println(dao.statsRemainUser(from, to));
        System.out.println("start init data spent:" + ((System.currentTimeMillis() - now.getTime()) / 1000));
    }


}
