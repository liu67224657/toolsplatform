package com.enjoyf.wiki.bean.temp;

import net.sf.json.JSONObject;

import java.util.Date;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/7/13
 * Description:
 */
public class WikiRankBean {
    private long id;
    private String wikikey;
    private String text;
    private long time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWikikey() {
        return wikikey;
    }

    public void setWikikey(String wikikey) {
        this.wikikey = wikikey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("wikikey", wikikey);
        jsonObject.put("text", text);
        jsonObject.put("id", id);
        jsonObject.put("time", time);

        return jsonObject.toString();
    }

    public static WikiRankBean getByJson(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);


        if(!jsonObject.containsKey("id") || !jsonObject.containsKey("text") || !jsonObject.containsKey("wikikey")){
            return null;
        }

        long id = jsonObject.getLong("id");
        String text = jsonObject.getString("text");
        long time = 0;
        if (jsonObject.containsKey("time")) {
            time = jsonObject.getLong("time");
        }
        String wikiKey = jsonObject.getString("wikikey");

        WikiRankBean bean=new WikiRankBean();
        bean.setText(text);
        bean.setId(id);
        bean.setTime(time);
        bean.setWikikey(wikiKey);

        return bean;
    }


    @Override
    public String toString() {
        return "WikiRankBean{" +
                "id=" + id +
                ", wikiKey='" + wikikey + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
