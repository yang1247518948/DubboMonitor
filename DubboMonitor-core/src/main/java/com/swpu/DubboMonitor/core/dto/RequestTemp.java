package com.swpu.DubboMonitor.core.dto;

import java.util.Date;

public class RequestTemp
{
    private String requestId;

    private String appId;

    private String appName;

    private String parentId;

    private String url;

    private String requestMethod;

    private Date startTime;

    private Integer runTime;

    private String userId;
    
    private long time;

    private boolean useFlag;
    
    private String span;

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
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

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
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

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public boolean isUseFlag()
    {
        return useFlag;
    }

    public void setUseFlag(boolean useFlag)
    {
        this.useFlag = useFlag;
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
