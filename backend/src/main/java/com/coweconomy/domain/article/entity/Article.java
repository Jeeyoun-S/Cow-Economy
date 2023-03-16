package com.coweconomy.domain.article.entity;

import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import com.coweconomy.domain.user.entity.UserTestResult;
import com.coweconomy.domain.word.entity.ArticleWord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Long articleId;

    @Column(length = 30)
    @NotNull
    private String articleCategory;

    @NotNull
    @Column(columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime articleRegtime;

    @Column(length = 30)
    private String articleEditor;

    @Column(length = 30)
    @NotNull
    private String articlePress;

    @Column(length = 100)
    @NotNull
    private String articleTitle;

    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull
    private String articleContent;

    @Column(length = 500)
    @NotNull
    private String articleUrl;

    @Column(length = 500)
    private String articleThumbnail;

    @NotNull
    @Column(columnDefinition = "int default 0")
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
