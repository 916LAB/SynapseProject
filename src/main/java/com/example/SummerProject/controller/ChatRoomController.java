package com.example.SummerProject.controller;

import com.example.SummerProject.dto.ChatRoomDto;
import com.example.SummerProject.entity.Chatroom;
import com.example.SummerProject.repository.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/*
    기능 : 채팅방 컨트롤러
    주요 기능 : 채팅방 CRUD 기능을 처리하는 페이지로 연결
    참조 : 이후 서비스 폴더로 분할시 로직 분할 다시 해야함
 */
@Slf4j
@Controller
public class ChatRoomController {
    @Autowired
    ChatRoomRepository chatRoomRepository;
    // 채팅방 생성 로직
    @PostMapping("/chatroom")
    public String Chatroom(Model model){

        // 대화 중인 상대인지 체크
        String a = chatRoomRepository.find("person1","person2");
        String b = chatRoomRepository.find("person2", "person1");

        // a또는b 가 이반환되는 값이 있다면 한번이라도 대화한적 있음
        if(a!=null || b!=null){
            // roomid 찾기
            String c = chatRoomRepository.findid("person1","person2");
            // roomid 찾는 반대 경우
            if(c==null){
                c = chatRoomRepository.findid("person2","person1");
            }
            // roomid chatroom이라는 이름으로 전달
            model.addAttribute("chatroom",c);
            log.info("roomid:" + c);

        }else{ // 한번이라도 대화한적 없다면
            // 새로운 roomid 생성
            ChatRoomDto chatroomdto = new ChatRoomDto();
            // roomid 전달
            model.addAttribute("chatroom",chatroomdto.getRoomid());
            log.info("roomid:" + chatroomdto.getRoomid());
            // entity 변환 후 저장
            Chatroom chatroom = chatroomdto.toEntity();
            chatRoomRepository.save(chatroom);
        }

        // 채팅 상대 내역 가져와서 전달
        List<String> test = chatRoomRepository.findPerson("person1");
        if (test.isEmpty()) {
        } else {
            model.addAttribute("list",test);
        }

        return "chat/Message";
    }
    // 채팅방 삭제 로직

    // 채팅방 조회 로직
}
