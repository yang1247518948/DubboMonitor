package com.yang.monitor.core;

import java.util.List;
import java.util.Map;

import com.yang.monitor.core.dto.RequestDTO;
import com.yang.monitor.core.dto.RequestTemp;
import com.yang.monitor.persist.entity.RequestEntity;

/**
 * RequestManager
 * @author: yangping
 */
public interface RequestManager
{

    /**
     * @param requestId
     * @param appId
     * @return 删除数据的条数
     */
    int deleteByPrimaryKey(String requestId, String appId);

    /** 
     * 向数据库存储一条请求信息(残缺)
     * @param record
     * @return 插入数据的条数
     */
    int insertSelective(RequestEntity record);

    /**
     * 根据请求Id查询请求信息
     * @param: requestId 请求Id
     * @return: RequestTemp
     */
    RequestTemp getByPrimaryKey(String requestId, String appId);

    /**
     * 批量创建请求
     * @param:  reuqetInfoEntity
     * @return: int 成功插入请求的记录数
     */
    int insertBatch(List<RequestEntity> requestInfoEntityList);

    /**
     * 多条件查询请求信息
     * @param: 以下为查询参数中map的key的说明，多个参数为并且的关系
     * @param: beginTime ： 请求开始时间
     * @param: runTime ： 请求消耗时间
     * @param: endTime ：请求结束时间
     * @param: url ： 请求 的Url
     * @param: userId ： 用户Id
     * @param: page ： 偏移量（分页参数）
     * @param: count ： 指定返回条数（分页参数）
     * @return: RequestDTO的List
     */
    List<RequestDTO> queryRequestByParams(Map<String, Object> params);

    /**
     * 多条件查询请求信息
     * @param: 以下为查询参数中map的key的说明，多个参数为并且的关系
     * @param: beginTime ： 请求开始时间
     * @param: runTime ： 请求消耗时间
     * @param: endTime ：请求结束时间
     * @param: url ： 请求 的Url
     * @param: userId ： 用户Id
     * @param: page ： 偏移量（分页参数）
     * @param: count ： 指定返回条数（分页参数）
     * @return: 查询数据的总条数
     */
    int countAllRequestByParams(Map<String, Object> params);

    /** 
     * 插入一条请求数据
     * @param requestTemp
     * @return 插入数据的条数
     */
    int insertOneRequest(RequestTemp requestTemp);

    /** 
     * 更新一条请求数据
     * @param requestTemp
     * @return 更新数据的条数
     */
    int updateRequestParentId(RequestTemp requestTemp);
}
