package com.coweconomy.domain.user.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@DynamicInsert
public class UserAuthority {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(length = 15)
    @NotNull
    private String authorityName;

}
