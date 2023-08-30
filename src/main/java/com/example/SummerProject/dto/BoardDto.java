package com.example.SummerProject.dto;

import com.example.SummerProject.entity.Board;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString

public class BoardDto {
    private Long id;
    private String title;
    private String content;

    public Board toEntity(){
        return new Board(id,title,content);
    }
}
