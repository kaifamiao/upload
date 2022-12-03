package com.pomelo.job;

import com.pomelo.Main;
import com.pomelo.common.FtpUtils;
import com.pomelo.config.UploadConfig;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayDateUpload implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long start = System.currentTimeMillis();
        Date date = new Date();
        String now = new SimpleDateFormat("yyyyMMdd").format(date);
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("上传文件：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        now = UploadConfig.getDirectory() + "全部Ａ股" + now + UploadConfig.getSuffix();
        try {
            FtpUtils.sshSftp(now);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return;
        }
        long end = System.currentTimeMillis();
        logger.info("上传完成，耗时：" + (end - start) + "ms");
    }
}
