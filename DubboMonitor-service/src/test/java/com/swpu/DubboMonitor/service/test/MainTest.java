package com.swpu.DubboMonitor.service.test;

import org.junit.Assert;

import com.swpu.DubboMonitor.service.util.RedisUtil;

public class MainTest
{

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.getJedis();
        redisUtil.hset("123456", "1", "dengyu");
        redisUtil.listRightPush("12345", "dengyu");
        for(int i=0;i<1000;i++){
            String vString = (String)redisUtil.hget("123456", "1");
            System.out.println(vString);
        }
        boolean flag = redisUtil.exists("123456");
        System.out.println(flag);
    }

}
