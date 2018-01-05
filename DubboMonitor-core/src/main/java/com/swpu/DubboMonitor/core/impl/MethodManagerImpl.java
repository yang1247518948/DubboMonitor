package com.swpu.DubboMonitor.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.DubboMonitor.core.util.BeanUtils;
import com.swpu.DubboMonitor.core.MethodManager;
import com.swpu.DubboMonitor.core.dto.MethodDTO;
import com.swpu.DubboMonitor.core.dto.MethodTemp;
import com.swpu.DubboMonitor.persist.dao.MethodDao;
import com.swpu.DubboMonitor.persist.entity.MethodEntity;

public class MethodManagerImpl implements MethodManager {

	@Autowired
	private MethodDao methodDao;
	
	public List<MethodDTO> methodSearchByParams(Map<String, Object> map){
		List<MethodEntity> methodList;
		methodList=methodDao.selectByParams(map);
		//将list<MethodEntity>转化成List<MethodDTO>
		if( !CollectionUtils.isEmpty(methodList)){
			List<MethodDTO> methodDTOs=new ArrayList<>();
			for (MethodEntity methodEntity : methodList){
			    MethodDTO methodDTO=new MethodDTO();
			    BeanUtils.copyProperties(methodEntity, methodDTO);
                methodDTOs.add(methodDTO);
            }
			return methodDTOs;
		}else{
			return null;
		}
	}
	
	//得到数据的条数
	public int countAllMethodByParams(Map<String, Object> map){
		return methodDao.selectCountAllMethodByParams(map);
	}
	
	public int insertOneMethod(MethodTemp methodTemp){
        MethodEntity methodEntity=new MethodEntity();
        BeanUtils.copyProperties(methodTemp, methodEntity);
        return methodDao.insertSelective(methodEntity);
    }
	
	public Map<String, String> getBySpan(String requestId,String span){
	     return methodDao.selectBySpan(requestId,span);
	}
	public MethodTemp getMethodTempById(String id){
	    MethodEntity methodEntity=new MethodEntity();
	    MethodTemp methodTemp=new MethodTemp();
	    methodEntity=methodDao.selectByPrimaryKey(id);
	    if(methodEntity!=null){
	        BeanUtils.copyProperties(methodEntity, methodTemp);
	    }
	    return methodTemp;
	}
	
	public int updateMethodParentId(MethodTemp methodTemp){
	    MethodEntity methodEntity=new MethodEntity();
        BeanUtils.copyProperties(methodTemp, methodEntity);
	    return methodDao.updateByPrimaryKey(methodEntity);
	}
}
