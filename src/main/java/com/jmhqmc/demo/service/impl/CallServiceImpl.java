package com.jmhqmc.demo.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jmhqmc.demo.Constants;
import com.jmhqmc.demo.dao.BaseDao;
import com.jmhqmc.demo.dao.RedisDao;
import com.jmhqmc.demo.service.CallService;

import net.sf.json.JSONObject;

@Service("callService")
public class CallServiceImpl implements CallService{
	@Resource(name="redisDao")
	private RedisDao redisDao;
	
	@Resource(name="mongoDao")
	private BaseDao<JSONObject> baseDao;

	public String updateCarPosition(JSONObject message) {
		String mobile = message.getString("mobile");
		double longitude = message.getDouble("longitude");
		double latitude = message.getDouble("latitude");
		String updateTime = Constants.DATE_TIME_FORMATER.format(new Date());
		String plateNumber = message.getString("plateNumber");
		
		JSONObject js = new JSONObject();
		js.accumulate("mobile", mobile);
		js.accumulate("longitude", longitude);
		js.accumulate("latitude", latitude);
		js.accumulate("updateTime", updateTime);
		js.accumulate("plateNumber", plateNumber); 
		//baseDao.insert(js, "trail");
		
		redisDao.setDataToCache(mobile, js.toString());

		return "{\"command\":4,\"request\":1,\"mobile\":\""+message.getString("mobile")+"\"}";
	}

	public String subscribe(JSONObject message) {
		// TODO Auto-generated method stub
		return "{\"command\":4,\"request\":2,\"consumerMobile\":\""+message.getString("consumerMobile")+"\"}";
	}

	public String findCar(JSONObject message) {
		// TODO Auto-generated method stub
		return "{\"command\":4,\"request\":3,\"mobile\":\""+message.getString("mobile")+"\"}";
	}

	public String accept(JSONObject message) {
		// TODO Auto-generated method stub
		return "{\"command\":4,\"request\":4,\"producerMobile\":\""+message.getString("producerMobile")+"\"}";
	}

	public String notice(JSONObject message) {
		// TODO Auto-generated method stub
		//return "{\"command\":4,\"request\":5,\"mobile\":\""+message.getString("mobile")+"\"}";
		return null;
	}

	public String start(JSONObject message) {
		// TODO Auto-generated method stub
		return "{\"command\":4,\"request\":6,\"producerMobile\":\""+message.getString("producerMobile")+"\"}";
	}

	public String finish(JSONObject message) {
		// TODO Auto-generated method stub
		return "{\"command\":4,\"request\":7,\"producerMobile\":\""+message.getString("producerMobile")+"\"}";
	}

	public String pay(JSONObject message) {
		// TODO Auto-generated method stub
		return "{\"command\":4,\"request\":8,\"consumerMobile\":\""+message.getString("consumerMobile")+"\"}";
	}

	public String evaluation(JSONObject message) {
		// TODO Auto-generated method stub
		return "{\"command\":4,\"request\":9,\"mobile\":\""+message.getString("mobile")+"\"}";
	}

}
