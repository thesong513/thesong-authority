package com.thesong.common.utils;

import lombok.extern.slf4j.Slf4j;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author thesong
 * @Date 2020/7/28 19:56
 * @Version 1.0
 */

@Slf4j
public class Config {

    private static Properties props;

    static {
        readProperties("prop.properties");
    }

    private static void readProperties(String configPath){
        try {
            props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(Config.class.getClassLoader()
                    .getResourceAsStream(configPath), "UTF-8");
            props.load(inputStream);
        } catch (Exception e) {
            log.info("配置文件读取失败！", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }


}
