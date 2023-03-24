package com.coweconomy.domain.user.dto;

import com.coweconomy.domain.user.entity.UserArticleMemo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserArticleMemoDto {

    // 메모 ID
    private Long memoId;

    // 메모 등록 시간
    private LocalDateTime regtime;

    // 메모 내용
    private String memoContent;

    // 메모 시작 구간
    private int memoStartRange;

    // 메모 종료 구간
    private int memoEndRange;

    // memoStartRange 내 메모 시작 위치
    private int memoStartIndex;

    // memoEndRange 내 메모 종료 위치
    private int memoEndIndex;

    // 메모 공개 여부
    private boolean memoPublicScope;

    // 기사 ID
    private Long articleId;

    public UserArticleMemoDto(UserArticleMemo userArticleMemo) {
        this.memoId = userArticleMemo.getMemoId();
        this.regtime = userArticleMemo.getRegtime();
        this.memoContent = userArticleMemo.getMemoContent();
        this.memoStartRange = userArticleMemo.getMemoStartRange();
        this.memoEndRange = userArticleMemo.getMemoEndRange();
        this.memoStartIndex = userArticleMemo.getMemoStartIndex();
        this.memoEndIndex = userArticleMemo.getMemoEndIndex();
        this.memoPublicScope = userArticleMemo.getMemoPublicScope();
        this.articleId = userArticleMemo.getArticle().getArticleId();
    }

}
