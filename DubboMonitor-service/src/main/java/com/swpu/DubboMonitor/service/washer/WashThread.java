package com.swpu.DubboMonitor.service.washer;


import org.apache.commons.lang.StringUtils;
import com.swpu.DubboMonitor.core.dto.Record;

/**
 * 线程类
 * @author: dengyu
 * @Date:2017年11月22日
 */
public class WashThread implements Runnable
{

    private boolean check = true;
    private DataWasher dataWasher;
    private RepositoryUtil repositoryUtil;

    public WashThread(DataWasher dataWasher,RepositoryUtil repositoryUtil)
    {
        this.dataWasher = dataWasher;
        this.repositoryUtil = repositoryUtil;
    }

    /** 
     * 数据清洗函数，从codis中获取一条数据调用函数处理
     */
    @Override
    public void run()
    {
        while (check)
        {
            Record record;
            try
            {
                record = repositoryUtil.getRecord();
                if (record != null)
                {
                    String combineKey = record.getTraceID() + record.getSpan();
                    if (!StringUtils.isBlank(record.getMethodName()))
                    {
                        if (record.getMethodName().matches("httpUse.*"))
                        {
                            record.setSpan("http" + record.getSpan());
                            dataWasher.dealOneHttpRecord(record);
                        }
                        else
                        {
                            dataWasher.dealOneRecord(record);
                        }
                    }
                    repositoryUtil.removeKey(combineKey);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
