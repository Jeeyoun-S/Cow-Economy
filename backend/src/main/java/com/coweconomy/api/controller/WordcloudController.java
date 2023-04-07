package com.coweconomy.api.controller;

import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.domain.word.dto.WordcloudDto;
import com.coweconomy.service.WordcloudService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/wordcloud")
public class WordcloudController {
    private static final Logger logger = LoggerFactory.getLogger(WordcloudController.class);

    @Autowired
    WordcloudService wordcloudService;

    @ApiOperation(value = "Word Cloud", notes = "오늘 일자의 word cloud를 조회한다.")
    @GetMapping
    public BaseResponse getWordCloud() {
        //word cloud 가져오기
        List<WordcloudDto> wordcloudDtoList = wordcloudService.getWordCloudList();
        System.out.println(wordcloudDtoList.toString());
        return BaseResponse.success(wordcloudDtoList);
    }

}
