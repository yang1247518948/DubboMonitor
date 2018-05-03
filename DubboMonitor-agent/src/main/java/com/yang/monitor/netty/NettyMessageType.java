package com.yang.monitor.netty;

/**
 * @author yangping
 * @date 2017.12.26 11:05
 **/
public class NettyMessageType {
	//service send to client
	public static final int AGENT_CLOSE_MESSAGE = 0x11;
	public static final int AGENT_OPEN_MESSAGE = 0x12;
	public static final int AGENT_STATUS_QUARY = 0x13;

	//client send to service
	public static final int CLIENT_INFO = 0x21;
	public static final int CLIENT_DATA = 0x22;
	public static final int CLIENT_HEAVE_LOAD = 0x23;

}
