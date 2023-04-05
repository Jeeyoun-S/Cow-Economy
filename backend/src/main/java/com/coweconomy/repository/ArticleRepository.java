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

//    @Query(value = "SELECT * FROM ( SELECT *, RANK() OVER (PARTITION BY a.article_category ORDER BY a.article_id DESC) AS RN " +
//                     "FROM article a WHERE a.article_title like %:keyword% and a.article_id < :finance ) AS RANKING " +
//                    "WHERE RANKING.RN <= 7 " +
//                    "ORDER BY article_id desc", nativeQuery = true)
    @Query(value = "SELECT * FROM ( SELECT *, RANK() OVER (PARTITION BY a.article_category ORDER BY a.article_id DESC) AS RN " +
            "            FROM article a WHERE a.article_title like %:keyword% and a.article_id<(case when a.article_category='금융' " +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t then :finance " +
            "                                                                                     when a.article_category='증권' " +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t then :stock " +
            "                                                                                     when a.article_category='산업/재계' " +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t then :industry " +
            "                                                                                     when a.article_category='중기/벤처' " +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t then :venture " +
            "                                                                                     when a.article_category='부동산' " +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t then :estate " +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t when a.article_category = '글로벌 경제' " +
            "                                                                                     then :worldwide " +
            "                                                                                     when a.article_category = '생활경제' " +
            "                                                                                     then :life " +
            "                                                                                     when a.article_category = '경제 일반' " +
            "                                                                                     then :common " +
            "                                                                                     end ) ) AS RANKING " +
            "            WHERE RANKING.RN <= 7", nativeQuery = true)
    List<Article> findByKeywordSearch(@Param("keyword") String keyword, Long finance, Long stock, Long industry, Long venture,
                                      Long estate, Long worldwide, Long life, Long common);
}