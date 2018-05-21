package com.yang.monitor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.yang.monitor.core.dto.MethodDTO;
import com.yang.monitor.record.Record;
import com.yang.monitor.util.ResultUtil;
import com.yang.monitor.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yang.monitor.service.MethodService;

/**
 * 方法的数据获取
 * @author: yangping
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
            if (!StringUtils.isEmpty(page))
            {
                map.put("page", Integer.parseInt(page));
                int count;
                if (!StringUtils.isEmpty(request.getParameter("count")))
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
                if (!StringUtils.isEmpty(methodName))
                {
                    map.put("methodName", methodName);
                }
                if (!StringUtils.isEmpty(runTime))
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

    @RequestMapping(value = "/test")
    @ResponseBody
    public Record test(){
        Gson gson = new Gson();
        Record record = new Record();
        record.setSpan("1,1");
        record.setAppID("dadfsafd");
        for (int i = 0;i < 10;i++) {
            gson.toJson(record);
        }
        return record;
    }
}
