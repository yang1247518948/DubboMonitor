package com.yang.monitor.netty;

import com.yang.monitor.record.Record;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * netty编码器
 *
 * @author yangping
 * @date 2017.12.25 11:19
 **/
public class RecordEncoder extends MessageToByteEncoder {

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
		if(object instanceof ConcurrentLinkedQueue) {
			byte[] body = queueConvertToBytes((ConcurrentLinkedQueue<Record>) object);
			int dataLength = body.length;
			byteBuf.writeInt(NettyMessageType.CLIENT_DATA);
			byteBuf.writeInt(dataLength);
			byteBuf.writeBytes(body);
		}else if(object instanceof ClientInfo){
			byte[] body = infoConvertToBytes((ClientInfo) object);
			int dataLength = body.length;
			byteBuf.writeInt(NettyMessageType.CLIENT_INFO);
			byteBuf.writeInt(dataLength);
			byteBuf.writeBytes(body);
		}else if (object instanceof Integer){
			int msg = (int) object;
			byteBuf.writeInt(msg);
		}
	}

	private byte[] infoConvertToBytes(ClientInfo object) {
		try {
			return mapper.writeValueAsBytes(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte[] queueConvertToBytes(ConcurrentLinkedQueue<Record> record) {
		try {
			return mapper.writeValueAsBytes(record);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
