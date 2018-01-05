package com.swpu.DubboMonitor.service.washer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.TypeReference;
import com.swpu.DubboMonitor.core.dto.Record;
import com.swpu.DubboMonitor.service.common.WasherGobal;


/**
 * 工厂类，主要用来获取日志
 * @author: dengyu
 * @Date:2017年11月22日
 */
public class RepositoryUtil
{

    public HashSet<String> keySets = new HashSet<String>();
    public List<Record> records = new ArrayList<Record>();
    

    /** 
     * 遍历list，如果没有冲突，返回一条日志，如果有就从codis中取出一条日志返回
     * @return Record
     */
    public Record getRecord()
    {
        try
        {
            if (!CollectionUtils.isEmpty(records))
            {
                synchronized (RepositoryUtil.class)
                {
                    for (int i = 0; i < records.size(); i++)
                    {
                        Record record = records.get(i);
                        String combineKey = record.getTraceID() + record.getSpan();
                        if (!keySets.contains(combineKey))
                        {
                            keySets.add(combineKey);
                            records.remove(record);
                            return record;
                        }
                    }
                }
            }
            return getRecordFromCodis();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /** 
     * 日志处理完成后，从keySet中移除这条日志的trace和span组成的联合主键
     * @param key
     */
    public void removeKey(String key)
    {
        keySets.remove(key);
    }

    /** 
     * 从codis的队列中取出一条日志，如果该日志的trace和span组成的联合主键在keySet中存在，则将这条日志放到list中去，然后继续从codis中取，直到取出一条日志为止
     * @return Record
     */
    public Record getRecordFromCodis() throws InterruptedException
    {
        boolean flag = true;
        Record record = null;
        while (flag)
        {
            record = codisSerivce.listLeftPop(WasherGobal.KEY_DLMONTITOR,new TypeReference<Record>(){});
            if (record != null)
            {
                synchronized (RepositoryUtil.class)
                {
                    if(StringUtils.isBlank(record.getTraceID()) || StringUtils.isBlank(record.getSpan()))
                    {
                        continue;
                    }
                    String combineKey = record.getTraceID() + record.getSpan();
                    if (keySets.contains(combineKey))
                    {
                        records.add(record);
                    }
                    else
                    {
                        keySets.add(combineKey);
                        return record;
                    }
                }
            }
            else
            {
                Thread.sleep(100);
            }
        }
        return record;
    }
}
