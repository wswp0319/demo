package com.jmhqmc.demo.service;

import net.sf.json.JSONObject;

public interface CallService {

	public String updateCarPosition(JSONObject message);

	public String subscribe(JSONObject message);

	public String findCar(JSONObject message);

	public String accept(JSONObject message);

	public String notice(JSONObject message);

	public String start(JSONObject message);

	public String finish(JSONObject message);

	public String pay(JSONObject message);

	public String evaluation(JSONObject message);
}
