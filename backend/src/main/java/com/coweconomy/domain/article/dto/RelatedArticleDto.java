package com.coweconomy.domain.article.dto;

import lombok.Data;

@Data
public class RelatedArticleDto {
    //기사 아이디
    private Long articleId;
    //기사 제목
    private String articleTitle;
    //기사 썸네일
    private String articleThumbnail;

    public RelatedArticleDto(Long articleId, String articleTitle, String articleThumbnail) {
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.articleThumbnail = articleThumbnail;
    }
}
