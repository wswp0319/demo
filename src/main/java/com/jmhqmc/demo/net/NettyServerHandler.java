package com.jmhqmc.demo.net;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jmhqmc.demo.service.CallService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import net.sf.json.JSONObject;

@Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter{
	private int loss_connect_time = 0;
	public static final int HEART_BEATS = 0;//乘客端上行心跳消息
	public static final int UPDATE_CAR_POSITION = 1;//乘客端上行更新专车坐标
	public static final int SUBSCRIBE = 2;//乘客端上行预定
	public static final int FIND_CAR = 3;//乘客端上行找车
	public static final int ACCEPT = 4;//专车司机端上行接单
	public static final int NOTICE = 5;//服务端下行通知
	public static final int START = 6;//专车司机上行开始行程
	public static final int FINISH = 7;//专车司机上行结束行程
	public static final int PAY = 8;//乘客端上行支付
	public static final int EVALUATION = 9;//司机、乘客端相互评价
	public static final int END = 100;//司机、乘客端结束消息
	private CallService callService;
	private Map<String,ChannelHandlerContext> session = new ConcurrentHashMap<String,ChannelHandlerContext>();
	
	public NettyServerHandler() {
		
	}
	
	protected String doService(final ChannelHandlerContext ctx,String message) {
		JSONObject jo = JSONObject.fromObject(message);
		int command = Integer.parseInt(jo.getString("command"));
		String m = (String)jo.get("mobile");
		String returnValue = null;
		ChannelHandlerContext c = null;
		String msg = null;
		
		switch(command) {
			case HEART_BEATS:
				//保存客户端连接
				//"{"command":0,"mobile":"18888888888"}
				session.put(jo.getString("mobile"), ctx);
				break;
			case UPDATE_CAR_POSITION:
				//{"command":1,"mobile":"15812345678","longitude":114.239248,"latitude":30.611607,"plateNumber":"鄂ABS168"}
				returnValue = callService.updateCarPosition(jo);
				ctx.writeAndFlush(returnValue);
				break;
			case SUBSCRIBE:
				//{"command":2,"consumerMobile":"18888888888","producerMobile":"15812345678","plateNumber":"鄂ABS168"}
				returnValue = callService.subscribe(jo);
				ctx.writeAndFlush(returnValue);
				//通知乘客司机已经有司机抢单
				c = session.get("consumerMobile");
				msg = "{\"command\":4,\"message\":\"司机已接单\",\"producerMobile\":\"15812345678\",\"plateNumber\":\"鄂ABS168\"}";
				notify(c,msg);
				break;
			case FIND_CAR:
				//用户上行找车返回找车结果
				//{"command":3,"mobile":"18888888888","longitude":114.639248,"latitude":30.811607}
				final String s = callService.findCar(jo);
				Thread t = new Thread() {
					public void run() {
						for(int i=0;i<10;i++) {
							ctx.writeAndFlush(s);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				};
				t.start(); 
				
				break;
			case ACCEPT:
				//{"command":4,"consumerMobile":"18888888888","producerMobile":"15812345678","plateNumber":"鄂ABS168"}
				returnValue = callService.accept(jo);
				ctx.writeAndFlush(returnValue);
				//通知乘客司机已经接单
				c = session.get("consumerMobile");
				msg = "{\"command\":4,\"message\":\"司机已接单\",\"producerMobile\":\"15812345678\",\"plateNumber\":\"鄂ABS168\"}";
				notify(c,msg);
				//case NOTICE:
				//returnValue = callService.notice(jo);
				//根据手机号码取出channel向手机号码对应的用户下发消息
				//c = session.get(m);
				//notify(ctx, message);
				break;
			case START:
				//{"command":6,"consumerMobile":"18888888888","producerMobile":"15812345678","plateNumber":"鄂ABS168"}
				returnValue = callService.start(jo);
				ctx.writeAndFlush(returnValue);
				//通知乘客系好安全带
				c = session.get("consumerMobile");
				msg = "{\"command\":4,\"message\":\"请系好安全带\"}";
				notify(c,msg);
				break;
			case FINISH:
				//{"command":7,"consumerMobile":"18888888888","producerMobile":"15812345678","plateNumber":"鄂ABS168"}
				returnValue = callService.finish(jo);
				ctx.writeAndFlush(returnValue);
				//通知乘客付费
				c = session.get("consumerMobile");
				msg = "{\"command\":4,\"message\":\"请支付\"}";
				notify(c,msg);
				break;
			case PAY:
				//{"command":8,"consumerMobile":"18888888888","producerMobile":"15812345678","plateNumber":"鄂ABS168"}
				returnValue = callService.pay(jo);
				ctx.writeAndFlush(returnValue);
				//通知司机挣多少钱
				break;
			case EVALUATION:
				returnValue = callService.evaluation(jo);
				break;
			case END:
				//关闭客户端连接
				c = session.get(m);
				c.close();
				c=null;
				session.remove(jo.get("mobile"));
				break;
		}
		
		return returnValue;
	}
	
	private void notify(ChannelHandlerContext ctx,String message) {
		if(ctx!=null) {
			ctx.writeAndFlush(message);
		}
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				loss_connect_time++;
				
				if (loss_connect_time > 2) {
					ctx.channel().close();
				}
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//System.out.println("server channelRead..");
		System.out.println(ctx.channel().remoteAddress() + "->Server :" + msg.toString());
		doService(ctx,msg.toString());
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//ctx.channel().writeAndFlush("{\"msg\":\"success\"}");
		//ctx.close();
    }

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	public CallService getCallService() {
		return callService;
	}

	public void setCallService(CallService callService) {
		this.callService = callService;
	}

	public static void main(String[] args) {
		JSONObject j = new JSONObject();
		j.accumulate("command", 1);
		j.accumulate("consumerMobile", "13958586969");
		j.accumulate("startLongitude", 104.01245514D);
		j.accumulate("slatLatitude",50.1231545d);
		j.accumulate("endLongitude", 104.984524D);
		j.accumulate("endLatitude",50.8541254d);
		System.out.println(j);
		
		JSONObject jo = JSONObject.fromObject("{\"command\":1,\"consumerMobile\":\"13958586969\",\"startLongitude\":104.01245514,\"slatLatitude\":50.1231545,\"endLongitude\":104.984524,\"endLatitude\":50.8541254}\r\n");
		System.err.println(jo.get("command"));
		System.err.println(jo.get("consumerMobile"));
		System.err.println(jo.get("startLongitude"));
		System.err.println(jo.get("slatLatitude"));
		System.err.println(jo.get("endLongitude"));
		System.err.println(jo.get("endLatitude"));
	}
}
