package com.coweconomy.common.model.response;

import org.hibernate.annotations.Comment;

import java.util.List;

public class QuizResponseDto {

    @Comment("경제 단어 설명")
    private String question;

    // !! 만드는 중
    private List<String> answers;


    //        questions: [
        //        {
        //            question:
        //            "[ ]란 OECD 기준에 따라 가구를 소득 순으로 나열했을 때, 한가운데에 있는 가구소득(중위소득)의 50~150% 범위에 속한 가구를 뜻한다.",
        //                    answers: {
        //                    a: "중산층가구",
        //                    b: "0.5인 가구",
        //                    c: "중위소득",
        //                    d: "4차 산업혁명",
        //          },
        //            correctAnswer: "a",
        //        },
    //      ],
}
