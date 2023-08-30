package com.example.SummerProject.controller;

import  org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String showMain(){
        return "MainPage";
    }

    // 테스트용
    @GetMapping("/chat")
    public String showChatView(){
        return "chat/Message";
    }

    @GetMapping("/mypage")
    public String showMypageView(){
        return "mypage/mypage";
    }

}