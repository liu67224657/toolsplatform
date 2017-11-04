package test.enjoyf.mcms;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: zhimingli
 * Date: 14-7-24
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
public class UpdateMongoJoymeArchiveLog {
	private static Mongo mongo = null;
	private static DB db = null;
	private static DBCollection joymewikilog = null;

	public static void main(String[] args) {
		//dev 172.16.75.65:4066
		//prod 192.168.30.21:15021
		try {
			mongo = new Mongo("192.168.30.21:15021");
			db = mongo.getDB("joyme_wiki_count");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		joymewikilog = db.getCollection("joyme_wiki_log_201501");
		getCms(joymewikilog);

	}

	private static void updateMongoJoymeArchiveLog(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat simpleDateFormatshort = new SimpleDateFormat("yyyy-MM-dd");
		DBCursor dbCursor = joymewikilog.find();
		long start = System.currentTimeMillis();
		System.out.println(dbCursor.size());
		int i = 0;
		while (dbCursor.hasNext()) {
			i++;
			DBObject dbObject = dbCursor.next();
			long create_date_long = 0;
			try {
				String create_date = simpleDateFormat.format(dbObject.get("create_date"));
				String create_date_short = simpleDateFormatshort.format(dbObject.get("create_date"));
				create_date_long = simpleDateFormat.parse(create_date).getTime();
				//	System.out.println(create_date + "__" + create_date_long + "__" + _id);
				DBObject allCondition = new BasicDBObject();
				allCondition.put("$inc", new BasicDBObject("create_date_long", create_date_long));
				allCondition.put("$set", new BasicDBObject("create_date", create_date_short));
				joymewikilog.update(new BasicDBObject("_id", dbObject.get("_id")), allCondition, true, false);
			} catch (Exception e) {
				System.out.println(dbObject);
			}
			System.out.println(i);
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000);

//		DBObject dbObject = joymewikilog.findOne(new BasicDBObject("_id", 48785393));
//		try {
//			System.out.println(dbObject);
////			String create_date = simpleDateFormat.format(dbObject.get("create_date"));
////			String create_date_short = simpleDateFormatshort.format(dbObject.get("create_date"));
////			long create_date_long = simpleDateFormat.parse(create_date).getTime();
////			DBObject allCondition=new BasicDBObject();
////			allCondition.put("$inc", new BasicDBObject("create_date_long", create_date_long));
////			allCondition.put("$set", new BasicDBObject("create_date", create_date_short));
//		//	joymewikilog.update(new BasicDBObject("_id", dbObject.get("_id")), allCondition, true, false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private static void getCms(DBCollection joymewikilog){

		BasicDBObject basicDBObject =new BasicDBObject();
		basicDBObject.put("sid","sgss");
		basicDBObject.put("create_date","2015-01-05");
		DBCursor cursor = joymewikilog.find(basicDBObject);
		System.out.println(cursor.size());
	}
}
