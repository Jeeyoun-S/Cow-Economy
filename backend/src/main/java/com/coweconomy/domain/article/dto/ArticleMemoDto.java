package com.coweconomy.domain.article.dto;

import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.dto.UserArticleDto;
import com.coweconomy.domain.user.dto.UserArticleMemoDto;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ArticleMemoDto {

    // 기사 ID
    private Long articleId;

    // 기사 카테고리
    private String articleCategory;
    
    // 기사 제목
    private String articleTitle;
    
    // 기사에 해당하는 메모 리스트
    List<UserArticleMemoDto> memoList;

    public ArticleMemoDto(Article article, List<UserArticleMemo> userArticleMemoList) {
        this.articleId = article.getArticleId();
        this.articleCategory = article.getArticleCategory();
        this.articleTitle = article.getArticleTitle();
        this.memoList = userArticleMemoList.stream().map(m -> new UserArticleMemoDto(m)).collect(Collectors.toList());
    }
}
