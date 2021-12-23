package com.soso.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatRoom {

/*
    pub/sub 방식을 이용하면 구독자 관리가 알아서 되므로 웹소켓 세션 관리가 필요 없어짐
    또한 발송 구현도 알아서 해결되므로 일일이 클라이언트에게 메시지를 발송하는 구현 필요 없어짐

        private String name;
        private Set<WebSocketSession> sessions = new HashSet<>();
        // 입장한 클라이언트들의 정보를 가지고 있어야 하므로 WebSocketSession 정보 리스트를 멤버 필드로 갖는다.
        private String roomId;

        @Builder
        public ChatRoom(String roomId, String name){
            this.roomId = roomId;
            this.name = name;
        }

        // 입장, 대화하기 기능이 있으므로 분기 처리
        public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService){
            // 채팅방 입장시
            if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
                // 채팅방의 session 정보에 클라이언트 session 리스트에 추가
                sessions.add(session);
                chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
            }

            sendMessage(chatMessage, chatService);
        }

        public <T> void sendMessage(T message, ChatService chatService) {
            // 체팅방에 메시지가 도착할 경우 채팅방의 모든 session에 메시지 발송
            sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
        }

*/

    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }



}
