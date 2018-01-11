package com.swpu.DubboMonitor.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.util.StringUtils;

public class BeanUtils
{
    @SuppressWarnings("rawtypes")
    public static void copyProperties(Object resources,Object target) 
    {
        Field[] fields = resources.getClass().getDeclaredFields();
        String[] resourcesFields = new String[fields.length];
        for(int i=0;i<fields.length;i++){
            resourcesFields[i]=fields[i].getName();
        }
        
        
        Field[] fields1 = target.getClass().getDeclaredFields();
        String[] targetFields = new String[fields1.length];
        for(int i=0;i<fields1.length;i++){
            targetFields[i]=fields1[i].getName();
        }
        try
        {
            for(int i=0 ; i < resourcesFields.length ; i++){
                String type = fields[i].getGenericType().toString();
                for(int j = 0 ; j < targetFields.length ; j++){
                    String typeT = fields1[j].getGenericType().toString();
                    if(type.equals(typeT) && targetFields[j] == resourcesFields[i])
                    {
                        String methodName = changeFirstLetter(targetFields[j]);
                        if(StringUtils.isEmpty(methodName)){
                            continue;
                        }
                        try
                        {
                            Method getMethod = resources.getClass().getDeclaredMethod("get"+methodName);
                            if(getMethod != null){
                                Object object = getMethod.invoke(resources);
                                if(object != null){ 
                                    Method setMethod = target.getClass().getDeclaredMethod("set"+methodName,(Class)fields[i].getGenericType());
                                    setMethod.invoke(target, object);
                                }
                            }
                            break;
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static String changeFirstLetter(String methodName){
        if(!StringUtils.isEmpty(methodName)){
            char letter = methodName.charAt(0);
            if(letter >= 'a' && letter <= 'z'){
                char firString = (char) (letter-32);
                String returnString = String.valueOf(firString) + methodName.subSequence(1, methodName.length());
                return returnString;
            }else{
                return methodName;
            }
        }
        return null;
    }
}
