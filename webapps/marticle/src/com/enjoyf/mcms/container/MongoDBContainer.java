package com.enjoyf.mcms.container;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDBContainer {
    private static DB mongoDB = null;
    
    public static DB getDB() throws UnknownHostException{
        if(mongoDB == null){
            System.setProperty( "MONGO.POOLSIZE" , "3000" ) ;
            Mongo mongo = new Mongo(ConfigContainer.getMongoDBIp(), ConfigContainer.getMongoDBPort());
            mongoDB = mongo.getDB(ConfigContainer.getMongoDBName());
        }
        return mongoDB;
    }
    
    public static DBCollection getCollection(String collectionName) throws UnknownHostException{
        return getDB().getCollection(collectionName);
    }
}
