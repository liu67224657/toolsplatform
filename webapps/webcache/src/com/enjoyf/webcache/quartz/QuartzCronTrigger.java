package com.enjoyf.webcache.quartz;

import org.quartz.SchedulerException;

/**
 * Created by zhimingli on 2015/3/17.
 */
public interface QuartzCronTrigger {
    //
    public void init() throws SchedulerException;

    //
    public void start() throws SchedulerException;
}
