package com.coweconomy.domain.user.entity;

import com.coweconomy.domain.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class UserArticleMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @NotNull
    @Column(columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime regtime;

    @Column(length = 500)
    private String memoContent;

    private int memoStartPoint;

    private int memoEndPoint;

    private int memoScrollPoint;

    @NotNull
    @Column(columnDefinition = "Boolean default false")
    private Boolean memoPublicScope;

    @Builder
    public UserArticleMemo(Long memoId, User user, Article article, LocalDateTime regtime, String memoContent, int memoStartPoint, int memoEndPoint, int memoScrollPoint, boolean memoPublicScope) {
        this.memoId = memoId;
        this.user = user;
        this.article = article;
        this.regtime = regtime;
        this.memoContent = memoContent;
        this.memoStartPoint = memoStartPoint;
        this.memoEndPoint = memoEndPoint;
        this.memoScrollPoint = memoScrollPoint;
        this.memoPublicScope = memoPublicScope;
    }
}
