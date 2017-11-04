package com.enjoyf.framework.log;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: ericliu
 * Date: 14-9-26
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public interface ILogService {
    /**
     * 上报PV日志
     * @param pvLog
     */
    public void reportLogInfo(PageViewLog pvLog);

}
