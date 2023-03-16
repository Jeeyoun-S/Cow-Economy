package com.coweconomy.repository;

import com.coweconomy.domain.user.entity.UserArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserArticleRepository extends JpaRepository<UserArticle, Long> {

    @Query("select ua from UserArticle ua where ua.user.userId= :userId and ua.regtime>= :regtime")
//    @Query("select ua from UserArticle ua where ua.user.userId= :userId")
//    List<UserArticle> findByArticle(@Param("userId") Long userId);
    List<UserArticle> findByArticle(@Param("userId") Long userId, @Param("regtime") Date regtime);
//    List<UserArticle> findByUserArticleId(@Param("userId") Long userId, @Param("oneWeekAgo") Date oneWeekAgo);
}
