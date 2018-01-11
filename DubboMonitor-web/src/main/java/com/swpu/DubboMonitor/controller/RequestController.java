package com.swpu.DubboMonitor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swpu.DubboMonitor.core.dto.RequestDTO;
import com.swpu.DubboMonitor.service.RequestService;
import com.swpu.DubboMonitor.util.ResultUtil;
import com.swpu.DubboMonitor.vo.ResultVO;

/**
 * 请求的数据获取
 * @author: dengyu
 */
@Controller
@RequestMapping("/request")
public class RequestController
{

    @Autowired
    private RequestService requestService;

    
    /** 
     * 通过条件筛选请求
     * @param request
     * @return ResultVO
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getRequestInfoList")
    @ResponseBody
    public ResultVO getRequestInfoList(HttpServletRequest request)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        String page = request.getParameter("page");
        String beginTime = request.getParameter("beginTime");
        String runTime = request.getParameter("runTime");
        String endTime = request.getParameter("endTime");
        String userId = request.getParameter("userId");
        String url = request.getParameter("url");
        
        if (StringUtils.isEmpty(page))
        {
            return ResultUtil.failure(-1, "参数错误");
        }
        if (!StringUtils.isEmpty(beginTime))
        {
            params.put("beginTime", beginTime);
        }
        if (!StringUtils.isEmpty(runTime))
        {
            params.put("runTime", runTime);
        }
        if (!StringUtils.isEmpty(endTime))
        {
            params.put("endTime",  endTime);
        }
        if (!StringUtils.isEmpty(userId))
        {
            params.put("userId", userId);
        }
        if (!StringUtils.isEmpty(url))
        {
            params.put("url", url);
        }
        params.put("page", (Integer.parseInt(page) - 1) * 15);
        params.put("count", 15);
        try
        {
            List<RequestDTO> requestInfoList = requestService.queryRequestByParams(params);
            if (CollectionUtils.isEmpty(requestInfoList))
            {
                return ResultUtil.failure(-3, "未获取数据");
            }
            int count = requestService.countRequestByParams(params);
            int returnPage = Integer.parseInt(page);
            return ResultUtil.success(requestInfoList, returnPage, count,null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResultUtil.failure(-2, "系统错误");
    }
}
