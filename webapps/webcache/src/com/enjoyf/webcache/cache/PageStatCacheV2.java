package com.enjoyf.webcache.cache;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.bean.WebCacheClientType;
import com.enjoyf.webcache.container.PropertiesContainer;
import com.enjoyf.webcache.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/8/26
 * Description:
 */
public class PageStatCacheV2 {


    public static void main(String[] args) {

    }

    private static final String PAGE_STAT_CACHE = "page_stat_pv_";

    private static final String PAGE_STAT_CACHES_POOL = "page_stat_pool_";

    public void putPageStatCachePool(String valueJson, String dateStr) {
        PropertiesContainer.getInstance().getRedisManager().sadd(PAGE_STAT_CACHES_POOL + dateStr, valueJson);
    }

    public String getPageStatCachePool(String dateStr) {
        return PropertiesContainer.getInstance().getRedisManager().spop(PAGE_STAT_CACHES_POOL + dateStr);
    }

    public long lengthCachePool(String dateStr) {
        return PropertiesContainer.getInstance().getRedisManager().scard(PAGE_STAT_CACHES_POOL + dateStr);
    }



    /**
     * 写入时候将统计结果写入到,cachejob的临时flag拼接的key中.
     *
     * @param statId
     * @param clientType
     * @param pvNum
     */
    public void setPageStatCacheByTimeOut(String statId, Date date, WebCacheClientType clientType, int pvNum) {
        try {
            PropertiesContainer.getInstance().getRedisManager().set(getCacheKey(statId,date,clientType), String.valueOf(pvNum), 7*24 * 60 * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //wiki数字站的统计用这个
    public void putPageStatCache(String statId,Date date, WebCacheClientType clientType, int pvNum) {
        PropertiesContainer.getInstance().getRedisManager().set(getCacheKey(statId,date,clientType), String.valueOf(pvNum));
    }

    public int getInsertDbCache(String statId, Date date, WebCacheClientType clientType) {
        //前天最后一次
        String num = PropertiesContainer.getInstance().getRedisManager().get(getCacheKey(statId,date,clientType));
        if (StringUtil.isEmpty(num)) {
            return 0;
        }
        return Integer.valueOf(num);
    }

    private String getCacheKey(String statId, Date date, WebCacheClientType clientType) {
        String dateString=DateUtil.convert2String(date.getTime(), DateUtil.DATE_FORMAT_SHORT);
        return PAGE_STAT_CACHE + dateString + "_" + statId + "_" + clientType.getCode();
    }
}
