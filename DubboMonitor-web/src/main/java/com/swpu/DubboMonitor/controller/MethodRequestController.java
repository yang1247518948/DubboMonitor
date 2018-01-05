package com.swpu.DubboMonitor.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swpu.DubboMonitor.core.dto.MethodRequestDTO;
import com.swpu.DubboMonitor.service.MethodRequestService;
import com.swpu.DubboMonitor.util.ResultUtil;
import com.swpu.DubboMonitor.vo.ResultVO;

/**
 * 方法链的数据
 * @author: zhaoyan
 * @Date:2017年11月27日
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
