package com.coweconomy.api.request;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 오늘의 Quiz 문제 출제 시 Request Dto
 */
@Data
public class QuizRequestDto {
    private Long userId;
}
