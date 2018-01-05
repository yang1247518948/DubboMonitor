package com.swpu.DubboMonitor.persist;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.swpu.DubboMonitor.persist.dao.MethodRequestDao;
import com.swpu.DubboMonitor.persist.entity.MethodRequestEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:config/spring-mybatis.xml")
public class MethodRequestDaoTest
{
    @Autowired
    MethodRequestDao methodRequestDao;

    @Test
    public void selectByRequestIDTest()
    {
        String requestId = "s545s6";
        List<MethodRequestEntity> methods = methodRequestDao.selectMethodListByRequestID(requestId);
        for (MethodRequestEntity methodRequestEntity : methods)
        {
            System.out.println(methodRequestEntity.toString());
        }
    }

    @Test
    public void selectMethodByIdTest()
    {
        String id = "10";
        MethodRequestEntity methodEntity = methodRequestDao.selectMethodById(id);
        Assert.assertNotNull(methodEntity);
        System.out.println(methodEntity.toString());
        
    }

}
