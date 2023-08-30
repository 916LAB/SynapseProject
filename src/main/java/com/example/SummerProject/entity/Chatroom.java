package com.example.SummerProject.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

import java.util.Set;

/*
    기능 : 채팅방 엔티티
    주요 기능 : 채팅방 정보를 DB에서 읽을 수 있게 entity 선언, 일대다 관계 테이블 생성, 외래키 참조
 */
@Entity
@NoArgsConstructor
public class Chatroom {
    @Id
    private String roomid;

    @OneToMany(mappedBy = "chatRoom")
    private Set<Message> messages;

    public Chatroom(String roomid) {
        this.roomid = roomid;
    }

}
