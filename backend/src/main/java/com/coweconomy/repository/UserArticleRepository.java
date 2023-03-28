package com.coweconomy.repository;

import com.coweconomy.domain.user.entity.UserArticle;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserArticleRepository extends JpaRepository<UserArticle, Long> {

    /**
     * 회원이 일주일 이내에 읽은 기사 조회
     * @param userId 회원 id(seq)
     * @return List<UserArticle> 회원이 읽은 기사 List
     * **/
    @Query("select ua from UserArticle ua where ua.user.userId= :userId and ua.regtime>= :regtime")
    List<UserArticle> findByArticle(@Param("userId") Long userId, @Param("regtime") LocalDateTime regtime);

    /**
     * userId에 해당되는 6개월 간 읽은 기사 수 조회
     * @param userId 회원 id(seq)
     * @return List<Object[]>
     * **/
    @Query("select date_format(ua.regtime, '%Y-%m'), count(ua) " +
            "from UserArticle ua " +
            "where ua.user.userId= :userId and ua.regtime>= :regtime " +
            "group by date_format(ua.regtime, '%Y-%m') " +
            "order by date_format(ua.regtime, '%Y-%m') asc")
    List<Object[]> findByUserUserIdAndRegtimeBefore(@Param("userId") Long userId, @Param("regtime") LocalDateTime regtime);

    /**
     * userId에 해당되는 1년 간 읽은 기사의 카테고리 조회
     * @param userId 회원 id(seq), year 연
     * @return List<Object[]>
     * **/
    @Query("select distinct ua.article.articleCategory, count(*) " +
            "from UserArticle ua " +
            "where ua.user.userId= :userId " +
            "and ua.regtime >= :startOfYear and ua.regtime < :startOfNextYear " +
            "group by ua.article.articleCategory")
    List<Object[]> findArticleCategoryByUserIdAndYear(@Param("userId") Long userId, @Param("startOfYear") LocalDateTime startOfYear, @Param("startOfNextYear") LocalDateTime startOfNextYear);

}
