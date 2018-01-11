package com.swpu.DubboMonitor.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.swpu.DubboMonitor.core.MethodRequestManager;
import com.swpu.DubboMonitor.core.dto.MethodRequestDTO;
import com.swpu.DubboMonitor.core.util.TransferUtil;
import com.swpu.DubboMonitor.persist.dao.MethodRequestDao;
import com.swpu.DubboMonitor.persist.entity.MethodRequestEntity;

public class MethodRequestManagerImpl implements MethodRequestManager
{

    @Autowired
    MethodRequestDao methodRequestDao;
    public void start(){
        System.out.println("hehehhehe !!");
    }
    public List<MethodRequestDTO> getMethodListByRequestID(String requestId)
    {
        if (!StringUtils.isEmpty(requestId))
        {
            List<MethodRequestEntity> methodRequestList = methodRequestDao.selectMethodListByRequestID(requestId);
            List<MethodRequestDTO> methodRequestDTOList = new ArrayList<MethodRequestDTO>();

            if (!CollectionUtils.isEmpty(methodRequestList))
            {
                for (MethodRequestEntity methodRequestEntity : methodRequestList)
                {
                    MethodRequestDTO methodRequsetDTO = new MethodRequestDTO();
                    methodRequsetDTO = TransferUtil.transferMethodRequest(methodRequestEntity, methodRequsetDTO);
                    methodRequestDTOList.add(methodRequsetDTO);
                }
                return methodRequestDTOList;
            }
        }
        return null;
    }

    public List<MethodRequestDTO> getAppNameListByRequestID(String requestId)
    {
        if (!StringUtils.isEmpty(requestId))
        {
            List<MethodRequestEntity> methodRequestList = methodRequestDao
                .selectMethodListByRequestID(requestId);
            List<MethodRequestDTO> methodRequestDTOList = new ArrayList<MethodRequestDTO>();

            if (!CollectionUtils.isEmpty(methodRequestList))
            {
                for (MethodRequestEntity methodRequestEntity : methodRequestList)
                {
                    MethodRequestEntity parentMethod = methodRequestDao
                        .selectMethodById(methodRequestEntity.getParentId());
                    if (parentMethod == null)
                    {
                        MethodRequestDTO methodRequsetDTO = new MethodRequestDTO();
                        methodRequsetDTO = TransferUtil.transferApp(methodRequestEntity,
                            methodRequsetDTO);
                        methodRequestDTOList.add(methodRequsetDTO);
                    }
                    else
                    {
                        if (parentMethod.getAppName() != null && methodRequestEntity.getAppName() != null)
                        {
                            if (!parentMethod.getAppName().equals(methodRequestEntity.getAppName()))
                            {
                                MethodRequestDTO methodRequsetDTO = new MethodRequestDTO();
                                methodRequsetDTO = TransferUtil.transferApp(methodRequestEntity,
                                    methodRequsetDTO);
                                methodRequestDTOList.add(methodRequsetDTO);
                            }
                        }
                    }
                }
                return methodRequestDTOList;
            }
        }
        return null;
    }

}
