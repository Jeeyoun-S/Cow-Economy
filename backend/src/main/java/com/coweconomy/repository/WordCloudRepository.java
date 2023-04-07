package com.coweconomy.repository;

import com.coweconomy.domain.word.entity.WordCloud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordCloudRepository extends JpaRepository<WordCloud, Long> {
}
