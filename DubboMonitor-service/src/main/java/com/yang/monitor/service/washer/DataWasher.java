package com.yang.monitor.service.washer;


import com.yang.monitor.core.MethodManager;
import com.yang.monitor.core.RequestManager;
import com.yang.monitor.core.dto.MethodTemp;
import com.yang.monitor.core.dto.Record;
import com.yang.monitor.core.dto.RequestTemp;
import com.yang.monitor.core.util.TransferUtil;
import com.yang.monitor.service.common.WasherGobal;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据清洗函数类
 * @author: yangping
 * @Date:2017年11月22日
 */
public class DataWasher
{
    @Autowired
    MethodManager methodManager;

    @Autowired
    RequestManager requestManager;


    private EHCacheService service = EHCacheService.getInstance();

    /**
     * 处理方法打印的日志
     * @param record 日志
     */
    public void dealOneRecord(Record record)
    {
        MethodTemp temp = null;
        boolean flag=false;
        String span = record.getSpan();
        String traceID = record.getTraceID();

        temp = service.getMethod(traceID+span);
        if (temp != null)
        {

            int index = span.lastIndexOf('.');
            if (index != -1 && span.substring(0, index).indexOf('.') != -1)
            {
                String parentSpan = span.substring(0, index);
                MethodTemp tempParen = service.getMethod(traceID+parentSpan);
                if (tempParen != null)
                {
                    temp.setParentId(tempParen.getId());
                }
                else {
                    flag=true;
                }
            }
            temp = TransferUtil.transferMethodTemp(temp, record);

            if(!StringUtils.isBlank(temp.getClassName()) && temp.getClassName().equals("SQL USE")) {
                String tempStr = temp.getMethodName().replaceAll("\\s+", " ");
                temp.setMethodName(tempStr);
            }
            methodManager.insertOneMethod(temp);
            if(flag) {
                WasherGobal.methodId.add(temp.getId());
            }
        } else {
            service.addMethod(traceID+record.getSpan(),record);
        }
    }


    /**
     * 处理请求打印的日志
     * @param record (日志)
     */
    public void dealOneHttpRecord(Record record)
    {
        RequestTemp temp = null;
        String span = record.getSpan();
        boolean flag = false;
        String traceID = record.getTraceID();
        temp = service.getRequest(traceID+span);
        if (temp != null)
        {
            if (span.indexOf('.') != -1)
            {
                span = span.substring(4);
                MethodTemp tempParen = service.getMethod(traceID+span);
                if (tempParen != null)
                {
                    temp.setParentId(tempParen.getAppId());
                } else {
                    flag=true;
                }
            }
            temp = TransferUtil.transferRequestTemp(temp, record);
            requestManager.insertOneRequest(temp);
            if(flag) {
                String id=traceID+","+temp.getAppId();
                WasherGobal.requestId.add(id);
            }
        }
        else {
//            codisService.hset(traceID, span, TransferUtil.recordToRequesTemp(record));
            service.addRequset(traceID+record.getSpan(),record);
        }
    }
}
