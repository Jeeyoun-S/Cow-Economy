package com.coweconomy.service;

import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.repository.UserArticleMemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    UserArticleMemoRepository userArticleMemoRepository;
    
    /**
     * userId에 해당하는 모든 memo 가져오기
     * @param userId 사용자 ID
     * **/
    public List<UserArticleMemo> getUserMemo(Long userId) {
        return userArticleMemoRepository.findAllByUser_UserId(userId);
    }
}
