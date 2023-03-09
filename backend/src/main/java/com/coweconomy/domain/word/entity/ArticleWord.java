package com.coweconomy.domain.word.entity;

import com.coweconomy.domain.article.entity.Article;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@DynamicInsert
public class ArticleWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleWordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    @NotNull
    private EconomyWord economyWord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @NotNull
    private Article article;

    private Long wordIndex;
}
