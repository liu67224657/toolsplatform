package com.enjoyf.mcms.service;

import java.net.UnknownHostException;

import com.enjoyf.framework.mongodb.MongoDBDao;
import com.enjoyf.mcms.container.ConfigContainer;

public class MongoDBService {
    public void initMongoDB() throws UnknownHostException{
        MongoDBDao.initMongo(ConfigContainer.getMongoDBIp(), ConfigContainer.getMongoDBPort() , ConfigContainer.getMongoDBMaxConns() + "");
        MongoDBDao.initDB(ConfigContainer.getMongoDBName());
    }
}
