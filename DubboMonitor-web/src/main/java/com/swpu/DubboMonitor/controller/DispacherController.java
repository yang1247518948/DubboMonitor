package com.swpu.DubboMonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * 页面跳转控制器
 * @author: zhaoyan
 * @Date:2017年11月27日
 */
@Controller
public class DispacherController
{
    
    /** 
     * 跳转到首页
     * @return ModelAndView
     */
    @RequestMapping(value = "/indexPage")
    public ModelAndView toIndexPage(){
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
    
    /**
     * 跳转到请求详情页
     * @param requestId
     * @return ModelAndView
     */
    @RequestMapping(value = "/requestDetailsPage",method = RequestMethod.GET)
    public ModelAndView toRequestDetailsPage(String requestId){
        ModelAndView mav = new ModelAndView("detailsRe");
        mav.addObject("requestId",requestId);
        return mav;
    }
    
    
    
    /** 
     * 跳转到方法详情页
     * @param requestId
     * @return ModelAndView
     */
    @RequestMapping(value = "/methodDetailsPage",method = RequestMethod.GET)
    public ModelAndView toMethodDetailsPage(String requestId){
        ModelAndView mav = new ModelAndView("detailsMe");
        mav.addObject("requestId",requestId);
        return mav;
    }
}
