package com.swpu.DubboMonitor.vo;

/**
 * 返回给前端的对象
 * @author: zhaoyan
 * @Date:2017年12月7日
 */
public class ResultVO {
	 //错误码
    private Integer code;
    //提示信息
    private String msg;
    //具体的数据
    private Object data;
    //总条数
    private int count;
    //当前页
    private Integer page;
    
    private Object app;
    
	public Object getApp()
    {
        return app;
    }

    public void setApp(Object app)
    {
        this.app = app;
    }

    public Integer getCount() {
		return count;
	}

	public void setCount(int count) {
	    this.count = count;
	}

	public Integer getPage()
    {
        return page;
    }

    public void setPage(Integer page)
    {
        this.page = page;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
