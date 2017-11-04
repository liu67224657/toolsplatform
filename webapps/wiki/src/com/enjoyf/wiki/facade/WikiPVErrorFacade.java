package com.enjoyf.wiki.facade;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.framework.mongodb.MongoDBDao;
import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.util.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.servlet.http.HttpServletRequest;

public class WikiPVErrorFacade extends Thread {
	private HttpServletRequest request;
	private String uri = null;
	private String channel = null;
	private String referer = null;
	private String ip = null;
	private String wikiType = null;
	private String wikiKey = null;
	private String sid = null;


	private static String getDate() {
		return DateUtil.convert2String(System.currentTimeMillis(), "yyyyMM");
	}

	public WikiPVErrorFacade(HttpServletRequest request, String channel, String wikiType, String wikiKey) {
		uri = request.getRequestURI();
		this.channel = channel;
		referer = request.getHeader("referer");
		ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP");
		this.wikiType = wikiType;
		this.wikiKey = wikiKey;
		this.sid = request.getParameter("sid");
		LogService.infoSystemLog("[" + ip + "][" + channel + "] visit the uri [" + uri + "]", false);
	}

	@Override
	public void run() {
		try {
			//TODO 迁移到阿里云，mongodb暂时去掉
			//this.insertPV(request, wikiType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertPV(HttpServletRequest request, String wikiType) throws Exception {
		MongoDBDao dao = new MongoDBDao("joyme_wikierror_seq");
		dao.requestStart();
		try {
			// 获得当前的年月日格式的字符串
			String date = DateUtil.convert2String(System.currentTimeMillis(), DateUtil.DATE_FORMAT);
			// 查看IP是否存在
			dao.setCollection("joyme_wiki_error_log" + getDate());
			BasicDBObject condition = new BasicDBObject();
			condition.put("key", wikiKey);
			condition.put("wiki_type", wikiType);
			condition.put("url", uri);
			if (!StringUtil.isEmpty(sid)) {
				condition.put("sid", sid);
			}
			DBObject object = dao.collection.findOne(condition);

			condition.put("create_date", date);
			condition.put("addr", ip);
			boolean isExists = object != null;
			if (!isExists) {
				dao.collection.insert(condition);
			}
		} finally {
			dao.requestDone();
		}
	}

}
