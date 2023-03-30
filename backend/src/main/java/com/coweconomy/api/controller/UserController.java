package com.coweconomy.api.controller;

import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.api.response.UserInfoResponseDto;
import com.coweconomy.common.jwt.JwtTokenUtil;
import com.coweconomy.domain.article.dto.ArticleMemoDto;
import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.service.UserInfoService;
import com.coweconomy.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
    @GetMapping("/info")
    public BaseResponse getUserInfo(HttpServletRequest request) {

        // 0) 현재 login 한 유저 아이디 추출
//        String accessToken = request.getHeader("Authorization").substring(7);
//        Long userId = userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId();
        Long userId = Long.valueOf("1");

        // 1) user info 조회 (레벨, 경험치, 이름) - userInfoService.getUserByUserId(userId)

        // 2) memo List 조회
        List<UserArticleMemo> memoList = userInfoService.getUserMemo(userId);
        Map<Article, List<UserArticleMemo>> memoListByGroup = memoList.stream().collect(Collectors.groupingBy(UserArticleMemo::getArticle));
        List<ArticleMemoDto> articleMemoDtoList = memoListByGroup.entrySet().stream().map(entry -> new ArticleMemoDto(entry.getKey(), entry.getValue())).collect(Collectors.toList());

        // 3) 그래프 정보 조회
        // i. 6개월 간 읽은 기사 수 - userInfoService.getReadArticleCount(userId)
        // ii. 올해 회원이 읽은 기사의 카테고리 별 기사 수 return  ex) [["경제", 2], ["금융", 1]]
        // iii. 현재 년-월에 회원이 Quiz에서 맞춘 경제용어 카테고리 별 개수 조회

        return BaseResponse.success(
                new UserInfoResponseDto(
                        userInfoService.getUserByUserId(userId),
                        articleMemoDtoList,
                        userInfoService.getReadArticleCount(userId),
                        userInfoService.getReadArticleCategory(userId),
                        userInfoService.getQuizPassWordCategory(userId))
        );
    }

}
