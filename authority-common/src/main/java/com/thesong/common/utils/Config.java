package com.thesong.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author thesong
 * @Date 2020/7/28 19:56
 * @Version 1.0
 */


public class Config {

    private static Log log = LogFactory.getLog(com.thesong.common.utils.Config.class);

    private static Properties props;

    static {
        readProperties("prop.properties");
    }

    public static void readProperties(String configPath){
        try {
            props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(
                    com.thesong.common.utils.Config.class.getClassLoader().getResourceAsStream(configPath), "UTF-8");
            props.load(inputStream);
            System.out.println(props);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("配置文件读取失败！");
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }


}
