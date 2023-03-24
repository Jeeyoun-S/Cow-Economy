package com.coweconomy.api.request;

import lombok.Data;

/**
 * 오늘의 Quiz 4지선다 제작을 위해 ChatGPT API 사용 시 Request Dto
 */
@Data
public class ChatGPTRequest {

    // chatGPT에게 물어볼 message
    private String message;

}
