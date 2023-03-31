package com.coweconomy.api.request;

import lombok.Data;

/**
 * 오늘의 Quiz 4지선다 제작을 위해 ChatGPT API 사용 시 Request Dto
 */
@Data
public abstract class ChatGPTRequest {

    // chatGPT에게 물어볼 message
    // private String message;

    // 기사 ID
    private Long articleId;

    // 경제용어
    private EconomyWord economyWord;

    public static class EconomyWord {
        private int wordId;
        private String wordExpl;
        private String word;
        private String subword;
    }
}
