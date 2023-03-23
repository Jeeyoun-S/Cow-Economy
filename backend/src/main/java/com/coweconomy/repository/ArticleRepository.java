package com.coweconomy.repository;

import com.coweconomy.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * article Id로 Article Table에서 값 찾기
     * @param articleId
     * @return Optional<Article>
     * **/
    Optional<Article> findByArticleId(Long articleId);
}