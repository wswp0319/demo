package demo.netty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class TestHandler extends ChannelInboundHandlerAdapter {
	private ChannelHandlerContext ctx;
	private double longitude = 114.239248d;
	private double latitude = 30.611607d;
	private BigDecimal xp = new BigDecimal(0.00065d);
	private BigDecimal yp = new BigDecimal(0.00060d);
	private DecimalFormat format = new DecimalFormat();

	public void move() {
		format.setMaximumFractionDigits(6);
		format.setRoundingMode(RoundingMode.UP);
		if (longitude < 114.335259d) {
			BigDecimal bigDecimal = new BigDecimal(longitude);
			longitude = Double.valueOf(format.format(bigDecimal.add(xp).doubleValue()));
		}
		if (latitude < 30.643056d) {
			BigDecimal bigDecimal = new BigDecimal(latitude);
			latitude = Double.valueOf(format.format(bigDecimal.add(yp).doubleValue()));
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelActive();
		this.ctx = ctx;
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("停止时间是：" + new Date());
		System.out.println("HeartBeatClientHandler channelInactive");
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.ALL_IDLE) {
				// move();
				// JSONObject js = new JSONObject();
				// js.accumulate("command", 1);
				// js.accumulate("mobile", "13858588888");
				// js.accumulate("longitude", longitude);
				// js.accumulate("latitude", latitude);
				// js.accumulate("plateNumber", "鄂Aj43512");
				// System.out.println("循环触发时间：" + new Date() + "\t" + js.toString());
				// ctx.channel().writeAndFlush(js.toString());

				ctx.channel().writeAndFlush("{\"command\":0,\"mobile\":\"18888888888\"}");

			}
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// String message = (String) msg;
		// System.out.println(message);
		// if (message.equals("Heartbeat")) {
		// ctx.write("has read message from server");
		// ctx.flush();
		// }
		// ReferenceCountUtil.release(msg);
		System.err.println(msg);
	}

	public void send(Object msg) {
		ctx.channel().writeAndFlush(msg);
	}

	public void close() {
		ctx.channel().close();
	}

	public static void main(String[] args) {
		TestHandler t = new TestHandler();
		for (int i = 0; i < 10; i++) {
			System.out.println("X: " + t.longitude + " Y: " + t.latitude);
			t.move();
		}
	}
}
