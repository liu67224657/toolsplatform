package com.enjoyf.mcms.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.enjoyf.mcms.bean.GameBean;
import com.enjoyf.util.DateUtils;
import com.enjoyf.util.StringUtil;
import com.enjoyf.util.URLUtil;

public class GameDownloadService {
    private static URLUtil urlUtil = new URLUtil();
    public final static String DATE_FORMAT = "yyyy年MM月dd日";
    public final static String ANDROID = "android";
    public final static String IPAD = "ipad";
    public final static String IPHONE = "iphone";
    public final static String DEFAULT_CHANNEL = "def";

    public GameBean getGame(String url) throws IOException {
        String gameJson = urlUtil.openURLWithTimeOut(url);
        if (gameJson == null)
            return null;

        JSONObject object = JSONObject.fromObject(gameJson);
        if (object.get("rs") != null & object.getInt("rs") == 0)
            return null;

        // 获得game的详细内容
        JSONObject result = (JSONObject) object.get("result");

        GameBean bean = new GameBean();
        // 名字
        bean.setGameName(this.getJsonValue(result, "gameName"));
        // icon
        bean.setGameIcon(this.getJsonValue(result, "gameIcon"));

        JSONArray array = (JSONArray) result.get("gameDevice");

        bean.setAndroid(array.contains(ANDROID));
        bean.setIpad(array.contains(IPAD));
        bean.setIPhone(array.contains(IPHONE));
        bean.setDisplayPlatform(getDisplayPlatForm(bean));

        // 发行商 TODO
        bean.setFactoryName(this.getJsonValue(result, "gamePublishers"));
        // // 大小
        // bean.setGameSize(this.getJsonValue(result, "gamesize"));

        // 类型
        bean.setGameType(getGameType(result));
        // 评分
        bean.setRate(this.getJsonValue(result, "gameRate"));

        // 更新时间
        String publishTime = this.getJsonValue(result, "gamePublicTime");
        if (publishTime != null) {
            String date = DateUtils.convert2String(Long.parseLong(publishTime), DATE_FORMAT);
            bean.setGamePublishDate(date);
        }

        // androidLink
        JSONObject downloadInfoObject = (JSONObject) result.get("gameDownloadInfo");
        bean.setAndroidDownLink(getDownloadItemMap(downloadInfoObject, ANDROID, "download"));
        bean.setIpadDownLink(getDownloadItemMap(downloadInfoObject, IPAD, "download"));
        bean.setIphoneDownLink(getDownloadItemMap(downloadInfoObject, IPHONE, "download"));

        // 大小
        bean.setAndroidGameSize(getGameSize(downloadInfoObject, ANDROID, "gamesize"));
        bean.setIpadGameSize(getGameSize(downloadInfoObject, IPAD, "gamesize"));
        bean.setIphoneGameSize(getGameSize(downloadInfoObject, IPHONE, "gamesize"));

        bean.setDisplayGameSize(this.getDisplayGameSize(bean));

        //设置地址
        bean.setWikiUrl(this.getJsonValue(result, "wikiUrl"));
        bean.setCmsUrl(this.getJsonValue(result, "cmsUrl"));
        //设置client的地址
        bean.setClientWikiUrl(this.getJsonValue(result, "clientWiki"));
        bean.setClientCmsUrl(this.getJsonValue(result, "clientCms"));

        //设置显示专区或者wiki的地址
        setDisplayWikiCmsUrl(bean);


        //下载推荐
        bean.setDownloadRecommend(this.getJsonValue(result, "downloadRecommend"));

        bean.setGameDbId(this.getJsonValue(result, "gameDbId"));
        return bean;
    }

    /**
     * 设置专区和wiki的url
     *
     * @param bean
     */
    private void setDisplayWikiCmsUrl(GameBean bean) {
        if (StringUtil.isEmpty(bean.getWikiUrl()) && StringUtil.isEmpty(bean.getCmsUrl()))
            bean.setDisplayWikiCmsUrl(null);
        if (!StringUtil.isEmpty(bean.getCmsUrl()))
            bean.setDisplayWikiCmsUrl(bean.getCmsUrl());
        if (!StringUtil.isEmpty(bean.getWikiUrl()))
            bean.setDisplayWikiCmsUrl(bean.getWikiUrl());
    }

