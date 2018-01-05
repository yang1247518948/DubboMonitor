package com.swpu.DubboMonitor.core;

import java.util.List;
import com.swpu.DubboMonitor.core.dto.MethodRequestDTO;

/**
 * MethodRequestManager
 * @author: zhaoyan  
 * @Date: 2017年11月15日
 */
public interface MethodRequestManager
{
    
    /** 
     * 通过requestId查询整个调用链的方法
     * @param requestId
     * @return MethodRequestDTO的List
     */
    List<MethodRequestDTO> getMethodListByRequestID(String requestId);
    
    
    /** 
     * 通过requestId查询app调用链
     * @param requestId
     * @return MethodRequestDTO的List
     */
    List<MethodRequestDTO> getAppNameListByRequestID(String requestId);
    
}
