package com.enjoyf.webcache.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

/**
 * Created by zhimingli on 2015/3/17.
 */
public abstract class AbstractQuartzCronTrigger implements QuartzCronTrigger {

    private static String JOB_NAME = "default_job";//任务名
    private static String JOB_GROUP_NAME = "default_job_group";//任务组名

    private static String TRIGGER_NAME = "default_trigger";//触发器名
    private static String TRIGGER_GROUP_NAME = "default_trigger_group";//触发器组

    //
    public Scheduler scheduler;

    //
    public AbstractQuartzCronTrigger() throws SchedulerException {
        // Initiate a Schedule Factory
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        // Retrieve a scheduler from schedule factory
        scheduler = schedulerFactory.getScheduler();
    }

    public abstract void init() throws SchedulerException;

    protected void addTriggerJob(String cronExp, Class className) throws SchedulerException {
        //任务名称 任务组名称 任务执行类
        JobDetail jobDetail = new JobDetail(JOB_NAME, JOB_GROUP_NAME, className);

        CronTrigger trigger = new CronTrigger(TRIGGER_NAME, TRIGGER_GROUP_NAME);
        try {
            trigger.setCronExpression(cronExp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // schedule a job with JobDetail and Trigger
        scheduler.scheduleJob(jobDetail, trigger);
    }


    //
    public final void start() throws SchedulerException {
        // start the scheduler
        scheduler.start();
    }
}
