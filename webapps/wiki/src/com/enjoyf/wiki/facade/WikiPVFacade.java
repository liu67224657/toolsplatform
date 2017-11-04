package com.enjoyf.wiki.facade;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.framework.log.LogServiceSngl;
import com.enjoyf.framework.log.PageViewLog;
import com.enjoyf.framework.mongodb.MongoDBDao;
import com.enjoyf.util.StringUtil;
import com.enjoyf.wiki.bean.JoymeCount;
import com.enjoyf.wiki.util.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

public class WikiPVFacade extends Thread {
	private HttpServletRequest request;
	private String uri = null;
	private String channel = null;
	private String referer = null;
	private String ip = null;
	private String wikiType = null;
	private String wikiKey = null;
	private String sessionid = null;
	private String uno = null;
	private String user_agent;
	private String sid = null;


    private static final String APPKEY ="wiki";

	private static String getDate() {
		return DateUtil.convert2String(System.currentTimeMillis(), "yyyyMM");
	}

	public WikiPVFacade(HttpServletRequest request, String channel, String wikiType, String wikiKey) {
		uri = request.getRequestURI();
		this.channel = channel;
		referer = request.getHeader("referer");
		ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP");
		this.wikiType = wikiType;
		this.wikiKey = wikiKey;
		this.request = request;
		sessionid = request.getSession().getId();
		uno = request.getHeader("uno");
		user_agent = request.getHeader("user-agent");

		this.sid = request.getParameter("sid");
		LogService.infoSystemLog("[" + ip + "][" + channel + "] visit the uri [" + uri + "]", false);
	}

	@Override
	public void run() {
		try {
//			this.insertPV(wikiType);
//
//			this.reportPV();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void reportPV() {
		PageViewLog pageViewLog = new PageViewLog();

        pageViewLog.setAppkey(APPKEY);
		pageViewLog.setLocationUrl(uri);
		pageViewLog.setChannel(channel);
		pageViewLog.setIp(ip);
		pageViewLog.setRefer(referer);
		if (!StringUtil.isEmpty(sid)) {
			pageViewLog.setSid(sid);
		}
		pageViewLog.setSessionId(sessionid);
		pageViewLog.setUno(uno);
		pageViewLog.setUserAgent(user_agent);

		LogServiceSngl.get().reportLogInfo(pageViewLog);
	}

	public void insertPV(String wikiType) throws Exception {
		MongoDBDao dao = new MongoDBDao("joyme_wikipv_seq");
		dao.requestStart();
		try {
			// 获得当前的年月日格式的字符串
			String date = DateUtil.convert2String(System.currentTimeMillis(), DateUtil.DATE_FORMAT);

			long date_long = DateUtil.convert2long(date, DateUtil.DATE_FORMAT);

			// 放入log
			int seqId = dao.getSequence("wiki_log_id");
			dao.setCollection("joyme_wiki_log_" + getDate());
			Map map = new HashMap();
			map.put("_id", seqId);
			map.put("url", uri);
			map.put("channel", channel);
			map.put("referer", referer);
			map.put("create_date", date);
			map.put("create_date_long", System.currentTimeMillis());
			map.put("addr", ip);
			map.put("wikikey", wikiKey);
			map.put("wikitype", wikiType);
			if (!StringUtil.isEmpty(sid)) {
				map.put("sid", sid);
			}
			DBObject object = new BasicDBObject(map);
			dao.collection.insert(object);

			dao.setCollection("joyme_wiki_url_date_count");
			map = new HashMap();
			map.put("url", uri);
			map.put("date", date);
			map.put("date_long", date_long);
			map.put("wikikey", wikiKey);
			map.put("wikitype", wikiType);
			if (!StringUtil.isEmpty(sid)) {
				map.put("sid", sid);
			}
			DBObject urlPvCountQueryObj = new BasicDBObject(map);
			BasicDBObject urlPvCountObj = new BasicDBObject().append("$inc", new BasicDBObject("count", 1));
			dao.collection.update(urlPvCountQueryObj, urlPvCountObj, true, false);

			// 统计pv
			dao.setCollection("joyme_wiki_count");
			map = new HashMap();
			map.put("channel", channel);
			map.put("date", date);
			map.put("wikikey", wikiKey);
			map.put("wikitype", wikiType);
			if (!StringUtil.isEmpty(sid)) {
				map.put("sid", sid);
			}
			DBObject query = new BasicDBObject(map);

			DBObject number = new BasicDBObject().append("$inc", new BasicDBObject("count", 1));
			dao.collection.update(query, number, true, false);

			// 查看IP是否存在
			dao.setCollection("joyme_wiki_uv_log_" + getDate());
			// dao.collection.ensureIndex(new BasicDBObject("create_date", -1).append("addr",-1).append("wiki_type",-1),"idx_uniqe_date");
			boolean isAddrExisted = isAddrExisted(dao, date, wikiType);
			if (!isAddrExisted) {
				dao.setCollection("joyme_wiki_uv_count");
				map = new HashMap();
				map.put("channel", channel);
				map.put("date", date);
				map.put("wikikey", wikiKey);
				map.put("wikitype", wikiType);
				if (!StringUtil.isEmpty(sid)) {
					map.put("sid", sid);
				}
				query = new BasicDBObject(map);
				number = new BasicDBObject().append("$inc", new BasicDBObject("count", 1));
				dao.collection.update(query, number, true, false);
			}
		} finally {
			dao.requestDone();
		}
	}

	private boolean isAddrExisted(MongoDBDao dao, String date, String wikiType) throws ParseException {
		BasicDBObject condition = new BasicDBObject();
		condition.put("create_date", date);
		condition.put("addr", ip);
		condition.put("wikikey", wikiKey);
		condition.put("wiki_type", wikiType);
		if (!StringUtil.isEmpty(sid)) {
			condition.put("sid", sid);
		}
		DBObject object = dao.collection.findOne(condition);

		boolean isExists = object != null;
		if (!isExists) {
			dao.collection.insert(condition);
		}

		return isExists;
	}

	/**
	 * 获得一天的PV和UV的值
	 *
	 * @param date
	 * @return
	 * @throws java.text.ParseException
	 */
	public List getCount(String date) throws ParseException {
		MongoDBDao dao = new MongoDBDao("joyme_wiki_count");
		BasicDBObject condition = new BasicDBObject();
		condition.put("date", date);
		DBCursor cursor = dao.collection.find(condition);
		List pvList = new ArrayList();
		while (cursor.hasNext()) {
			DBObject object = cursor.next();
			JoymeCount jc = new JoymeCount();
			jc.setChannel(object.get("channel").toString());
			jc.setPvcount((Integer) object.get("count"));
			jc.setDate(date);
			pvList.add(jc);
		}

		dao.setCollection("joyme_wiki_uv_count");
		cursor = dao.collection.find(condition);
		while (cursor.hasNext()) {
			DBObject object = cursor.next();

			String channel = object.get("channel").toString();
			for (Iterator iterator = pvList.iterator(); iterator.hasNext(); ) {
				JoymeCount jc = (JoymeCount) iterator.next();
				if (jc.getChannel().equals(channel)) {
					jc.setUvcount((Integer) object.get("count"));
				}
			}
		}
		return pvList;
	}
}
