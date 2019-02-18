package com.jmhqmc.demo.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息队列发送者
 * @Author:
 * @CreateTime:
 */
@Service("amqp")
public class Producer {

	@Autowired
	private AmqpTemplate amqpTemplate;

	// private void sendQueue(String exchange_key, String queue_key, Message object)
	// {
	// // convertAndSend 将Java对象转换为消息发送至匹配key的交换机中Exchange
	// amqpTemplate.send(exchange_key, queue_key, object);
	// }
	public void sendMessage(Message object) {
		System.out.println("sendMessage begin");
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		amqpTemplate.send("test", "test_mq", object);
		// sendQueue("test", "test_mq", object);
		System.out.println("sendMessage end");
	}
}