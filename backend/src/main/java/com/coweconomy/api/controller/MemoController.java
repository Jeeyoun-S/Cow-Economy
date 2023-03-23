package com.coweconomy.api.controller;

import com.coweconomy.api.request.MemoRequestDto;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.domain.user.dto.UserArticleMemoDto;
import com.coweconomy.domain.user.entity.User;
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
        long userId = 1;

        // 입력 받은 request 값이 유효한지 확인
        if (memoService.isValidMemoRequest(memoRequestDto)) {
            
            // DB에 저장하기
            UserArticleMemo memo = memoService.saveMemoRequestDto(memoRequestDto, userId);
            
            if (memo != null) {
                
                // memo Entity를 UserArticleMemoDto로 변경
                UserArticleMemoDto userArticleMemoDto = new UserArticleMemoDto(memo);
                return BaseResponse.success(userArticleMemoDto);
            }
        }

        return BaseResponse.fail();
    }

    @ApiOperation(value = "메모 수정", notes = "기존 메모를 수정한다.")
    @PutMapping("")
    public BaseResponse modifyMemo(@RequestParam(name = "memoId") Long memoId, @RequestBody MemoRequestDto memoRequestDto) {

        // 임시로 사용자 ID를 1로 설정 (로그인 구현 완료 후, 수정 예정)
        long userId = 1;

//        if (memoService.checkMemoWriter(memoId))

        return BaseResponse.fail();
    }

    @ApiOperation(value = "메모 삭제", notes = "기존 메모를 삭제한다.")
    @DeleteMapping("")
    public BaseResponse deleteMemo(Long memoId) {

        // 임시로 사용자 ID를 1로 설정 (로그인 구현 완료 후, 수정 예정)
        long userId = 1;
        
        // memoId의 작성자가 userId가 맞는지 확인
//        if (memoService.checkMemoWriter(memoId, userId)) {
//            memoService.deleteMemo(memoId);
//            return BaseResponse.success(null);
//        }

        return BaseResponse.fail();
    }
}