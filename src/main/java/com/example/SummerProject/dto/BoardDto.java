package com.example.SummerProject.dto;

import com.example.SummerProject.entity.Board;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@ToString

public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private LocalDate date;
    public Board toEntity(){
        return new Board(id,title,content,date);
    }
}