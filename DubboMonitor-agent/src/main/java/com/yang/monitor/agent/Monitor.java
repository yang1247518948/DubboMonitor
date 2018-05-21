package com.yang.monitor.agent;

import com.yang.monitor.transation.ClassTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;


public class Monitor {

    public static void premain(String agentOps, Instrumentation inst) throws Exception {
        if(System.getProperty("agent.appName") == null) {
            throw new Exception("Failed to get AppName, Please add your app name in VM options by using \"-Dagent.appName=\"");
        }

        XMLReader.readFile();

        //加入拦截处理器
        ClassFileTransformer tf = new ClassTransformer();
        inst.addTransformer(tf);
    }
}
