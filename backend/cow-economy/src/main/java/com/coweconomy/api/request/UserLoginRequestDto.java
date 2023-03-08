package com.coweconomy.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API 요청에 필요한 Request 바디 정의
 */
@Getter
@Setter
public class UserLoginRequestDto {
    @ApiModelProperty(name = "유저 ID", example = "ssafy@gmail.com")
    String id;
    @ApiModelProperty(name = "유저 Password", example = "ssafy01#FFY")
    String password;
}
