package com.coweconomy.service;

import com.coweconomy.api.request.UserRegisterPostReq;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserAuthority;
import com.coweconomy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *	User 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
//@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * 회원가입
     * @param userRegisterInfo 회원가입 할 회원의 정보
     * @return User 회원 Entity
     * **/
    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        User user = userRepository.findByUserEmail(userRegisterInfo.getUserEmail());
        if (user != null) {
            return null;
        }
//        System.out.println("유저를 저장하겠습니다.");

        user = new User();
        user.setUserEmail(userRegisterInfo.getUserEmail());
        user.setUserNickname(userRegisterInfo.getUserNickname());
        user.setUserLevel(1);
        user.setUserExperience(0);
        userRepository.save(user);
//        System.out.println("네 유저를 저장했습니다.");
        return user;
    }

    /**
     * email에 해당되는 User 정보 조회
     * @param userEmail 회원 email
     * @return User 회원 Entity
     * **/
    @Override
    public User getUserByUserEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail);
        return user;
    }

    /**
     * email에 해당되는 User - 로그인 refrash_token 저장
     * @param userEmail 회원 email, token 리프레쉬 토큰
     * @return boolean [false 실패, true 성공]
     * **/
    @Override
    public boolean isTokenSaved(String userEmail, String token) {
        User user = userRepository.findByUserEmail(userEmail);
        user.setUserToken(token);
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    @Override
//    public UserDto getUserInfoByEmail(String userEmail) {
//        User user = userRepository.findByUserEmail(userEmail);
//        UserDto userDto = new UserDto(user);
//        return userDto;
//    }
}
