package com.coweconomy.api.controller;

import com.coweconomy.api.request.QuizRequestDto;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.common.util.RandomSelect;
import com.coweconomy.domain.user.dto.UserArticleDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.word.dto.ArticleWordQuizDto;
import com.coweconomy.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/quiz")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    QuizService quizService;

    RandomSelect randomSelect = new RandomSelect();

    /**
     * 오늘의 Quiz 문제 출제
     * - 회원이 일주일 내에 읽은 기사에서 > 해당 기사에 들어있는 경제 단어 가져오기
     */
    @PostMapping("")
    public BaseResponse<?> getTodayQuizQuestion(@RequestBody QuizRequestDto info) {
//        logger.info("#[QuizController]# 오늘의 Quiz 문제 출제- info: {}", info);

        // 1) 회원이 읽은 기사 Table: 회원 id로 기사 id 리스트 조회 + 읽은 시간 일주일 이내
        List<UserArticleDto> userReadArticle = quizService.getUserReadArticle(info.getUserId());
//        logger.info("#21# 회원이 읽은 기사 list 가져오기: {}", userReadArticle);

        // 2) 기사 내 경제 단어 Table: 읽은 기사에 있는 경제 단어 List 조회
        List<Long> articleIdList = new ArrayList<>();
        for (UserArticleDto ra: userReadArticle) {
            articleIdList.add(ra.getArticle());
        }
//        logger.info("#21# 읽은 기사 ID _List 확인: {}", articleIdList);
        List<ArticleWordQuizDto> wordList = quizService.getEconomyWord(articleIdList);
//        logger.info("#21# 읽은 기사 내 경제 단어 List 확인: {}", wordList);

        // 3) 가져온 경제 단어를 토대로 문제 출제
        // i) 7개 단어 선정 (Random)
        // ii) chatGPT API 사용해서 유사한 단어
        List<Integer> random = randomSelect.getRandomSelect(wordList.size());
//        logger.info("#21# 랜덤 선택 확인: {}", random);
        List<ArticleWordQuizDto> quizWord = new ArrayList<>();
        for (Integer idx: random) {
            quizWord.add(wordList.get(idx));
        }
//        logger.info("#21# 7개의 Quiz 선정 확인: {}, {}개", quizWord, quizWord.size());

        return BaseResponse.success(quizWord);
    }

    /**
     * Quiz 성공 시 - 경험치 획득 (+100)
     */
    @PostMapping("/getExp")
    public BaseResponse<?> getExperience(@RequestBody QuizRequestDto info) {
//        logger.info("#[QuizController]# 경험치 획득 (+100)- info: {}", info);

        // 1) 해당 user 경험치 +100 적용
        User user = quizService.getUserExperience(info.getUserId());
        if (user != null) {
            // S) user 현재 경험치 정보 return
//            logger.info("#21# 경험치 적용 user 확인: {}", user);
            return BaseResponse.success(user.getUserExperience());
        }

        // F) fail
        return BaseResponse.fail();
    }
}
