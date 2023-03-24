package com.coweconomy.api.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MemoRequestDto {

    @NotNull
    private String memoContent;

    // 메모 시작 구간
    private int memoStartRange = 0;

    // 메모 종료 구간
    private int memoEndRange = 0;

    // memoStartRange 속 메모 시작 위치
    private int memoStartIndex = 0;

    // memoEndRange 속 메모 종료 위치
    private int memoEndIndex = 0;

    // 메모 공개 여부 (true 공개, false 비공개)
    @NotNull
    private boolean memoPublicScope;
}