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
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis에서 메세지가 발행(publish)되면 대기하고 있는 onMessage가 해당 메세지를 받아 처리.
     * 해당 메세지 -> ChatMessage로 변환 -> messaging Template 이용 채팅방의 모든 websocket 클라이언트
     * 에게 메세지 전달
     */

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            // redis에서 발행된 데이터를 받아 deserialize
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatMessage 객체로 맵핑
            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            // Websocket 구독자에게 채팅 메시지 Send
            messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);
        } catch (Exception e){
            log.error(e.getMessage());
        }

    }
}
