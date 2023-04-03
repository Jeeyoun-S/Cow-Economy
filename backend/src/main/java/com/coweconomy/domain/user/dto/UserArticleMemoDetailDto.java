package com.coweconomy.domain.user.dto;

import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import lombok.Getter;

@Getter
public class UserArticleMemoDetailDto extends UserArticleMemoDto {

    // 사용자 닉네임
    private String userNickname;
    
    // 사용자 레벨
    private int userLevel;
    
    // 사용자 경험치
    private int userExperience;

    public UserArticleMemoDetailDto(UserArticleMemo userArticleMemo) {
        super(userArticleMemo);
        User user = userArticleMemo.getUser();
        this.userNickname = user.getUserNickname();
        this.userLevel = user.getUserLevel();
        this.userExperience = user.getUserExperience();
    }
}
