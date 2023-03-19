package com.coweconomy.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    // 모델?

    @ApiModelProperty(value = "회원 ID(seq)", example="1")
    private Long userId;
}