    private String getDisplayGameSize(GameBean bean) {
        if (bean.getIphoneGameSize() != null)
            return bean.getIphoneGameSize();
        if (bean.getIpadGameSize() != null)
            return bean.getIpadGameSize();
        if (bean.getAndroidGameSize() != null)
            return bean.getAndroidGameSize();
        return null;
    }

    private String getGameSize(JSONObject downloadInfoObject, String platform, String key) {
        Map androidGameSizeMap = getDownloadItemMap(downloadInfoObject, platform, key);
        if (androidGameSizeMap != null) {
            for (Iterator iterator = androidGameSizeMap.values().iterator(); iterator.hasNext(); ) {
                Object uo = (Object) iterator.next();
                if (uo == null || uo.equals("null"))
                    return null;
                else
                    return (String) uo;
            }
        }
        return null;
    }

    private String getDisplayPlatForm(GameBean bean) {
        String retStr = "";
        if (bean.isAndroid())
            retStr += "android,";
        if (bean.isIpad())
            retStr += "ipad,";
        if (bean.isIPhone())
            retStr += "iphone,";
        return StringUtil.removeLastCharacter(retStr, ",");
    }

    private Map getDownloadItemMap(JSONObject object, String platForm, String key) {
        Map map = new HashMap();
        JSONObject platObject = (JSONObject) object.get(platForm);
        if (platObject != null) {
            for (Iterator iterator = platObject.keys(); iterator.hasNext(); ) {
                String channel = (String) iterator.next();
                JSONObject itemObject = platObject.getJSONObject(channel);
                if (itemObject.get(key) == null || itemObject.get(key).equals("null")) {
                    map.put(channel, null);
                } else {
                    map.put(channel, itemObject.getString(key));
                }
            }
        }
        return map;
    }

    private String getGameType(JSONObject result) {
        JSONArray array = (JSONArray) result.get("gameCategroy");
        String gameTypeStr = "";
        for (int i = 0; i < array.size(); i++) {
            gameTypeStr += array.getString(i) + "/";
        }
        gameTypeStr = StringUtil.removeLastCharacter(gameTypeStr, "/");
        return gameTypeStr;
    }

    public String getJsonValue(JSONObject object, String key) {
        if (object != null && object.has(key) && object.get(key) != null && !object.get(key).equals("null"))
            return object.getString(key);
        return null;
    }

    public String getDownloadLink(GameBean bean, String platform, String channel) {
        Map map = null;
        if (platform.equals(ANDROID))
            map = bean.getAndroidDownLink();
        else if (platform.equals(IPAD))
            map = bean.getIpadDownLink();
        else
            map = bean.getIphoneDownLink();

        channel = channel == null ? DEFAULT_CHANNEL : channel;
        channel = channel.equals("default") ? DEFAULT_CHANNEL : channel;
        channel = channel.equals("json") ? DEFAULT_CHANNEL : channel;
        if (map != null) {
            if (channel.equals(DEFAULT_CHANNEL)) { // 输入为默认的时候
                return getDefaultUrl(map);
            } else { // 查找有没有自己渠道的
                if (map.containsKey(channel) && map.get(channel) != null) { // 如果有，直接返回
                    String url = map.get(channel).toString();
                    if (url == null || url.equals("") || url.equals("null"))
                        return null;
                    else
                        return map.get(DEFAULT_CHANNEL).toString();
                }
                return null;
            }
        } else {
            return null;
        }
    }

    private String getDefaultUrl(Map map) {
        if (map.containsKey(DEFAULT_CHANNEL) && map.get(DEFAULT_CHANNEL) != null) { // 如果有，直接返回
            return map.get(DEFAULT_CHANNEL).toString();
        } else { // 返回第一个
            for (Iterator iterator = map.values().iterator(); iterator.hasNext(); ) {
                Object uo = (Object) iterator.next();
                return (uo == null ? null : (String) uo);
            }
            return null;
        }
    }
}
