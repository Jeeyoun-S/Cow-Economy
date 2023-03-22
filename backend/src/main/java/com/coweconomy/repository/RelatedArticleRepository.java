package com.coweconomy.repository;

import com.coweconomy.domain.article.entity.RelatedArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelatedArticleRepository extends JpaRepository <RelatedArticle, Long> {
    /**
     * related Article Id로 related_article Table에서 값 찾기
     * @param relatedArticleId
     * @return Optional<RelatedArticle>
     */
    Optional<RelatedArticle> findByRelatedArticleId(Long relatedArticleId);
}
