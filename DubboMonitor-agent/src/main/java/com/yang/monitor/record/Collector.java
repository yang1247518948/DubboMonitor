package com.yang.monitor.record;



import com.yang.monitor.agent.XMLReader;
import com.yang.monitor.netty.NettyClientHandler;
import com.yang.monitor.netty.RecordDecoder;
import com.yang.monitor.netty.RecordEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.yang.monitor.record.Collector.WriteThread.handler;

/**
 * 数据缓冲区以及写入类
 */
public class Collector {

	private static ConcurrentLinkedQueue<Record> writeQueue;
	private static ConcurrentLinkedQueue<Record> readQueue;
	private static ConcurrentLinkedQueue<Record> bufferOne = new ConcurrentLinkedQueue<>();
	private static ConcurrentLinkedQueue<Record> bufferTwo = new ConcurrentLinkedQueue<>();

	static Logger logger = LoggerFactory.getLogger(Collector.class);

	private static boolean writeToOne = true;
	public static boolean sendFlag = false;
	private static boolean stopFlag = false;

	private static WriteThread writeThread = new WriteThread();
	private static boolean isStart = false;

	public static ChannelFutureListener listener = new ChannelFutureListener() {
		@Override
		public void operationComplete(ChannelFuture channelFuture) throws Exception {
			if(channelFuture.isSuccess()){
				readQueue.clear();
				sendFlag = false;
			}
		}
	};

	static {
		writeQueue = bufferOne;
		readQueue = bufferTwo;
	}


	private static void initService() throws Exception {

	}

	/**
	 * 开启线程
	 */
	public static void write() {
		try {
			if (!isStart) {
				try {
					initService();
				} catch (Exception e) {
					e.printStackTrace();
					stopFlag = true;
					return;
				}
				new Thread(writeThread).start();
				isStart = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 添加一条标准调用日志
	 *
	 * @param record 记录实例
	 */
	static public void addOne(Record record) {
		if (stopFlag) {
			return;
		}
		writeQueue.add(record);
		send();

	}

	@Deprecated
	static public void addOneAppUse(Record record) {
		if (stopFlag) {
			return;
		}
		writeQueue.add(record);
		send();
	}

	/**
	 * 发送当前writeQueue中所有数据
	 */
	private static void send() {
		if (writeQueue.size() >= 30) {
			if (writeToOne && !sendFlag) {
				writeQueue = bufferTwo;
				readQueue = bufferOne;
				writeToOne = false;
				handler.send(readQueue);
			} else if (!sendFlag) {
				writeQueue = bufferOne;
				readQueue = bufferTwo;
				writeToOne = true;
				handler.send(readQueue);
			}

		}
	}

	/**
	 * 线程实现类
	 */
	static class WriteThread implements Runnable {
		int port = Integer.valueOf(XMLReader.readNettyPort());
		String IP = XMLReader.readNettyIP();

		static NettyClientHandler handler = new NettyClientHandler();

		/**
		 * 启动netty并阻塞，形成事件驱动
		 */
		public void run() {
			EventLoopGroup group = new NioEventLoopGroup();

			try {
				Bootstrap b = new Bootstrap();
				b.group(group);
				b.channel(NioSocketChannel.class);
				b.remoteAddress(new InetSocketAddress(IP, port));
				b.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new RecordDecoder(),new RecordEncoder(), handler);
					}
				});
				ChannelFuture f = b.connect().sync();

				f.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				try {
					group.shutdownGracefully().sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 关闭代理
	 */
	public static void openAgent(){
		stopFlag = false;
	}

	/**
	 * 开启代理
	 */
	public static void closeAgent(){
		stopFlag = true;
	}

}


