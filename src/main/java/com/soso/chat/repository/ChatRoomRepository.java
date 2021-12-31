package com.soso.chat.repository;

import com.soso.chat.dto.ChatRoom;
import com.soso.chat.pubsub.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;


// 채팅방을 생성하고 정보를 조회하는 Repository(service 대체)
@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
/*
    // 실서비스는 DB/저장매체에 채팅방 정보를 저장하도록 구현해야함
    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }*/

    // 채팅방(Topic)에 바행되는 메세지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final  String CHAT_ROOMS = "CHAT_ROOMS";
    private final RedisTemplate<String,Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    // 채팅방의 대화 메세지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic 정보를 Map에 넣어 roomId로 찾을 수 있도록 한다.
    private Map<String, ChannelTopic> topics;



}
