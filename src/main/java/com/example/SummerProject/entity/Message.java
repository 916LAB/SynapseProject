package com.example.SummerProject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/*
    기능 : 메시지 엔티티
    주요 기능 : 메시지 테이블, 일대다 관계 연결, 외래키 참조
 */
@Entity
@NoArgsConstructor
@Setter
@Getter
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
    @JsonBackReference
    private Chatroom chatRoom;

    @Column
    private OffsetDateTime time;

    @Builder
    public Message(String content, String sender, String reciver) {
        this.content = content;
        this.sender = sender;
        this.reciver = reciver;
        this.time = OffsetDateTime.now(ZoneOffset.UTC).withOffsetSameInstant(ZoneOffset.ofHours(9));  // UTC+9 기준으로 현재 시간 저장
    }
    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
