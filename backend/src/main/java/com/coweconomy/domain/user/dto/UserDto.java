package com.coweconomy.domain.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    // 회원 ID(seq)
    private Long userId;
}
