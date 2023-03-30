package com.coweconomy.domain.user.dto;

import com.coweconomy.domain.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Data
@NoArgsConstructor
public class UserDto {

    @Comment("회원의 Id(seq)")
    private Long userId;
    @Comment("회원의 로그인 Email")
    private String userEmail;
    @Comment("카카오에서 가져온 회원 닉네임")
    private String userNickname;
    @Comment("회원의 경제 레벨")
    private int userLevel;
    @Comment("회원의 경험치")
    private int userExperience;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.userEmail = user.getUserEmail();
        this.userNickname = user.getUserNickname();
        this.userLevel = user.getUserLevel();
        this.userExperience = user.getUserExperience();
    }
}
