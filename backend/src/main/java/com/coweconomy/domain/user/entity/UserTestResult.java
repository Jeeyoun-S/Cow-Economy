package com.coweconomy.domain.user.entity;

import com.coweconomy.domain.article.entity.Article;
import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class UserTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원 별 오늘의 Quiz 결과 ID(seq)")
    private Long testResultId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @Comment("[FK] 회원 ID(seq)")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    @Comment("[FK] 기사 ID(seq)")
    private Article article;

    @NotNull
    @Column(columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    @Comment("Quiz를 푼 시간 [YYYY-mm-DD HH:mm:ss]")
    private LocalDateTime regtime;
}
