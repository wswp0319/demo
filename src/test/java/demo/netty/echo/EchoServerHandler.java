package demo.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 服务端回调方法 Created by tianjun on 2016/12/19 0019.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
		System.out.println("server 读取数据……");
		// 读取数据
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("接收客户端数据:" + body);
		// 向客户端写数据

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		System.out.println("server 读取数据完毕..");
		// System.out.println("server向client发送数据");
		// String currentTime = new Date(System.currentTimeMillis()).toString();
		// ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		// ctx.writeAndFlush(resp);
		// ctx.flush();//刷新后才将数据发出到SocketChannel
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();

	}
}