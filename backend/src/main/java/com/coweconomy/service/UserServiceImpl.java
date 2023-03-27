package com.coweconomy.service;

import com.coweconomy.api.request.UserRegisterPostReq;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserAuthority;
import com.coweconomy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *	User 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
//@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * 새로운 User 생성
     * @param userRegisterInfo
     * @return
     */
    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        User user = userRepository.findByUserEmail(userRegisterInfo.getUserEmail());
        if (user != null) {
            return null;
        }

        user = new User();
        user.setUserEmail(userRegisterInfo.getUserEmail());
        user.setUserNickname(userRegisterInfo.getUserNickname());
        user.setUserLevel(1);
        user.setUserExperience(0);
        userRepository.save(user);
        return user;
    }

    /**
     * 사용자 Email로 User 객체 반환
     * @param userEmail
     * @return
     */
    @Override
    public User getUserByUserEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail);
        return user;
    }

    /**
     * DB에 RefreshToken 저장
     * @param userEmail
     * @param token
     * @return
     */
    @Override
    public boolean isTokenSaved(String userEmail, String token) {
        User user = userRepository.findByUserEmail(userEmail);
        user.setToken(token);
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}