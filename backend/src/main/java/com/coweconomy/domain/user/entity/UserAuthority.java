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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원 권한 ID(seq)")
    private Long userAuthorityId;
    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    @Comment("[FK] 회원 ID(seq)")
    private User user;

    @Column(length = 15)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Comment("권한 이름 [USER: 일반사용자, ADMIN: 관리자]")
    private RoleType authorityName;

}
