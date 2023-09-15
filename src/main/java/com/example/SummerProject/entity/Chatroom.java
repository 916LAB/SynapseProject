package com.example.SummerProject.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

/*
    기능 : 채팅방 엔티티
    주요 기능 : 채팅방 정보를 DB에서 읽을 수 있게 entity 선언, 일대다 관계 테이블 생성, 외래키 참조
 */
@Entity
@NoArgsConstructor
@Getter
public class Chatroom {
    @Id
    private String roomid;

    @Column
    private String person1;

    @Column
    private String person2;

    @OneToMany(mappedBy = "chatRoom")
    @JsonManagedReference
    private Set<Message> messages;

    @Builder
    public Chatroom(String roomid,String person1,String person2) {
        this.roomid = roomid;
        this.person1 = person1;
        this.person2 = person2;
    }

}
