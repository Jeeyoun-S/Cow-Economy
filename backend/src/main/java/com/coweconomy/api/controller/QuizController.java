package com.coweconomy.api.controller;

import com.coweconomy.api.request.QuizRequestDto;
import com.coweconomy.api.request.QuizResultRequestDto;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.common.jwt.JwtTokenUtil;
import com.coweconomy.common.util.RandomSelect;
import com.coweconomy.domain.user.dto.UserArticleDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserTestResult;
import com.coweconomy.domain.word.dto.ArticleWordDto;
import com.coweconomy.domain.word.dto.ArticleWordQuizDto;
import com.coweconomy.service.QuizService;
import com.coweconomy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin()
@RequestMapping("/quiz")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    QuizService quizService;

    @Autowired
    UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    RandomSelect randomSelect = new RandomSelect();

    /**
     * 오늘의 Quiz 수행 여부 확인
     * - 회원이 오늘 Quiz를 진행했는지 안했는지 조회
     */
    @GetMapping("/")
    public BaseResponse<?> checkQuizDone(HttpServletRequest request) {
        logger.info("#[QuizController]# Quiz 수행 여부 확인");

        try {
            // 0) 현재 login 한 유저 아이디 추출
            String accessToken = request.getHeader("Authorization").substring(7);
            Long userId = userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId();
//            logger.info("#21# Quiz 수행 여부 확인 - 현재 login 한 userId: {}", userId);

            boolean result = quizService.checkQuizToday(userId);
//            logger.info("#21# Quiz 도전 가능/불가능 확인: {}", result);

            // Quiz 도전 가능 (true)
            if (result) return BaseResponse.success(result);
                // Quiz 불가능 (false)
            else if (result == false) return BaseResponse.success(result);

            return BaseResponse.fail();
        } catch (Exception exception) {
            logger.error(exception.toString());
            return BaseResponse.fail();
        }
    }

    /**
     * 오늘의 Quiz 문제 출제
     * - 회원이 일주일 내에 읽은 기사에서 > 해당 기사에 들어있는 경제 단어 가져오기
     */
    @PostMapping("")
    public BaseResponse<?> getTodayQuizQuestion(@RequestBody QuizRequestDto info, HttpServletRequest request) {
        logger.info("#[QuizController]# 오늘의 Quiz 문제 출제- info: {}", info);

        try {
        // 0) 현재 login 한 유저 아이디 추출
//        String accessToken = request.getHeader("Authorization").substring(7);
//        info.setUserId(userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId());

        // 1) 회원이 읽은 기사 Table: 회원 id로 기사 id 리스트 조회 + 읽은 시간 일주일 이내
        List<UserArticleDto> userReadArticle = quizService.getUserReadArticle(info.getUserId());
//        logger.info("#21# 회원이 읽은 기사 list 가져오기: {}", userReadArticle);

            // 2) 기사 내 경제 단어 Table: 읽은 기사에 있는 경제 단어 List 조회
            // - 회원이 읽은 기사 ID 추출
            List<Long> articleIdList = new ArrayList<>();
            for (UserArticleDto ra: userReadArticle) {
                articleIdList.add(ra.getArticle());
            }
//        logger.info("#21# 읽은 기사 ID _List 확인: {}", articleIdList);
            // - 읽은 기사 내 경제 용어 추출
            List<ArticleWordDto> wordList = quizService.getEconomyWord(articleIdList);
        logger.info("#21# 읽은 기사 내 경제 단어 List 확인: {}", wordList.size());

            // 2-1) 만약, 경제 단어가 7개 이하일 경우 > ?? > 오늘의 Quiz 접근 제한
//            if (wordList.size() < 7) return BaseResponse.fail();
            if (wordList == null) return BaseResponse.fail();

            // 3) 가져온 경제 단어를 토대로 문제 출제
            // - 7개 단어 선정 (Random)
            List<Integer> random = randomSelect.getRandomSelect(wordList.size());
//        logger.info("#21# 랜덤 선택 확인: {}", random);
            List<ArticleWordDto> quizWord = new ArrayList<>();
            for (Integer idx: random) {
                quizWord.add(wordList.get(idx));
            }
        logger.info("#21# 7개의 Quiz 선정 확인: {}, {}개", quizWord, quizWord.size());

            return BaseResponse.success(quizWord);
        }
        catch (Exception exception) {
            logger.error(exception.toString());
            return BaseResponse.fail();
        }
    }


    /**
     * Quiz 성공/실패 여부 저장 + 성공 시 경험치 획득(+100)
     */
    @PostMapping("/setResult")
    public BaseResponse<?> setQuizResult(@RequestBody QuizResultRequestDto quizResult, HttpServletRequest request) {
        logger.info("#[QuizController]# Quiz 결과 - info: {}", quizResult);

        // 0) 현재 login 한 유저 아이디 추출
        String accessToken = request.getHeader("Authorization").substring(7);
        quizResult.setUserId(userService.getUserByUserEmail(jwtTokenUtil.getUserEmailFromToken(accessToken)).getUserId());

        // * 성공 ↔ 실패 여부에 따라 다른 로직 처리
        // 1) 성공/실패 결과 저장
        boolean userTestResult = quizService.quizResultSave(quizResult);
        // F) 성공/실패 결과 저장 Fail
        if (userTestResult == false) return BaseResponse.fail();

        // 2) Quiz 성공 시 경험치 부여
        if (quizResult.getIsPassFlag()) {
            // i) 해당 user 경험치 +100 적용
            User user = quizService.getUserExperience(quizResult.getUserId());
//            logger.info("#21# 경험치 획득 확인: {}", user);

            // F) 경험치 +100 적용 Fail
            if (user == null) return BaseResponse.fail();

            // S) 경험치 적용 성공 > 현 user 경험치 return
            return BaseResponse.success(user.getUserExperience());
        }

        // S) 결과 저장 성공
        return BaseResponse.success(null);
    }

}
