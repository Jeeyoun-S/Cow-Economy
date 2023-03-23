package com.coweconomy.api.request;

import lombok.Data;

@Data
public class MemoRequestDto {

    long articleId;
    String memoContent;
    int memoStartPoint;
    int memoEndPoint;
    int memoScrollPoint;
    boolean memoPublicScope;
}