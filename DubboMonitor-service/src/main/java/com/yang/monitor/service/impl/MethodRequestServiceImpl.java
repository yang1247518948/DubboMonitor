package com.yang.monitor.service.impl;

import java.util.List;

import com.yang.monitor.core.MethodRequestManager;
import com.yang.monitor.core.dto.MethodRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yang.monitor.service.MethodRequestService;

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