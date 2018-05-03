package com.yang.monitor.service.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil
{
    private String ADDR = "192.168.50.142";

    // Redis的端口号
    private int PORT = 6380;

    // 访问密码
    private static String AUTH = "yangpingyang";

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private int MAX_ACTIVE = 1024;

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private int MAX_IDLE = 200;

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private int MAX_WAIT = 10000;

    private int TIMEOUT = 10000;

    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private boolean TEST_ON_BORROW = true;

    private JedisPool jedisPool = null;

    // private Jedis resource = null;

    /**
     * 初始化连接池，并获取Jedis实例
     * 
     * @return
     */
    public void initJedisPool()
    {
        try
        {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Jedis getJedis()
    {
        if (jedisPool != null)
        {
            return jedisPool.getResource();
        }
        return null;
    }

    /**
     * 释放jedis资源
     * 
     * @param jedis
     */
    public void returnResource(Jedis jedis)
    {
        if (jedis != null)
        {
            jedisPool.returnResource(jedis);
        }
    }

    public byte[] serialize(Object obj)
    {
        ObjectOutputStream obi = null;
        ByteArrayOutputStream bai = null;
        try
        {
            bai = new ByteArrayOutputStream();
            obi = new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt = bai.toByteArray();
            return byt;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    // 反序列化
    public Object unserizlize(byte[] byt)
    {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        bis = new ByteArrayInputStream(byt);
        try
        {
            oii = new ObjectInputStream(bis);
            Object obj = oii.readObject();
            return obj;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Object listLeftPop(String key)
    {
        try
        {
            if (!StringUtils.isEmpty(key))
            {
                byte[] bytes;
                Jedis resource = jedisPool.getResource();
                bytes = resource.lpop(serialize(key));
                returnResource(resource);
                if (bytes != null && bytes.length > 0)
                {
                    Object object = unserizlize(bytes);
                    return object;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void listRightPush(String key, Object object)
    {
        if (!StringUtils.isEmpty(key) && object != null)
        {
            byte[] bytes = serialize(object);
            Jedis resource = jedisPool.getResource();
            resource.rpush(serialize(key), bytes);
            returnResource(resource);
        }
    }

    public boolean exists(String key)
    {
        if (!StringUtils.isEmpty(key))
        {
            Jedis resource = jedisPool.getResource();
            boolean flag = resource.exists(serialize(key));
            returnResource(resource);
            return flag;
        }
        return false;
    }

    public void expire(String key, int seconds)
    {
        Jedis resource = jedisPool.getResource();
        if (resource.exists(serialize(key)))
        {
            resource.expire(serialize(key), seconds);
        }
        returnResource(resource);
    }

    public void hset(String keyRedis, String keyMap, Object obj)
    {
        if (!StringUtils.isEmpty(keyRedis) && !StringUtils.isEmpty(keyMap) && obj != null)
        {
            Jedis resource = jedisPool.getResource();
            resource.hset(serialize(keyRedis), serialize(keyMap), serialize(obj));
            returnResource(resource);
        }
    }

    public Object hget(String keyRedis, String keyMap)
    {
        if (!StringUtils.isEmpty(keyRedis) && !StringUtils.isEmpty(keyMap))
        {
            Jedis resource = jedisPool.getResource();
            byte[] bytes = resource.hget(serialize(keyRedis), serialize(keyMap));
            returnResource(resource);
            if (bytes.length > 0)
            {
                Object object = unserizlize(bytes);
                return object;
            }
            else
            {
                return null;
            }
        }
        return null;
    }

    public Long lsize(String key)
    {
        if (!StringUtils.isEmpty(key))
        {
            Jedis resource = jedisPool.getResource();
            Long size = resource.llen(serialize(key));
            returnResource(resource);
            return size;
        }
        return (long) 0;
    }
}
