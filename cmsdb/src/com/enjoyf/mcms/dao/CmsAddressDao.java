package com.enjoyf.mcms.dao;

import com.enjoyf.mcms.bean.CmsAddress;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CmsAddressDao {
    public Mongo mongo = null;
    public DB db = null;

    public CmsAddressDao(String host, int port, String maxConns) throws UnknownHostException {
        if (mongo == null) {
            System.setProperty("MONGO.POOLSIZE", maxConns);
            mongo = new Mongo(host, port);
        }
        db = mongo.getDB("cms");
    }

    public CmsAddressDao(String host, int port) throws UnknownHostException {
        if (mongo == null) {
            new CmsAddressDao(host, port, "50");
        }
    }

    public CmsAddressDao(String host, int port, int maxExecutableThread, int maxWaitingThread) throws UnknownHostException {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoOptions mongoOptions = new MongoOptions();
        mongo = new Mongo(serverAddress, mongoOptions);
    }


    public CmsAddress getCmsAddress(int aid) {
        DBCollection collection = db.getCollection("cms_address");

        BasicDBObject condition = new BasicDBObject();
        condition.put("aid", aid);
        DBCursor cursor = collection.find(condition);
        if (cursor.hasNext()) {
            return getObjectByDb(cursor.next());
        }

        return null;
    }

    public Map<Integer, CmsAddress> queryCmsAddressByAidList(List<Integer> aidList) {

        Map<Integer, CmsAddress> map = new HashMap<Integer, CmsAddress>();

        DBCollection collection = db.getCollection("cms_address");

        DBObject condition = new BasicDBObject();
        BasicDBList values = new BasicDBList();
        values.addAll(aidList);
        condition.put("aid", new BasicDBObject("$in", values));
        DBCursor cursor = collection.find(condition);
        while (cursor.hasNext()) {
            CmsAddress cmsAddress = getObjectByDb(cursor.next());
            map.put(cmsAddress.getAid(), cmsAddress);
        }

        return map;
    }

    public CmsAddress getObjectByDb(DBObject object) {
        CmsAddress ca = new CmsAddress();
        ca.setAid((Integer) object.get("aid"));
        BasicDBList location = (BasicDBList) object.get("location");
        if (location != null && location.size() == 2) {
            double lat = Double.parseDouble(String.valueOf(location.get(0)));
            double lng = Double.parseDouble(String.valueOf(location.get(1)));
            ca.setLocation(new double[]{lat, lng});
        }
        ca.setAddress((String) object.get("address"));

        return ca;
    }


}
