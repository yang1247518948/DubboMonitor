package com.swpu.DubboMonitor.service.washer.beans;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.DubboMonitor.service.common.WasherGobal;
import com.swpu.DubboMonitor.service.washer.DataCorrect;
import com.swpu.DubboMonitor.service.washer.DataWasher;
import com.swpu.DubboMonitor.service.washer.RepositoryUtil;
import com.swpu.DubboMonitor.service.washer.WashThread;

/**
 * 查询方法链的接口
 * @author: dengyu
 */
public class InitWasher
{
    
    @Autowired
    private RepositoryUtil repositoryUtil;
    
    @Autowired
    private DataWasher dataWasher;
    
    @Autowired
    private DataCorrect dataCorrect;
        
    boolean isStart = false;

    /** 
     * 线程启动方法，启动多线程
     */
    public void begin() throws Exception
    {
        try
        {
            ExecutorService pool = Executors.newFixedThreadPool(WasherGobal.THREAD_COUNT);
            if (!isStart)
            {
                for (int i = 0; i < WasherGobal.THREAD_COUNT; i++)
                {
                    pool.execute(new Thread(new WashThread(dataWasher,repositoryUtil)));
                }
                isStart = true;
            }
        }
        catch (Exception e)
        {
            System.out.println("线程启动失败!!");
            e.printStackTrace();
        }
    }
}
