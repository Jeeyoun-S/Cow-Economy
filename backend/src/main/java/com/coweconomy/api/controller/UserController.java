package com.coweconomy.api.controller;

import com.coweconomy.api.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//        logger.info("## [Controller]: 동작 TEST용 실행-");

        return ResponseEntity.ok(BaseResponse.success(null));
    }


}
