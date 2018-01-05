package com.swpu.DubboMonitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swpu.DubboMonitor.core.dto.MethodDTO;
import com.swpu.DubboMonitor.core.MethodManager;
import com.swpu.DubboMonitor.service.MethodService;


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

