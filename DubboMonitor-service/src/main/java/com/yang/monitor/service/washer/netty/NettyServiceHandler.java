package com.yang.monitor.service.washer.netty;


import com.yang.monitor.core.dto.Record;
import com.yang.monitor.netty.ClientInfo;
import com.yang.monitor.service.washer.EHCacheService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author yangping
 * @date 2017.12.25 15:38
 **/
public class NettyServiceHandler extends SimpleChannelInboundHandler {


	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {

		if(object instanceof ConcurrentLinkedQueue) {

			ConcurrentLinkedQueue<Record> queue = (ConcurrentLinkedQueue<Record>) object;

			EHCacheService.getInstance().addQueueToCache(
					channelHandlerContext.channel().id().asShortText()+System.currentTimeMillis(),
					queue
			);

		}else if(object instanceof ClientInfo){
			System.out.println(((ClientInfo) object).getAppName());
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		Channel channel = ctx.channel();
		String id = ctx.channel().id().asLongText();
		ClientService.addChannel(id,channel);


//		ctx.writeAndFlush(NettyMessageType.AGENT_CLOSE_MESSAGE);
//		ctx.writeAndFlush(NettyMessageType.AGENT_OPEN_MESSAGE);
	}

}
