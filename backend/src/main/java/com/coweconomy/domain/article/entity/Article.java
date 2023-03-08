package com.coweconomy.domain.article.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@Data
@DynamicInsert
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(length = 30)
    @NotNull
    private String articleCategory;
}
