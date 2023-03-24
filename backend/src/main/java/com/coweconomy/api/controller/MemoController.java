package com.coweconomy.api.controller;

import com.coweconomy.api.request.MemoRequestDto;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.domain.user.dto.UserArticleMemoDto;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.service.MemoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/memo")
public class MemoController {

    @Autowired
    MemoService memoService;

    @ApiOperation(value = "메모 등록", notes = "새로운 메모를 등록한다.")
    @PostMapping("")
    public BaseResponse<UserArticleMemoDto> addMemo(@RequestBody MemoRequestDto memoRequestDto) {

        // 임시로 사용자 ID를 1로 설정 (로그인 구현 완료 후, 수정 예정)
        Long userId = 1L;

        // 입력 받은 request 값이 유효한지 확인
        if (memoService.isValidMemoRequest(memoRequestDto)) {
            // 유효하다면 DB에 저장하기
            UserArticleMemo memo = memoService.addMemo(memoRequestDto, userId);
            if (memo != null) {
                return BaseResponse.success(new UserArticleMemoDto(memo));
            }
        }

        return BaseResponse.fail();
    }

    @ApiOperation(value = "메모 수정", notes = "기존 메모를 수정한다.")
    @PutMapping("")
    public BaseResponse modifyMemo(@RequestParam(name = "memoId", required = true) Long memoId, @RequestBody MemoRequestDto memoRequestDto) {

        // 임시로 사용자 ID를 1로 설정 (로그인 구현 완료 후, 수정 예정)
        Long userId = 1L;
        
        // userId가 작성한 메모가 맞는지 확인
        if (memoService.checkMemoWriter(memoId, userId)) {
            // 메모 내용이 유효한지 확인
            if (memoService.isValidMemoRequest(memoRequestDto)) {
                // 유효하다면 수정 진행
                UserArticleMemo memo = memoService.modifyMemo(memoRequestDto, memoId);
                if (memo != null) {
                    return BaseResponse.success(new UserArticleMemoDto(memo));
                }
            }
        }

        return BaseResponse.fail();
    }

    @ApiOperation(value = "메모 삭제", notes = "기존 메모를 삭제한다.")
    @DeleteMapping("")
    public BaseResponse deleteMemo(Long memoId) {

        // 임시로 사용자 ID를 1로 설정 (로그인 구현 완료 후, 수정 예정)
        Long userId = 1L;
        
        // memoId의 작성자가 userId가 맞는지 확인
        if (memoService.checkMemoWriter(memoId, userId)) {
            memoService.deleteMemo(memoId);
            return BaseResponse.success(null);
        }

        return BaseResponse.fail();
    }
}