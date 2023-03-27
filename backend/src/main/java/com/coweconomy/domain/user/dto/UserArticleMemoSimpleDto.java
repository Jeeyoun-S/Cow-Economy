package com.coweconomy.domain.user.dto;

import com.coweconomy.domain.user.entity.UserArticleMemo;
import lombok.Getter;

@Getter
public class UserArticleMemoSimpleDto extends UserArticleMemoDto {

    // 메모 ID
    private Long memoId;

    // 메모 공개 여부
    private boolean memoPublicScope;

    public UserArticleMemoSimpleDto(UserArticleMemo userArticleMemo) {
        super(userArticleMemo);
        this.memoId = userArticleMemo.getMemoId();
        this.memoPublicScope = userArticleMemo.getMemoPublicScope();
    }
}
