package com.coweconomy.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 50, unique=true)
    @NotNull
    private String userEmail;

    @Column(length = 30)
    @NotNull
    private String userNickname;

    @NotNull
    private int userLevel;

    @NotNull
    private int userExperience;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserArticle> userArticleList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserArticleMemo> userArticleMemoList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTestResult>  userTestResultList = new ArrayList<>();
}
