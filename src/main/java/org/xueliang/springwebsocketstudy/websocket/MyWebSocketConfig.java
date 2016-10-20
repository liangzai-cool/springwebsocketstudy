package org.xueliang.springwebsocketstudy.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 注册WebSocket处理类
 * @author XueLiang
 * @date 2016年10月18日 下午2:10:27
 * @version 1.0
 */
@Component
@EnableWebSocket
public class MyWebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private MyWebSocketHandler myWebSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myWebSocketHandler, "/socketjs").addInterceptors(new MyHandshakeInterceptor());
	}
}
