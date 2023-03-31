package com.coweconomy.api.request;

import lombok.Data;

import java.util.List;

/**
 * 오늘의 Quiz 결과 Request Dto
 */
@Data
public class QuizResultRequestDto {

    // 회원 ID
    private Long userId;

    // Quiz 성공 여부 [true = 성공, false = 실패]
    private Boolean isPassFlag;

    // Quiz 출제 시 선택한 기사 List
    List<Long> selectArticleId;

}
