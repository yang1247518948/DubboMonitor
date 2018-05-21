package com.yang.monitor.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.yang.monitor.core.dto.MethodRequestDTO;
import com.yang.monitor.core.dto.MethodTemp;
import com.yang.monitor.core.dto.RequestTemp;
import com.yang.monitor.persist.entity.MethodRequestEntity;
import com.yang.monitor.record.Record;
import org.springframework.beans.BeanUtils;


/**
 * DTO和Entity的转换工具类
 * @author: yangping
 */
public class TransferUtil
{

    /** 
     * 将MethodRequestEntity转换成MethodRequestDTO(方法链)
     * @param methodRequestEntity
     * @param methodRequestDTO
     * @return MethodRequestDTO
     */
    public static MethodRequestDTO transferMethodRequest(MethodRequestEntity methodRequestEntity,
                                                         MethodRequestDTO methodRequestDTO)
    {
        
        methodRequestDTO.setId(methodRequestEntity.getId());
        methodRequestDTO.setpId((methodRequestEntity.getParentId()));
        String name = "MethodName:" + methodRequestEntity.getMethodName() + ";\r\n ClassName:"
                      + methodRequestEntity.getClassName() + ";\r\n AppName:"
                      + methodRequestEntity.getAppName() + ";\r\n RpcUse:"
                      + ("0".equals(methodRequestEntity.getRpcUse()) ? "false" : "true")
                      + ";\r\n RunTime:" + (methodRequestEntity.getRunTime() < 1 ? "<1" : methodRequestEntity.getRunTime()) + "ms;\r\n StartTime:"
                      + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(methodRequestEntity.getStartTime()) + ";\r\n ThreadNum:"
                      + methodRequestEntity.getThreadNum();
        methodRequestDTO.setName(name);
        methodRequestDTO.setOpen("false");
        String iconSkin = ColorContainer.getColors(methodRequestEntity.getAppName());
        if(methodRequestDTO.getpId()==null){
            methodRequestDTO.setIconSkin(iconSkin.replace("i", "pI"));
        }else{
            methodRequestDTO.setIconSkin(iconSkin);
        }
        return methodRequestDTO;
    }
    
    /** 
     * 将MethodRequestEntity转换成MethodRequestDTO(App链)
     * @param methodRequestEntity
     * @param methodRequestDTO
     * @return MethodRequestDTO
     */
    public static MethodRequestDTO transferApp(MethodRequestEntity methodRequestEntity,
                                                         MethodRequestDTO methodRequestDTO)
    {
        
        methodRequestDTO.setId(methodRequestEntity.getId());
        methodRequestDTO.setpId((methodRequestEntity.getParentId()));
        String name = "AppName:" + methodRequestEntity.getAppName();
        methodRequestDTO.setName(name);
        methodRequestDTO.setOpen("true");
        String iconSkin = ColorContainer.getColors(methodRequestEntity.getAppName());
        if(methodRequestDTO.getpId()==null){
            methodRequestDTO.setIconSkin(iconSkin.replace("i", "pI"));
        }else{
            methodRequestDTO.setIconSkin(iconSkin);
        }
        return methodRequestDTO;
    }
    
    /** 
     * 将Record转换成MethodTemp
     * @param record
     * @return MethodTemp
     */
    public static MethodTemp recordToMethodTemp(Record record)
    {
        MethodTemp methodTemp=new MethodTemp();
        BeanUtils.copyProperties(record, methodTemp);
        methodTemp.setId(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
        methodTemp.setRequestId(record.getTraceID());
        methodTemp.setRpcUse((record.isRpcUse()==true)?"1":"0");
        if(record.getResult()!=null){
            methodTemp.setResult(record.getResult().toString());
        }
        methodTemp.setAppId(record.getAppID());
        return methodTemp;
    }
    /** 
     * 将Record转换成MethodTemp
     * @param methodTemp
     * @param record
     * @return MethodTemp
     */
    public static MethodTemp transferMethodTemp(MethodTemp methodTemp,Record record){
        int runTime=(int)Math.abs(methodTemp.getTime()-record.getTime());
        methodTemp.setRunTime(runTime);
        long startTime=methodTemp.getTime() >= record.getTime() ? record.getTime() : methodTemp.getTime();
        Date date=new Date(startTime);
        methodTemp.setStartTime(date);
        return methodTemp;
    }
    
    /** 
     * 将Record转换成RequestTemp
     * @param record
     * @return RequestTemp
     */
    public static RequestTemp recordToRequesTemp(Record record){
        RequestTemp requestTemp=new RequestTemp();
        BeanUtils.copyProperties(record, requestTemp);
        requestTemp.setRequestId(record.getTraceID());
        requestTemp.setAppId(record.getAppID());
        requestTemp.setAppName(record.getAppName());
        if(record.isUseFlag()){
            requestTemp.setUrl(record.getClassName());
            requestTemp.setRequestMethod(record.getThreadNum());
        }
        return requestTemp;
    }
    
    /** 
     * 将Record转换成RequestTemp
     * @param requestTemp
     * @param record
     * @return RequestTemp
     */
    public static RequestTemp transferRequestTemp(RequestTemp requestTemp,Record record){
        if(!requestTemp.getUseFlag()){
            requestTemp.setUrl(record.getClassName());
            requestTemp.setRequestMethod(record.getThreadNum());
        }
        int runTime=(int)Math.abs(requestTemp.getTime()-record.getTime());
        requestTemp.setRunTime(runTime);
        long startTime=requestTemp.getTime() >= record.getTime() ? record.getTime() : requestTemp.getTime();
        Date date=new Date(startTime);
        requestTemp.setStartTime(date);
        return requestTemp;
    }
}
