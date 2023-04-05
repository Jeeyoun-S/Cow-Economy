package com.coweconomy.service;

import com.coweconomy.domain.word.dto.WordcloudDto;
import com.coweconomy.domain.word.entity.WordCloud;
import com.coweconomy.repository.WordCloudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordcloudService {
    @Autowired
    WordCloudRepository wordCloudRepository;

    /**
     * 워드 클라우드 조회하기
     * @return
     */
    public List<WordcloudDto> getWordCloudList() {
        List<WordCloud> wordClouds = wordCloudRepository.findAll();
        if (wordClouds.size() > 0) {
            List<WordcloudDto> wordcloudDtoList = wordClouds.stream().map(o -> new WordcloudDto(o)).collect(Collectors.toList());
            return wordcloudDtoList;
        }
        return null;
    }
}
