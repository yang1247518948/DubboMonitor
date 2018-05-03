package com.yang.monitor.core;

import java.util.List;
import com.yang.monitor.core.dto.MethodRequestDTO;

/**
 * MethodRequestManager
 * @author: yangping
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
