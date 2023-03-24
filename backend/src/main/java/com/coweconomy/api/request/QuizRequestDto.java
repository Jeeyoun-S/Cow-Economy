package com.coweconomy.api.request;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

/**
 * 오늘의 Quiz 문제 출제 시 Request Dto
 */
@Data
public class QuizRequestDto {

    // 회원 ID
    private Long userId;
}
