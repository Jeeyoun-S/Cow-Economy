package com.coweconomy.domain.user.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@DynamicInsert
public class UserArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userArticleId;
}
