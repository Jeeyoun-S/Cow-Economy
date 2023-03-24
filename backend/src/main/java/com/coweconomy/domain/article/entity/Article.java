package com.coweconomy.domain.article.entity;

import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.domain.user.entity.UserTestResult;
import com.coweconomy.domain.word.entity.ArticleWord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("기사 ID")
    private Long articleId;

    @Column(length = 30)
    @NotNull
    @Comment("기사 카테고리 : 금융, 증권, 산업/재계, 중기/벤처, 부동산, 글로벌 경제, 생활경제, 경제 일반")
    private String articleCategory;

    @NotNull
    @Column(columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    @Comment("기사 작성일 : yyyyMMdd")
    private LocalDateTime articleRegtime;

    @Column(length = 30)
    @Comment("기자명")
    private String articleEditor;

    @Column(length = 30)
    @NotNull
    @Comment("언론사")
    private String articlePress;

    @Column(length = 100)
    @NotNull
    @Comment("기사 제목")
    private String articleTitle;

    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull
    @Comment("기사 내용")
    private String articleContent;

    @Column(length = 500)
    @NotNull
    @Comment("기사 URL")
    private String articleUrl;

    @Column(length = 500)
    @Comment("기사 썸네일")
    private String articleThumbnail;

    @NotNull
    @Column(columnDefinition = "int default 0")
    @Comment("기사 조회수")
    private int articleHits;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<UserArticle> userArticleList = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<UserArticleMemo> userArticleMemoList = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<UserTestResult> userTestResultList = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleWord> articleWordList = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<RelatedArticle> relatedArticleList = new ArrayList<>();

}
