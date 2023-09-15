package com.example.SummerProject.controller;

import com.example.SummerProject.dto.ChatRoomDto;
import com.example.SummerProject.entity.Chatroom;
import com.example.SummerProject.entity.Message;
import com.example.SummerProject.repository.ChatRoomRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @GetMapping("/chatroom/{id}")
    public String Chatroom(HttpSession session, Model model,@PathVariable String id){
        String sessionId = (String) session.getAttribute("sessionId");
        // 대화 중인 상대인지 체크
        String a = chatRoomRepository.find(sessionId,id);
        String b = chatRoomRepository.find(id, sessionId);

        // a또는b 가 이반환되는 값이 있다면 한번이라도 대화한적 있음
        if(a!=null || b!=null){
            // roomid 찾기
            String c = chatRoomRepository.findroomid(sessionId,id);
            // roomid 찾는 반대 경우
            if(c==null){
                c = chatRoomRepository.findroomid(id,sessionId);
            }
            // roomid chatroom이라는 이름으로 전달
            model.addAttribute("chatroom",c);
            log.info("roomid:" + c);

        }else{ // 한번이라도 대화한적 없다면
            // 새로운 roomid 생성
            ChatRoomDto chatroomdto = new ChatRoomDto();
            chatroomdto.setPerson1(sessionId);
            chatroomdto.setPerson2(id);
            // roomid 전달
            model.addAttribute("chatroom",chatroomdto.getRoomid());
            log.info("roomid:" + chatroomdto.getRoomid());
            // entity 변환 후 저장
            Chatroom chatroom = chatroomdto.toEntity();
            chatRoomRepository.save(chatroom);
        }

        // 채팅 상대 내역 가져와서 전달
        List<String> test = chatRoomRepository.findPerson(sessionId);
        List<String> test2 = chatRoomRepository.findPerson2(sessionId);

        List<String> combined = Stream.concat(test.stream(), test2.stream())
                .collect(Collectors.toList());
        combined.add(id);

        if (test.isEmpty()) {
        } else {
            model.addAttribute("list",combined);
        }

        model.addAttribute("sessionId", sessionId);
        return "chat/Message";
    }


    // 메시지 읽어드리는 로직
    @ResponseBody
    @RequestMapping("/api/chat_history")
    public Set<Message> somthing(@RequestParam String partner,HttpSession session){

        String sessionid = (String) session.getAttribute("sessionId");
        String roomid=null;
        log.info("partner name : " + partner);
        Optional<Chatroom> optionalChatRoom = null;
        Set<Message> messages = null;
        log.info(sessionid);
        log.info(chatRoomRepository.findroomid(partner,sessionid) + "," + chatRoomRepository.findroomid(sessionid,partner));

        if(chatRoomRepository.findroomid(partner,sessionid) != null || chatRoomRepository.findroomid(sessionid,partner)!=null){
            if(chatRoomRepository.findroomid(partner,sessionid) != null){
                roomid = chatRoomRepository.findroomid(partner,sessionid);
            }else{
                roomid = chatRoomRepository.findroomid(sessionid,partner);
            }

            log.info("roomid : " + roomid);
            optionalChatRoom = chatRoomRepository.findById(roomid);
        }

        log.info("optionalChatRoom : " + optionalChatRoom );

        if (optionalChatRoom != null && optionalChatRoom.isPresent()) {  // 값이 있는 경우
            Chatroom chatRoom = optionalChatRoom.get();
            messages = chatRoom.getMessages();
            // messages의 내용을 로그에 출력합니다.
            messages.forEach(message -> log.info(message.toString()));
        } else {
            // 값이 없는 경우 처리
            log.info("null임");
        }


        return messages;
    }

    @ResponseBody  // @ResponseBody 추가
    @RequestMapping("/api/chat_room")
    public String room(@RequestParam String partner,HttpSession session){
        String sessionid = (String) session.getAttribute("sessionId");
        String newroomid=null;
        if(chatRoomRepository.findroomid(partner,sessionid) != null || chatRoomRepository.findroomid(sessionid,partner)!=null){
            if(chatRoomRepository.findroomid(partner,sessionid) != null){
                newroomid = chatRoomRepository.findroomid(partner,sessionid);
            }else{
                newroomid = chatRoomRepository.findroomid(sessionid,partner);
            }

            log.info("roomid : " + newroomid);
        }
        return newroomid;
    }
}
