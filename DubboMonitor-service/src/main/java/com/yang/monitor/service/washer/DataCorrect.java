package com.yang.monitor.service.washer;



import com.yang.monitor.core.MethodManager;
import com.yang.monitor.core.RequestManager;
import com.yang.monitor.core.dto.MethodTemp;
import com.yang.monitor.core.dto.RequestTemp;
import com.yang.monitor.service.common.WasherGobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 数据修正函数类
 * @author: yangping
 * @Date:2017年12月7日
 */
@Component
public class DataCorrect {
    @Autowired
    private MethodManager methodManager;
    
    @Autowired
    private RequestManager requestManager;
        
    
    /** 
     * 定时器，用来定时更新数据库中parentId为空的数据
     */
    @Scheduled(fixedDelay = 3000)
    public void taskBegin() {
        if(WasherGobal.methodId.size() != 0) {
            for (String methodId : WasherGobal.methodId) {
                MethodTemp methodTemp=methodManager.getMethodTempById(methodId);
                if(methodTemp != null) {
                    String span=methodTemp.getSpan();
                    String parentSpan = span.substring(0,span.lastIndexOf('.'));
                    String requestId = methodTemp.getRequestId();
                    Map<String , String> result = methodManager.getBySpan(requestId,parentSpan);
                    if(!CollectionUtils.isEmpty(result)) {
                        methodTemp.setParentId(result.get("id"));
                        methodManager.updateMethodParentId(methodTemp);
                        WasherGobal.methodId.remove(methodId);
                    }
                }
            }
        }
        
        if(WasherGobal.requestId.size() != 0) {
            for(String id : WasherGobal.requestId) {
                String[] combineId=id.split(",");
                String requestId= combineId[0];
                String appId = combineId[1];
                RequestTemp requestTemp = requestManager.getByPrimaryKey(requestId,appId);
                if(requestTemp != null) {
                    String span=requestTemp.getSpan().substring(4);
                    String requestId1 = requestTemp.getRequestId();
                    Map<String , String> result = methodManager.getBySpan(requestId1,span);
                    if(!CollectionUtils.isEmpty(result)) {
                        requestTemp.setParentId(result.get("app_id"));
                        requestManager.updateRequestParentId(requestTemp);
                        WasherGobal.requestId.remove(requestId);
                    }
                }
            }
        }
    }
}
