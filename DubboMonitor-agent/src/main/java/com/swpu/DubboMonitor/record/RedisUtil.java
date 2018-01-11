package com.swpu.DubboMonitor.record;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil
{
    private static String ADDR = "127.0.0.1";

    // Redis的端口号
    private static int PORT = 6379;

    // 访问密码
    private static String AUTH = "123456";

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;

    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    
    private static Jedis resource = null;
    /**
     * 初始化Redis连接池
     */
    static {
        try {
            // 在高版本的jedis
            // jar包，比如2.8.2，我们在使用中发现使用JedisPoolConfig时，没有setMaxActive和setMaxWait属性了，这是因为高版本中官方废弃了此方法，用以下两个属性替换。
            // maxActive ==> maxTotal
            // maxWait ==> maxWaitMillis
            getJedis();
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     * 
     * @return
     */
    public synchronized static void getJedis() {
        try {
            if (jedisPool != null) {
                if(resource == null){
                    resource = jedisPool.getResource();
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放jedis资源
     * 
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
    
    public static byte [] serialize(Object obj){
        ObjectOutputStream obi=null;
        ByteArrayOutputStream bai=null;
        try {
            bai=new ByteArrayOutputStream();
            obi=new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt=bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //反序列化
    public static Object unserizlize(byte[] byt){
        ObjectInputStream oii=null;
        ByteArrayInputStream bis=null;
        bis=new ByteArrayInputStream(byt);
        try {
            oii=new ObjectInputStream(bis);
            Object obj=oii.readObject();
            return obj;
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return null;
    }
    
    public static Object listLeftPop(String key){
        try{
            byte[] bytes = resource.lpop(key.getBytes());
            Object object = unserizlize(bytes);
            return object;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean exists(String key){
        return resource.exists(key);
    }
    
    public static void expire(String key,int seconds){
        if(resource.exists(key)){
            resource.expire(key, seconds);
        }
    }
    
    public static void hset(String keyRedis,String keyMap,Object obj){
        resource.hset(serialize(keyRedis),serialize(keyMap), serialize(obj));
    }
    public static Object hget(String keyRedis,String keyMap){
        byte[] bytes = resource.hget(serialize(keyRedis),serialize(keyMap));
        if(bytes.length > 0){
            Object object = unserizlize(bytes);
            return object;
        }else{
            return null;
        }
    }
    public static void listRightPush(String key,Object object){
        byte[] bytes = serialize(object);
        resource.rpush(serialize(key), bytes);
    }
}
