package com.coweconomy.api.controller;

import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.api.response.LoginResponse;
import com.coweconomy.api.response.UserLoginResponseDto;
import com.coweconomy.common.jwt.JwtTokenProvider;
import com.coweconomy.common.jwt.UserAuthentication;
import com.coweconomy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    @RequestMapping("/kakao/callback")
    public String login(@RequestParam String userId) {
//        if(userId.equals("admin")) {
            System.out.println("안녕");
            Authentication authentication = new UserAuthentication(userId, null, null);
            String token = JwtTokenProvider.generateToken(authentication);

            return token;
//        }
//        return "error";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }
}
