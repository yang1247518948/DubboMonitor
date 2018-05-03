package com.yang.monitor.persist.entity;

import java.util.Date;

public class RequestEntity {
	
    private String requestId;

    private String appId;

    private String appName;

    private String parentId;

    private String url;

    private String requestMethod;

    private Date startTime;
    
    private Date createTime;

    private Date updateTime;
    
    private String span;

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    private Integer runTime;

    private String userId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSpan()
    {
        return span;
    }

    public void setSpan(String span)
    {
        this.span = span;
    }

    public Integer getRunTime()
    {
        return runTime;
    }

    public void setRunTime(Integer runTime)
    {
        this.runTime = runTime;
    }

}