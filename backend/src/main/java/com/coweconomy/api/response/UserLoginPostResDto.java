package com.coweconomy.api.response;

import com.coweconomy.common.model.response.BaseResponseBody;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API 요청에 대한 응답값(Response) 정의
 */
@Getter
@Setter
public class UserLoginPostResDto extends BaseResponseBody  {

    @ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
    String accessToken;
    String refreshToken;

    @ApiModelProperty(name="사용자 등록 여부", example="true")
    boolean isRegistered;

    public static UserLoginPostResDto of(Integer statusCode, String message, String accessToken, String refreshToken) {
        UserLoginPostResDto response = new UserLoginPostResDto();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }
}
