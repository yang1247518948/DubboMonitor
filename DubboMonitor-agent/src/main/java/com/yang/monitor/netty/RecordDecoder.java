package com.yang.monitor.netty;

import com.yang.monitor.record.Record;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 *
 * 自定义netty解码器
 *
 * @author yangping
 * @date 2017.12.25 11:45
 **/
//todo 2017.12.27 封装message为统一对象，修改解码器
public class RecordDecoder extends ByteToMessageDecoder {
	private ObjectMapper mapper = new ObjectMapper();

	private static final int HEAD_LENGTH = 4;

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		if (byteBuf.readableBytes() < HEAD_LENGTH) {
			return;
		}
		//标记位置，用于回退
		byteBuf.markReaderIndex();

		//获取传入流的消息类型
		int type = byteBuf.readInt();

		//根据消息类型使用不同的解码方式
		switch (type){
			case NettyMessageType.CLIENT_DATA: {
				int dataLength = byteBuf.readInt();
				if (dataLength < 0) {
					channelHandlerContext.close();
				}
				//读到的消息体长度如果小于传送消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
				if (byteBuf.readableBytes() < dataLength) {
					byteBuf.resetReaderIndex();
					return;
				}
				// 读到的长度要求，取出数据
				byte[] body = new byte[dataLength];
				byteBuf.readBytes(body);
				//反序列化
				Object obj = queueConvertToObject(body);
				list.add(obj);
				break;
			}
			case NettyMessageType.CLIENT_INFO: {
				int dataLength = byteBuf.readInt();
				if (dataLength < 0) {
					channelHandlerContext.close();
				}
				//读到的消息体长度如果小于传送消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
				if (byteBuf.readableBytes() < dataLength) {
					byteBuf.resetReaderIndex();
					return;
				}
				// 读到的长度要求，取出数据
				byte[] body = new byte[dataLength];
				byteBuf.readBytes(body);
				//反序列化
				Object obj = infoConvertToObject(body);
				list.add(obj);
				break;
			}
			case NettyMessageType.CLIENT_HEAVE_LOAD:
			case NettyMessageType.AGENT_CLOSE_MESSAGE:
			case NettyMessageType.AGENT_OPEN_MESSAGE:
				list.add(type);
				break;
			default:{

			}
		}
	}

	/**
	 * 传递的是queue，其中包含记录的数据
	 * @param body 字节流
	 * @return queue对象
	 */
	private Object 	queueConvertToObject(byte[] body) {

		JavaType javaType = mapper.getTypeFactory().constructParametricType(ConcurrentLinkedQueue.class, Record.class);
		try {
			return  mapper.readValue(body,javaType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 传递的是ClienInfo，其中包含记录的数据
	 * @param body 字节流文件
	 * @return queue对象
	 */
	private Object 	infoConvertToObject(byte[] body) {
		try {
			return  mapper.readValue(body,ClientInfo.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
