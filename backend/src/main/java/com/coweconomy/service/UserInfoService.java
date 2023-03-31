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
import java.time.YearMonth;
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
            List<Object[]> articleCntList = userArticleRepository.findByUserUserIdAndRegtimeBefore(userId, sixMonthAgo);

            List<Object[]> articleList = new ArrayList<>();
            YearMonth startDate = YearMonth.now().minusMonths(5);
            YearMonth endDate = YearMonth.now();
            for (YearMonth date = startDate; date.isBefore(endDate.plusMonths(1)); date = date.plusMonths(1)) {
                String yearMonthStr = date.toString();
                boolean hasDate = false;

                // 만약 현재 날짜(YY-mm)의 count 수가 있다면 articleList에 넣기
                for (Object[] article: articleCntList) {
                    if (article[0].equals(yearMonthStr)) {
                        articleList.add(new Object[] { article[0], article[1] });
                        hasDate = true;
                        break;
                    }
                }
                // 없다면, 0으로 넣기
                if (!hasDate) {
                    articleList.add(new Object[]{ yearMonthStr, 0 });
                }
            }

            return articleList;
        }
        catch (Exception exception) {
            logger.error(exception.toString());
            return null;
        }
    }

    /**
     * userId에 해당되는 1년 간 읽은 기사의 카테고리 조회 (올해 기준)
     * @param userId 회원 id(seq)
     * @return List<String>
     * **/
    public List<Object[]> getReadArticleCategory(Long userId) {
        try {
            LocalDateTime startOfYear = LocalDateTime.of(LocalDateTime.now().getYear(), Month.JANUARY, 1, 0, 0, 0); // 올해
            LocalDateTime startOfNextYear = LocalDateTime.of(LocalDateTime.now().getYear()+1, Month.JANUARY, 1, 0, 0, 0); // 내년
            List<Object[]> readArticleCategoryList = userArticleRepository.findArticleCategoryByUserIdAndYear(userId, startOfYear, startOfNextYear);
//            logger.info("#21# 읽은 기사 카테고리 조회 시 올해, 내년 확인: {} - {}", startOfYear, startOfNextYear);

            // * 카테고리 별 Count List 세팅
            return setCategoryCountList(readArticleCategoryList);
        }
        catch (Exception exception) {
            logger.error(exception.toString());
            return null;
        }
    }

    /**
     * userId에 해당되는 연-월 Quiz에서 맞춘 경제용어 카테고리 조회
     * @param userId 회원 id(seq)
     * @return List<Object[]>
     * **/
    public List<Object[]> getQuizPassWordCategory(Long userId) {
        try {
//            logger.info("#21# 현재 년-월 확인: {}-{}", LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());

            // * 카테고리 별 Count List 세팅
            return setCategoryCountList(userTestResultRepository.findArticleCategoryByUserIdAndYearAndMonth(userId, LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue()));
        } catch (Exception exception) {
            logger.error(exception.toString());
            return null;
        }
    }

    /**
     * repository 결과로 받은 List를 통해 count 개수가 null인 카테고리는 0으로 세팅
     * @param categoryCntList (repository 수행 결과) 카테고리 별 개수 List
     * @return List<Object[]>
     * **/
    private List<Object[]> setCategoryCountList(List<Object[]> categoryCntList) {
        List<String> category = new ArrayList<>(Arrays.asList("금융", "증권", "산업/재계", "중기/벤처", "부동산", "글로벌 경제", "생활경제", "경제 일반"));

        List<Object[]> result = new ArrayList<>();
        for (String categoryName: category) {
            boolean hasCategory = false;

            // 만약, 카테고리가 있다면 readArticleCntList에 넣기
            for (Object[] categoryCnt : categoryCntList) {
                if (categoryCnt[0].equals(categoryName)) {
                    result.add(new Object[]{categoryCnt[0], categoryCnt[1]});
                    hasCategory = true;
                    break;
                }
            }

            // 없다면 0으로 set
            if (!hasCategory) {
                result.add(new Object[]{categoryName, 0});
            }
        }

        return result;
    }

}
