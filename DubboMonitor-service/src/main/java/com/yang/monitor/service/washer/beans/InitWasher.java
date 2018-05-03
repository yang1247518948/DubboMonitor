package com.yang.monitor.service.washer.beans;

import com.yang.monitor.service.common.WasherGobal;
import com.yang.monitor.service.washer.DataCorrect;
import com.yang.monitor.service.washer.DataWasher;
import com.yang.monitor.service.washer.RepositoryUtil;
import com.yang.monitor.service.washer.WashThread;
import com.yang.monitor.service.washer.netty.NettyWorker;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 查询方法链的接口
 * @Copyright: 丹露
 * @author: yangping
 * @Date:2017年11月13日
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
    public void begin() throws Exception {

        System.out.print("aaaaaaaa");
        try
        {
//
            System.out.print("aaaaaaa");
            new Thread(new NettyWorker()).start();
            ExecutorService pool = Executors.newFixedThreadPool(WasherGobal.THREAD_COUNT);
//
                for (int i = 0; i < 5; i++)
                {
                    pool.execute(new Thread(new WashThread(dataWasher,repositoryUtil)));

                }
                isStart = true;

            File file = new File(getStoragePath());
            System.out.print(file.canWrite());
        }
        catch (Exception e)
        {
            System.out.println("线程启动失败!!");
            e.printStackTrace();
        }
    }

    private String getStoragePath() {
        return "/Users/deepholo/Documents/cache/";
    }
}
