package com.yang.monitor.service;

import java.util.List;
import java.util.Map;
import com.yang.monitor.core.dto.MethodDTO;


/**
 * 查询方法的接口
 * @author: yangping
 */
public interface MethodService {
    /** 
     * 通过参数查询方法表中的数据
     * @param params 
     * @return MethodDto的List
     */
	 List<MethodDTO> methodsSearchByParams(Map<String, Object> map);
	 
	  /** 
	     * 通过参数统计总共有多少条数据
	     * @param params 
	     * @return 数据总条数
	     */
	 int countAllMethodByParams(Map<String, Object> map);
}
