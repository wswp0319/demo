package com.jmhqmc.demo.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Websocket处理器
 */
@Service("webSocketHandler")
public class WebSocketHandler extends TextWebSocketHandler {
	//已建立连接的用户  
    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();  
  
    /** 
     * 处理前端发送的文本信息 
     * js调用websocket.send时候，会调用该方法 
     * 
     * @param session 
     * @param message 
     * @throws Exception 
     */  
    @Override  
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {  
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");  
        // 获取提交过来的消息详情  
        //回复一条信息，  
        session.sendMessage(new TextMessage("reply msg:" + message.getPayload()));  
    }  
  
  
    /** 
     * 当新连接建立的时候，被调用 
     * 连接成功时候，会触发页面上onOpen方法 
     * 
     * @param session 
     * @throws Exception 
     */  
    @Override  
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {  
        users.add(session);  
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");  
        session.sendMessage(new TextMessage(username + " connect"));  
        session.sendMessage(new TextMessage("hello wellcome"));  
    }  
  
    /** 
     * 当连接关闭时被调用 
     * 
     * @param session 
     * @param status 
     * @throws Exception 
     */  
    @Override  
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {  
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");  
        users.remove(session);  
    }  
  
    /** 
     * 传输错误时调用 
     * 
     * @param session 
     * @param exception 
     * @throws Exception 
     */  
    @Override  
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {  
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");  
        if (session.isOpen()) {  
            session.close();  
        }  
        users.remove(session);  
    }  
	
	 /** 
     * 给所有在线用户发送消息 
     * 
     * @param message 
     */  
    public void sendMessageToUsers(TextMessage message) {  
        for (WebSocketSession user : users) {  
            try {  
                if (user.isOpen()) {  
                    user.sendMessage(message);  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * 给某个用户发送消息 
     * 
     * @param userName 
     * @param message 
     */  
    public void sendMessageToUser(String userName, TextMessage message) {  
        for (WebSocketSession user : users) {  
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {  
                try {  
                    if (user.isOpen()) {  
                        user.sendMessage(message);  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                break;  
            }  
        }  
    }  
}