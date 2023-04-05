package com.coweconomy.domain.word.entity;

import lombok.Getter;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
public class WordCloud {
    @Id
    @Column(length = 100)
    @Comment("명사 (ID)")
    private String name;

    @Comment("명사 빈도수")
    @NotNull
    private Long value;


}
