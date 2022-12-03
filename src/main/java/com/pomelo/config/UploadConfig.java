package com.pomelo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UploadConfig {

    private static String directory;
    private static String corn;
    private static String suffix;
    private static String ip;
    private static String user;
    private static String password;
    private static String port;
    private static String filePath;

    public static String getDirectory() {
        return directory;
    }

    public static String getCorn() {
        return corn;
    }

    public static String getSuffix() {
        return suffix;
    }

    public static String getIp() {
        return ip;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static String getPort() {
        return port;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void init() {
        InputStream resource = UploadConfig.class.getClassLoader().getResourceAsStream("application.properties");
        Logger logger = LoggerFactory.getLogger(UploadConfig.class);
        Properties properties = new Properties();
        try {
            properties.load(resource);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        UploadConfig.directory =  properties.getProperty("directory");
        UploadConfig.corn = properties.getProperty("corn");
        UploadConfig.suffix = properties.getProperty("suffix");
        UploadConfig.ip = properties.getProperty("ip");
        UploadConfig.user = properties.getProperty("user");
        UploadConfig.password = properties.getProperty("password");
        UploadConfig.port = properties.getProperty("port");
        UploadConfig.filePath = properties.getProperty("filePath");
        logger.error("配置加载完毕");
    }
}
