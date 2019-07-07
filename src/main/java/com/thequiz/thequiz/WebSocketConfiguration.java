package com.thequiz.thequiz;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
	private static ChartWebSocketHandler chartWebSocketHandler = new ChartWebSocketHandler();

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	    registry.addHandler(chartWebSocketHandler , "/TheQuiz/cd-socket");
	    //.setAllowedOrigins("*");
	}

}
