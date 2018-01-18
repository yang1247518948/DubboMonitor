package com.swpu.DubboMonitor.service.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 数据清洗的全局变量类
 * 
 * @author dengyu
 */
public class WasherGobal
{

    /** 存放父亲为空时的method的Id */
    public static CopyOnWriteArrayList<String> methodId = new CopyOnWriteArrayList<String>();

    /** 存放父亲为空时的request的traceId和span，通过逗号连接 */
    public static CopyOnWriteArrayList<String> requestId = new CopyOnWriteArrayList<String>();

    /** 从配置文件中读出的字节流 */
    public static InputStream inputStream = null;

    /** 加载配置文件 */
    public static Properties properties = null;

    /** 存放Record的Codis的键 */
    public static String KEY_DLMONTITOR = null;

    /** 开启的线程数量 */
    public static int THREAD_COUNT;

    /** codis缓存库中存放Hash的键过期时间 */
    public static int CACHE_TIME_OUT;

    static
    {
        inputStream = WasherGobal.class.getClassLoader().getResourceAsStream("datawash.properties");
        properties = new Properties();
        try
        {
            properties.load(inputStream);
            KEY_DLMONTITOR = properties.getProperty("KEY_DLMONTITOR");
            THREAD_COUNT = Integer.parseInt(properties.getProperty("THREAD_COUNT"));
            CACHE_TIME_OUT = Integer.parseInt(properties.getProperty("CACHE_TIME_OUT"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
