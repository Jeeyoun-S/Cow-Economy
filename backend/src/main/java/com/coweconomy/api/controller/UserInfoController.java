package com.coweconomy.api.controller;

import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.domain.user.dto.UserArticleMemoDto;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.service.UserInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @ApiOperation(value = "사용자 마이페이지 정보", notes = "사용자의 마이페이지 정보를 모두 조회한다.")
    @GetMapping("/user/info")
    public BaseResponse getUserInfo() {

        // 임시로 사용자 ID를 1로 설정 (로그인 구현 완료 후, 수정 예정)
        long userId = 1;

        List<UserArticleMemo> memoList = userInfoService.getUserMemo(userId);
        List<UserArticleMemoDto> memoDtoList = new ArrayList<>();
        for (UserArticleMemo userArticleMemo : memoList) {
            memoDtoList.add(new UserArticleMemoDto(userArticleMemo));
        }

        return BaseResponse.success(memoDtoList);
    }
}
