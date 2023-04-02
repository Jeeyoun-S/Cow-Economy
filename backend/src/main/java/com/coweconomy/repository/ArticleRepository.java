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
    List <Article> findByTodayArticles();
}