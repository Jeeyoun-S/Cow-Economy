package com.coweconomy.domain.user.entity;

import com.coweconomy.domain.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class UserArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원이 읽은 기사 ID")
    private Long userArticleId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @Comment("회원 ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    @Comment("기사 ID")
    private Article article;

    @NotNull
    @Column(columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    @Comment("기사를 읽은 시간 : yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regtime;
}
