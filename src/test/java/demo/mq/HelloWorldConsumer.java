package demo.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class HelloWorldConsumer {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws IOException, TimeoutException {

		ConnectionFactory cf = new ConnectionFactory();
		// rabbitmq监听IP
		cf.setHost("127.0.0.1");
		// rabbitmq默认监听端口，注意要记得开启端口
		cf.setPort(5672);

		// 设置访问的用户
		cf.setUsername("guest");
		cf.setPassword("guest");
		// 建立连接
		Connection conn = cf.newConnection();
		// 创建消息通道
		Channel channel = conn.createChannel();

		// 创建hello队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" Waiting for msg....");
		// 创建消费者，并接受消息
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String msg = new String(body, "UTF-8");
				System.out.println("Received is = '" + msg + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
		channel.close();
	}

}