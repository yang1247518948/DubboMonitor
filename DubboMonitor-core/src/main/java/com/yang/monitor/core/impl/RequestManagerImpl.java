package com.yang.monitor.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yang.monitor.persist.dao.RequestDao;
import com.yang.monitor.persist.entity.RequestEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.yang.monitor.core.RequestManager;
import com.yang.monitor.core.dto.RequestDTO;
import com.yang.monitor.core.dto.RequestTemp;

public class RequestManagerImpl implements RequestManager {
    @Autowired
    private RequestDao requestDao;

    @Override
    public int deleteByPrimaryKey(String requestId, String appId) {
        if (StringUtils.isBlank(requestId)) {
            return requestDao.deleteByPrimaryKey(requestId, appId);
        }
        return 0;
    }


    @Override
    public int insertSelective(RequestEntity record) {
        if (record != null) {
            return requestDao.insertSelective(record);
        }
        return 0;
    }

    @Override
    public RequestTemp getByPrimaryKey(String requestId, String appId) {
        if (StringUtils.isEmpty(requestId) && StringUtils.isEmpty(appId)) {
            RequestTemp requestTemp = new RequestTemp();
            RequestEntity requestEntity = requestDao.selectByPrimaryKey(requestId, appId);
            if (requestEntity != null) {
                BeanUtils.copyProperties(requestEntity, requestTemp);
                return requestTemp;
            }
        }
        return null;
    }

    @Override
    public int insertBatch(List<RequestEntity> requestInfoEntityList) {
        if (requestInfoEntityList != null && requestInfoEntityList.size() > 0) {
            return requestDao.insertBatch(requestInfoEntityList);
        }
        return 0;
    }

    public List<RequestDTO> queryRequestByParams(Map<String, Object> params) {
        List<RequestEntity> requestEntitles = requestDao.queryRequestByParams(params);
        List<RequestDTO> requestDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(requestEntitles)) {
            for (RequestEntity requestEntity : requestEntitles) {
                RequestDTO requestDto = new RequestDTO();
                BeanUtils.copyProperties(requestEntity, requestDto);
                requestDtoList.add(requestDto);
            }
        }
        return requestDtoList;
    }

    @Override
    public int countAllRequestByParams(Map<String, Object> params) {
        return requestDao.countAllRequestsByParams(params);
    }

    @Override
    public int insertOneRequest(RequestTemp requestTemp) {
        RequestEntity requestEntity = new RequestEntity();
        BeanUtils.copyProperties(requestTemp, requestEntity);
        return requestDao.insertSelective(requestEntity);
    }

    @Override
    public int updateRequestParentId(RequestTemp requestTemp) {
        RequestEntity requestEntity = new RequestEntity();
        BeanUtils.copyProperties(requestTemp, requestEntity);
        return requestDao.updateByPrimaryKeySelective(requestEntity);
    }
}
