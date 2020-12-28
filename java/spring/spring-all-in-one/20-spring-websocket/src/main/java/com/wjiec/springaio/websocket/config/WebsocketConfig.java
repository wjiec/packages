package com.wjiec.springaio.websocket.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.HashSet;
import java.util.Set;

@EnableWebSocket
@Configuration
public class WebsocketConfig implements WebSocketConfigurer {

    private final Set<WebSocketHandler> handlers;

    public WebsocketConfig(ApplicationContext context) {
        handlers = new HashSet<>(context.getBeansOfType(WebSocketHandler.class).values());
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        for (var handler : handlers) {
            registry.addHandler(handler, "/ws").setAllowedOrigins("*").withSockJS();
        }
    }

}
