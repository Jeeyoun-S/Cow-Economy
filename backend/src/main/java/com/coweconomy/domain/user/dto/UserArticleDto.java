package com.coweconomy.domain.user.dto;

import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticle;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserArticleDto {

    private Long userArticleId;
    private Long article;   // article_id
    private LocalDateTime regtime;

    public UserArticleDto(UserArticle userArticle) {
        this.userArticleId = userArticle.getUserArticleId();
        this.article = userArticle.getArticle().getArticleId();
        this.regtime = userArticle.getRegtime();
    }
}
