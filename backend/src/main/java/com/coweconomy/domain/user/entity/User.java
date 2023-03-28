package com.coweconomy.domain.user.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원 ID(seq)")
    private Long userId;

    @Column(length = 50, unique=true)
    @NotNull
    @Comment("회원 이메일 [로그인 시 ID 역할]")
    private String userEmail;

    @Column(length = 30)
    @NotNull
    @Comment("회원 닉네임 [카카오 계정의 닉네임]")
    private String userNickname;

    @NotNull
    @Comment("회원 레벨 [1-6 레벨까지 있음, 1레벨 = F등급, 6레벨 = S등급]")
    private int userLevel;

    @NotNull
    @Comment("회원 경험치")
    private int userExperience;

    @Column(length = 300)
    @Comment("로그인 시 회원 refrash_token")
    private String userToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserArticle> userArticleList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserArticleMemo> userArticleMemoList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTestResult>  userTestResultList = new ArrayList<>();

    public void increaseExperience() {
        this.userExperience = this.userExperience + 1;
        
        // 경험치 증가에 따른 레벨 조절
        if (this.userExperience > 500) this.userLevel = 2;
        else if (this.userExperience > 1000) this.userLevel = 3;
        else if (this.userExperience > 1500) this.userLevel = 4;
        else if (this.userExperience > 2500) this.userLevel = 5;
        else if (this.userExperience > 3500) this.userLevel = 6;
        else if (this.userExperience > 5000) this.userLevel = 7;
    }
}
