package com.enjoyf.mcms.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.enjoyf.util.URLUtil;

public class VideoTools {
    private URLUtil util = new URLUtil();

    public String getMP4ViedoUrl(String id) throws JSONException {
        try {
            String s = null;
            String youkuURL = "http://v.youku.com/player/getPlayList/VideoIDS/" + id;
            try {
                s = util.openURLWithTimeOut(youkuURL);
            } catch (Exception e) {
                System.out.println("===Error when visit youku which url is : " + youkuURL);
                return null;
            }

            JSONObject jsobj = JSONObject.fromObject(s);
            JSONArray jsonarr = jsobj.getJSONArray("data");
            if (jsonarr == null || jsonarr.size() == 0) {
                return null;
            }

            JSONObject obj1 = jsonarr.getJSONObject(0);
            // String title = obj1.getString("title");
            double seed = obj1.getDouble("seed");
            JSONObject obj2 = obj1.getJSONObject("streamfileids");
            String mp4id = null;
            // String flvid = null;
            String format = null;
            try {
                mp4id = obj2.getString("mp4");
                format = "mp4";
            } catch (JSONException e) {
                System.out.println("No mp4 format which id is:" + id);
                return null;
                // try {
                // flvid = obj2.getString("flv");
                // format = "flv";
                // } catch (JSONException e1) {
                // System.out.println("没有FLV格式");
                // return "";
                // }
            }
            String realfileid = null;
            // if (format.equals("mp4")) {
            realfileid = getFileID(mp4id, seed);
            // } else {
            // realfileid = getFileID(flvid, seed);
            // }

            String idLeft = realfileid.substring(0, 8);
            String idRight = realfileid.substring(10);

            String sid = genSid();
            JSONObject obj3 = obj1.getJSONObject("segs");
            JSONArray mp4arr = obj3.getJSONArray(format);
            String VideoUrl = "";
            if (mp4arr.size() > 0) {
                JSONObject o = mp4arr.getJSONObject(0);
                String k = o.getString("k");
                String url = "http://f.youku.com/player/getFlvPath/sid/" + sid + "_" + String.format("%1$02X", 0) + "/st/" + format + "/fileid/"
                        + idLeft + String.format("%1$02X", 0) + idRight + "?K=" + k;
                VideoUrl += url;
                return VideoUrl;
            }

            // for (int i = 0; i < mp4arr.size(); i++) {
            // JSONObject o = mp4arr.getJSONObject(i);
            // String k = o.getString("k");
            // String url = "http://f.youku.com/player/getFlvPath/sid/" + sid +
            // "_"
            // + String.format("%1$02X", i) + "/st/" + format + "/fileid/" +
            // idLeft
            // + String.format("%1$02X", i) + idRight + "?K=" + k;
            // System.out.println(url);
            // VideoUrl += url + "#";
            // }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getFileID(String fileid, double seed) {
        String mixed = getFileIDMixString(seed);
        String[] ids = fileid.split("\\*");
        StringBuilder realId = new StringBuilder();
        int idx;
        for (int i = 0; i < ids.length; i++) {
            idx = Integer.parseInt(ids[i]);
            realId.append(mixed.charAt(idx));
        }
        return realId.toString();
    }

    public static String genSid() {
        int i1 = (int) (1000 + Math.floor(Math.random() * 999));
        int i2 = (int) (1000 + Math.floor(Math.random() * 9000));
        return System.currentTimeMillis() + "" + i1 + "" + i2;
    }

    private static String getFileIDMixString(double seed) {
        StringBuilder mixed = new StringBuilder();
        StringBuilder source = new StringBuilder("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/\\:._-1234567890");
        int index, len = source.length();
        for (int i = 0; i < len; ++i) {
            seed = (seed * 211 + 30031) % 65536;
            index = (int) Math.floor(seed / 65536 * source.length());
            mixed.append(source.charAt(index));
            source.deleteCharAt(index);
        }
        return mixed.toString();
    }

    public String getMobile(String value) {
        if (value.indexOf("youku") >= 0 && (value.endsWith(".swf") || value.endsWith(".html"))) {
            if (value.indexOf("v_show") >= 0) {
                return value;
            }

            int position = value.lastIndexOf("/");
            String id = "";
            if (position > 0) {
                id = value.substring(0, position);
                int position1 = id.lastIndexOf("/");
                if (position1 > 0) {
                    id = id.substring(position1 + 1, id.length());
                }

                return id;
            }

            return value;
        } else {
            return value;
        }
    }
}
