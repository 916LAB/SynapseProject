package com.example.SummerProject.dto;

import com.example.SummerProject.entity.Chatroom;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
/*
    기능 : 사용자의 채팅방 개설시 정보
    주요 기능 : 채팅방 번호를 랜덤으로 부여 시켜서 저장, entity 변환 기능
 */
public class ChatRoomDto {

    public String roomid;
    public String person1;
    public String person2;
    public ChatRoomDto(){
        this.roomid = UUID.randomUUID().toString();
    }

    public Chatroom toEntity(){
        return new Chatroom(roomid,person1,person2);
    }
}
