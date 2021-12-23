package com.soso.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soso.chat.dto.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    // 서버에 생성된 모든 채팅방의 정보를 모아둔 구조체
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init(){
        // 채팅방 정보 저장 => 빠른 구현을 위해 일단 외부 저장소를 이요하지 않고 HashMap에 저장
        chatRooms = new LinkedHashMap<>();
    }

    // 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        // 채팅방 Map에 담긴 정보 조회
        return new ArrayList<>(chatRooms.values());
    }

    //
    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    // 채팅방 생성
    public ChatRoom createRoom(String name) {
        // 구별 ID를 가진 채팅방 객체 생성 -> 채팅방 Map에 추가
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, chatRoom);
        return chatRoom;
    }

    // 메시지 발송
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            // 지정한 WebSocket 세션에 메세지 발송
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
