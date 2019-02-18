package com.jmhqmc.demo.net;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public abstract class AbstractNettyServer implements NettyServer {
	// 用于管理所有的channel
	public static final ChannelGroup ALL_CHANNELS = new DefaultChannelGroup("NADRON-CHANNELS",
			GlobalEventExecutor.INSTANCE);
	protected final NettyConfig nettyConfig;
	protected ChannelInitializer<? extends Channel> channelInitializer;

	public AbstractNettyServer(NettyConfig nettyConfig, ChannelInitializer<? extends Channel> channelInitializer) {
		this.nettyConfig = nettyConfig;
		this.channelInitializer = channelInitializer;
	}

	public void startServer(int port) throws Exception {
		nettyConfig.setPortNumber(port);
		nettyConfig.setSocketAddress(new InetSocketAddress(port));
		startServer();
	}

	public void startServer(InetSocketAddress socketAddress) throws Exception {
		nettyConfig.setSocketAddress(socketAddress);
		startServer();
	}

	public void stopServer() throws Exception {
		ChannelGroupFuture future = ALL_CHANNELS.close();
		try {
			future.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (null != nettyConfig.getBossGroup()) {
				nettyConfig.getBossGroup().shutdownGracefully();
			}
			if (null != nettyConfig.getWorkerGroup()) {
				nettyConfig.getWorkerGroup().shutdownGracefully();
			}
		}
	}

	public ChannelInitializer<? extends Channel> getChannelInitializer() {
		return channelInitializer;
	}

	// 获取configuration @link(NettyConfig.class)
	public NettyConfig getNettyConfig() {
		return nettyConfig;
	}

	// 获取bossGroup，在spring中配置
	protected EventLoopGroup getBossGroup() {
		return nettyConfig.getBossGroup();
	}

	// 获取workerGroup， 在spring中配置
	protected EventLoopGroup getWorkerGroup() {
		return nettyConfig.getWorkerGroup();
	}

	public InetSocketAddress getSocketAddress() {
		return nettyConfig.getSocketAddress();
	}

	public String toString() {
		return "NettyServer [socketAddress=" + nettyConfig.getSocketAddress() + ", portNumber="
				+ nettyConfig.getPortNumber() + "]";
	}

}