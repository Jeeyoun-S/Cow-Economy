package com.coweconomy.repository;

import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserArticleMemoRepository extends JpaRepository<UserArticleMemo, Long> {

    /**
     * memo id로 memo table에서 값 찾기
     * @param memoId
     * @return UserArticleMemo
     * **/
    Optional<UserArticleMemo> findByMemoId(Long memoId);
}
