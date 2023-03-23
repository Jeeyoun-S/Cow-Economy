package com.coweconomy.domain.user.entity;

import com.coweconomy.domain.user.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class UserAuthority {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAuthorityId;
    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Comment("권한(USER: 일반사용자, ADMIN: 관리자)")
    @Column(length = 15)
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleType authorityName;

}
