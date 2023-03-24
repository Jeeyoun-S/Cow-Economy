package com.coweconomy.api.controller;

import com.coweconomy.api.request.ChatGPTRequest;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.service.MyChatGPTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatGPT")
public class ChatGPTController {

    private static final Logger logger = LoggerFactory.getLogger(ChatGPTController.class);

    private final MyChatGPTService myChatGPTService;

    @Autowired
    public ChatGPTController(MyChatGPTService myChatGPTService) {
        this.myChatGPTService = myChatGPTService;
    }

    /**
     * 해당 경제용어와 유사한 경제용어 3개 조회
     * - Quiz 문제 출제 시 사용
     */
    @PostMapping("/askChatGPT")
    public BaseResponse<?> generateCompletion(@RequestBody ChatGPTRequest chatGPTRequest) {
//        logger.info("#[Gpt3Controller]# 해당 경제용어와 유사한 경제용어 3개 조회 동작 - message: {}", chatGPTRequest);

        try {
            String result = myChatGPTService.getChatResponse(chatGPTRequest.getMessage());
            return BaseResponse.success(result);
        } catch (Exception exception) {
            logger.error(exception.toString());
            return BaseResponse.fail();
        }
    }

}
