package com.swpu.DubboMonitor.core;

import java.util.List;
import java.util.Map;
import com.swpu.DubboMonitor.core.dto.MethodDTO;
import com.swpu.DubboMonitor.core.dto.MethodTemp;
/**
 * MethodManager
 * @author: dengyu  
 */
public interface MethodManager {
    
	 /** 
	 * 通过参数查询方法 
	 * @param map
	 * @return MethodDTO的List
	 */
	List<MethodDTO> methodSearchByParams(Map<String, Object> map);
	 
	 /** 
	 * 通过参数统计数据条数
	 * @param map
	 * @return 查询的数据的总条数
	 */
	int countAllMethodByParams(Map<String, Object> map);
	 
	 /** 
	 * 插入一条方法数据
	 * @param methodTemp
	 * @return 插入数据的条数
	 */
	public int insertOneMethod(MethodTemp methodTemp);
	 
	 /** 
	 * 通过requestId和span查询AppId和Id
	 * @param requestId
	 * @param span
	 * @return Map<key,value> key:appId和Id，value:方法表中的app_id和id
	 */
	public Map<String, String> getBySpan(String requestId,String span);
	  
	 /** 
	 * 通过主键查询一条方法数据
	 * @param id
	 * @return MethodTemp
	 */
	public MethodTemp getMethodTempById(String id);
	 
	 /** 
	 * 更新一条方法数据
	 * @param methodTemp
	 * @return 更新数据的条数
	 */
	public int updateMethodParentId(MethodTemp methodTemp);

}
