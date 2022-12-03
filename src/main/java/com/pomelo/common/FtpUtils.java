package com.pomelo.common;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.pomelo.config.UploadConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

public class FtpUtils {

    public static void sshSftp(String fileName) throws Exception {
        Logger logger = LoggerFactory.getLogger(FtpUtils.class);
        Session session = null;
        Channel channel = null;
        JSch jsch = new JSch();
        if (Integer.parseInt(UploadConfig.getPort()) <= 0) {
            //连接服务器，采用默认端口
            session = jsch.getSession(UploadConfig.getUser(), UploadConfig.getIp());
        } else {
            //采用指定的端口连接服务器
            session = jsch.getSession(UploadConfig.getUser(), UploadConfig.getIp(), Integer.parseInt(UploadConfig.getPort()));
        }
        //如果服务器连接不上，则抛出异常
        if (session == null) {
            logger.error("session is null");
            return ;
        }
        //设置登陆主机的密码
        session.setPassword(UploadConfig.getPassword());//设置密码
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        //设置登陆超时时间
        session.connect(30000);
        logger.info("登录成功！");
        try {
            //创建sftp通信通道
            channel = (Channel) session.openChannel("sftp");
            channel.connect(1000);
            ChannelSftp sftp = (ChannelSftp) channel;
            //以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
            sftp.cd(UploadConfig.getFilePath());
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            String name = StringUtils.substringAfter(file.getName(), "全部Ａ股");
            sftp.put(fileInputStream, name);
            logger.info("上传成功:" + UploadConfig.getFilePath() + name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关流操作
            session.disconnect();
            if (channel != null) {
                channel.disconnect();
            }
        }
    }
}
