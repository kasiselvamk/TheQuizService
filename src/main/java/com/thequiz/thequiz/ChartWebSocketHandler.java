package com.thequiz.thequiz;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
 import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

@Component
public class ChartWebSocketHandler extends TextWebSocketHandler {
	public static Gson gson = new Gson();
	public static ConcurrentHashMap<String,WebSocketSession> sessionMap =  new ConcurrentHashMap<String,WebSocketSession>();

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		//session.sendMessage(new TextMessage("Hello " ));
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println(session.getId());
		Double[] COData = Constance.getcalculatedChartData();
		chartObj CO = new chartObj(1,COData);
    	if(!sessionMap.containsKey(session.getId())) {
			System.out.println("Added:"+session.getId());
        	sessionMap.put(session.getId(), session);
    	}
 		session.sendMessage(new TextMessage(gson.toJson(CO)));		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		if(sessionMap.containsKey(session.getId())) {
			System.out.println("Removed:"+session.getId()+ " "+closeStatus.getReason());
        	sessionMap.remove(session.getId());
    	} 
	}
}
