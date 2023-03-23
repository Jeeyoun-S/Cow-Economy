package com.coweconomy.domain.user.entity;

import com.coweconomy.domain.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class UserArticleMemo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("메모 ID")
    private Long memoId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @Comment("회원 ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    @Comment("기사 ID")
    private Article article;

    @NotNull
    @Column(columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    @Comment("메모 작성 시간 : yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regtime;

    @Column(length = 500)
    @Comment("메모 내용")
    private String memoContent;

    @Column
    @Comment("메모 시작 구간")
    private int memoStartRange;

    @Column
    @Comment("메모 종료 구간")
    private int memoEndRange;

    @Column
    @Comment("memoStartRange 속 메모 시작 위치")
    private int memoStartIndex;

    @Column
    @Comment("memoEndRange 속 메모 종료 위치")
    private int memoEndIndex;

    @NotNull
    @Column(columnDefinition = "Boolean default false")
    @Comment("메모 공개 여부 : true-공개, false-비공개")
    private Boolean memoPublicScope;

}
