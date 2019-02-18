package com.jmhqmc.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.jmhqmc.demo.service.WebSocketHandler;


/**
 * 测试类
 */

@Controller
@RequestMapping("/w!")
public class PageController extends BaseController{

	@Autowired
	WebSocketHandler webSocketHandler;
	
//	@Bean // 这个注解会从Spring容器拿出Bean
//	public WebSocketHandler infoHandler() {
//		return new WebSocketHandler();
//	}

	@RequestMapping(value = "/init", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/demo/websocket";
	}

	@RequestMapping(value = "/send", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String send(HttpServletRequest request) {
		String username = request.getParameter("username");
		webSocketHandler.sendMessageToUser(username, new TextMessage("你好，欢迎测试！！！！"));
		return null;
	}
}