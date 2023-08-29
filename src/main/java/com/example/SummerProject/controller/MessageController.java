package com.example.SummerProject.controller;

import com.example.SummerProject.dto.MessageDto;
import com.example.SummerProject.entity.Chatroom;
import com.example.SummerProject.entity.Message;
import com.example.SummerProject.repository.ChatRoomRepository;
import com.example.SummerProject.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


/*
    기능 : 메시지 컨트롤러
    주요 기능 : 메시지 전송시 로직 처리
 */
@Slf4j
@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @MessageMapping("/chat")
    @SendTo("/sub/greetings") // 목적지 여기로 DTO 를 보내 화면에 표시
    public MessageDto send(MessageDto dto){
        log.info(String.valueOf(dto));

        // 채팅방 조회
        Chatroom chatRoom = chatRoomRepository.findById(dto.getRoomid())
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        // 메시지 전송 로직
        Message message = new Message(dto.getContent(), dto.getSender(), dto.getReciver());
        message.setChatRoom(chatRoom);
        messageRepository.save(message);

        return dto;
    }

}
