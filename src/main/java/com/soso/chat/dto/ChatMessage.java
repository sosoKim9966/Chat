package com.soso.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    // 메세지 타입 : 입장, 채팅
    public enum MessageType{
        ENTER, TALK
    }
    // 메세지 타입
    private MessageType type;
    // 방번호
    private String roomId;
    // 메세지 보낸 사람
    private String sender;
    // 메세지지
   private String message;


}
