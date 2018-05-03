package com.yang.monitor.service.washer.netty;

import com.yang.monitor.netty.NettyMessageType;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yangping
 * @date 2017.12.26 9:22
 **/
public class ClientService {
	private static Map<String,Channel> channels = new ConcurrentHashMap<>();
	private static Map<String,String>  channelNames = new ConcurrentHashMap<>();

	public static void addChannel(String id, Channel channel){
		channels.put(id,channel);
	}

	public static void removeChannel(String id){
		channels.remove(id);
		channelNames.remove(id);
	}

	public static void addChannelName(String id, String name){
		channelNames.put(id,name);
	}

	public static Set<String> getIDs(){
		return channels.keySet();
	}

	public static String getName(String id){
		return  channelNames.get(id);
	}

	public static void closeAllAgent(){
		for (String ii : channels.keySet()) {
			channels.get(ii).writeAndFlush(NettyMessageType.AGENT_CLOSE_MESSAGE);
		}
	}

	public static void openAllAgent(){
		for (String ii : channels.keySet()) {
			channels.get(ii).writeAndFlush(NettyMessageType.AGENT_OPEN_MESSAGE);
		}
	}

	public static void closeAgentByID(String id){
		channels.get(id).writeAndFlush(NettyMessageType.AGENT_CLOSE_MESSAGE);
	}

	public static void openAgentByID(String id){
		channels.get(id).writeAndFlush(NettyMessageType.AGENT_OPEN_MESSAGE);
	}
}
