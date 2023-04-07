package com.coweconomy.repository;

import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


    Page<Article> findByArticleIdLessThanAndArticleTitleContainsOrderByArticleIdDesc(Long lastArticleId, String Keyword, PageRequest pageRequest);

    /**
     JPA WITH절, 인라인 적용 불가로 동일 쿼리 반복 작성
     */
    @Query(value = "SELECT * FROM article article, " +
            "      (SELECT a.article_title, min(a.article_id) AS article_id FROM article AS a, " +
            "           (SELECT * FROM ( SELECT *, RANK() OVER (PARTITION BY article_category ORDER BY article_id DESC) AS RN " +
            "                         FROM article WHERE article_title LIKE %:keyword% AND article_id < (CASE WHEN article_category='금융' THEN :finance " +
            "                                                                             WHEN article_category='증권' THEN :stock " +
            "                                                                             WHEN article_category='산업/재계' THEN :industry " +
            "                                                                             WHEN article_category='중기/벤처' THEN :venture " +
            "                                                                             WHEN article_category='부동산' THEN :estate " +
            "                                                                             WHEN article_category = '글로벌 경제' THEN :worldwide " +
            "                                                                             WHEN article_category = '생활경제' THEN :life " +
            "                                                                             WHEN article_category = '경제 일반' THEN :common " +
            "                                                                             END )) AS RANKING " +
            "                   WHERE RANKING.RN <= 7) b " +
            "       WHERE a.article_title = b.article_title " +
            "       GROUP BY a.article_title) min_article, " +
            "      (SELECT * FROM ( SELECT *, RANK() OVER (PARTITION BY article_category ORDER BY article_id DESC) AS RN " +
            "                         FROM article WHERE article_title LIKE %:keyword% AND article_id < (CASE WHEN article_category='금융' THEN :finance " +
            "                                                                             WHEN article_category='증권' THEN :stock " +
            "                                                                             WHEN article_category='산업/재계' THEN :industry " +
            "                                                                             WHEN article_category='중기/벤처' THEN :venture " +
            "                                                                             WHEN article_category='부동산' THEN :estate " +
            "                                                                             WHEN article_category = '글로벌 경제' THEN :worldwide " +
            "                                                                             WHEN article_category = '생활경제' THEN :life " +
            "                                                                             WHEN article_category = '경제 일반' THEN :common " +
            "                                                                             END )) AS RANKING " +
            "                   WHERE RANKING.RN <= 7) b " +
            " WHERE article.article_id = min_article.article_id AND article.article_id = b.article_id", nativeQuery = true)
    List<Article> findByKeywordSearch(@Param("keyword") String keyword, Long finance, Long stock, Long industry, Long venture,
                                      Long estate, Long worldwide, Long life, Long common);

    @Query(value = "SELECT * FROM article article, " +
            "      (SELECT a.article_title, min(a.article_id) AS article_id FROM article AS a, " +
            "           (SELECT * FROM ( SELECT *, RANK() OVER (PARTITION BY article_category ORDER BY article_hits DESC) AS RN " +
            "                         FROM article WHERE article_id < (CASE WHEN article_category='금융' THEN :finance " +
            "                                                               WHEN article_category='증권' THEN :stock " +
            "                                                               WHEN article_category='산업/재계' THEN :industry " +
            "                                                               WHEN article_category='중기/벤처' THEN :venture " +
            "                                                               WHEN article_category='부동산' THEN :estate " +
            "                                                               WHEN article_category = '글로벌 경제' THEN :worldwide " +
            "                                                               WHEN article_category = '생활경제' THEN :life " +
            "                                                               WHEN article_category = '경제 일반' THEN :common " +
            "                                                               END )" +
            "                                                               ORDER BY article_hits DESC) AS RANKING " +
            "                   WHERE RANKING.RN <= 7) b " +
            "       WHERE a.article_title = b.article_title " +
            "       GROUP BY a.article_title) min_article, " +
            "      (SELECT * FROM ( SELECT *, RANK() OVER (PARTITION BY article_category ORDER BY article_hits DESC) AS RN " +
            "                         FROM article WHERE article_id < (CASE WHEN article_category='금융' THEN :finance " +
            "                                                               WHEN article_category='증권' THEN :stock " +
            "                                                               WHEN article_category='산업/재계' THEN :industry " +
            "                                                               WHEN article_category='중기/벤처' THEN :venture " +
            "                                                               WHEN article_category='부동산' THEN :estate " +
            "                                                               WHEN article_category = '글로벌 경제' THEN :worldwide " +
            "                                                               WHEN article_category = '생활경제' THEN :life " +
            "                                                               WHEN article_category = '경제 일반' THEN :common " +
            "                                                               END )" +
            "                                                               ORDER BY article_hits DESC) AS RANKING " +
            "                   WHERE RANKING.RN <= 7) b " +
            " WHERE article.article_id = min_article.article_id AND article.article_id = b.article_id", nativeQuery = true)
    List<Article> findByHowNews(Long finance, Long stock, Long industry, Long venture, Long estate, Long worldwide, Long life, Long common);

    @Query(value = "SELECT * FROM article article, " +
            "      (SELECT a.article_title, min(a.article_id) AS article_id FROM article AS a, " +
            "           (SELECT * FROM ( SELECT *, RANK() OVER (PARTITION BY article_category ORDER BY article_id DESC) AS RN " +
            "                         FROM article WHERE article_id < (CASE WHEN article_category='금융' THEN :finance " +
            "                                                               WHEN article_category='증권' THEN :stock " +
            "                                                               WHEN article_category='산업/재계' THEN :industry " +
            "                                                               WHEN article_category='중기/벤처' THEN :venture " +
            "                                                               WHEN article_category='부동산' THEN :estate " +
            "                                                               WHEN article_category = '글로벌 경제' THEN :worldwide " +
            "                                                               WHEN article_category = '생활경제' THEN :life " +
            "                                                               WHEN article_category = '경제 일반' THEN :common " +
            "                                                               END )) AS RANKING " +
            "                   WHERE RANKING.RN <= 7) b " +
            "       WHERE a.article_title = b.article_title " +
            "       GROUP BY a.article_title) min_article, " +
            "      (SELECT * FROM ( SELECT *, RANK() OVER (PARTITION BY article_category ORDER BY article_id DESC) AS RN " +
            "                         FROM article WHERE article_id < (CASE WHEN article_category='금융' THEN :finance " +
            "                                                               WHEN article_category='증권' THEN :stock " +
            "                                                               WHEN article_category='산업/재계' THEN :industry " +
            "                                                               WHEN article_category='중기/벤처' THEN :venture " +
            "                                                               WHEN article_category='부동산' THEN :estate " +
            "                                                               WHEN article_category = '글로벌 경제' THEN :worldwide " +
            "                                                               WHEN article_category = '생활경제' THEN :life " +
            "                                                               WHEN article_category = '경제 일반' THEN :common " +
            "                                                               END )) AS RANKING " +
            "                   WHERE RANKING.RN <= 7) b " +
            " WHERE article.article_id = min_article.article_id AND article.article_id = b.article_id", nativeQuery = true)
    List<Article> findByRecentNews(Long finance, Long stock, Long industry, Long venture, Long estate, Long worldwide, Long life, Long common);


}