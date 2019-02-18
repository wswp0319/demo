package com.jmhqmc.demo.websocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Websocket处理器
 */
public class WebSocketHandler extends TextWebSocketHandler {
	private List<WebSocketSession> socketSessions = new ArrayList<WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("connect to the websocket success......");
		session.sendMessage(new TextMessage("Server:connected OK!"));
	}

	@Override
	public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) throws Exception {
		TextMessage returnMessage = new TextMessage(wsm.getPayload() + " received at server");
		System.out.println(wss.getHandshakeHeaders().getFirst("Cookie"));
		wss.sendMessage(returnMessage);
	}

	@Override
	public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {
		if (wss.isOpen()) {
			wss.close();
		}
		System.out.println("websocket connection closed......");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession wss, CloseStatus cs) throws Exception {
		System.out.println("websocket connection closed......");
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}