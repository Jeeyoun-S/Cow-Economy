package com.coweconomy.api.controller;

import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.common.jwt.JwtTokenUtil;
import com.coweconomy.domain.article.dto.ArticleMemoDto;
import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.dto.UserArticleMemoDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    UserInfoService userInfoService;

    // 로그인한 사용자 정보를 가져오기
    @Autowired
    UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    // 오류 처리
    private BaseResponse exceptionHandling(Exception exception, String type) {
        logger.error(type + exception.toString());
        return BaseResponse.fail();
    }

    @ApiOperation(value = "사용자 마이페이지 정보", notes = "사용자의 마이페이지 정보를 모두 조회한다.")
    @GetMapping("/user/info")
    public BaseResponse getUserInfo(HttpServletRequest request) {

        try {
            // 현재 login 한 유저 아이디 추출
            String accessToken = request.getHeader("Authorization").substring(7);
            Long userId = userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId();

            // 리턴할 Data 값은 Map 형식으로 지정
            Map<String, Object> returnMap = new HashMap<>();

            // 1. 사용자 정보

            // 2. 사용자 메모
            // 사용자가 작성한 모든 메모 가져오기
            List<UserArticleMemo> memoList = userInfoService.getUserMemo(userId);
            // 메모를 기사별로 분류하기
            Map<Article, List<UserArticleMemo>> memoListByGroup = memoList.stream().collect(Collectors.groupingBy(UserArticleMemo::getArticle));
            // 분류된 기사 Entity Map을 Dto List로 바꾸기
            List<ArticleMemoDto> articleMemoDtoList = memoListByGroup.entrySet().stream().map(entry -> new ArticleMemoDto(entry.getKey(), entry.getValue())).collect(Collectors.toList());
            // 결과값에 넣어주기
            returnMap.put("memo", articleMemoDtoList);

            return BaseResponse.success(returnMap);
        } catch (Exception exception) {
            return exceptionHandling(exception, "# RUI #");
        }
    }
}
