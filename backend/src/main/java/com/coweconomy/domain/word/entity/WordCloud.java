package com.coweconomy.domain.word.entity;

import lombok.Getter;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Getter
public class WordCloud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("워드 클라우드 ID")
    private Long wordCloudId;

    @Column(length = 100)
    @Comment("명사")
    private String name;

    @Comment("명사 빈도수")
    private Long value;
}
