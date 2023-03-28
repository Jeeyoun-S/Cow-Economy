package com.coweconomy.repository;

import com.coweconomy.domain.article.entity.Article;
import com.coweconomy.domain.word.entity.EconomyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EconomyWordRepository extends JpaRepository<EconomyWord, Long> {

    EconomyWord findByWordId(Long wordId);
}