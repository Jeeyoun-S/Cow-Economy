package com.coweconomy.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse extends BaseResponse{
    private String status;
    private UserLoginPostResDto data;

    public LoginResponse(String status, UserLoginPostResDto responseDto) {
        this.status = status;
        this.data = responseDto;
    }

    public LoginResponse(String status) {
        this.status = status;
    }
}
