package com.coweconomy.api.controller;

import com.coweconomy.api.request.QuizRequestDto;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.domain.user.dto.UserArticleDto;
import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.word.entity.EconomyWord;
import com.coweconomy.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    QuizService quizService;

    /**
     * 오늘의 Quiz 문제 출제
     */
    @PostMapping("")
    public BaseResponse<?> getTodayQuizQuestion(@RequestBody QuizRequestDto info) {
        logger.info("#[QuizController]# 오늘의 Quiz 문제 출제- info: {}", info);

        // 1) 사용자가 읽은 기사에서
        // 2) 기사에 들어있는 경제 단어를 가져와야 함

        // 1) 회원이 읽은 기사 Table: 회원 id로 기사 id 리스트 조회 + 읽은 시간 일주일 이내
        List<UserArticleDto> userReadArticle = quizService.getUserReadArticle(info.getUserId());
        logger.info("#21# 회원이 읽은 기사 list 가져오기: {}", userReadArticle);

        // 2) 경제 단어 Table: 읽은 기사에 있는 경제 단어 List 조회
        List<Long> articleIdList = new ArrayList<>();
        for (UserArticleDto ra: userReadArticle) {
            articleIdList.add(ra.getArticle());
        }
        logger.info("#21# 읽은 기사 ID _List 확인: {}", articleIdList.get(0));
        List<EconomyWord> wordList = quizService.getEconomyWord(articleIdList);

//        UserArticle userReadArticle = quizService.getUserReadArticleId(userId);
//        logger.info("#21# 회원이 읽은 기사 list 가져오기: {}", userReadArticle);
        //  > 일주일 이내로 읽은 기사 id 리스트를 가져옴
        // 기사 출현 경제 용어 Table: 기사 id 리스트에 해당되는 경제 용어 id 가져옴
        //  > 일주일 이내로 읽은 기사의 경제 용어 id 리스트를 가져옴
        // 경제 용어 Table: 경제 용어 id로 경제 단어 정보 리스트 가져옴

        return BaseResponse.success(null);
    }
}
