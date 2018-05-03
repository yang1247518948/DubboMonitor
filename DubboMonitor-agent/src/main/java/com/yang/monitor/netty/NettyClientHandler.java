package com.yang.monitor.netty;


import com.yang.monitor.agent.AppInfo;
import com.yang.monitor.record.Collector;
import com.yang.monitor.record.Record;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentLinkedQueue;

//todo 将message包装为一个类，统一处理

/**
 * 代理部分的netty逻辑处理
 * @author yangping
 * @date 2017.12.25 16:04
 **/
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {
	private ChannelHandlerContext ctx;

	/**
	 * 获取到传递的数据时的处理
	 * @param channelHandlerContext 对应channel上下文
	 * @param msg 收到的对象
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
		int x = (int) msg;
		switch (x){
			case NettyMessageType.AGENT_CLOSE_MESSAGE:
				Collector.closeAgent();
				break;
			case NettyMessageType.AGENT_OPEN_MESSAGE:
				Collector.openAgent();
				break;
			case NettyMessageType.AGENT_STATUS_QUARY:
				ClientInfo info = new ClientInfo();
				info.setAppName(AppInfo.getInstance().getAppName());
				ctx.writeAndFlush(info);
				break;
		}

	}

	/**
	 * 信道激活时的函数
	 * @param ctx 信道上下文
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
		ClientInfo info = new ClientInfo();
		info.setAppName(AppInfo.getInstance().getAppName());
		ctx.writeAndFlush(info);
	}

	/**
	 * 发送当前对列中数据
	 * @param queue 队列
	 */
	public void send(ConcurrentLinkedQueue<Record> queue){
		Collector.sendFlag = true;
		ChannelFuture future = ctx.writeAndFlush(queue);
		future.addListener(Collector.listener);
	}
}
