package com.coweconomy.service;

import com.coweconomy.api.request.UserRegisterPostReq;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.User;

import java.util.Optional;

/**
 *	User 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
    /**
     * DB에 회원이 없을 경우 새로운 회원 생성
     * @param userRegisterInfo
     * @return
     */
    User createUser(UserRegisterPostReq userRegisterInfo);

    /**
     * 회원 Email로 User 객체 가져오기
     * @param userEmail
     * @return
     */
    User getUserByUserEmail(String userEmail);

    /**
     * RefreshToken DB 저장
     * @param userEmail
     * @param token
     * @return
     */
    boolean isTokenSaved(String userEmail, String token);
}
