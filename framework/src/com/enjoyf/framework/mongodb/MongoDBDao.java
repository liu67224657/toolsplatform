package com.enjoyf.framework.mongodb;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongoDBDao {
    public static Mongo mongo = null;
    public static DB db = null;
    public DBCollection collection = null;

    public static void initMongo(String host, int port, String maxConns) throws UnknownHostException {
        if (mongo == null) {
            System.setProperty("MONGO.POOLSIZE", maxConns);
            mongo = new Mongo(host, port);
        }
    }

    public static void initMongo(String host, int port) throws UnknownHostException {
        if (mongo == null) {
            initMongo(host, port, "50");
        }
    }

    public static void initMongo(String host, int port, int maxExecutableThread, int maxWaitingThread) throws UnknownHostException {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoOptions mongoOptions = new MongoOptions();
        mongo = new Mongo(serverAddress, mongoOptions);
    }

    public static void initDB(String dbName) {
        if (db == null)
            db = mongo.getDB(dbName);
    }

    public MongoDBDao(String collectionName) {
        collection = db.getCollection(collectionName);
    }
    
    public MongoDBDao(String dbName , String collectionName){
        DB db = mongo.getDB(dbName);
        collection = db.getCollection(collectionName);
    }

    public void requestStart() {
        db.requestStart();
    }

    public void requestDone() {
        db.requestDone();
    }

    public MongoDBDao() {

    }

    public void setCollection(String collectionName) {
        collection = db.getCollection(collectionName);
    }

    public DBCollection getCollection(String collectionName) {
        DBCollection collection = db.getCollection(collectionName);
        return collection;
    }

    public int getSequence(String tableName) throws Exception {
        if (collection == null)
            throw new Exception("Collection is null, please set collection");

        DBObject query = new BasicDBObject();
        query.put("col_name", tableName);
        DBObject newDocument = new BasicDBObject().append("$inc", new BasicDBObject().append("cnt", 1));
        DBObject ret = collection.findAndModify(query, newDocument);

        if (ret == null) {
            collection.update(query, newDocument, true, false);
            return 1;
        } else {
            return (Integer) ret.get("cnt") + 1;
        }
    }

    public static void main(String[] args) throws Exception {
        MongoDBDao.initMongo("172.16.75.30", 3333);
        MongoDBDao.initDB("joyme_pv");
        MongoDBDao dao = new MongoDBDao("sequence");
        System.out.println(dao.getSequence("testtable"));
    }
}
