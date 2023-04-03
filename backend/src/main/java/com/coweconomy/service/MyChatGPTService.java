package com.coweconomy.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @return List<String>
     * **/
    public List<String> getChatResponse(String message) {
        try {
            // 1) chatGPT에게 메세지 보내기 - chatgptService.sendMessage(message)
            // 2) chatGPT 응답 메세지에서 경제단어 추출 [정규식 사용]
            Pattern pattern = Pattern.compile("(?:\\d\\. )(.+?)(?=\\(|\n|$)");
            Matcher matcher = pattern.matcher(chatgptService.sendMessage(message));

            List<String> words = new ArrayList<>();
            while (matcher.find()) {
                words.add(matcher.group(1));
            }

            return words;
        }
        catch (Exception exception) {
            logger.error(exception.toString());
            return null;
        }
    }

}
