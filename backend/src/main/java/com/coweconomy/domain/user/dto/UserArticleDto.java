package com.coweconomy.domain.user.dto;

import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.domain.user.entity.UserArticle;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserArticleDto {

    @Comment("회원이 읽은 기사 ID(seq)")
    private Long userArticleId;
    @Comment("회원이 읽은 기사 ID")
    private Long article;   // article_id
    @Comment("읽은 시간")
    private LocalDateTime regtime;

    public UserArticleDto(UserArticle userArticle) {
        this.userArticleId = userArticle.getUserArticleId();
        this.article = userArticle.getArticle().getArticleId();
        this.regtime = userArticle.getRegtime();
    }
}
