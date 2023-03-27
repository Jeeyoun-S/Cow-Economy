package com.coweconomy.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyChatGPTService {
    private static final Logger logger = LoggerFactory.getLogger(MyChatGPTService.class);

    private ChatgptService chatgptService;

    @Autowired
    public MyChatGPTService(ChatgptService chatgptService) {
        this.chatgptService = chatgptService;
    }

    /**
     * 해당 경제용어와 유사한 경제용어 3개 chatGPT에게 물어보기
     * @param message 물어볼 메세지
     * @return String
     * **/
    public String getChatResponse(String message) {
        try {
            // chatGPT에게 메세지 보내기
            String responseMessage = chatgptService.sendMessage(message);
            return responseMessage;
        } catch (Exception exception) {
            logger.error(exception.toString());
            return null;
        }
    }

}
