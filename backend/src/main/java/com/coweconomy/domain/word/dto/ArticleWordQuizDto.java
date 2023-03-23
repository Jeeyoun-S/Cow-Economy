package com.coweconomy.domain.word.dto;

import com.coweconomy.domain.word.entity.ArticleWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Data
@NoArgsConstructor
public class ArticleWordQuizDto {

    // 기사 안에 있는 단어 ID(seq)
    private Long articleWordId;
    // 경제 단어 ID
    private Long wordId;
    // 경제 단어
    private String word;
    // 서브 경제 단어
    private String subword;
    // 경제 단어 설명
    private String wordExpl;
    // 경제 기사 ID
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
