package com.enjoyf.webcache.cache;

import com.enjoyf.framework.log.LogService;
import com.enjoyf.util.MD5Util;
import com.enjoyf.util.StringUtil;
import com.enjoyf.webcache.bean.WebCacheClientType;
import com.enjoyf.webcache.bean.WebCacheUrl;
import com.enjoyf.webcache.bean.WebCacheUrlRule;
import com.enjoyf.webcache.container.PropertiesContainer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @Auther: <a mailto="ericliu@straff.joyme.com">ericliu</a>
 * Create time: 15/8/26
 * Description:todo 这么难看的代码不是我写的.如果让我写我重构整个功能,一个这么简单的功能写的这么费劲.闹呢!
 */
@Deprecated
public class PageStatCache {


    private static final String PAGE_STAT_CACHE = "page_stat_pv_";
    //临时flag,在cachejob启动时候更新,cachejob结束后将flag写入到CACHE_FLAG中,计算结果写入到该flag对应的key中
    private static final String PIWIK_JOB_FLAG = "piwik_job_flag";
    //cache的flag,在cachejob结束后,将PIWIK_JOB_FLAG对应的flag写入到这个key中,获取结果用这个flag对应的key,保证获取数据的稳定
    private static final String CACHE_FLAG = "page_stat_pv_flag";
    //每天的DBflag
    private static final String PIWIK_DB_JOB_FLAG = "piwik_db_job_flag";
    private static final String PAGE_STAT_CACHES_POOL = "page_stat_pool_";

    public void putPageStatCachePool(String valueJson, String dateStr) {
        PropertiesContainer.getInstance().getRedisManager().sadd(PAGE_STAT_CACHES_POOL + dateStr, valueJson);
    }

    public String getPageStatCachePool(String dateStr) {
        return PropertiesContainer.getInstance().getRedisManager().spop(PAGE_STAT_CACHES_POOL + dateStr);
    }

    /**
     * 更新dbjob的key
     * 更新cachejob中的临时key
     */
    public static void putPiwikJobTimeFlag(){
        Date date = new Date();
        long flag = date.getTime();
        LogService.infoSystemLog("====piwikJob flag:" + flag, true);
        PropertiesContainer.getInstance().getRedisManager().set(PIWIK_JOB_FLAG, String.valueOf(flag));
        //
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        PropertiesContainer.getInstance().getRedisManager().set(PIWIK_DB_JOB_FLAG + df.format(date), String.valueOf(flag));
        LogService.infoSystemLog("====piwikDbJob "+df.format(date)+" flag:" + flag, true);
    }

    public static void putCacheTimeFlag(){
        String flag = getPiwikJobTimeFlag();
        LogService.infoSystemLog("====piwikJob flag:" + flag, true);
        PropertiesContainer.getInstance().getRedisManager().set(CACHE_FLAG, flag);
    }

    /**
     * 获取cachejob启动时候的flag,
     * @return
     */
    private static String getPiwikJobTimeFlag(){
        return PropertiesContainer.getInstance().getRedisManager().get(PIWIK_JOB_FLAG);
    }

    /**
     *
     * 获取当天piwik缓存结果的flag,
     * @return
     */
    private static String getCacheTimeFlag(){
        return PropertiesContainer.getInstance().getRedisManager().get(CACHE_FLAG);
    }

    /**
     * 通过cache对应的flag拼接的key获取统计结果
     * @param statId
     * @param clientType
     * @return
     */
    public int getPageStatCache(String statId, WebCacheClientType clientType) {
        String flag = getCacheTimeFlag();
        String num = PropertiesContainer.getInstance().getRedisManager().get(PAGE_STAT_CACHE + flag + "_" + statId + "_" + clientType.getCode());
        if (StringUtil.isEmpty(num)) {
            return 0;
        }
        return Integer.valueOf(num);
    }

    /**
     * 写入时候将统计结果写入到,cachejob的临时flag拼接的key中.
     * @param statId
     * @param clientType
     * @param pvNum
     */
    public void incrPageStatCache(String statId, WebCacheClientType clientType, int pvNum) {
        try {
            String flag = getPiwikJobTimeFlag();
            PropertiesContainer.getInstance().getRedisManager().incr(PAGE_STAT_CACHE + flag + "_" + statId + "_" + clientType.getCode(), Long.valueOf(pvNum), 7*24*60*60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long removePageStatCache(String statId, WebCacheClientType clientType){
        try {
            String flag = getPiwikJobTimeFlag();
            return PropertiesContainer.getInstance().getRedisManager().remove(PAGE_STAT_CACHE + flag + "_" + statId + "_" + clientType.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return 0l;
        }
    }
    
    //wiki数字站的统计用这个
    public void putPageStatCache(String statId, WebCacheClientType clientType, int pvNum, String dateStr) {
        PropertiesContainer.getInstance().getRedisManager().set(PAGE_STAT_CACHE + dateStr + "_" + statId + "_" + clientType.getCode(), String.valueOf(pvNum));
    }

    public long lengthCachePool(String dateStr) {
        return PropertiesContainer.getInstance().getRedisManager().scard(PAGE_STAT_CACHES_POOL + dateStr);
    }

    public int getInsertDbCache(String statId, WebCacheClientType clientType) {
        //前天最后一次
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        java.util.Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date date = calendar.getTime();
        String flag = PropertiesContainer.getInstance().getRedisManager().get(PIWIK_DB_JOB_FLAG + df.format(date));
        LogService.infoSystemLog("====piwikDbJob insert "+df.format(date)+" flag:" + flag + "==" + df.format(new Date()), true);

        String num = PropertiesContainer.getInstance().getRedisManager().get(PAGE_STAT_CACHE + flag + "_" + statId + "_" + clientType.getCode());
        if (StringUtil.isEmpty(num)) {
            return 0;
        }
        return Integer.valueOf(num);
    }
}
