package com.coweconomy.api.controller;

import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.api.response.UserInfoResponseDto;
import com.coweconomy.common.jwt.JwtTokenUtil;
import com.coweconomy.domain.user.dto.UserArticleMemoDto;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.service.UserInfoService;
import com.coweconomy.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * 사용자 마이페이지 정보 모두 조회
     */
    @ApiOperation(value = "사용자 마이페이지 정보", notes = "사용자의 마이페이지 정보를 모두 조회한다.")
    @GetMapping("/user/info")
    public BaseResponse getUserInfo(HttpServletRequest request) {

        // 0) 현재 login 한 유저 아이디 추출
        String accessToken = request.getHeader("Authorization").substring(7);
        Long userId = userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId();

        // 1) user info 조회 (레벨, 경험치, 이름)
        UserDto user = userInfoService.getUserByUserId(userId);
//        logger.info("#21# 마이페이지 - user info 조회: {}", user);

        // 2) memo List 조회
        List<UserArticleMemo> memoList = userInfoService.getUserMemo(userId);
        List<UserArticleMemoDto> memoDtoList = new ArrayList<>();
        for (UserArticleMemo userArticleMemo : memoList) {
            memoDtoList.add(new UserArticleMemoDto(userArticleMemo));
        }

        // 3) 그래프 정보 조회 - 6개월 간 읽은 기사 수
        List<Object[]> articleCntList = userInfoService.getReadArticleCount(userId);
//        logger.info("#21# 6개월 간 읽은 기사 수 확인: {}", articleCntList);
//        for (Object[] obj : articleCntList) {
//            System.out.println(Arrays.toString(obj));
//        }
        return BaseResponse.success(new UserInfoResponseDto(user, memoDtoList, articleCntList));
    }

    /**
     * [그래프] 마이페이지 - 읽은 기사의 카테고리 조회
     * - 연 별 데이터 조회 가능
     */

    /**
     * [그래프] 마이페이지 - 경제 용어의 카테고리
     * - 월 별 데이터 조회 가능
     */
}
