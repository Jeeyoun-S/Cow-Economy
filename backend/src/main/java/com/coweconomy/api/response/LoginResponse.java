package com.coweconomy.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse extends BaseResponse{
    private String status;
    private UserLoginResponseDto data;

    public LoginResponse(String status, UserLoginResponseDto responseDto) {
        this.status = status;
        this.data = responseDto;
    }

    public LoginResponse(String status) {
        this.status = status;
    }
}
