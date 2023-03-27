package com.coweconomy.api.controller;

import com.coweconomy.api.request.MemoRequestDto;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.repository.UserArticleMemoRepository;
import com.coweconomy.service.MemoService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/memo")
public class MemoController {

    @Autowired
    MemoService memoService;

    @Autowired
    UserArticleMemoRepository userArticleMemoRepository;

    @GetMapping("")
    public int getUserMemo() {
        return 0;
    }

    @PostMapping("")
    public BaseResponse addMemo(MemoRequestDto memoRequestDto) {

        // 입력 받은 request가 유효한 값인지 확인
        if (memoService.isValidMemoRequest(memoRequestDto)) {

            // reqeust를 entity로 변경
            UserArticleMemo userArticleMemo = memoService.changeMemoRequestToEntity(memoRequestDto);
            
            // entity가 null이 아닌 경우
            if (userArticleMemo != null) {

                // DB에 넣기
                UserArticleMemo resultMemo = userArticleMemoRepository.save(userArticleMemo);

                return BaseResponse.success(resultMemo);
            }
        }

        return BaseResponse.fail();
    }

    @PutMapping("")
    public BaseResponse modifyMemo() {


        return BaseResponse.fail();
    }

    @DeleteMapping("")
    public BaseResponse deleteMemo(Long memoId) {

        Optional<UserArticleMemo> optionalUserArticleMemo = userArticleMemoRepository.findByMemoId(memoId);
        if (optionalUserArticleMemo.isPresent()) {
            userArticleMemoRepository.delete(optionalUserArticleMemo.get());
            return BaseResponse.success(null);
        }

        return BaseResponse.fail();
    }
}