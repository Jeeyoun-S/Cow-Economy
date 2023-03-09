package com.coweconomy.domain.user.entity;

import com.coweconomy.domain.article.entity.Article;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicInsert
public class UserArticleMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @NotNull
    private Article article;

    @NotNull
    private LocalDateTime regtime;

    @Column(length = 500)
    private String memoContent;

    private int memoStartPoint;

    private int memoEndPoint;

    @NotNull
    private int memoPublicScope;
}
