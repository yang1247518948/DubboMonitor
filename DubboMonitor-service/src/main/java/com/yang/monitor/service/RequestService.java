package com.yang.monitor.service;

import java.util.List;
import java.util.Map;

import com.yang.monitor.core.dto.RequestDTO;

/**
 * 查询请求的接口
 * @author: yangping
 */
public interface RequestService
{

    /** 
     * 通过主键删除一条请求数据
     * @param requestId
     * @param appId
     * @return true:成功,false:失败
     */
    boolean removeRequestByPrimaryKey(String requestId,String appId);

    /** 
     * 通过参数查询请求数据
     * @param params
     * @return RequestDTO的List
     */
    List<RequestDTO> queryRequestByParams(Map<String, Object> params);
    
    /** 
     * 通过参数统计总共有多少条数据
     * @param params 
     * @return 数据总条数
     */
    int countRequestByParams(Map<String, Object> params);

}
