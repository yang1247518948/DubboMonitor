package com.swpu.DubboMonitor.persist.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.swpu.DubboMonitor.persist.entity.RequestEntity;

/**
 *  RequestDao
 * @author: dengyu  
 */
public interface RequestDao
{

    /** 
     * 通过主键删除一条请求数据
     * @param requestId
     * @param appId
     * @return 删除的数据的条数
     */
    int deleteByPrimaryKey(@Param("requestId")String requestId,@Param("appId")String appId);


    /** 
     * 插入一条请求数据
     * @param record
     * @return 插入数据的条数
     */
    int insertSelective(RequestEntity record);

    /** 
     * @Description: 通过主键查询一条请求数据
     * @param requestId
     * @param appId
     * @return RequestEntity
     */
    RequestEntity selectByPrimaryKey(@Param("requestId")String requestId,@Param("appId")String appId);

    /**
     * 批量插入请求数据
     * @param requestInfoEntity
     * @return 插入数据的条数
     */
    int insertBatch(List<RequestEntity> requestInfoEntity);

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
     * @return: RequestEntity的List
     */
    List<RequestEntity> queryRequestByParams(Map<String, Object> params);

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
     * @return: 条件查询的数据的总条数
     */
    int countAllRequestsByParams(Map<String, Object> params);

    /** 
     * 更新一个请求数据
     * @param params 
     * @return 更新的数据的条数
     */
    int updateByPrimaryKeySelective(RequestEntity params);

}