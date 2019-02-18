package com.jmhqmc.demo.aspect;

//import com.rabbitmq.client.messageproperties;
import com.jmhqmc.demo.domain.Person;
import com.jmhqmc.demo.mq.Producer;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PersonServiceAspect {

	@Autowired
	Producer producer;
	

	// @Pointcut("@annotation(com.jmhqmc.demo.service.PersonService)")
	public PersonServiceAspect() {
		System.err.println(this.getClass());
	}

	// @Pointcut("execution(* com.jmhqmc.demo.service.PersonService..*.*(..))")
	// public void cut(){
	// System.err.println(this.getClass());
	// }

	@After("execution(* com.jmhqmc.demo.service.PersonService.insert(com.jmhqmc.demo.domain.Person,java.lang.String)) && args(object, collectionName)")
	@Async 
	public void insertAfter(Person object, String collectionName) {
		System.err.println(object + " " + collectionName);

		MessageProperties mp = new MessageProperties();
		mp.setContentEncoding("UTF-8");
		mp.setContentType(MessageProperties.CONTENT_TYPE_JSON);
		
//		Consumer<Message> consumer = new Consumer<Message>() {
//			public void accept(Message s) throws Exception {
//				//Thread.sleep(15000);
//				for(int i=0;i<1000000;i++) {
//					System.err.print("a");
//				}
//				System.out.println();
//				producer.sendMessage(s);
//				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//			}
//		};

		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		Message msg = new Message(object.toString().getBytes(), mp);
		producer.sendMessage(msg);
//		Observable.just(msg).subscribe(consumer);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	}


	@After("execution(* com.jmhqmc.demo.service.PersonService.insert(com.jmhqmc.demo.domain.Person,java.lang.String)) && args(object, collectionName)")
	@Async
	public void insertAfter1(Person object, String collectionName) {
		System.err.println(object + " " + collectionName);

		MessageProperties mp = new MessageProperties();
		mp.setContentEncoding("UTF-8");
		mp.setContentType(MessageProperties.CONTENT_TYPE_JSON);

//		Consumer<Message> consumer = new Consumer<Message>() {
//			public void accept(Message s) throws Exception {
//				//Thread.sleep(15000);
//				for(int i=0;i<1000000;i++) {
//					System.err.print("a");
//				}
//				System.out.println();
//				producer.sendMessage(s);
//				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//			}
//		};

		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		Message msg = new Message(object.toString().getBytes(), mp);
		producer.sendMessage(msg);
//		Observable.just(msg).subscribe(consumer);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	}


}
