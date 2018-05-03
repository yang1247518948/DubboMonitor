package com.yang.monitor.service.impl;

import java.util.List;
import java.util.Map;

import com.yang.monitor.core.RequestManager;
import com.yang.monitor.core.dto.RequestDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.monitor.service.RequestService;

@Service
public class RequestserviceImpl implements RequestService
{

    @Autowired
    private RequestManager requestManager;
    @Override
    public boolean removeRequestByPrimaryKey(String requestId,String appId)
    {
        if (StringUtils.isEmpty(requestId)&&StringUtils.isEmpty(appId))
        {
            return false;
        }
        return requestManager.deleteByPrimaryKey(requestId,appId) == 1 ? true : false;
    }
    @Override
    public List<RequestDTO> queryRequestByParams(Map<String, Object> params)
    {
        return requestManager.queryRequestByParams(params);
    }
    @Override
    public int countRequestByParams(Map<String, Object> params)
    {   
        return requestManager.countAllRequestByParams(params);
    }

}
