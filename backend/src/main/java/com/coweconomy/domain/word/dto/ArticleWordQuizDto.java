package com.coweconomy.domain.word.dto;

import com.coweconomy.domain.word.entity.ArticleWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Data
@NoArgsConstructor
public class ArticleWordQuizDto {

    @Comment("기사 안에 있는 단어 ID(seq)")
    private Long articleWordId;
    @Comment("경제 단어 ID")
    private Long wordId;
    @Comment("경제 단어")
    private String word;
    @Comment("서브 경제 단어")
    private String subword;
    @Comment("경제 단어 설명")
    private String wordExpl;
    @Comment("경제 기사 ID")
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
