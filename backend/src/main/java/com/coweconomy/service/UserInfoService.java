package com.coweconomy.service;

import com.coweconomy.api.controller.UserController;
import com.coweconomy.domain.user.dto.UserDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.repository.UserArticleMemoRepository;
import com.coweconomy.repository.UserArticleRepository;
import com.coweconomy.repository.UserRepository;
import com.coweconomy.repository.UserTestResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

    @Autowired
    UserArticleMemoRepository userArticleMemoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserArticleRepository userArticleRepository;

    @Autowired
    UserTestResultRepository userTestResultRepository;
    
    /**
     * userId에 해당하는 모든 memo 가져오기
     * @param userId 사용자 ID
     * **/
    public List<UserArticleMemo> getUserMemo(Long userId) {
        return userArticleMemoRepository.findAllByUser_UserId(userId);
    }

    /**
     * userId에 해당되는 User 정보 조회
     * @param userId 회원 id(seq)
     * @return User 회원 Entity
     * **/
    public UserDto getUserByUserId(Long userId) {
        Optional<User> user = userRepository.findByUserId(userId);

        // S) user 정보 return
        if (user.isPresent()) {
            // Level에 따라 F ~ S 중 하나로 mapping (ASCII CODE)
            Map<Integer, Integer> levelMap = new HashMap<>();
            levelMap.put(1, 70); // F
            levelMap.put(2, 68); // D
            levelMap.put(3, 67); // C
            levelMap.put(4, 66); // B
            levelMap.put(5, 65); // A
            levelMap.put(6, 83); // S
            user.get().setUserLevel(levelMap.getOrDefault(user.get().getUserLevel(), 0));

            return new UserDto(user.get());
        }
        // F)
        return null;
    }

    /**
     * userId에 해당되는 6개월 간 읽은 기사 수 조회
     * @param userId 회원 id(seq)
     * @return List<Integer>
     * **/
    public List<Object[]> getReadArticleCount(Long userId) {
        try {
            LocalDateTime sixMonthAgo = LocalDateTime.now().minusMonths(6);
            return userArticleRepository.findByUserUserIdAndRegtimeBefore(userId, sixMonthAgo);
        }
        catch (Exception exception) {
            logger.error(exception.toString());
            return null;
        }
    }

    /**
     * userId에 해당되는 1년 간 읽은 기사의 카테고리 조회
     * @param userId 회원 id(seq), year 연
     * @return List<String>
     * **/
    public List<Object[]> getReadArticleCategory(Long userId, String year) {
        try {
            // 올해
            LocalDateTime startOfYear = LocalDateTime.of(Integer.parseInt(year), Month.JANUARY, 1, 0, 0, 0);
            // 내년
            LocalDateTime startOfNextYear = LocalDateTime.of(Integer.parseInt(year)+1, Month.JANUARY, 1, 0, 0, 0);
//            logger.info("#21# 읽은 기사 카테고리 조회 시 올해, 내년 확인: {} - {}", startOfYear, startOfNextYear);

            return userArticleRepository.findArticleCategoryByUserIdAndYear(userId, startOfYear, startOfNextYear);
        }
        catch (Exception exception) {
            logger.error(exception.toString());
            return null;
        }
    }

    /**
     * userId에 해당되는 연-월 Quiz에서 맞춘 경제용어 카테고리 조회
     * @param userId 회원 id(seq), year 연, month 월
     * @return List<Object[]>
     * **/
    public List<Object[]> getQuizPassWordCategory(Long userId, String year, String month) {
        try {
            return userTestResultRepository.findArticleCategoryByUserIdAndYearAndMonth(userId, Integer.parseInt(year), Integer.parseInt(month));
        }
        catch (Exception exception) {
            logger.error(exception.toString());
            return null;
        }
    }

}
