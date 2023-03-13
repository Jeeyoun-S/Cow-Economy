package com.coweconomy.repository;

import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.user.entity.UserArticleMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserArticleMemoRepository extends JpaRepository<UserArticleMemo, Long> {

}
