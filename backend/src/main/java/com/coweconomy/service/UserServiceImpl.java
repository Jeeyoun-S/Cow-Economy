package com.coweconomy.service;

import com.coweconomy.domain.user.entity.User;
import com.coweconomy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *	User 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
//@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    /**
     * ID에 해당되는 User 정보 조회(가져오기)
     * @param userId 조회할 회원 ID
     * @return User 회원 Entity
     * **/
    @Override
    public Optional<User> findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }
}
