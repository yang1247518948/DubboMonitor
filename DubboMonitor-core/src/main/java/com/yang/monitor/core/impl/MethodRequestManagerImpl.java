package com.yang.monitor.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.yang.monitor.core.util.TransferUtil;
import com.yang.monitor.persist.dao.MethodRequestDao;
import com.yang.monitor.persist.entity.MethodRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.yang.monitor.core.MethodRequestManager;
import com.yang.monitor.core.dto.MethodRequestDTO;

public class MethodRequestManagerImpl implements MethodRequestManager {

    @Autowired
    MethodRequestDao methodRequestDao;
    public void start(){
        System.out.println("hehehhehe !!");
    }
    public List<MethodRequestDTO> getMethodListByRequestID(String requestId) {
        if (!StringUtils.isEmpty(requestId)) {
            List<MethodRequestEntity> methodRequestList = methodRequestDao.selectMethodListByRequestID(requestId);
            List<MethodRequestDTO> methodRequestDTOList = new ArrayList<MethodRequestDTO>();

            if (!CollectionUtils.isEmpty(methodRequestList)) {
                for (MethodRequestEntity methodRequestEntity : methodRequestList) {
                    MethodRequestDTO methodRequsetDTO = new MethodRequestDTO();
                    methodRequsetDTO = TransferUtil.transferMethodRequest(methodRequestEntity, methodRequsetDTO);
                    methodRequestDTOList.add(methodRequsetDTO);
                }
                return methodRequestDTOList;
            }
        }
        return null;
    }

    public List<MethodRequestDTO> getAppNameListByRequestID(String requestId) {
        if (!StringUtils.isEmpty(requestId)) {
            List<MethodRequestEntity> methodRequestList = methodRequestDao
                .selectMethodListByRequestID(requestId);
            List<MethodRequestDTO> methodRequestDTOList = new ArrayList<MethodRequestDTO>();

            if (!CollectionUtils.isEmpty(methodRequestList)) {

                HashMap<String, MethodRequestEntity> map = new HashMap<>();

                for (MethodRequestEntity entity : methodRequestList) {
                    map.put(entity.getId(),entity);
                }


                for (MethodRequestEntity methodRequestEntity : methodRequestList) {
                    MethodRequestEntity parentMethod = map
                            .get(methodRequestEntity.getParentId());
                    if (parentMethod == null) {
                        MethodRequestDTO methodRequsetDTO = new MethodRequestDTO();
                        methodRequsetDTO = TransferUtil.transferApp(methodRequestEntity,
                                methodRequsetDTO);
                        methodRequestDTOList.add(methodRequsetDTO);
                    }
                    else {
                        if (parentMethod.getAppName() != null && methodRequestEntity.getAppName() != null) {
                            if (!parentMethod.getAppName().equals(methodRequestEntity.getAppName())) {
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
