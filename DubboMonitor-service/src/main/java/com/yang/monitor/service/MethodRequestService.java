package com.yang.monitor.service;

import java.util.List;
import com.yang.monitor.core.dto.MethodRequestDTO;

/**
 * 查询方法链的接口
 * @author: yangping
 */
public interface MethodRequestService
{
    
    /** 
     * 通过requestId获取一条方法链
     * @param requestId
     * @return MethodRequestDTO的List
     */
    List<MethodRequestDTO> getMethodListByRequestID(String requestId);
    
    /** 
     * 通过requestId获取一条App链
     * @param requestId
     * @return MethodRequestDTO的List
     */
    List<MethodRequestDTO> getAppNameListByRequestID(String requestId);
}