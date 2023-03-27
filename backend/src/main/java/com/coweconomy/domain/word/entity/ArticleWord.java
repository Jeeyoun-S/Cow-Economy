package com.coweconomy.domain.word.entity;

import com.coweconomy.domain.article.entity.Article;
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
public class ArticleWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("기사 출현 경제 용어 ID")
    private Long articleWordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @Comment("기사 ID")
    private Article article;

    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull
    @Comment("관련 경제 용어 ID 리스트")
    private String subWordId;
}
