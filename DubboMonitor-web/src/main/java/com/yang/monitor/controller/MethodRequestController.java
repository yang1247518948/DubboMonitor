package com.yang.monitor.controller;

import java.util.List;

import com.yang.monitor.core.dto.MethodRequestDTO;
import com.yang.monitor.util.ResultUtil;
import com.yang.monitor.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yang.monitor.service.MethodRequestService;

/**
 * 方法链的数据
 * @author: yangping
 */
@Controller
@RequestMapping("/methodRequest")
public class MethodRequestController
{

    @Autowired
    MethodRequestService methodRequestService;

    
    /** 
     * 获得方法链
     * @param requestId
     * @return ResultVO
     */
    @RequestMapping(value = "/getMethodChain", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO getMethodChain(String requestId)
    {
        if (!StringUtils.isEmpty(requestId))
        {
            List<MethodRequestDTO> methodChainDto = null;
            List<MethodRequestDTO> appChainDto = null;
            try
            {
                methodChainDto = methodRequestService.getMethodListByRequestID(requestId);
                appChainDto = methodRequestService.getAppNameListByRequestID(requestId);
                if (!CollectionUtils.isEmpty(methodChainDto)&&!CollectionUtils.isEmpty(appChainDto))
                {
                    return ResultUtil.success(methodChainDto, 0, 0,appChainDto);
                }
                else
                {
                    return ResultUtil.failure(-3, "未获取数据");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return ResultUtil.failure(-2, "系统错误");
            }
        }
        return ResultUtil.failure(-1, "参数错误");
    }
}
