package com.coweconomy.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserTestResultDto {

    @Comment("회원의 테스트 결과 ID(seq)")
    private Long testResultId;

    @Comment("Quiz를 푼 - 회원 ID(seq)")
    private Long user;

    @Comment("Quiz에 나온 - 기사 ID(seq)")
    private Long article;

    @Comment("Quiz를 푼 시각")
    private LocalDateTime regtime;

}
