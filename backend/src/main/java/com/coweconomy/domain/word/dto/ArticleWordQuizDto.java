package com.coweconomy.domain.word.dto;

import com.coweconomy.domain.word.entity.ArticleWord;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleWordQuizDto {

    private Long articleWordId;
    private Long wordId;
    private String word;
    private String subword;
    private String wordExpl;
    private Long articleId;

    public ArticleWordQuizDto(ArticleWord articleWord) {
        this.articleWordId = articleWord.getArticleWordId();
        this.wordId = articleWord.getEconomyWord().getWordId();
        this.word = articleWord.getEconomyWord().getWord();
        this.subword = articleWord.getEconomyWord().getSubword();
        this.wordExpl = articleWord.getEconomyWord().getWordExpl();
        this.articleId = articleWord.getArticle().getArticleId();
    }
}
