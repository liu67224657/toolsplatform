package com.enjoyf.mcms.facade;

import javax.servlet.http.HttpServletRequest;

import com.enjoyf.mcms.container.MongoDBContainer;
import com.enjoyf.mcms.container.PVContainer;
import com.enjoyf.mcms.service.ChannelService;
import com.enjoyf.mcms.util.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

@Deprecated
public class PVFacade extends Thread {
//    private static JoymePvService service = new JoymePvService();
//    private static BaseJDBCDAO dao = new BaseJDBCDAOImpl();
    private static DBCollection visitLogCollection = null;
    
    public void insertPV(HttpServletRequest request) throws Exception {
        String channel = ChannelService.getChannel(request);
        String date = DateUtil.convert2String(System.currentTimeMillis(), DateUtil.DATE_FORMAT);
        String key = channel + "__" + date;

        addCount(key);

        String context = "Channel :" + channel + " has a pv, which url is :" + request.getRequestURI();
        String referer = request.getHeader("referer");
        if (referer != null && referer.indexOf("?m=") > 0) {
            key = "360_" + key;
            addCount(key);
            context += " and it comes from 360";
        }
        if(visitLogCollection == null){
            visitLogCollection = MongoDBContainer.getCollection("article_visit_log");
            BasicDBObject document = new BasicDBObject();
            document.put("channel", channel);
            document.put("date", date);
//            document.put("refer", val);
        }
        System.out.println(context);
    }

    private void addCount(String key) {
        long count = 0l;
        if (PVContainer.pvMap.get(key) != null) {
            count = (Long) PVContainer.pvMap.get(key);
        }
        PVContainer.pvMap.put(key, count + 1);
    }

    @Override
    public void run() {
        try {
            while (true) {
                insertPV();
                Thread.sleep(30 * 1000);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        super.run();
    }

    // public boolean insertPV() throws Exception {
    // Connection conn = null;
    // try {
    // conn = dao.getConnection();
    // Set entrySet = PVContainer.pvMap.entrySet();
    // for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
    // Entry entry = (Entry) iterator.next();
    // String key = entry.getKey().toString();
    // long value = (Long) entry.getValue();
    //
    // String[] strs = key.split("__");
    // String channel = strs[0];
    // String date = strs[1];
    // JoymePv pv = service.queryJoymePv(conn, channel, date);
    //
    // if(pv == null){
    // pv = new JoymePv();
    // pv.setJoymePvChannel(channel);
    // pv.setJoymePvCount(value);
    // pv.setJoymePvDate(new Date(System.currentTimeMillis()));
    // service.insertJoymePv(conn, pv);
    // } else {
    // pv.setJoymePvCount(pv.getJoymePvCount() + value);
    // service.updateJoymePv(conn, pv);
    // }
    // }
    // return true;
    // } catch (Exception e){
    // e.printStackTrace();
    // return false;
    // }
    // finally {
    // dao.closeConnection(conn);
    // PVContainer.pvMap.clear();
    // }
    // }

    public boolean insertPV() throws Exception {
        
        
        
        return false;
    }
}
