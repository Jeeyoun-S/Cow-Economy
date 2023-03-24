package com.coweconomy.service;

import com.coweconomy.api.request.UserRegisterPostReq;
import com.coweconomy.domain.user.entity.User;

/**
 *	User 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {

    /**
     * 회원가입
     * @param userRegisterInfo 회원가입 할 회원의 정보
     * @return User 회원 Entity
     * **/
    User createUser(UserRegisterPostReq userRegisterInfo);

    /**
     * email에 해당되는 User 정보 조회
     * @param userEmail 회원 email
     * @return User 회원 Entity
     * **/
    User getUserByUserEmail(String userEmail);

    /**
     * email에 해당되는 User - 로그인 refrash_token 저장
     * @param userEmail 회원 email, token 리프레쉬 토큰
     * @return boolean [false 실패, true 성공]
     * **/
    boolean isTokenSaved(String userEmail, String token);
}
