package com.swpu.DubboMonitor.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.swpu.DubboMonitor.core.dto.MethodDTO;
import com.swpu.DubboMonitor.core.util.BeanUtils;
import com.swpu.DubboMonitor.persist.entity.MethodEntity;

public class test
{

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
    {
        // TODO Auto-generated method stub
        MethodEntity methodEntity = new MethodEntity();
        methodEntity.setClassName("ssssss");
        methodEntity.setAppId("sssssdengyu");
        methodEntity.setMethodName("sssssss");
        methodEntity.setId("ssss");
        MethodDTO methodDTO = new MethodDTO();
        //System.out.println(String.class.toString() + "   " +String.class.getClass().toString());
       BeanUtils.copyProperties(methodEntity, methodDTO);
//        System.out.println(String.class.toString());
//        System.out.println(methodDTO.getAppId());
        
        Method getMethod = methodEntity.getClass().getDeclaredMethod("getId");
        Object object = getMethod.invoke(methodEntity);
        //Method set = methodDTO.getClass().getDeclaredMethod("setId", String.class);
        //set.invoke(methodDTO,"dengyu");
        
       // System.out.println(methodDTO.getId());
        
        System.out.println(object.toString());
    }

}
