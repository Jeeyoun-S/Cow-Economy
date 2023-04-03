package com.coweconomy.repository;

import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // #21# TEST용
    Article findByArticleId(Long articleId);
    @Query(value = "select * from article a where date(a.article_regtime) = date(now()) order by article_hits desc limit 10", nativeQuery = true)
    List <Article> findByTodayHotArticles();

    @Query(value="SELECT * FROM ( SELECT *, RANK() OVER (PARTITION BY a.article_category ORDER BY a.article_hits DESC, a.article_id DESC) AS RN " +
            "FROM article a WHERE date(a.article_regtime) = date(now()) ) AS RANKING " +
            "WHERE RANKING.RN <= 5", nativeQuery = true)
    List <Article> findByCategoryHotArticle();
    @Query(value="SELECT * FROM ( SELECT *, RANK() OVER (PARTITION BY a.article_category ORDER BY a.article_regtime DESC, a.article_id DESC) AS RN " +
            "FROM article a WHERE date(a.article_regtime) = date(now()) ) AS RANKING " +
            "WHERE RANKING.RN <= 5", nativeQuery = true)
    List <Article> findByCategoryRecentArticle();


;}