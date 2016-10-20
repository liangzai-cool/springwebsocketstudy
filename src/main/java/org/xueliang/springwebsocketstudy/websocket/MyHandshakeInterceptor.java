package org.xueliang.springwebsocketstudy.websocket;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * 握手拦截器
 * @author XueLiang
 * @date 2016年10月20日 下午6:12:28
 * @version 1.0
 */
public class MyHandshakeInterceptor implements HandshakeInterceptor {
	
	private static final Logger LOGGER = LogManager.getLogger(MyHandshakeInterceptor.class);

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		long uid = System.nanoTime();
		attributes.put("uid", uid);
		LOGGER.info("before uid[{}] handshake", uid);
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
	}

}
