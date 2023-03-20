package com.coweconomy.domain.user.dto;

import com.coweconomy.domain.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String userEmail;
    private String userNickname;
    private int userLevel;
    private int userExperience;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.userEmail = user.getUserEmail();
        this.userNickname = user.getUserNickname();
        this.userLevel = user.getUserLevel();
        this.userExperience = user.getUserExperience();
    }

//    @ApiModelProperty(value = "회원 ID(seq)", example="1")
//    private Long userId;
}
