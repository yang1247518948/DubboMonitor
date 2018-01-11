package com.swpu.DubboMonitor.util;

import com.swpu.DubboMonitor.vo.ResultVO;

/**
 * 返回前端数据的工具类
 * @author: dengyu
 */
public class ResultUtil
{
    
    /** 
     * 请求成功时返回的对象,code:0默认代表成功
     * @param data 返回给前端的数据
     * @param page 当前页数
     * @param count 数据的总条数
     * @param app app的名字
     * @return ResultVO
     */
    public static ResultVO success(Object data, Integer page, int count,Object app)
    {
        ResultVO result = new ResultVO();
        result.setCode(0);
        result.setCount(count);
        result.setPage(page);
        result.setMsg("成功");
        result.setData(data);
        result.setApp(app);
        return result;
    }
    
    /** 
     * 请求失败的返回的对象
     * @param code 错误码
     * @param msg 错误的提示消息
     * @return ResultVO
     */
    public static ResultVO failure(Integer code, String msg)
    {
        ResultVO result = new ResultVO();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /** 
     * 请求成功时返回的对象(没有数据)
     * @return ResultVO
     */
    public static ResultVO success()
    {   
        return success(null, 0, 0,null);
    }
}
