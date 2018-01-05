package com.swpu.DubboMonitor.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.swpu.DubboMonitor.core.util.BeanUtils;
import com.swpu.DubboMonitor.core.RequestManager;
import com.swpu.DubboMonitor.core.dto.RequestDTO;
import com.swpu.DubboMonitor.core.dto.RequestTemp;
import com.swpu.DubboMonitor.persist.dao.RequestDao;
import com.swpu.DubboMonitor.persist.entity.RequestEntity;

public class RequestManagerImpl implements RequestManager
{
    @Autowired
    private RequestDao requestDao;

    @Override
    public int deleteByPrimaryKey(String requestId,String appId)
    {
        if (StringUtils.isBlank(requestId))
        {
            return requestDao.deleteByPrimaryKey(requestId,appId);
        }
        return 0;
    }


    @Override
    public int insertSelective(RequestEntity record)
    {
        if (record != null)
        {
            return requestDao.insertSelective(record);
        }
        return 0;
    }

    @Override
    public RequestTemp getByPrimaryKey(String requestId,String appId)
    {
        if (StringUtils.isEmpty(requestId)&&StringUtils.isEmpty(appId))
        {
            RequestTemp requestTemp= new RequestTemp();
            RequestEntity requestEntity = requestDao.selectByPrimaryKey(requestId,appId);
            if(requestEntity != null)
            {
                BeanUtils.copyProperties(requestEntity, requestTemp);
                return requestTemp;
            }
        }
        return null;
    }

    @Override
    public int insertBatch(List<RequestEntity> requestInfoEntityList)
    {
        if (requestInfoEntityList != null && requestInfoEntityList.size() > 0)
        {
            return requestDao.insertBatch(requestInfoEntityList);
        }
        return 0;
    }

    public List<RequestDTO> queryRequestByParams(Map<String, Object> params)
    {
        List<RequestEntity> requestEntitylist = requestDao.queryRequestByParams(params);
        List<RequestDTO> requestDtoList = new ArrayList<RequestDTO>();
        if (!CollectionUtils.isEmpty(requestEntitylist))
        {
            for (RequestEntity requestEntity : requestEntitylist)
            {
                RequestDTO requestDto = new RequestDTO();
                BeanUtils.copyProperties(requestEntity, requestDto);
                requestDtoList.add(requestDto);
            }
        }
        return requestDtoList;
    }

    @Override
    public int countAllRequestByParams(Map<String, Object> params)
    {
        return requestDao.countAllRequestsByParams(params);
    }
    
    @Override
    public int insertOneRequest(RequestTemp requestTemp)
    {
        RequestEntity requesEntity=new RequestEntity();
        BeanUtils.copyProperties(requestTemp, requesEntity);
        return requestDao.insertSelective(requesEntity);
    }
    
    @Override
    public int updateRequestParentId(RequestTemp requestTemp){
        RequestEntity requesEntity=new RequestEntity();
        BeanUtils.copyProperties(requestTemp, requesEntity);
        return requestDao.updateByPrimaryKeySelective(requesEntity);
    }
}
