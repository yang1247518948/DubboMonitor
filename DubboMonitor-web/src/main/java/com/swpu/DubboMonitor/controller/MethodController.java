package com.swpu.DubboMonitor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swpu.DubboMonitor.service.MethodService;
import com.swpu.DubboMonitor.core.dto.MethodDTO;
import com.swpu.DubboMonitor.util.ResultUtil;
import com.swpu.DubboMonitor.vo.ResultVO;

/**
 * 方法的数据获取
 * @author: dengyu
 * @Date:2017年11月27日
 */
@Controller
@RequestMapping("/method")
public class MethodController
{

    @Autowired
    private MethodService methodService;

    /**
     * 通过条件删选方法
     * @param request
     * @return ResultVO
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getMethodInfoList")
    @ResponseBody
    public ResultVO methodSearchByOther(HttpServletRequest request)
    {
        try
        {
            Map<String, Object> map = new HashMap<String, Object>();
            String page = request.getParameter("page");
            if (!StringUtils.isBlank(page))
            {
                map.put("page", Integer.parseInt(page));
                int count;
                if (!StringUtils.isBlank(request.getParameter("count")))
                {
                    count = Integer.parseInt(request.getParameter("count"));
                }
                else
                {
                    count = 15;
                }
                map.put("count", count);
                int offSet = (Integer.parseInt(page) - 1) * count;
                if (offSet >= 0)
                {
                    map.put("offSet", offSet);
                }
                else
                {
                    return ResultUtil.failure(-1, "参数错误");
                }
                String methodName = request.getParameter("methodName");
                String runTime = request.getParameter("runTime");
                if (!StringUtils.isBlank(methodName))
                {
                    map.put("methodName", methodName);
                }
                if (!StringUtils.isBlank(runTime))
                {
                    map.put("runTime", Integer.parseInt(runTime));
                }
                List<MethodDTO> methodInfo = methodService.methodsSearchByParams(map);
                if (methodInfo != null)
                {
                    int countAll = methodService.countAllMethodByParams(map);
                    int returnPage = Integer.parseInt(page);
                    return ResultUtil.success(methodInfo, returnPage, countAll, null);
                }
                else
                {
                    return ResultUtil.failure(-3, "未获取数据");
                }
            }
            else
            {
                return ResultUtil.failure(-1, "参数错误");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultUtil.failure(-2, "系统错误");
        }
    }
}
