package com.coweconomy.api.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /v1/api/user) 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
public class UserRegisterPostReq {

    // 회원 이메일
    String userEmail;
    // 회원 닉네임
    String userNickname;
    // 회원 레벨
    int userLevel;
    // 회원 경험치
    int userExperience;

}
