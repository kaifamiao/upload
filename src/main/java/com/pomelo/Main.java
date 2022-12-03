package com.pomelo;

import com.pomelo.config.UploadConfig;
import com.pomelo.job.DayDateUpload;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args) throws SchedulerException {
        UploadConfig.init();
        // 启动定时器 定时器执行上传文件的功能
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.clear();
        JobDetail jobDetail = JobBuilder.newJob(DayDateUpload.class)
                .withIdentity("dataUpdate", "dataUpdateGroup").build();
        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(UploadConfig.getCorn());
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("dataUpdateTrigger", "dataUpdateTriggerGroup")
                .startNow()
                .withSchedule(cronSchedule).build();
        scheduler.scheduleJob(jobDetail, trigger);
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("启动定时器...");
        scheduler.start();
    }
}