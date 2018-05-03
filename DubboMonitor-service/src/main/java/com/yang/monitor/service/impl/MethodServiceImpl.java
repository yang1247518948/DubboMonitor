package com.yang.monitor.service.impl;

import java.util.List;
import java.util.Map;

import com.yang.monitor.core.MethodManager;
import com.yang.monitor.core.dto.MethodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.monitor.service.MethodService;


@Service
public class MethodServiceImpl implements MethodService {
	
	@Autowired
    MethodManager methodManager;
	@Override
	public List<MethodDTO> methodsSearchByParams(Map<String, Object> map){
		return methodManager.methodSearchByParams(map);
	}
	@Override
	public int countAllMethodByParams(Map<String, Object> map){
		return methodManager.countAllMethodByParams(map);
	}
}

