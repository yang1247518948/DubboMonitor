package com.yang.monitor.service.washer;


import com.yang.monitor.core.dto.MethodTemp;
import com.yang.monitor.core.dto.RequestTemp;
import com.yang.monitor.core.util.TransferUtil;
import com.yang.monitor.record.Record;
import com.yang.monitor.service.washer.beans.QueueBean;
import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EHCacheService {

    private PersistentCacheManager persistentCacheManager;
    private Cache<String,QueueBean> recordCache;
    private Cache<String,MethodTemp> tempCache;
    private Cache<String,RequestTemp> requestCache;

    private ConcurrentLinkedQueue<String> names = new ConcurrentLinkedQueue<>();

    public static EHCacheService getInstance(){
        return CacheHolder.instance;
    }

    /**
     * 构造函数
     */
    private EHCacheService(){
        persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(getStoragePath() + File.separator + "myData"))
                .withCache("Record",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, QueueBean.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(10, EntryUnit.ENTRIES)  //堆
                                        .offheap(50, MemoryUnit.MB)    //堆外
                                        .disk(10, MemoryUnit.GB)      //磁盘
                        )
                ).withCache("CacheRecord",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, MethodTemp.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(500, EntryUnit.ENTRIES)  //堆
                                        .offheap(50, MemoryUnit.MB)    //堆外
                                        .disk(10, MemoryUnit.GB)      //磁盘
                        )
                ).withCache("RequestTemp",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, RequestTemp.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(500, EntryUnit.ENTRIES)  //堆
                                        .offheap(50, MemoryUnit.MB)    //堆外
                                        .disk(10, MemoryUnit.GB)      //磁盘
                        )
                ).build(true);
        recordCache = persistentCacheManager.getCache("Record",String.class,QueueBean.class);
        tempCache = persistentCacheManager.getCache("CacheRecord",String.class,MethodTemp.class);
        requestCache = persistentCacheManager.getCache("RequestTemp",String.class,RequestTemp.class);
    }

    /**
     * 获取缓存路径
     * @return 缓存路径
     */
    private String getStoragePath() {
        return "/Users/deepholo/cache";
    }

    /**
     * 添加queue到ehcache
     * @param key key
     * @param queue 队列
     */
    public void addQueueToCache(String key,ConcurrentLinkedQueue<Record> queue){
        names.add(key);
        recordCache.put(key,new QueueBean(queue));

    }

    /**
     * 获取最早加入缓存的队列
     * @return 队列
     */
    public ConcurrentLinkedQueue<Record> getQueue(){
        String name = names.poll();
        if (name == null) {
            return null;
        }
        ConcurrentLinkedQueue<Record> result = recordCache.get(name).getQueue();
        recordCache.remove(name);
        return result;
    }

    /**
     * 获取一条方法
     * @param key 键
     * @return 对应方法
     */
    public MethodTemp getMethod(String key){
        return tempCache.get(key);
    }

    public void addMethod(String key,Record value){
        tempCache.put(key, TransferUtil.recordToMethodTemp(value));
    }

    public void removeMethod(String key){
        tempCache.remove(key);
    }

    public boolean methodexists(String key){
        return tempCache.containsKey(key);
    }

    public RequestTemp getRequest(String key){
        return requestCache.get(key);
    }

    public void addRequset(String key,Record value){
        requestCache.put(key, TransferUtil.recordToRequesTemp(value));
    }

    public void removeRequest(String key){
        requestCache.remove(key);
    }

    public boolean requestExists(String key){
        return requestCache.containsKey(key);
    }

    public boolean isEmpty(){
        return names.isEmpty();
    }

    private static class CacheHolder{
        private static EHCacheService instance = new EHCacheService();
    }
}
