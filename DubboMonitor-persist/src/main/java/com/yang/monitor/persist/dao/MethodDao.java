package com.yang.monitor.persist.dao;

import java.util.List;
import java.util.Map;

import com.yang.monitor.persist.entity.MethodEntity;
import org.apache.ibatis.annotations.Param;


/**
 * MethodDao
 * @author: yangping
 */
public interface MethodDao {
    /** 
     * 通过主键删除一条方法数据
     * @param id
     * @return 删除的数据的条数
     */
    int deleteByPrimaryKey(String id);

    
    /** 
     * 插入一条方法数据
     * @param record
     * @return 插入数据的条数
     */
    int insertSelective(MethodEntity record);
    
    /** 
     * 批量插入方法数据
     * @param record
     * @return 插入数据的条数
     */
    int insertBathMethod(List<MethodEntity> record);
    
    /** 
     * 更新一条方法数据
     * @param record
     * @return 更新数据的条数
     */
    int updateByPrimaryKey(MethodEntity record);

    /** 
     * 通过主键查询方法数据
     * @param id
     * @return MethodEntity
     */
    MethodEntity selectByPrimaryKey(String id);

    /** 
     * 多条件查询请求信息
     * @param: 以下为查询参数中map的key的说明，多个参数为并且的关系
     * @param: methodName： 方法名称(模糊查询)
     * @param: runTime ： 方法消耗时间
     * @return MethodEntity的List
     */
    List<MethodEntity> selectByParams(Map<String, Object> params);
    
    /** 
     * 多条件查询请求信息
     * @param: 以下为查询参数中map的key的说明，多个参数为并且的关系
     * @param: methodName： 方法名称(模糊查询)
     * @param: runTime ： 方法消耗时间
     * @return 条件查询数据的总条数
     */
    int selectCountAllMethodByParams(Map<String ,Object> params);
   
    /** 
     * 通过requestId和span从方法表中查询id和appId
     * @param params
     * @return Map<key,value> key:id,appId,value:数据库中对应的值
     */
    Map<String, String> selectBySpan(@Param("requestId")String requestId,@Param("span")String span);
}