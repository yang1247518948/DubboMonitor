package com.swpu.DubboMonitor.service.washer;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.swpu.DubboMonitor.core.MethodManager;
import com.swpu.DubboMonitor.core.RequestManager;
import com.swpu.DubboMonitor.core.dto.MethodTemp;
import com.swpu.DubboMonitor.core.dto.RequestTemp;
import com.swpu.DubboMonitor.service.common.WasherGobal;

/**
 * 数据修正函数类
 * @author: dengyu
 */
@Component
public class DataCorrect 
{
    @Autowired
    private MethodManager methodManager;
    
    @Autowired
    private RequestManager requestManager;
        
    
    /** 
     * 定时器，用来定时更新数据库中parentId为空的数据
     * @param fixedDelay=3000  每3秒运行一次这个函数(从上一次运行结束后开始计时)
     */
    @Scheduled(fixedDelay = 3000)
    public void taskBegin()
    {
        if(WasherGobal.methodId.size() != 0)
        {
            for (String methodId : WasherGobal.methodId)
            {
                MethodTemp methodTemp=methodManager.getMethodTempById(methodId);
                if(methodTemp != null)
                {
                    String span=methodTemp.getSpan();
                    String parentSpan = span.substring(0,span.lastIndexOf('.'));
                    String requestId = methodTemp.getRequestId();
                    Map<String , String> result = methodManager.getBySpan(requestId,parentSpan);
                    if(!CollectionUtils.isEmpty(result))
                    {
                        methodTemp.setParentId(result.get("id"));
                        methodManager.updateMethodParentId(methodTemp);
                        WasherGobal.methodId.remove(methodId);
                    }
                }
            }
        }
        
        if(WasherGobal.requestId.size() != 0)
        {
            for(String id : WasherGobal.requestId)
            {
                String[] combineId=id.split(",");
                String requestId= combineId[0];
                String appId = combineId[1];
                RequestTemp requestTemp = requestManager.getByPrimaryKey(requestId,appId);
                if(requestTemp != null)
                {
                    String span=requestTemp.getSpan().substring(4);
                    String requestId1 = requestTemp.getRequestId();
                    Map<String , String> result = methodManager.getBySpan(requestId1,span);
                    if(!CollectionUtils.isEmpty(result))
                    {
                        requestTemp.setParentId(result.get("app_id"));
                        requestManager.updateRequestParentId(requestTemp);
                        WasherGobal.requestId.remove(requestId);
                    }
                }
            }
        }
    }
}
