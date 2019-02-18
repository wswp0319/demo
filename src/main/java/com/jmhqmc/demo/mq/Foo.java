package com.jmhqmc.demo.mq;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class Foo implements MessageListener {
	// public void listen(Message message) {
	// System.out.println(" [x] Received '" + message + "'");
	// }

	public void onMessage(Message message) {
		String str = "";
		Map map = message.getMessageProperties().getHeaders();
		try {
			str = new String(message.getBody(), "UTF-8");
			System.out.println("=============监听【QueueListenter】消息" + message);
			System.out.print("=====获取消息" + str);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}