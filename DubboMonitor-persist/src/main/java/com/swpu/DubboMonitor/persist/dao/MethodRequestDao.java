package com.swpu.DubboMonitor.persist.dao;

import java.util.List;

import com.swpu.DubboMonitor.persist.entity.MethodRequestEntity;

/**
 * MethodRequestDao
 * @author: zhaoyan  
 * @Date: 2017年11月14日
 */
public interface MethodRequestDao
{
    /** 
     * 通过reuqestId查询一条方法链 
     * @param requestId
     * @return MethodRequestEntity的List
     */
    List<MethodRequestEntity> selectMethodListByRequestID(String requestId);
    
    /** 
     * 通过id查询一条方法数据 
     * @param id
     * @return MethodRequestEntity
     */
    MethodRequestEntity selectMethodById(String id);
}
