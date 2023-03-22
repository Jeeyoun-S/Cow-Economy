package com.coweconomy.service;

import com.coweconomy.domain.user.dto.UserArticleDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.word.dto.ArticleWordDto;
import com.coweconomy.domain.word.dto.ArticleWordQuizDto;
import com.coweconomy.domain.word.entity.ArticleWord;
import com.coweconomy.domain.word.entity.EconomyWord;
import com.coweconomy.repository.UserArticleRepository;
import com.coweconomy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    @Autowired
    UserArticleRepository userArticleRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * 회원이 일주일 이내에 읽은 기사 조회
     * @param userId 조회할 회원 ID
     * @return List<UserArticle> 회원이 읽은 기사 List
     * **/
    public List<UserArticleDto> getUserReadArticle(Long userId) {
        List<UserArticle> userArticles = new ArrayList<>();
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
//        logger.info("#21# 일주일 전: {}", oneWeekAgo);
        userArticles = userArticleRepository.findByArticle(userId, oneWeekAgo);

        List<UserArticleDto> result = userArticles.stream().map(u->new UserArticleDto(u)).collect(Collectors.toList());
        return result;
    }

    /**
     * 읽은 기사에 있는 경제 단어 List 조회
     * @param List<Long> 읽은 기사 ID _List
     * @return List<ArticleWordQuizDto> 읽은 기사 안에 있는 경제 단어
     * **/
    public List<ArticleWordQuizDto> getEconomyWord(List<Long> articleIdList) {
        List<ArticleWord> articleWords = new ArrayList<>();
        articleWords = userArticleRepository.findByArticleIn(articleIdList);

        List<ArticleWordQuizDto> result = articleWords.stream().map(a->new ArticleWordQuizDto(a)).collect(Collectors.toList());
        return result;
    }

    /**
     * 경험치 획득 (+100)
     * @param userId 경험치 획득한 회원 ID
     * @return User 회원 정보
     * **/
    public User getUserExperience(Long userId) {
        // 1) 회원정보 가져오기
        User user = userRepository.findByUserId(userId);

        if (user != null) {
            // 2) 경험치 획득 적용
            user.setUserExperience(user.getUserExperience() + 100);
            logger.info("#21# 경험치 획득 적용 user 확인: {}", user);
            // ! 연관된 user_test_result 테이블도 데이터 저장해야함 
            User result = userRepository.save(user);

            return result;
        }
        // 3) 없을 경우 null return
        return null;
    }

}
