package com.soso.chat.pubsub;

import com.soso.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 *  Redis 발행 서비스 구현
 *  채팅방에 입장하면 메세지를 작성하면 해당 메세지를 Redis Topic에 발행하는 가능의 서비스.
 *  메세지 발행 -> 대기중 redis 구독 서비스가 메세지 처리
 */


@RequiredArgsConstructor
@Service
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }


}
