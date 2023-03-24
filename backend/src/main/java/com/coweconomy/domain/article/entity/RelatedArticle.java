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
    @Comment("관련 기사 ID")
    private Long relatedArticleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @Comment("기사 ID")
    private Article article;

    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull
    @Comment("관련 기사 ID 6개 : 1,2,3,4,5,6")
    private String subArticleId;

}
