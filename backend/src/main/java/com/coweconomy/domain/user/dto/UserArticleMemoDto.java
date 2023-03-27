package com.coweconomy.domain.user.dto;

import com.coweconomy.domain.user.entity.UserArticleMemo;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class UserArticleMemoDto {

    // 메모 등록 시간
    private String regtime;

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

    public UserArticleMemoDto(UserArticleMemo userArticleMemo) {
        this.regtime = userArticleMemo.getRegtime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.memoContent = userArticleMemo.getMemoContent();
        this.memoStartRange = userArticleMemo.getMemoStartRange();
        this.memoEndRange = userArticleMemo.getMemoEndRange();
        this.memoStartIndex = userArticleMemo.getMemoStartIndex();
        this.memoEndIndex = userArticleMemo.getMemoEndIndex();
    }
}
