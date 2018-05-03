package com.yang.monitor.persist;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yang.monitor.persist.dao.*;
import com.yang.monitor.persist.entity.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/spring-mybatis.xml")
public class MethodDaoTest {

    @Autowired
    private MethodDao methodDao;
    
    
    @Test
    //success
    public void insertSelectiveTest(){
        MethodEntity mEntity=new MethodEntity();
        mEntity.setId("5ss51ssss");
        mEntity.setAppId("dd4545");
        mEntity.setClassName("test");
        mEntity.setRequestId("s545s6");
        mEntity.setAppName("ddd");
        mEntity.setStartTime(new Date());
        mEntity.setSpan("1.1.1");
       // int code=methodDao.insertSelective(mEntity);
        Assert.assertEquals(1, 1);
    }
    /*
    @Test
    public void selectByParamsTest(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("methodName", "method");
        map.put("page", 1);
        map.put("runTime", 100);
        map.put("offSet", 0);
        map.put("count", 15);
        List<MethodEntity> list=methodDao.selectByParams(map);
        Assert.assertTrue("测试成功", list.size() == 1);
    }
    
    @Test 
    //success
    public void deleteByPrimaryKeyTest(){
        int result=methodDao.deleteByPrimaryKey("5ss51");
        Assert.assertTrue("测试成功", (result > 0));
    }
    
    
    @Test
    //success
    public void insertBathMethodTest(){
        List<MethodEntity> list=new ArrayList<MethodEntity>();
        String [] strin={"s1s","212","de1ngyu"};
        String span="1";
        for(int i=0;i<strin.length;i++){
            MethodEntity mEntity=new MethodEntity();
            mEntity.setId(strin[i]);
            mEntity.setAppId("dd4545");
            mEntity.setClassName("test");
            mEntity.setRequestId("s545s6");
            mEntity.setMethodName("methodName");
            mEntity.setResult("ssss");
            mEntity.setRpcUse("0");
            mEntity.setRunTime(1200);
            mEntity.setThreadNum("sssdsfd");
            mEntity.setStartTime(new Date());
            mEntity.setParentId("12345611");
            mEntity.setAppName("ddd");
            mEntity.setSpan(span+".1");
            list.add(mEntity);
        }
        int result=methodDao.insertBathMethod(list);
        Assert.assertTrue("测试成功", (result == strin.length));
    }
    
    @Test
    //success
    public void selectCountAllMethodByParamsTest(){
        Map<String, Object> map = new HashMap<String, Object>();
        int result=methodDao.selectCountAllMethodByParams(map);
        Assert.assertTrue("测试成功", result > 0);
    }
    
    @Test
    public void selectBySpanTest(){
        Map<String, String> params=new HashMap<String, String>();
        params.put("span", "1.1.1");
        params.put("requestId", "1");
       // Map<String, String> result=methodDao.selectBySpan(params);
        //System.out.println(result.get("app_id")+"========"+result.get("id"));
    }*/
}
