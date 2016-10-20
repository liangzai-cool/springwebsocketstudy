package org.xueliang.springwebsocketstudy.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * websocket处理类
 * @author XueLiang
 * @date 2016年10月18日 下午2:11:16
 * @version 1.0
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Map<Long, WebSocketSession> webSocketSessionMap = new HashMap<Long, WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		long uid = (long) session.getAttributes().get("uid");
		webSocketSessionMap.put(uid, session);
		LOGGER.info("record a session, id is: {}", uid);
	}

	/**
	 * 收到消息
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String payload = (String) message.getPayload();
		LOGGER.info("receive a message: {}", payload);
		sendMessage(payload);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		LOGGER.error("transport error", exception);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		long uid = (long) session.getAttributes().get("uid");
		webSocketSessionMap.remove(uid);
		LOGGER.info("session [uid={}] closed.", uid);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	/**
	 * 给所有websocket发送消息
	 * @param text
	 * @throws IOException
	 */
	public void sendMessage(String text) {
		LOGGER.info("broadcasting message: {}", text);
		for (Long uid : webSocketSessionMap.keySet()) {
			try {
				LOGGER.info("broadcast to session[uid={}]", uid);
				webSocketSessionMap.get(uid).sendMessage(new TextMessage(text));
			} catch (IOException e) {
				LOGGER.error("broadcast error", e);
			}
		}
	} 

}
