package com.enjoyf.cms.service;

import com.enjoyf.cms.container.PropertiesContainer;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.framework.mongodb.MongoDBDao;
import com.mongodb.Mongo;

public class MongoService {
    protected static Mongo mongo = null;

    public Mongo initMongo() {
        if (mongo == null) {
            try {
                String ip = PropertiesContainer.getMongoDBIP();
                int port = PropertiesContainer.getMongoDBPort();
                if(port == 0)
                    return null;
                String maxConns = PropertiesContainer.getMongoDBMaxConns();
                MongoDBDao.initMongo(ip, port, Integer.parseInt(maxConns), 50);
                MongoDBDao.initDB(PropertiesContainer.getMongoDBName());
                mongo = MongoDBDao.mongo;
            } catch (Exception e) {
                LogService.errorSystemLog("Error when init mongo", e);
            }
        }
        return mongo;
    }
}
