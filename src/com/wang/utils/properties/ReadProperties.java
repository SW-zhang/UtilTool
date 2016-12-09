package com.wang.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取Properties文件的工具类
 *
 */
public class ReadProperties {

    private Properties p = new Properties();

    /**
     * 读取properties配置文件信息
     *
     * @throws IOException
     */
    public Properties loadProperties(String path) throws IOException {
        InputStream in = ReadProperties.class.getClassLoader().getResourceAsStream(path);
        p.load(in);
        return p;
    }

    /**
     * 根据key得到value的值
     *
     * @throws IOException
     */
    public static String getValue(String path, String key) {
        try {
            return new ReadProperties().loadProperties(path).getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}