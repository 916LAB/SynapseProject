package com.example.SummerProject.controller;

import com.example.SummerProject.entity.User;
import com.example.SummerProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class MainController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/main")
    public String showMain(HttpSession session, Model model){

        // 세션에서 필요한 값 가져오기
        String sessionId = (String) session.getAttribute("sessionId");

        // 모델에 값 추가
        model.addAttribute("sessionId", sessionId);

        return "MainPage";
    }

    // 테스트용
    @GetMapping("/chat")
    public String showChatView(HttpSession session, Model model) {
        // 세션에서 필요한 값 가져오기
        String sessionId = (String) session.getAttribute("sessionId");

        // 모델에 값 추가
        model.addAttribute("sessionId", sessionId);

        return "chat/Message";
    }

    @GetMapping("/mypage")
    public String showMypageView(HttpSession session,Model model){
        String sessionId = (String) session.getAttribute("sessionId");
        log.info(sessionId);

        List<User> users = (List<User>) userRepository.findAll();

        // 모델에 값 추가
        model.addAttribute("sessionId", sessionId);

        model.addAttribute("users", users);

        return "mypage/mypage";
    }

}