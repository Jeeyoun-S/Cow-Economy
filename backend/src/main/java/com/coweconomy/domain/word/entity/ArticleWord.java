package com.coweconomy.domain.word.entity;

import com.coweconomy.domain.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

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
    @JoinColumn(name = "word_id")
    @Comment("경제 용어 ID")
    private EconomyWord economyWord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @Comment("기사 ID")
    private Article article;

    @Comment("기사 내 단어 시작 위치")
    private Long wordIndex;


}
