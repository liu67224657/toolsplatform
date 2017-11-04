package com.enjoyf.wiki.service;

import com.enjoyf.framework.jdbc.exception.JoymeDBException;
import com.enjoyf.framework.jdbc.exception.JoymeServiceException;
import com.enjoyf.wiki.bean.WikiAdvertise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto="EricLiu@staff.joyme.com">Eric Liu</a>
 * Create time:  13-8-23 上午11:41
 * Description:
 */
public class AdvertiseCacheService {

    private WikiAdvertiseService wikiAdvertiseService = new WikiAdvertiseService();

    private static Map<String, WikiAdvertise> advertiseCacheMap = new HashMap<String, WikiAdvertise>();


    public void initAdvertiseCacheMap() throws JoymeServiceException, JoymeDBException {

        List<WikiAdvertise> wikiAdvertiseList = wikiAdvertiseService.queryWikiAdvertise(null);

        for (WikiAdvertise wikiAdvertise : wikiAdvertiseList) {
            advertiseCacheMap.put(generatorMapKey(wikiAdvertise.getWikiAdvertiseKey(), wikiAdvertise.getWikiAdvertiseContextPath()), wikiAdvertise);
        }
    }

    public WikiAdvertise getWikiAdvertise(String key, String contextPath) {
        String mapKey = generatorMapKey(key, contextPath);

        WikiAdvertise wikiAdvertise = advertiseCacheMap.get(mapKey);
        if (wikiAdvertise != null) {
            return wikiAdvertise;
        }

        try {
            List<WikiAdvertise> list = wikiAdvertiseService.queryWikiAdvertise(null, key, contextPath);

            if (list == null || list.size() == 0) {
                return wikiAdvertise;
            }

            wikiAdvertise = list.get(0);
            advertiseCacheMap.put(mapKey, wikiAdvertise);
        } catch (JoymeDBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JoymeServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return wikiAdvertise;
    }

    public boolean createAdvertise(WikiAdvertise wikiAdvertise) throws JoymeServiceException, JoymeDBException {
        boolean success = false;
        try {
            success = this.deleteAdvertise(wikiAdvertise.getWikiAdvertiseKey(), wikiAdvertise.getWikiAdvertiseContextPath());

            success = wikiAdvertiseService.insertWikiAdvertise(null, wikiAdvertise) > 0;

            advertiseCacheMap.put(generatorMapKey(wikiAdvertise.getWikiAdvertiseKey(), wikiAdvertise.getWikiAdvertiseContextPath()), wikiAdvertise);
        } catch (JoymeServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JoymeDBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return success;
    }

    public boolean deleteAdvertise(String key, String wikiContextPath) throws JoymeServiceException, JoymeDBException {
        boolean success = wikiAdvertiseService.deleteWikiAdvertise(null, key, wikiContextPath) > 0;
        advertiseCacheMap.remove(generatorMapKey(key, wikiContextPath));
        return success;
    }

    private String generatorMapKey(String key, String wikiContextPath) {
        return key + "_" + wikiContextPath;
    }


}
