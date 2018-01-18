package com.swpu.DubboMonitor.persist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.swpu.DubboMonitor.persist.dao.RequestDao;
import com.swpu.DubboMonitor.persist.entity.RequestEntity;
import com.google.gson.Gson;

/**
 * <p>
 * 
 * </p>
 * 
 * @Copyright
 * @author zy
 * @Date:2017年11月18日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/spring-mybatis.xml")
public class RequestDaoTest
{

    @Autowired
    private RequestDao requestDao;
/*
    @Test
    public void insert_test()
    {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setAppId("1212");
        requestEntity.setAppName("asas");
        requestEntity.setRequestId("3");
        requestEntity.setRequestMethod("get");
        requestEntity.setStartTime(new Date());
        requestEntity.setUrl("www.baidu.com");
        requestEntity.setUserId("100");
        requestEntity.setRunTime(1000);
        requestEntity.setParentId("12121");
    }

    @Test
    public void deleteByPrimaryKey_test()
    {
        Assert.assertEquals(1, requestDao.deleteByPrimaryKey("3","12"));
    }

    @Test
    public void insertSelective_test()
    {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setAppId("1212");
        requestEntity.setAppName("asas");
        requestEntity.setRequestId("3");
        requestEntity.setRequestMethod("get");
        requestEntity.setStartTime(new Date());
        requestEntity.setUrl("www.baidu.com");
        requestEntity.setUserId("100");
        requestEntity.setRunTime(1000);
        requestEntity.setParentId("12121");
        Assert.assertEquals(1, requestDao.insertSelective(requestEntity));
    }
*/
    @Test
    public void selectByPrimaryKey_test()
    {
        String requestId = "sss52222222222";
        String appId = "1111";
//        RequestEntity requestEntity = requestDao.selectByPrimaryKey(requestId,appId);
//        System.out.println(new Gson().toJson(requestEntity));
    }
/*
    @Test
    public void insertBatch_test()
    {
        List<RequestEntity> list = new ArrayList<>();

        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setAppId("1212");
        requestEntity.setAppName("11111");
        requestEntity.setRequestId("23231");
        requestEntity.setRequestMethod("get");
        requestEntity.setStartTime(new Date());
        requestEntity.setUrl("www.tencent.com");
        requestEntity.setUserId("100");
        requestEntity.setRunTime(1000);
        requestEntity.setParentId("12121");

        RequestEntity requestEntity2 = new RequestEntity();
        requestEntity2.setAppId("1212");
        requestEntity2.setAppName("12121");
        requestEntity2.setRequestId("1111111");
        requestEntity2.setRequestMethod("get");
        requestEntity2.setStartTime(new Date());
        requestEntity2.setUrl("www.tencent.com");
        requestEntity2.setUserId("100");
        requestEntity2.setRunTime(1000);
        requestEntity2.setParentId("12121");

        list.add(requestEntity);
        list.add(requestEntity2);

        Assert.assertNotEquals(0, requestDao.insertBatch(list));
    }

    @Test
    public void queryRequestByParamsOfNullParams_test()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        List<RequestEntity> list = requestDao.queryRequestByParams(map);
        System.out.println(list.size());
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void queryRequestByParamsOfEndTime_test()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("endTime", new Date());
        List<RequestEntity> list = requestDao.queryRequestByParams(map);
        System.out.println(list.size());
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void queryRequestByParamsOfStartTime_test()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);
        date = c.getTime();
        map.put("endTime", date);
        List<RequestEntity> list = requestDao.queryRequestByParams(map);
        System.out.println(list.size());
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void queryRequestByParamsOfRunTime_test()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("runTime", 0);
        List<RequestEntity> list = requestDao.queryRequestByParams(map);
        System.out.println(list.size());
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void queryRequestByParamsOfUrl_test()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", "www.baidu.com");
        List<RequestEntity> list = requestDao.queryRequestByParams(map);
        System.out.println(list.size());
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void queryRequestByParamsOfUserId_test()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", "100");
        List<RequestEntity> list = requestDao.queryRequestByParams(map);
        System.out.println(list.size());
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void countAllRequestsByParams_test()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", 1);
        map.put("count", 15);
        map.put("userId", "100");
        Assert.assertNotEquals(0, requestDao.countAllRequestsByParams(map));
        System.out.println(requestDao.countAllRequestsByParams(map));
    }
    */
}
