package com.soso.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;


@RequiredArgsConstructor
@Configuration
@EnableWebSocket  // WebSocket 활성화
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    // connection 맺을때 CORS 허용
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // endpoint = 양 사용자 간 웹소켓 핸드셰이크를 위해 지정(WebSocket에 접속하기 위한)
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }



}
