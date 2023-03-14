package com.coweconomy.api.controller;

import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.api.response.LoginResponse;
import com.coweconomy.api.response.UserLoginResponseDto;
import com.coweconomy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    /**
     * 동작 TEST용 API
     */
    @GetMapping("/")
    public ResponseEntity<?> getExcute() {
        logger.info("## [Controller]: 동작 TEST용 실행-");

        return ResponseEntity.ok(BaseResponse.success(null));
    }
}
