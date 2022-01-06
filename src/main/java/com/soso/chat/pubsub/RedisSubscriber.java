package com.soso.chat.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soso.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 *  Redis 구독 서비스 구현
 *  Redis에 메세지 발행이 될 때까지 대기 -> 메세지 발행 -> 해당 메세지 처리 리스너
 *
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber{

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * 2021-01-06 onMessage 삭제
     * Redis에서 메세지가 발행(publish)되면 대기하고 있는 onMessage가 해당 메세지를 받아 처리.
     * 해당 메세지 -> ChatMessage로 변환 -> messaging Template 이용 채팅방의 모든 websocket 클라이언트
     * 에게 메세지 전달
     *
     * sendMessage 추가
     * 메세지 리스너에 메세지가 수신되면 아래 RedisSubsciber.sendMessage 수행
     * Redis에서 메세지가 발행(publish)되면 대기하고 있던 Redis Subsciber가 해당 메세지를 받아 처리
     */

    public void sendMessage(String publishMessage) {
        try{
            // ChatMessage 객체로 맵핑
            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            // 채팅방을 구독한 클라이언트에게 메세지 발송
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e){
            log.error("Exception {}", e);
        }

    }
}
