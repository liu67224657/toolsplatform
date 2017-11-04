package com.enjoyf.mcms.facade;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.framework.log.LogServiceSngl;
import com.enjoyf.framework.log.PageViewLog;
import com.enjoyf.framework.mongodb.MongoDBDao;
import com.enjoyf.mcms.bean.JoymeCount;
import com.enjoyf.mcms.service.ChannelService;
import com.enjoyf.mcms.util.DateUtil;
import com.enjoyf.util.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

public class ArchivePVFacade extends Thread {
    private HttpServletRequest request;
    private String uri = null;
    private String channel = null;
    private String referer = null;
    private String ip = null;
    private String sessionid = null;
    private String uno = null;
    private String user_agent;
    private String sid = null;

    private static final String APPKEY = "marticle";

    private static String getDate() {
        return DateUtil.convert2String(System.currentTimeMillis(), "yyyyMM");
    }

    public ArchivePVFacade(){}

    public ArchivePVFacade(HttpServletRequest request) {
        uri = request.getRequestURI();
        channel = ChannelService.getChannel(request);
        referer = request.getHeader("referer");
        ip = request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : request.getHeader("X-Real-IP");

        sessionid = request.getSession().getId();
        uno = request.getHeader("uno");
        user_agent = request.getHeader("user-agent");

        this.sid = request.getParameter("sid");

        LogService.infoSystemLog("[" + ip + "][" + channel + "] visit the uri [" + uri + "]", false);
    }

    @Override
    public void run() {
        try {
            this.reportPV();
//            this.insertPV();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.run();
    }

//    public void insertPV() throws Exception {
//        MongoDBDao dao = new MongoDBDao("joyme_seq");
//        dao.requestStart();
//        try {
//            int seqId = dao.getSequence("archive_log_id");
//
//
//            // 获得当前的年月日格式的字符串
//            String date = DateUtil.convert2String(System.currentTimeMillis(), DateUtil.DATE_FORMAT);
//            // 查看IP是否存在
//            dao.setCollection("joyme_archive_uv_log_" + getDate());
//            boolean isAddrExisted = isAddrExisted(dao, date);
//
//            // dao.collection.findOne(new BasicDBObject("addr", ip), );
//
//            dao.setCollection("joyme_archive_log_" + getDate());
//            // 放入log
//            Map map = new HashMap();
//            map.put("_id", seqId);
//            map.put("url", uri);
//            map.put("channel", channel);
//            map.put("referer", referer);
//            map.put("create_date", date);
//            map.put("create_date_long", System.currentTimeMillis());
//            map.put("addr", ip);
//            DBObject object = new BasicDBObject(map);
//            dao.collection.insert(object);
//
//            // 统计pv
//            dao.setCollection("joyme_archive_count");
//            map = new HashMap();
//            map.put("channel", channel);
//            map.put("date", date);
//            DBObject query = new BasicDBObject(map);
//
//            DBObject number = new BasicDBObject().append("$inc", new BasicDBObject("count", 1));
//            dao.collection.update(query, number, true, false);
//
//            if (!isAddrExisted) {
//                dao.setCollection("joyme_archive_uv_count");
//                map = new HashMap();
//                map.put("channel", channel);
//                map.put("date", date);
//                query = new BasicDBObject(map);
//                number = new BasicDBObject().append("$inc", new BasicDBObject("count", 1));
//                dao.collection.update(query, number, true, false);
//            }
//        } finally {
//            dao.requestDone();
//        }
//    }

//    private boolean isAddrExisted(MongoDBDao dao, String date) throws ParseException {
////        String beginTime = date + " 00:00:00";
////        String endTime = date + " 23:59:59";
////        Date beginDate = DateUtils.parseDate(beginTime, new String[] { Constants.DATE_WITH_HOUR_STRING });
////        Date endDate = DateUtils.parseDate(endTime, new String[] { Constants.DATE_WITH_HOUR_STRING });
////        BasicDBObject condition = new BasicDBObject();
////        condition.put("create_date", new BasicDBObject("$gt", beginDate).append("$lte", endDate));
////        condition.put("addr", ip);
////        DBObject object = dao.collection.findOne(condition);
////        return object != null;
//        BasicDBObject condition = new BasicDBObject();
//        condition.put("create_date", date);
//        condition.put("addr", ip);
//        condition.put("channel", channel);
//        DBObject object = dao.collection.findOne(condition);
//
//        boolean isExists = object != null;
//        if (!isExists) {
//            dao.collection.insert(condition);
//        }
//
//        return isExists;
//    }

    /**
     * 获得一天的PV和UV的值
     *
     * @param date
     * @return
     * @throws ParseException
     */
//    public List getCount(String date) throws ParseException {
//        MongoDBDao dao = new MongoDBDao("joyme_archive_count");
//        BasicDBObject condition = new BasicDBObject();
//        condition.put("date", date);
//        DBCursor cursor = dao.collection.find(condition);
//        List pvList = new ArrayList();
//        while (cursor.hasNext()) {
//            DBObject object = cursor.next();
//            JoymeCount jc = new JoymeCount();
//            jc.setChannel(object.get("channel").toString());
//            jc.setPvcount((Integer) object.get("count"));
//            jc.setDate(date);
//            pvList.add(jc);
//        }
//
//        dao.setCollection("joyme_archive_uv_count");
//        cursor = dao.collection.find(condition);
//        while (cursor.hasNext()) {
//            DBObject object = cursor.next();
//
//            String channel = object.get("channel").toString();
//            for (Iterator iterator = pvList.iterator(); iterator.hasNext(); ) {
//                JoymeCount jc = (JoymeCount) iterator.next();
//                if (jc.getChannel().equals(channel)) {
//                    jc.setUvcount((Integer) object.get("count"));
//                }
//            }
//        }
//        return pvList;
//    }

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
