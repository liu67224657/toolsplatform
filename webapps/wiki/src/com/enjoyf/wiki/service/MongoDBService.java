package com.enjoyf.wiki.service;

import com.enjoyf.framework.mongodb.MongoDBDao;
import com.enjoyf.wiki.container.PropertiesContainer;

import java.net.UnknownHostException;

public class MongoDBService {
    public void initMongoDB() throws UnknownHostException {
        MongoDBDao.initMongo(PropertiesContainer.getInstance().getMongoDbIp(), PropertiesContainer.getInstance().getMongoDBPort(), PropertiesContainer.getInstance().getMongoDbMaxConns()+"");
        MongoDBDao.initDB(PropertiesContainer.getInstance().getMongoDbName());
    }
}
