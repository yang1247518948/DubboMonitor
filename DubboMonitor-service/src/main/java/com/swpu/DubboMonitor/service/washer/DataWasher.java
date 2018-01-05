package com.swpu.DubboMonitor.service.washer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.TypeReference;
import com.swpu.DubboMonitor.core.MethodManager;
import com.swpu.DubboMonitor.core.RequestManager;
import com.swpu.DubboMonitor.core.dto.MethodTemp;
import com.swpu.DubboMonitor.core.dto.RequestTemp;
import com.swpu.DubboMonitor.core.util.TransferUtil;
import com.swpu.DubboMonitor.core.dto.Record;
import com.swpu.DubboMonitor.service.common.WasherGobal;

/**
 * 数据清洗函数类
 * @author: dengyu
 * @Date:2017年11月22日
 */
public class DataWasher
{
    @Autowired
    MethodManager methodManager;

    @Autowired
    RequestManager requestManager;


    /** 
     * 处理方法打印的日志
     * @param Reocrd(日志)
     */
    public void dealOneRecord(Record record)
    {
        MethodTemp temp = null;
        boolean flag=false;
        String span = record.getSpan();
        String traceID = record.getTraceID();
        if (codisService.exists(traceID))
        {
            temp = codisService.hget(traceID, span, new TypeReference<MethodTemp>(){});
            if (temp != null)
            {
                int index = span.lastIndexOf('.');
                if (index != -1 && span.substring(0, index).indexOf('.') != -1)
                {
                    String parentSpan = span.substring(0, index);
                    MethodTemp tempParen = codisService.hget(traceID, parentSpan,new TypeReference<MethodTemp>(){});
                    if (tempParen != null)
                    {
                        temp.setParentId(tempParen.getId());
                    }
                    else 
                    {
                        flag=true;
                    }
                }
                temp = TransferUtil.transferMethodTemp(temp, record);
                if(!StringUtils.isBlank(temp.getClassName()) && temp.getClassName().equals("SQL USE"))
                {
                    String tempStr = temp.getMethodName().replaceAll("\\s+", " ");
                    temp.setMethodName(tempStr);
                }
                methodManager.insertOneMethod(temp);
                if(flag)
                {
                    WasherGobal.methodId.add(temp.getId());
                }
            }
            else
            {
                codisService.hset(traceID, span, TransferUtil.recordToMethodTemp(record));
            }
        }
        else
        {
            codisService.hset(traceID, span, TransferUtil.recordToMethodTemp(record));
            codisService.expire(traceID, WasherGobal.CACHE_TIME_OUT);
        }
    }

    
    /** 
     * 处理请求打印的日志
     * @param Reocrd(日志)
     */
    public void dealOneHttpRecord(Record record)
    {
        RequestTemp temp = null;
        String span = record.getSpan();
        boolean flag = false;
        String traceID = record.getTraceID();
        if (codisService.exists(traceID))
        {
            temp = codisService.hget(traceID, span, new TypeReference<RequestTemp>(){});
            if (temp != null)
            {
                if (span.indexOf('.') != -1)
                {
                    span = span.substring(4);
                    MethodTemp tempParen = codisService.hget(traceID, span,new TypeReference<MethodTemp>(){});
                    if (tempParen != null)
                    {
                        temp.setParentId(tempParen.getAppId());
                    }
                    else 
                    {
                        flag=true; 
                    }
                }
                temp = TransferUtil.transferRequestTemp(temp, record);
                requestManager.insertOneRequest(temp);
                if(flag)
                {
                    String id=traceID+","+temp.getAppId();
                    WasherGobal.requestId.add(id);
                }
            }
            else
            {
                codisService.hset(traceID, span, TransferUtil.recordToRequesTemp(record));
            }
        }
        else
        {
            codisService.hset(traceID, span, TransferUtil.recordToRequesTemp(record));
            codisService.expire(traceID, WasherGobal.CACHE_TIME_OUT);
        }
    }
}
