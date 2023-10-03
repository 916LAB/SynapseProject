package com.example.SummerProject.api;

import com.example.SummerProject.dto.UserDto;
import com.example.SummerProject.entity.User;
import com.example.SummerProject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class UserApiController {

    private UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    /*
      회원 정보 저장
    */
    @PostMapping("/signup/SignUpPage")
    public ResponseEntity<User> saveUserInfo(@RequestBody @Valid UserDto userDto) {
        User saved = userService.create(userDto);
        return (saved != null) ?
                ResponseEntity.status(HttpStatus.OK).body(saved) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /*
    닉네임 중복확인
     */
    @GetMapping("/signup/SignUpPage/{nickname}")
    public ResponseEntity<Boolean> checkNickName(@PathVariable String nickname) {
        boolean result = userService.existsByNickname(nickname);
        return (result != true) ?
                ResponseEntity.status(HttpStatus.OK).body(true) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /*
    로그인
     */
    @PostMapping("/MainPage")
    public ResponseEntity<Boolean> login(@RequestBody UserDto userDto, HttpServletRequest request, Model model) {

        String userSessionId = userService.login(userDto);

        // 세션이 있으면 있는 세션을 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();

        // 세션에 로그인 회원 아이디 정보 보관
        session.setAttribute("sessionId", userSessionId);

        // 모델에 값 추가
        model.addAttribute("sessionId", userSessionId);

        // 세션 유효시간 지정
        session.setMaxInactiveInterval(3000);

        return (userSessionId != null) ?
                ResponseEntity.status(HttpStatus.OK).body(true) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    /*
    로그 아웃
     */
    @PostMapping("/logOut")
    public ResponseEntity<Boolean> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body(true);

    }


}
