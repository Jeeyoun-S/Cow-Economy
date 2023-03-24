package com.coweconomy.domain.user.entity;

import com.coweconomy.api.request.QuizResultRequestDto;
import com.coweconomy.domain.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
//    private LocalDateTime regtime;
    private LocalDateTime regtime;

    @NotNull
    @Column(columnDefinition = "Boolean default false")
    @Comment("Quiz 결과 : true-성공(1), false-실패(0)")
    private Boolean testResultFlag;

    public UserTestResult(User user, Article article, QuizResultRequestDto quizResultRequestDto) {
        this.user = user;
        this.article = article;
        this.testResultFlag = quizResultRequestDto.getIsPassFlag();
    }

    @PrePersist
    public void prePersist() {
        if (this.regtime == null) {
            this.regtime = LocalDateTime.now();
        }
    }
}
