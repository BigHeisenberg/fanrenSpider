package main;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import utils.SpiderUtils;

public class MainScheduler
{
    public static Scheduler getScheduler()
            throws SchedulerException
    {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }

    public static void schedulerJob()
            throws SchedulerException
    {
        JobDetail jobDetail = JobBuilder.newJob(SpiderUtils.class).withIdentity("job1", "group1").build();//启动类

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group3")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(300).repeatForever())//5分钟启动一次
                .build();
        Scheduler scheduler = getScheduler();

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();
    }

    public static void main(String[] args) throws SchedulerException
    {
        MainScheduler mainScheduler = new MainScheduler();
        mainScheduler.schedulerJob();
    }
}
