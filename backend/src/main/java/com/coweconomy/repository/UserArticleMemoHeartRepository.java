package com.coweconomy.repository;

import com.coweconomy.domain.article.entity.MemoHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserArticleMemoHeartRepository extends JpaRepository<MemoHeart, Long> {
}
