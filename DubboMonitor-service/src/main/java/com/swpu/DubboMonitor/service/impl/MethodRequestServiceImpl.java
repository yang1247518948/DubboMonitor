package com.swpu.DubboMonitor.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swpu.DubboMonitor.core.MethodRequestManager;
import com.swpu.DubboMonitor.core.dto.MethodRequestDTO;
import com.swpu.DubboMonitor.service.MethodRequestService;

@Service
public class MethodRequestServiceImpl implements MethodRequestService
{

    @Autowired
    MethodRequestManager methodRequestManager;

    @Override
    public List<MethodRequestDTO> getMethodListByRequestID(String requestId)
    {
        if (!StringUtils.isEmpty(requestId))
        {
            List<MethodRequestDTO> methodList = methodRequestManager.getMethodListByRequestID(requestId);
            return methodList;
        }
        return null;
    }
    @Override
    public List<MethodRequestDTO> getAppNameListByRequestID(String requestId)
    {
        if (!StringUtils.isEmpty(requestId))
        {
            List<MethodRequestDTO> methodList = methodRequestManager.getAppNameListByRequestID(requestId);
            return methodList;
        }
        return null;
    }

}