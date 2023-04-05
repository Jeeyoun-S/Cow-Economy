package com.coweconomy.domain.word.dto;

import com.coweconomy.domain.word.entity.WordCloud;
import lombok.Data;

@Data
public class WordcloudDto {
    //워드 클라우드 명사
    private String name;
    //워드 클라우드 명사 빈도수
    private Long value;

    public WordcloudDto(WordCloud wordCloud) {
        this.name = wordCloud.getName();
        this.value = wordCloud.getValue();
    }
}
