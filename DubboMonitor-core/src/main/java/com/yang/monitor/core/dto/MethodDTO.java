package com.yang.monitor.core.dto;

import java.util.Date;

public class MethodDTO
{

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

    private String appId;

    private String appName;

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
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }
}