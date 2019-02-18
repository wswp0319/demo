package demo.netty;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jmhqmc.demo.net.NettyServer;
import com.jmhqmc.demo.net.NettyTCPServer;

public class TestServer {

	public static void main(String[] args) {
		ApplicationContext ac = new FileSystemXmlApplicationContext(
				"D:\\mcworkspace\\demo\\src\\test\\java\\demo\\server.xml");
		NettyTCPServer server = (NettyTCPServer) ac.getBean("tcpServer");
		
		String log4j = "D:\\mcworkspace\\demo\\src\\main\\webapp\\WEB-INF\\conf\\log4j.properties";

		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(new FileInputStream(log4j)));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		PropertyConfigurator.configure(properties);
		Logger logger = Logger.getLogger(TestServer.class.getName());
		try {
			server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Server start.");
		
//		ServerManager manager = new ServerManager();
//		try {
//			manager.startServer();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
