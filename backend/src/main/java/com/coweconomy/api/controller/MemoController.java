package com.coweconomy.api.controller;

import com.coweconomy.api.request.MemoRequestDto;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.common.jwt.JwtTokenUtil;
import com.coweconomy.domain.user.dto.UserArticleMemoSimpleDto;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.service.MemoService;
import com.coweconomy.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/memo")
public class MemoController {

    private static final Logger logger = LoggerFactory.getLogger(MemoController.class);

    @Autowired
    MemoService memoService;

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

    @ApiOperation(value = "메모 등록", notes = "새로운 메모를 등록한다.")
    @PostMapping("/{articleId}")
    public BaseResponse<UserArticleMemoSimpleDto> addMemo(HttpServletRequest request, @PathVariable Long articleId, @RequestBody MemoRequestDto memoRequestDto) {

        try {
            // 현재 login 한 유저 아이디 추출
            String accessToken = request.getHeader("Authorization").substring(7);
            Long userId = userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId();

            // 입력 받은 request 값이 유효한지 확인
            if (memoService.isValidMemoRequest(memoRequestDto)) {
                // 유효하다면 DB에 저장하기
                UserArticleMemo memo = memoService.addMemo(memoRequestDto, userId, articleId);
                if (memo != null) {
                    return BaseResponse.success(new UserArticleMemoSimpleDto(memo));
                }
            }
        } catch (Exception exception) {
            return exceptionHandling(exception, "# RM #");
        }

        return BaseResponse.fail();
    }

    @ApiOperation(value = "메모 수정", notes = "기존 메모를 수정한다.")
    @PutMapping("/{memoId}")
    public BaseResponse<UserArticleMemoSimpleDto> modifyMemo(HttpServletRequest request, @PathVariable Long memoId, @RequestBody MemoRequestDto memoRequestDto) {

        try {
            // 현재 login 한 유저 아이디 추출
            String accessToken = request.getHeader("Authorization").substring(7);
            Long userId = userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId();

            // userId가 작성한 메모가 맞는지 확인
            UserArticleMemo userArticleMemo = memoService.checkMemoWriter(memoId, userId);
            if (userArticleMemo != null) {
                // 메모 내용이 유효한지 확인
                if (memoService.isValidMemoRequest(memoRequestDto)) {
                    // 유효하다면 수정 진행
                    UserArticleMemo memo = memoService.modifyMemo(userArticleMemo, memoRequestDto, memoId);
                    if (memo != null) {
                        return BaseResponse.success(new UserArticleMemoSimpleDto(memo));
                    }
                }
            }
        } catch (Exception exception) {
            return exceptionHandling(exception, "# UM #");
        }

        return BaseResponse.fail();
    }

    @ApiOperation(value = "메모 삭제", notes = "기존 메모를 삭제한다.")
    @DeleteMapping("")
    public BaseResponse deleteMemo(HttpServletRequest request, Long memoId) {

        try {
            // 현재 login 한 유저 아이디 추출
            String accessToken = request.getHeader("Authorization").substring(7);
            Long userId = userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId();

            // userId가 작성한 메모가 맞는지 확인
            UserArticleMemo userArticleMemo = memoService.checkMemoWriter(memoId, userId);
            if (userArticleMemo != null) {
                // 삭제 진행
                memoService.deleteMemo(userArticleMemo);
                return BaseResponse.success(null);
            }
        } catch (Exception exception) {
            return exceptionHandling(exception, "# DM #");
        }

        return BaseResponse.fail();
    }
    
    @ApiOperation(value = "메모 공개 여부 수정", notes = "기존의 공개 여부를 수정한다.")
    @PostMapping("")
    public BaseResponse<Boolean> modifyPublicScope(HttpServletRequest request, @RequestParam("memoId") Long memoId) {

        try {
            // 현재 login 한 유저 아이디 추출
            String accessToken = request.getHeader("Authorization").substring(7);
            Long userId = userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId();

            // userId가 작성한 메모가 맞는지 확인
            UserArticleMemo userArticleMemo = memoService.checkMemoWriter((long) memoId, userId);
            if (userArticleMemo != null) {
                // 메모 Scope 변경 진행
                boolean memoPublicScope = memoService.modifyMemoScope(userArticleMemo);
                return BaseResponse.success(memoPublicScope);
            }
        } catch (Exception exception) {
            return exceptionHandling(exception, "# UMP #");
        }

        return BaseResponse.fail();
    }
}