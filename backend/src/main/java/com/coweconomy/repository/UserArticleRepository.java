package com.coweconomy.repository;

import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.word.dto.ArticleWordQuizDto;
import com.coweconomy.domain.word.entity.ArticleWord;
import com.coweconomy.domain.word.entity.EconomyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface UserArticleRepository extends JpaRepository<UserArticle, Long> {

    /**
     * 회원이 일주일 이내에 읽은 기사 조회
     * @param userId 조회할 회원 ID
     * @return List<UserArticle> 회원이 읽은 기사 List
     * **/
    @Query("select ua from UserArticle ua where ua.user.userId= :userId and ua.regtime>= :regtime")
    List<UserArticle> findByArticle(@Param("userId") Long userId, @Param("regtime") LocalDateTime regtime);

    /**
     * 읽은 기사에 있는 경제 단어 List 조회
     * @param List<UserArticleDto> 읽은 기사 List
     * @return List<ArticleWordQuizDto> 읽은 기사 안에 있는 경제 단어
     * **/
    @Query("select aw from ArticleWord aw where aw.article.articleId in :articleId")
    List<ArticleWord> findByArticleIn(@Param("articleId") List<Long> articleId);

}
