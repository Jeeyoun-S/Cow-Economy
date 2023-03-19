package com.coweconomy.service;

import com.coweconomy.domain.user.entity.User;

import java.util.Optional;

/**
 *	User 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
    /**
     * ID에 해당되는 User 정보 조회(가져오기)
     * @param userId 조회할 회원 ID
     * @return User 회원 Entity
     * **/

    User registerUser(User user);
    Optional<User> findByUserEmail(String userEmail);
}
