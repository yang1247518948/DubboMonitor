package com.yang.monitor.netty;

/**
 * @author yangping
 * @date 2017.12.26 14:21
 **/
public class ClientInfo {

	//todo 添加负载查看
	private String IP;
	private String appName;
	private String agentStatus;

	public String getIP() {
		return IP;
	}

	public void setIP(String IP) {
		this.IP = IP;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(String agentStatus) {
		this.agentStatus = agentStatus;
	}
}
