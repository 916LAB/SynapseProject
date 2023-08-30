package com.example.SummerProject.controller;

import com.example.SummerProject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    회원가입 페이지
    */
    @GetMapping("/signup")
    public String showSignUp() {
        return "signup/SignUpPage";
    }

    /*
    로그인 후 페이지 넘어감
     */
    @GetMapping("/login")
    public String showLogin(){
        return "LoginPage";
    }


}
