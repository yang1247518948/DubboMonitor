package com.yang.monitor.transation;


import com.yang.monitor.agent.AppInfo;
import com.yang.monitor.transation.asm.MonitorClassAdapter;
import javassist.*;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassTransformer implements ClassFileTransformer {

    private static final String HTTP_START = "org/springframework/web/servlet/DispatcherServlet";
    private static final String DUBBO_START =
            "com/alibaba/dubbo/rpc/protocol/dubbo/DubboProtocol$DubboProtocolExchangeHandlerAdapter";
    private static final String DUBBO_END = "com/alibaba/dubbo/rpc/proxy/InvokerInvocationHandler";
    private static final String MYBATIS_START = "org/apache/ibatis/executor/statement/RoutingStatementHandler";
    private static final String HTTPX_START =  "com/danlu/dlhttpx/HttpService";
    private static final String DISCONF_START = "com/baidu/disconf/client/addons/properties/ReloadablePropertiesFactoryBean";
    private static final String DLCODIS_START = "com/danlu/dlcodis/impl/CodisServiceImpl";

    private Logger logger = LoggerFactory.getLogger(ClassTransformer.class);

    /**
     * 这里进行类的处理
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        byte [] trans = null;
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;

        //加入判断，只对需要修改的地方进行修改
        if(className.matches(".*persist.entity.*")||
                AppInfo.getInstance().matchBlackList(className)||
                !(AppInfo.getInstance().matchWhiteList(className)||
                HTTP_START.equals(className)|| DUBBO_START.equals(className)||
                DUBBO_END.equals(className)|| MYBATIS_START.equals(className)||
                HTTPX_START.equals(className)|| DISCONF_START.equals(className)||
                DLCODIS_START.equals(className))){
            return null;
        }

//        logger.info(className);

        //对工程部分开始插入代码
        try{
            //处理请求入口
            if (HTTP_START.equals(className)) {
                pool.insertClassPath(new LoaderClassPath(loader));
                cl = pool.get("org.springframework.web.servlet.DispatcherServlet");
				CtClass[] params = new CtClass[] {
						pool.get("javax.servlet.http.HttpServletRequest"),
						pool.get("javax.servlet.http.HttpServletResponse")};
				CtMethod ct = cl.getDeclaredMethod("doDispatch", params);

                ct.insertBefore("com.danlu.dlmonitor.record.Collector.write();");
                logger.debug("Write Inserted");
                ct.insertBefore("String traceId = $1.getHeader(\"traceId\");" +
                        "String span = $1.getHeader(\"span\");\n" +
                        "String remainderString = $1.getHeader(\"remainder\");" +
                        "String appId = $1.getHeader(\"appId\");" +
                        "String appName = $1.getHeader(\"appName\");" +
                        "String method = $1.getMethod();" +
                        "String requestURI = $1.getRequestURI();" +
                        "com.danlu.dlmonitor.agent.Interceptor.httpBegin(traceId,span,remainderString,appId,appName,method,requestURI);");
                logger.debug("start Inserted");
                ct.insertAfter("com.danlu.dlmonitor.agent.Interceptor.httpEnd();");

            //MyBatis拦截
            }else if(MYBATIS_START.equals(className)){
                cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod ct = cl.getDeclaredMethod("prepare");
                ct.insertAfter("com.danlu.dlmonitor.agent.Interceptor.addSQL(delegate.getBoundSql().getSql());");

            //Dubbo入口
            }else if(DUBBO_START.equals(className)){
                cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod[] methods = cl.getMethods();
                for(CtMethod method:methods){
                    if (method.getName().equals("doBeforeInvokeReturn")){
                        method.insertAfter("String x = ($1.getAttachment(\"remainder\")==null||" +
                                "$1.getAttachment(\"remainder\").equals(\"\"))?\"0\":$1.getAttachment(\"remainder\");" +
                                "com.danlu.dlmonitor.agent.Interceptor.addRPCTrace(" +
                                "$1.getAttachment(\"traceId\")," +
                                "$1.getAttachment(\"span\")," +
                                "Integer.valueOf(x).intValue()," +
                                "$1.getAttachment(\"appId\")," +
                                "$1.getAttachment(\"appName\"));");
                    }
                }

            //Dubbo出口
            }else if (DUBBO_END.equals(className)){
                cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod[] methods = cl.getMethods();
                for(CtMethod method:methods){
                    if (method.getName().equals("doBeforeInvokeReturn")){
                        method.insertBefore("com.danlu.dlmonitor.record.Collector.write();");
                        method.insertAfter("$1.setAttachment(\"traceId\", com.danlu.dlmonitor.agent.Interceptor.getTraceId());" +
                                "$1.setAttachment(\"span\", com.danlu.dlmonitor.agent.Interceptor.getSpan());" +
                                "$1.setAttachment(\"remainder\", String.valueOf(com.danlu.dlmonitor.agent.Interceptor.getRemainder()));" +
                                "$1.setAttachment(\"appId\", com.danlu.dlmonitor.agent.Interceptor.getAppId());" +
                                "$1.setAttachment(\"appName\", com.danlu.dlmonitor.agent.Interceptor.getAppName());");
                    }else if(method.getName().equals("invoke")){
                        method.insertAfter("com.danlu.dlmonitor.agent.Interceptor.endRPC();");
                    }
                }

            }else if(HTTPX_START.equals(className)){

                pool.insertClassPath(new LoaderClassPath(loader));
                cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));

                CtMethod jsonMethod = cl.getDeclaredMethod("buildJsonEntity");
                CtMethod textMethod = cl.getDeclaredMethod("buildTextEntity");

                if(jsonMethod==null||textMethod==null){
                    return null;
                }

                String code ="String traceId = com.danlu.dlmonitor.agent.Interceptor.getTraceId();" +
                        "        String span = com.danlu.dlmonitor.agent.Interceptor.getSpan();" +
                        "        int remainder = com.danlu.dlmonitor.agent.Interceptor.getRemainder();" +
                        "        String appId = com.danlu.dlmonitor.agent.Interceptor.getAppId();" +
                        "        String appName = com.danlu.dlmonitor.agent.AppInfo.getInstance().getAppName();" +
                        "        if (!org.apache.commons.lang.StringUtils.isEmpty(traceId))" +
                        "        {" +
                        "            $1.add(\"traceId\", traceId);" +
                        "        }" +
                        "        if (!org.apache.commons.lang.StringUtils.isEmpty(span))" +
                        "        {" +
                        "            $1.add(\"span\", span);" +
                        "        }" +
                        "        if (!org.apache.commons.lang.StringUtils.isEmpty(appId))" +
                        "        {" +
                        "            $1.add(\"appId\", appId);" +
                        "        }" +
                        "        if(!org.apache.commons.lang.StringUtils.isEmpty(appName)) {" +
                        "            $1.add(\"appName\", appName);" +
                        "        }" +
                        "        $1.add(\"remainder\", String.valueOf(remainder));";
                jsonMethod.insertBefore(code);
                textMethod.insertBefore(code);

            }else if(DISCONF_START.equals(className)){

                cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod ct = cl.getDeclaredMethod("setLocations");
                if(ct==null){
                    return null;
                }
                ct.insertBefore("com.danlu.dlmonitor.agent.Interceptor.modifyDisconf($1);");

            }else if(DLCODIS_START.equals(className)){

                cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtBehavior[] behaviors = cl.getDeclaredBehaviors();
                for(CtBehavior behavior:behaviors){
                    if(behavior.getName().equals("listRightPush")||behavior.getName().equals("<init>")){
                        continue;
                    }
                    behavior.insertBefore("com.danlu.dlmonitor.agent.Interceptor.codisUse(\""+ behavior.getName() +"\");");
                }

            }else{

                ClassReader cr = new ClassReader(classfileBuffer);
                ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
                ClassAdapter ca = new MonitorClassAdapter(cw);
                cr.accept(ca, ClassReader.SKIP_DEBUG);

                return cw.toByteArray();
            }
            trans = cl.toBytecode();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cl!=null){
                cl.detach();
            }
        }
        return trans;
    }
}
