package com.yang.monitor.service.washer;

import com.yang.monitor.core.dto.Record;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 工厂类，主要用来获取日志
 * yangping 与1月6日修改逻辑为ehcache提取
 * @author: yangping
 * @Date:2017年11月22日
 */
public class RepositoryUtil
{


    private EHCacheService service = EHCacheService.getInstance();

    private HashSet<String> keySets = new HashSet<>();
    private List<Record> records = new ArrayList<>();


    private ConcurrentLinkedQueue<String> ehCacheKey = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Record> recordQueue = null;
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
            return getRecordFromEhcache();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /** 
     * 日志处理完成后，从keySet中移除这条日志的trace和span组成的联合主键
     * @param key 当前key
     */
    public void removeKey(String key)
    {
        keySets.remove(key);
    }

    /** 
     * 从内存的队列中取出一条日志，如果该日志的trace和span组成的联合主键在keySet中存在，则将这条日志放到list中去，然后继续从enCache中取，直到取出一条日志为止
     * @return Record
     */
    private Record getRecordFromEhcache()
    {
        Record record = null;

        try {
            record = getOneRecord();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (record != null) {
            String combineKey = record.getTraceID() + record.getSpan();
            if (keySets.contains(combineKey)) {
                records.add(record);
                return null;
            }
            else {
                keySets.add(combineKey);
                return record;
            }
        }
        return null;
    }


    /**
     * 从ehcache queue中取出一条数据
     * @return 队头record
     * @throws InterruptedException sleep发生异常
     */
    private Record getOneRecord() throws InterruptedException {

        if(recordQueue==null||recordQueue.isEmpty()){
            if(service.isEmpty()){
                Thread.sleep(1000);
                return null;
            }

            synchronized (this){
                if(recordQueue==null||recordQueue.isEmpty()){
                    recordQueue = getRecordsFromEhcache();
                }
            }
        }

        if(recordQueue==null||recordQueue.isEmpty()){
            Thread.sleep(1000);
            return null;
        }


        return recordQueue.poll();
    }

    /**
     * 从ehcache中取出一条队列作为当前处理队列
     * @return 取出的队列
     */
    private ConcurrentLinkedQueue<Record> getRecordsFromEhcache(){
        if(recordQueue==null){
            return service.getQueue();
        }
        return null;
    }
}
