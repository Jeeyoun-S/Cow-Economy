package com.coweconomy.repository;

import com.coweconomy.domain.user.entity.UserArticle;
import com.coweconomy.domain.word.entity.ArticleWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleWordRepository extends JpaRepository<ArticleWord, Long> {

    /**
     * 읽은 기사에 있는 경제 단어 List 조회
     //     * @param Long<UserArticleDto> 읽은 기사 List
     * @return List<ArticleWordQuizDto> 읽은 기사 안에 있는 경제 단어
     * **/
    @Query("select aw from ArticleWord aw where aw.article.articleId in :articleId")
    List<ArticleWord> findByArticleIn(@Param("articleId") List<Long> articleId);
}
