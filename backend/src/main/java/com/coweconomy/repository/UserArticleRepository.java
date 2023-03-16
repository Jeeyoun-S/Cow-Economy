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

//    @Query("select ua from UserArticle ua where ua.user.userId= :userId")
    @Query("select ua from UserArticle ua where ua.user.userId= :userId and ua.regtime>= :oneWeekAgo")
    List<UserArticle> findByUserArticleId(@Param("userId") Long userId, @Param("oneWeekAgo") Date oneWeekAgo);
}
