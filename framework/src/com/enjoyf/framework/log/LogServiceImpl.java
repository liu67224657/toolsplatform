package com.enjoyf.framework.log;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-9-26
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class LogServiceImpl {
    private Logger pvLogger = Logger.getLogger("pageview");

    /**
     * 上报PV日志
     * @param pvLog
     */
    public void reportLogInfo(PageViewLog pvLog){
        pvLogger.info(pvLog);
    }

    /**
     * 测试用方法
     * @param text
     */
    public void reportLogInfo(String text){
        pvLogger.info(text);
    }

}
