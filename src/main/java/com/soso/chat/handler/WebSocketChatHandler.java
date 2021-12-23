/*
package com.soso.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soso.chat.dto.ChatMessage;
import com.soso.chat.dto.ChatRoom;
import com.soso.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        // 웹소켓 클라이언트로부터 채팅 메시지를 전달받아
        String payload = message.getPayload();
        log.info("payload { }", payload);
//      TextMessage textMessage = new TextMessage("Welcome chatting server~");
//      session.sendMessage(textMessage);

        // 채팅 메시지 객체로 변환
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        // 전달받은 메세지에 담긴 채팅방 id로 발송대상 채팅방 정보 조회
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        // 해당 채팅방에 입장해있는 모든 클라이언트들(WebSocket session)에게 타입에 따른 메시지 발송
        room.handleActions(session, chatMessage, chatService);

    }


}
*/
