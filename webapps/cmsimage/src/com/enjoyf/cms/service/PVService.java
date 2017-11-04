package com.enjoyf.cms.service;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.enjoyf.cms.util.DateUtil;
import com.enjoyf.framework.log.LogService;
import com.enjoyf.framework.log.LogServiceSngl;
import com.enjoyf.framework.log.PageViewLog;
import com.enjoyf.framework.mongodb.MongoDBDao;
import com.enjoyf.util.DateUtils;
import com.enjoyf.util.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PVService extends MongoService implements Runnable {
	private final static String JOYME_CMS_UV_LOG_COLLECTION = "joyme_cms_uv_log";//每月一张
	private final static String JOYME_CMS_UV_COUNT_COLLECTION = "joyme_cms_uv_count";
	private final static String JOYME_CMS_URL_DATE_COUNT_COLLECTION = "joyme_cms_url_date_count";//每月一张
	private final static String JOYME_CMS_PV_COUNT_COLLECTION = "joyme_cms_pv_count";
	private final static String JOYME_CMS_LOG_COLLECTION = "joyme_cms_log";//每月一张
	private final static String JOYME_SEQ_COLLECTION = "joyme_seq";

	private final static String INVALID_FLAG = "/a/other/";

	private static final String APPKEY = "cmsimage";


	private String uri = null;
	private String referer = null;
	private String ip = null;
	private String channel = null;
	private String sessionid = null;
	private String uno = null;
	private String user_agent;
	private String sid = null;

	public PVService(HttpServletRequest request) {
		uri = request.getRequestURI();
		referer = request.getHeader("referer");
		ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP");
		channel = ChannelService.getChannel(request);
		sessionid = request.getSession().getId();
		uno = request.getHeader("uno");
		user_agent = request.getHeader("user-agent");
		this.sid = request.getParameter("sid");

		LogService.infoSystemLog("[" + ip + "] visit the uri [" + uri + "]" + " the channel [" + channel + "]", false);
	}

	@Override
	public void run() {
		try {
			reportPV();
//			initMongo();
//			insertPV();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void insertPV() throws Exception {
		if (uri.contains(INVALID_FLAG))
			return;

		MongoDBDao dao = new MongoDBDao(JOYME_SEQ_COLLECTION);
		int seqId = dao.getSequence("cms_log_id");
		// 获得当前的年月日格式的字符串
		String date = DateUtils.convert2String(System.currentTimeMillis(), DateUtils.DATE_FORMAT);

		// 放入log
		dao.setCollection(JOYME_CMS_LOG_COLLECTION + "_" + getDate());
		Map map = new HashMap();
		map.put("_id", seqId);
		map.put("url", uri);
		map.put("referer", referer);
		map.put("create_time", new Date(System.currentTimeMillis()));
		map.put("create_date", date);
		map.put("addr", ip);
		map.put("channel", channel);
		if (!StringUtil.isEmpty(sid)) {
			map.put("sid", sid);
		}
		DBObject object = new BasicDBObject(map);
		dao.collection.insert(object);

		// 统计一天的pv
		dao.setCollection(JOYME_CMS_PV_COUNT_COLLECTION);
		map = new HashMap();
		map.put("date", date);
		map.put("channel", channel);
		if (!StringUtil.isEmpty(sid)) {
			map.put("sid", sid);
		}
		DBObject query = new BasicDBObject(map);
		DBObject number = new BasicDBObject().append("$inc", new BasicDBObject("count", 1));
		dao.collection.update(query, number, true, false);

		// 统计url的PV
		dao.setCollection(JOYME_CMS_URL_DATE_COUNT_COLLECTION + "_" + getDate());
		map = new HashMap();
		map.put("url", uri);
		map.put("date", date);
		map.put("host", getHost());
		map.put("channel", channel);
		if (!StringUtil.isEmpty(sid)) {
			map.put("sid", sid);
		}
		query = new BasicDBObject(map);
		number = new BasicDBObject().append("$inc", new BasicDBObject("count", 1));
		dao.collection.update(query, number, true, false);

		// 放入ip_log
		if (!isAddrExisted(dao, date)) {
			dao.setCollection(JOYME_CMS_UV_LOG_COLLECTION + "_" + getDate());
			map = new HashMap();
			map.put("date", date);
			map.put("addr", ip);
			map.put("channel", channel);
			if (!StringUtil.isEmpty(sid)) {
				map.put("sid", sid);
			}
			object = new BasicDBObject(map);
			dao.collection.insert(object);

			dao.setCollection(JOYME_CMS_UV_COUNT_COLLECTION);
			map = new HashMap();
			map.put("date", date);
			map.put("channel", channel);
			if (!StringUtil.isEmpty(sid)) {
				map.put("sid", sid);
			}
			query = new BasicDBObject(map);
			number = new BasicDBObject().append("$inc", new BasicDBObject("count", 1));
			dao.collection.update(query, number, true, false);
		}
	}

	private Object getHost() {
		try {
			URL u = new URL(uri);
			return u.getHost();
		} catch (Exception e) {
			return null;
		}
	}

	private boolean isAddrExisted(MongoDBDao dao, String date) throws ParseException {
//        String beginTime = date + " 00:00:00";
//        String endTime = date + " 23:59:59";
//        Date beginDate = org.apache.commons.lang.time.DateUtils.parseDate(beginTime, new String[] { Constants.DATE_WITH_HOUR_STRING });
//        Date endDate = org.apache.commons.lang.time.DateUtils.parseDate(endTime, new String[] { Constants.DATE_WITH_HOUR_STRING });
		dao.setCollection(JOYME_CMS_UV_LOG_COLLECTION + "_" + getDate());
		BasicDBObject condition = new BasicDBObject();
		condition.put("date", date);
		condition.put("addr", ip);
		condition.put("channel", channel);
		if (!StringUtil.isEmpty(sid)) {
			condition.put("sid", sid);
		}
		DBObject object = dao.collection.findOne(condition);
		return object != null;
	}


	private static String getDate() {
		return DateUtil.convert2String(System.currentTimeMillis(), "yyyyMM");
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
}
