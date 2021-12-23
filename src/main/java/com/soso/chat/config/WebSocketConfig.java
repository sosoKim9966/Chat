package com.soso.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker  // Stomp 사용하기 위함
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*private final WebSocketHandler webSocketHandler;

    @Override
    // connection 맺을때 CORS 허용
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // endpoint = 양 사용자 간 웹소켓 핸드셰이크를 위해 지정(WebSocket에 접속하기 위한)
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }*/

    // pub/sub 메세징을 구현
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메세지르 구독하는 요청의 prefix
        config.enableSimpleBroker("/sub");
        // 메세지를 발행하는 요청의 prefix
        config.setApplicationDestinationPrefixes("/pub");
    }

    // stomp webSocket 연결 endpoint
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")
                .withSockJS();
    }


}
