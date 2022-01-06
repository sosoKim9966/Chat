package com.soso.chat.controller;

import com.soso.chat.dto.ChatRoom;
import com.soso.chat.dto.LoginInfo;
import com.soso.chat.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// WebSocket 통신 외에 채팅 화면 view 구성을 위한 Controller
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final com.soso.chat.repository.ChatRoomRepository chatRoomRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomDetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

    // 로그인한 회원의 id 및 jwt 토큰 정보 조회
    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth" + auth);
        String name = auth.getName();
        log.info("name" + name);
        return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
    }

}
