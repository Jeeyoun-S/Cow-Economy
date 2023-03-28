package com.coweconomy.domain.word.dto;

import com.coweconomy.domain.word.entity.ArticleWord;
import com.coweconomy.domain.word.entity.EconomyWord;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class EconomyWordDto {

    // 경제 단어 소제목
    private String subword;

    // 경제 단어 설명
    private String wordExpl;

    public EconomyWordDto(EconomyWord economyWord) {
        this.subword = economyWord.getSubword();
        this.wordExpl = economyWord.getWordExpl();
    }
}

