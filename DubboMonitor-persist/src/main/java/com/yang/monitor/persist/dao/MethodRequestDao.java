package com.yang.monitor.persist.dao;

import java.util.List;

import com.yang.monitor.persist.entity.MethodRequestEntity;

/**
 * MethodRequestDao
 * @author: yangping
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
