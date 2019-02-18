package demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jmhqmc.demo.domain.Person;
import com.jmhqmc.demo.service.CallService;
import com.jmhqmc.demo.service.PersonService;

import net.sf.json.JSONObject;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class DubboConsumer {

	public static void main(String[] args) {
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(new String[]{"D:\\idea\\demo\\src\\test\\java\\demo\\test.xml"});
		context.start();

//		PersonService personService = (PersonService) context.getBean("personService");
//		JSONObject obj = personService.findAll("test");
//		System.out.println(obj.toString());
//		
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		
//		Map map = new HashMap();
//		map.put("username", "22");
//		Person p = personService.findOne(map, "test");
//		System.out.println(p.getId()+" "+p.getUsername()+" "+p.getAge());
		
		JSONObject js = new JSONObject();
		js.accumulate("mobile", "13858588888");
		js.accumulate("longitude", 104.0021546d);
		js.accumulate("latitude", 37.0215465d);
		js.accumulate("plateNumber", "鄂Aj43512");
		CallService callService = (CallService)context.getBean("callService");
		callService.updateCarPosition(js);
		
		
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
