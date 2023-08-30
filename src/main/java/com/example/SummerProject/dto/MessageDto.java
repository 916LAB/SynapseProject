package com.example.SummerProject.dto;

import com.example.SummerProject.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
/*
    기능 : 사용자에게 입력받는 메시지 정보
    주요 기능 : entity 변환 기능, 생정자를 통해 입력받은 정보를 담아두는 기능
 */
public class MessageDto {
    public String content;
    public String sender;
    public String reciver;
    public Message toEntity(){
        return new Message(content,sender,reciver);
    }
}
