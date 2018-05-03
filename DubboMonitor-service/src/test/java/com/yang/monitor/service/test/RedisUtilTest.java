package com.yang.monitor.service.test;

import org.junit.Assert;
import org.junit.Test;

import com.yang.monitor.service.util.RedisUtil;



public class RedisUtilTest
{
    @Test
    public void redisTest(){
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.initJedisPool();
        redisUtil.hset("123456", "1", "yangping");
        redisUtil.listRightPush("12345", "yangping");
        String vString = (String)redisUtil.hget("123456", "1");
        boolean flag = redisUtil.exists("123456");
        Assert.assertEquals("yangping", vString);
    }
}
