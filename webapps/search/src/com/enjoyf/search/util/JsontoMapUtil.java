package com.enjoyf.search.util;

import com.enjoyf.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhimingli on 2016/11/29 0029.
 */
public class JsontoMapUtil {


    public static Map<String, String> jsontoMap(String jsonString) {
        if (StringUtil.isEmpty(jsonString)) {
            return null;
        }
        Map result = new HashMap();
        try {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            Iterator iterator = jsonObject.keys();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                value = jsonObject.getString(key);
                result.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static void main(String[] args) {
        String str = "{\"entryid\":\"1b0251ccb8bd5f9ccf444e4bda7713e3\",\"id\":\"1224\",\"type\":1,\"title\":\"是先升级阴阳师御灵还,是先升级御魂呢\"}";

        try {
            Map<String, String> map = jsontoMap(str);
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry1 = (Map.Entry<String, String>) iterator.next();
                System.out.println(entry1.getKey() + "<------------->" + entry1.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
