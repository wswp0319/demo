package com.jmhqmc.demo.net;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.timeout.IdleStateHandler;

@Sharable
public class JMChannelInitializer extends ChannelInitializer<SocketChannel> {
	private static final int MAX_IDLE_SECONDS = 5;
	private int idleSeconds;
	private String decoder;
	private String encoder;
	private ChannelInboundHandlerAdapter handler;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// ChannelPipeline pipeline = ch.pipeline();
//		ch.pipeline().addLast("idleStateCheck",
//				new IdleStateHandler(MAX_IDLE_SECONDS, MAX_IDLE_SECONDS, MAX_IDLE_SECONDS));
		ch.pipeline().addLast(new IdleStateHandler(0, 0, MAX_IDLE_SECONDS, TimeUnit.SECONDS));
		ch.pipeline().addLast("decoder", (MessageToMessageDecoder) Class.forName(decoder).newInstance());
		ch.pipeline().addLast("encoder", (MessageToMessageEncoder) Class.forName(encoder).newInstance());
		ch.pipeline().addLast(handler);
	}

	public int getIdleSeconds() {
		return idleSeconds;
	}

	public void setIdleSeconds(int idleSeconds) {
		this.idleSeconds = idleSeconds;
	}

	public String getDecoder() {
		return decoder;
	}

	public void setDecoder(String decoder) {
		this.decoder = decoder;
	}

	public String getEncoder() {
		return encoder;
	}

	public void setEncoder(String encoder) {
		this.encoder = encoder;
	}

	public ChannelInboundHandlerAdapter getHandler() {
		return handler;
	}

	public void setHandler(ChannelInboundHandlerAdapter handler) {
		this.handler = handler;
	}

}