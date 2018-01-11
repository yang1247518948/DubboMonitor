package com.swpu.DubboMonitor.agent;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截相关的一系列内容<br>
 *     单例
 * <p>具体为白名单，黑名单，AppName以及相关的白名单，黑名单添加以及校验方法</p>
 * @author dengyu
 **/
public class AppInfo {

	private String appName = System.getProperty("agent.appName");
	private List<String> whiteList = new ArrayList<>();
	private List<String> blackList = new ArrayList<>();


	/**
	 * 单例获取
	 * @return 单例对象
	 */
	public static AppInfo getInstance(){
		return SingletonHolder.singleton;
	}

	private AppInfo(){
		blackList.add("com.danlu.dlmonitor.utils.*");
		blackList.add("com.danlu.dlmonitor.traceClass.*");
		blackList.add("com.danlu.dlmonitor.record.*");
		blackList.add("com.danlu.dlmonitor.filter.*");
		blackList.add("com.danlu.dlmonitor.agent.*");
		blackList.add("com.danlu.dlmonitor.transation.*");
		blackList.add("com.danlu.dlcommon.*");
		blackList.add("com.danlu.dlcodis.utils.*");
		blackList.add("com.danlu.dlhttpx.handler.*");
		blackList.add("com.danlu.dlhttpx.converter.*");
		blackList.add("com.danlu.dlhttpx.mapper.*");
		blackList.add("com.danlu.dlhttpx.ex.*");
		blackList.add("com.danlu.dlhttpx.util.*");
		blackList.add("com.danlu.dlhttpx.HttpExtractor.*");
		blackList.add("com.danlu.dlcodis.I.*");
	}

	public String getAppName(){
		return appName;
	}

	public void setAppName(String appName){
		this.appName = appName;
	}


	/**
	 * 添加白名单包名
	 * @param className 包名
	 */
	void addWhiteListItem(String className){
		whiteList.add(className);
	}

	/**
	 * 添加黑名单包名
	 * @param className 包名
	 */
	void addBlackListItem(String className){
		blackList.add(className);
	}

	/**
	 * 匹配是否符合白名单条件
	 * @param className 需要判断的类名
	 * @return 匹配则返回true
	 */
	public boolean matchWhiteList(String className){
		if(className==null){
			return false;
		}
		for (String name: whiteList){
			if(className.matches(name)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 匹配是否符合黑名单条件
	 * @param className 需要判断的类名
	 * @return 匹配则返回true
	 */
	public boolean matchBlackList(String className){
		if(className==null){
			return false;
		}
		for (String name: blackList){
			if(className.matches(name)){
				return true;
			}
		}
		return false;
	}


	/**
	 * 内部单例类
	 */
	private static class SingletonHolder{
		private static AppInfo singleton = new AppInfo();
	}
}
