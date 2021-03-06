package com.soso.chat.controller;

import com.soso.chat.dto.ChatMessage;
import com.soso.chat.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
/*

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

    Stomp   => 메시징 전송을 효율적으로 하기 위해 나온 프로토콜 (pub/sub)
               메세지 발송/메세지를 받아 처리하는 부분이 확실히 나누워져 있다.
               통신 메세지의 헤더에 값을 세팅할 수 있어 헤더 값을 기반으로 통신시 인증처리를 구현하는 것 가능

    pub/sub => 메세지를 공급하는 주체와 소비하는 주체를 분리하여 제공하는 메세징 방법
               ex) 우체통(topic) / 집배원(publisher) / 구독자(subscriber)
               채팅방 생성 - pub/sub 구현을 위한 topic 생성
               채팅방 입장 - topic 구독
                채팅  시작 - 해당 topic으로 메세지 발송(pub)/메세지 수신(sub)


    @MessageMapping를 통해 WebSocket으로 들어오는 메세지 발행 처리
    클라이언트      -> /pub/chat/message 발행요청
    Controller    -> 해당 메세지를 받아서 처리함.
    if(메세지 발행) -> /sub/chat/room/{roomID}로 메세지 send
    클라이언트      -> /sub/chat/room/{roomID}(= 채팅룸 구분값 = topic) -> 메세지 전달 -> view 출력


    private final SimpMessageSendingOperations messagingTemplate;
    private final RedisPublisher redisPublisher;

    private final ChatRoomRepository chatRoomRepository;
*/
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * websocket "/pub/chat/message"로 들어오는 메세징을 처리
     * 메세지 send -> jwt token 유효성 검사
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("token") String token ) {
        // 회원 대화명(id)을 조회하는 코드를 삽입 -> 유효성 체크 -> 유효하지 않은 토큰이 세팅될 경우 메세지 무시
        String nickname = jwtTokenProvider.getUserNameFormJwt(token);
        message.setSender(nickname);

        if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
           message.setSender("[알림]");
           message.setMessage(nickname + "님이 입장하셨습니다.");
        }
        // websocket에 발행된 메세지를 redis로 발행한다(publish)
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }



}
