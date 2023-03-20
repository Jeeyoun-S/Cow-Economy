package com.coweconomy.api.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /v1/api/user) 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
public class UserRegisterPostReq {
    Long userId;
    String userEmail;
    String userNickname;
    int userLevel;
    int userExperience;
}
