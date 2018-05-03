package com.yang.monitor.service.washer.netty;


import com.yang.monitor.netty.RecordDecoder;
import com.yang.monitor.netty.RecordEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author yangping
 * @date 2017.12.25 15:32
 **/
public class NettyWorker implements Runnable {
	private int port = 8000;

	@Override
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childOption(ChannelOption.SO_KEEPALIVE,true);
			b.childHandler(new ChannelInitializer<SocketChannel>() {//有连接到达时会创建一个channel
				protected void initChannel(SocketChannel ch) throws Exception {
					// pipeline管理channel中的Handler，在channel队列中添加一个handler来处理业务
					ch.pipeline().addLast(new RecordDecoder(),new RecordEncoder(),new NettyServiceHandler());
				}
			});

			// 服务器绑定端口监听
			ChannelFuture f = b.bind(port).sync();
			// 监听服务器关闭监听
			f.channel().closeFuture().sync();

			// 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
