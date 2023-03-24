package com.coweconomy.domain.article.dto;

import com.coweconomy.domain.article.entity.Article;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ArticleDto {

    // 기사 ID
    private Long articleId;
    
    // 기사 작성일
    private LocalDateTime articleRegtime;

    // 언론사
    private String articlePress;
    
    // 기사 제목
    private String articleTitle;

    public ArticleDto(Article article) {
        this.articleId = article.getArticleId();
        this.articleRegtime = article.getArticleRegtime();
        this.articlePress = article.getArticlePress();
        this.articleTitle = article.getArticleTitle();
    }
}
