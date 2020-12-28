package com.wjiec.springaio.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Slf4j
@Component
public class MessageHandler extends AbstractWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info(session.getId() + " : " + session.getRemoteAddress() + " -> " + message.toString());

        session.sendMessage(new TextMessage(new StringBuilder(message.getPayload()).reverse()));
    }

}
