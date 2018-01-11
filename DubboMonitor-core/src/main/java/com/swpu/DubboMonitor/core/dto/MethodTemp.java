package com.swpu.DubboMonitor.core.dto;

import java.io.Serializable;
import java.util.Date;

public class MethodTemp implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String methodName;

    private String className;

    private String requestId;

    private Date startTime;

    private Integer runTime;

    private String parentId;

    private String threadNum;

    private String rpcUse;

    private String result;
    
    private long time;

    private String appId;

    private String appName;
    
    private String span;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getRunTime()
    {
        return runTime;
    }

    public void setRunTime(Integer runTime)
    {
        this.runTime = runTime;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getThreadNum()
    {
        return threadNum;
    }

    public void setThreadNum(String threadNum)
    {
        this.threadNum = threadNum;
    }

    public String getRpcUse()
    {
        return rpcUse;
    }

    public void setRpcUse(String rpcUse)
    {
        this.rpcUse = rpcUse;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppName()
    {
        return this.appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public String getSpan()
    {
        return span;
    }

    public void setSpan(String span)
    {
        this.span = span;
    }
}