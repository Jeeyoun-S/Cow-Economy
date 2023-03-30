package com.coweconomy.service;

import com.coweconomy.api.controller.UserController;
import com.coweconomy.api.request.QuizResultRequestDto;
import com.coweconomy.domain.user.dto.UserArticleDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.user.entity.UserTestResult;
import com.coweconomy.domain.word.dto.ArticleWordDto;
import com.coweconomy.domain.word.entity.ArticleWord;
import com.coweconomy.domain.word.entity.EconomyWord;
import com.coweconomy.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserArticleRepository userArticleRepository;

    private final UserRepository userRepository;

    private final UserTestResultRepository userTestResultRepository;

    private final ArticleRepository articleRepository;

    private final EconomyWordRepository economyWordRepository;

    private final ArticleWordRepository articleWordRepository;

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
     * @param articleIdList 읽은 기사 ID _List
     * @return List<ArticleWordQuizDto> 읽은 기사 안에 있는 경제 단어
     * **/
    public List<ArticleWordDto> getEconomyWord(List<Long> articleIdList) {
        List<ArticleWord> articleWords = articleWordRepository.findByArticleIn(articleIdList);
//        logger.info("#21# 기사 안에 있는 경제 단어 조회:{}", articleWords.size() );

        List<ArticleWordDto> result = new ArrayList<>();
        Map<Long, String> map = new HashMap<>();

        for (ArticleWord articleWord:articleWords) {
            String[] subwordList = articleWord.getSubWordId().split(",");

            for (String id:subwordList) {
                EconomyWord word = economyWordRepository.findByWordId(Long.parseLong(id));
                if(map.containsKey(word.getWordId())) continue;
                map.put(word.getWordId(), word.getWord());
                ArticleWordDto dto = new ArticleWordDto(articleWord.getArticle().getArticleId(), word);
                result.add(dto);
            }

        }
        // * 단어 확인용
//        for(ArticleWordDto a: result){
//            logger.info("### {}",a.getEconomyWord().getWord());
//        }

        if (result.size() < 7) return null; // 경제단어 7개 이하 > Quiz 출제 불가
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
    @Transactional
    public User getUserExperience(Long userId) {
        // 1) 회원정보 가져오기
        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isPresent()) {
            // 2) 경험치 획득 적용
            User originUser = user.get();
            originUser.increaseExperience(100); // 경험치 조정 + 올라간 경험치에 따라 Level 조정
//            logger.info("#[QuizService]# 경험치 획득 적용 user 확인: {}", originUser);

            userRepository.save(originUser);
            return originUser;
        }
        // 3) 없을 경우 null return
        return null;
    }

    /**
     * Quiz 성공/실패 결과 저장
     * @param quizResult 회원 ID, Boolean 성공/실패 여부 [QuizResultRequestDto(userId=1, isPassFlag=true, selectArticleId=[1, 1, 2, 3, 3, 3, 3])]
     * @return boolean
     * **/
    @Transactional
    public boolean quizResultSave(QuizResultRequestDto quizResult) {
        try {
            // 1) 회원정보 가져오기
            Optional<User> user = userRepository.findByUserId(quizResult.getUserId());
            // F) 회원이 없을 경우 return null
            if (user == null) return false;

            // * (전처리) 기사 ID 중복제거
            HashSet<Long> set = new HashSet<>(quizResult.getSelectArticleId());
            List<Long> selectArticleList = new ArrayList<>(set);
//            logger.info("#21# 기사 ID 중복제거: {}", selectArticleList);
            for (Long articleId: selectArticleList) {
                // 2) Quiz 결과 저장
                UserTestResult result = userTestResultRepository.save(new UserTestResult(user.get(), articleRepository.findByArticleId(articleId), quizResult));
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
     * @param userId 회원 ID(seq)
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
