package com.coweconomy.domain.word.dto;

import com.coweconomy.domain.word.entity.ArticleWord;
import com.coweconomy.domain.word.entity.EconomyWord;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleWordQuizDto {

    // 기사 안에 있는 단어 ID(seq)
    private Long articleWordId;
//    // 경제 단어 ID
//    private Long wordId;
//    // 경제 단어
//    private String word;
//    // 서브 경제 단어
//    private String subword;
//    // 경제 단어 설명
//    private String wordExpl;
//    // 경제 기사 ID
//    private Long articleId;

    // 기사 안에 있는 경제단어 ID List (경제단어 객체 List)
    private List<EconomyWord> wordList;
    // 기사 안에 있는 경제단어 ID List
    private String wordIds;

    public ArticleWordQuizDto(ArticleWord articleWord) {
        this.articleWordId = articleWord.getArticleWordId();
        this.wordIds = articleWord.getSubWordId();
        this.wordList = new ArrayList<>();

//        this.articleWordId = articleWord.getArticleWordId();
//        this.wordId = articleWord.getEconomyWord().getWordId();
//        this.word = articleWord.getEconomyWord().getWord();
//        this.subword = articleWord.getEconomyWord().getSubword();
//        this.wordExpl = articleWord.getEconomyWord().getWordExpl();
//        this.articleId = articleWord.getArticle().getArticleId();

    }
}
