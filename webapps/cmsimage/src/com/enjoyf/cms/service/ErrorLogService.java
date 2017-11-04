package com.enjoyf.cms.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.enjoyf.cms.util.DateUtil;
import com.enjoyf.framework.mongodb.MongoDBDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ErrorLogService extends MongoService {
	public ErrorLogService() {
		initMongo();
	}

	public void saveErrorLog(String url, String referer) {
		MongoDBDao dao = new MongoDBDao("joyme_cms_error_invoke_" + getDate());
		Map map = new HashMap();
		map.put("error_url", url);
		map.put("referer", referer);
		map.put("date", new Date(System.currentTimeMillis()));
		map.put("data_long", System.currentTimeMillis());
		DBObject object = new BasicDBObject(map);
		dao.collection.insert(object);
	}

	private static String getDate() {
		return DateUtil.convert2String(System.currentTimeMillis(), "yyyyMM");
	}
}
