package com.coweconomy.api.controller;

import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.api.response.LoginResponse;
import com.coweconomy.api.response.UserLoginResponseDto;
import com.coweconomy.common.jwt.JwtTokenProvider;
import com.coweconomy.common.jwt.UserAuthentication;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @RequestMapping("/kakao/callback")
//    public String login(@RequestParam String userId) {
//            Authentication authentication = new UserAuthentication(userId, null, null);
//            String token = JwtTokenProvider.generateToken(authentication);
//// controller에서 값 null인지 아닌지 검사 => 분기, 회원가입 할지 로그인 할지
//            return token;
//    }

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/user/loginOrRegister")
    public ResponseEntity<?> loginOrRegister(@RequestParam String email) {
        Optional<User> userOptional = userService.findByUserEmail(email);
        User user;
        boolean isRegistered = false;

        if (userOptional.isPresent()) {
            // 회원이 이미 존재하는 경우 로그인 처리
            //토큰?
//            return null;
            user = userOptional.get();
            System.out.println("#123#");
            isRegistered = true;
        } else {
            System.out.println("#21# 회원가입 실행 email: " + email);
            // 회원이 존재하지 않는 경우 등록 후 로그인 처리
            User newUser = new User();
            newUser.setUserEmail(email);
            // 추가적으로 정보 등록
            newUser.setUserLevel(1);
            newUser.setUserExperience(10);
            newUser.setUserNickname("손");
            System.out.println("#21# 회원가입 할 사람- newUser: " + newUser.toString());

            user = userService.registerUser(newUser);

//            User registerdUser = userService.registerUser(newUser);

        }
        
        // JWT 토큰 생성
        Authentication authentication = new UserAuthentication(email, null, null);
        String token = JwtTokenProvider.generateToken(authentication);
        
        // 응답 객체 생성
//        UserLoginResponseDto responseDto = new UserLoginResponseDto();
//        responseDto.setToken(token);
//        System.out.println("responseDto: " + responseDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse("success", responseDto));
        UserLoginResponseDto responseDto = UserLoginResponseDto.of(
                HttpStatus.CREATED.value(),
                "success",
                token,
                isRegistered
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse("success", responseDto));
    }
}
