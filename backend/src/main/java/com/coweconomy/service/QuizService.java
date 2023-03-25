package com.coweconomy.service;

import com.coweconomy.api.controller.UserController;
import com.coweconomy.api.request.QuizResultRequestDto;
import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.dto.UserArticleDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.user.entity.UserTestResult;
import com.coweconomy.domain.word.dto.ArticleWordDto;
import com.coweconomy.domain.word.dto.ArticleWordQuizDto;
import com.coweconomy.domain.word.entity.ArticleWord;
import com.coweconomy.domain.word.entity.EconomyWord;
import com.coweconomy.repository.ArticleRepository;
import com.coweconomy.repository.UserArticleRepository;
import com.coweconomy.repository.UserRepository;
import com.coweconomy.repository.UserTestResultRepository;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
//@Transactional(readOnly = true)
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserArticleRepository userArticleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTestResultRepository userTestResultRepository;

    @Autowired
    ArticleRepository articleRepository;

    /**
     * 회원이 일주일 이내에 읽은 기사 조회
     * @param userId 조회할 회원 ID
     * @return List<UserArticle> 회원이 읽은 기사 List
     * **/
    public List<UserArticleDto> getUserReadArticle(Long userId) {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
//        logger.info("#21# 일주일 전: {}", oneWeekAgo);
        List<UserArticle> userArticles = userArticleRepository.findByArticle(userId, oneWeekAgo);

        return userArticles.stream().map(UserArticleDto::new).collect(Collectors.toList());
    }

    /**
     * 읽은 기사에 있는 경제 단어 List 조회
     * @param List<Long> 읽은 기사 ID _List
     * @return List<ArticleWordQuizDto> 읽은 기사 안에 있는 경제 단어
     * **/
    public List<ArticleWordQuizDto> getEconomyWord(List<Long> articleIdList) {
        List<ArticleWord> articleWords = userArticleRepository.findByArticleIn(articleIdList);

        // ! 중복 단어 제거
        //List<ArticleWordQuizDto> result = articleWords.stream().map(a->new ArticleWordQuizDto(a)).collect(Collectors.toList());
        // #21#
        List<ArticleWordQuizDto> result = articleWords.stream()
                .filter(distinctByKey(a->a.getEconomyWord().getWordId()))
                .map(ArticleWordQuizDto::new).collect(Collectors.toList());

        return result;
    }

    public static <T>Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 경험치 획득 (+100)
     * @param userId 경험치 획득한 회원 ID
     * @return User 회원 정보
     * **/
    public User getUserExperience(Long userId) {
        // 1) 회원정보 가져오기
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isPresent()) {
            // 2) 경험치 획득 적용
            User originUser = user.get();
            originUser.setUserExperience(originUser.getUserExperience() + 100);
//            logger.info("#[QuizService]# 경험치 획득 적용 user 확인: {}", originUser);

            userRepository.save(originUser);
            return originUser;
        }
        // 3) 없을 경우 null return
        return null;
    }

    /**
     * Quiz 성공/실패 결과 저장
     * @param Long 회원 ID, Boolean 성공/실패 여부 [QuizResultRequestDto(userId=1, isPassFlag=true, selectArticleId=[1, 1, 2, 3, 3, 3, 3])]
     * @return boolean
     * **/
    public boolean quizResultSave(QuizResultRequestDto quizResult) {
        try {
            // 1) 회원정보 가져오기
            Optional<User> user = userRepository.findByUserId(quizResult.getUserId());
            // F) 회원이 없을 경우 return null
            if (user == null) return false;

            for (Long atId: quizResult.getSelectArticleId()) {
                // 2) Quiz 결과 저장
                UserTestResult result = userTestResultRepository.save(new UserTestResult(user.get(), articleRepository.findByArticleId(atId), quizResult));
                if (result == null) return false;
            }
            return true;

        } catch (Exception exception) {
            logger.error(exception.toString());
            return false;
        }
    }

    /**
     * 오늘의 Quiz 수행 여부 확인
     * @param Long 회원 ID(seq)
     * @return boolean
     * **/
    public boolean checkQuizToday(Long userId) {
        try {
            LocalDateTime today = LocalDateTime.now();
            List<UserTestResult> result =
                    userTestResultRepository.findByUserUserIdAndRegtime(
                            userId,
                            today.withHour(00).withMinute(00).withSecond(00),
                            today.withHour(23).withMinute(59).withSecond(59));
//            logger.info("#21# result 확인: {}, start-{}, end-{}", result, today.withHour(00).withMinute(00).withSecond(00), today.withHour(23).withMinute(00).withSecond(00));

            // Quiz 도전 가능
            if (result.size() == 0) {
                return true;
            }
            else {
                // 불가능
                return false;
            }
        } catch (Exception exception) {
            logger.error(exception.toString());
            return false;
        }
    }

}
