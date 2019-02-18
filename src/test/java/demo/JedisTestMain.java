package demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.google.protobuf.InvalidProtocolBufferException;
import com.jmhqmc.demo.proto2.StudentProtos.Student;

public class JedisTestMain {

	public static void main(String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(50);
		config.setMinIdle(8);// 设置最小空闲数
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		// Idle时进行连接扫描
		config.setTestWhileIdle(true);
		// 表示idle object evitor两次扫描之间要sleep的毫秒数
		config.setTimeBetweenEvictionRunsMillis(30000);
		// 表示idle object evitor每次扫描的最多的对象数
		config.setNumTestsPerEvictionRun(10);
		// 表示一个对象至少停留在idle状态的最短时间，然后才能被idle object
		// evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
		config.setMinEvictableIdleTimeMillis(60000);

		JedisPool pool = new JedisPool(config, "192.168.0.211", 6379, 10000, "123456", 0);
		Jedis j = pool.getResource();
		// j.append("aa", "bb");

		System.out.println(j.get("ak"));
		System.out.println(j.get("da4d173b612540bcb7ef781561df6dc3"));

		Student.Builder sb = Student.newBuilder();
		sb.setName("小明");
		sb.setSex(1);
		sb.setHobby("撩妹");
		sb.setSkill("吹牛逼");

		Student student = sb.build();
		System.out.println(student.toString());
		
		byte[] array = student.toByteArray();

		try {
		    Student student1 = Student.parseFrom(array);
		    System.out.println(student1.toString());
		} catch (InvalidProtocolBufferException e) {
		    e.printStackTrace();
		}  
	}

}
