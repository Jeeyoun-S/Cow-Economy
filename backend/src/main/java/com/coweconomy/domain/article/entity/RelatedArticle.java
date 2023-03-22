package com.coweconomy.domain.article.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class RelatedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relatedArticleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Comment("관련 기사 6개 아이디")
    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull
    private String subArticleId;
}
