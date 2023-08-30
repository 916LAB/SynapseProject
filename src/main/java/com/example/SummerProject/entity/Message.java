package com.example.SummerProject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

/*
    기능 : 메시지 엔티티
    주요 기능 : 메시지 테이블, 일대다 관계 연결, 외래키 참조
 */
@Entity
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;
    @Column
    private String sender;
    @Column
    private String reciver;

    @ManyToOne
    @JoinColumn(name = "roomid")
    private Chatroom chatRoom;

    @Builder
    public Message(String content, String sender, String reciver) {
        this.content = content;
        this.sender = sender;
        this.reciver = reciver;
    }
}
